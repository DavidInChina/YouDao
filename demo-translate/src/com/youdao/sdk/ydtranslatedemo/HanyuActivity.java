package com.youdao.sdk.ydtranslatedemo;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.youdao.sdk.app.WordHelper;
import com.youdao.sdk.chdict.ChDictTranslate;
import com.youdao.sdk.chdict.ChDictor;
import com.youdao.sdk.chdict.ChdictMeans;
import com.youdao.sdk.chdict.DictListener;
import com.youdao.sdk.chdict.ExamLine;
import com.youdao.sdk.ydtranslatedemo.utils.ToastUtils;

public class HanyuActivity extends Activity {
    private ChDictor chDictor = null;

    private EditText editText;

    private Button startBtn;

    private TextView textView,moreBtn;
    
    private Handler handler =new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hanyu);
        editText = (EditText) findViewById(R.id.editext);
        startBtn = (Button) findViewById(R.id.startBtn);
        textView = (TextView) findViewById(R.id.textView);
        startBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    lookup();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    // 初始化之前，请保证离线文件存储在Environment.getExternalStorageDirectory().getPath()
                    // + "/yuwen/backup/"路径下
                    ToastUtils.show("初始化失败");
                }
            }

        });
        moreBtn = (TextView) findViewById(R.id.moreBtn);
        moreBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                //sdk自动封装了deeplink调用逻辑
//                WordHelper.openMore(HanyuActivity.this,text);
                //注意，若用户没安装有道词典，开发者可自己实现deeplink的跳转
                if(!WordHelper.openDeepLink(HanyuActivity.this, "吃饭家生")){
                	 //获取deeplink链接
                    String deeplinkUrl = WordHelper.getWordDetailUrl("hello");
                    //处理deeplink链接，可通过自定义浏览器打开
                    TranslateForwardHelper.toBrowser(HanyuActivity.this, deeplinkUrl);
                }
            }
        });


        /*
         * 可以查词之前设置离线词库路径，则采用默认路径Environment
         * .getExternalStorageDirectory().getPath() + "/yuwen/backup/"
         * 请确保设置的路径或者默认路径下包含汉语词典的离线包
         */
         chDictor = new ChDictor();
//        chDictor = new ChDictor("/update");
        
         moreBtn.setVisibility(View.GONE);

    }

    private void lookup() throws IOException {
        // TODO Auto-generated method stub
        // 注意，每次查询之前都需要初始化
        chDictor.init();
        String text = editText.getText().toString();
        if (!TextUtils.isEmpty(text)) {
            chDictor.lookup(text, new DictListener() {

                @Override
                public void onResult(final List<ChDictTranslate> chTs) {
                    // 注意：回调是在子线程中进行，拿到查询结果之后若有更新UI操作，请切换到主线程执行
                	handler.post(new Runnable(){
                		public void run(){
                			showResult(chTs);
                		}
                	});
                }

                @Override
                public void onError(String q, String info) {
                	
                }
            });
            return;
        }
        ToastUtils.show("请输入要查询的词");
    }
    
    public void showResult(final List<ChDictTranslate> chTs){

        String text = "查询失败";
        StringBuilder sb = new StringBuilder();
        for(ChDictTranslate chT:chTs){
        	if (chT.success()) {
                sb.append("<p>单词：" + chT.getQuery() + "<br/>");
                sb.append("<p>发音：" + chT.getPhone() + "<br/>");
                List<ChdictMeans> list = chT.getTranslations();
                if (list != null) {
                    sb.append("<p>含义如下：");
                    for (ChdictMeans chdictMeans: list) {
                        String trs = chdictMeans.getTranslate();
                        sb.append("<br/><br/>解释：" + trs
                                + "<br/>例句：");
                        List<ExamLine> lineList = chdictMeans
                                .getExamLines();
                        if (lineList == null) {
                            continue;
                        }
                        for (ExamLine line: lineList) {
                            String texts = line.getText();
                            String span = null;
                            if (line.isHighlight()) {
                                span = "<span><font color=\"#ff0000\">"
                                        + texts
                                        + "</font></span>";
                            } else {
                                span = "<span><font color=\"#808080\">"
                                        + texts
                                        + "</font></span>";
                            }
                            sb.append(span);
                        }
                    }
                }
                sb.append("</p>");
                moreBtn.setVisibility(View.VISIBLE);
            }
        }
        text = sb.toString();
        textView.setText(Html.fromHtml(text));
    
    }
    
    public void loginBack(View view) {
        this.finish();
    }
    
}

