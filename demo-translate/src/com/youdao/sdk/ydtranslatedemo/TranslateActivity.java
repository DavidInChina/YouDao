package com.youdao.sdk.ydtranslatedemo;

/**
 * @(#)BrowserActivity.java, 2013年10月22日. Copyright 2013 Yodao, Inc. All
 * rights reserved. YODAO PROPRIETARY/CONFIDENTIAL.
 * Use is subject to license terms.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.youdao.sdk.app.Language;
import com.youdao.sdk.app.LanguageUtils;
import com.youdao.sdk.ydonlinetranslate.TranslateErrorCode;
import com.youdao.sdk.ydonlinetranslate.TranslateListener;
import com.youdao.sdk.ydonlinetranslate.TranslateParameters;
import com.youdao.sdk.ydonlinetranslate.Translator;
import com.youdao.sdk.ydtranslate.Translate;
import com.youdao.sdk.ydtranslatedemo.utils.SwListDialog;
import com.youdao.sdk.ydtranslatedemo.utils.SwListDialog.ItemListener;
import com.youdao.sdk.ydtranslatedemo.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lukun
 */
public class TranslateActivity extends Activity {

    // 查询列表
    private ListView translateList;

    private TranslateAdapter adapter;

    private List<TranslateData> list = new ArrayList<TranslateData>();

    private ProgressDialog progressDialog = null;

    private Handler waitHandler = new Handler();

    private EditText fanyiInputText;

    private InputMethodManager imm;

    private TextView fanyiBtn;

    TextView languageSelectFrom;

    TextView languageSelectTo;

    private Translator translator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(Activity.RESULT_OK);
        try {
            getWindow().requestFeature(Window.FEATURE_PROGRESS);
            getWindow().setFeatureInt(Window.FEATURE_PROGRESS,
                    Window.PROGRESS_VISIBILITY_ON);
        } catch (Exception e) {
        }
        setContentView(R.layout.translate_list);
        initWeight();
        query();
    }

    /**
     * 初始化控件
     */
    public void initWeight() {
        fanyiInputText = (EditText) findViewById(R.id.fanyiInputText);

        fanyiBtn = (TextView) findViewById(R.id.fanyiBtn);

        translateList = (ListView) findViewById(R.id.commentList);

        imm = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);

        View view = this.getLayoutInflater().inflate(R.layout.translate_head,
                translateList, false);
        translateList.addHeaderView(view);
        adapter = new TranslateAdapter(this, list);
        translateList.setAdapter(adapter);
        fanyiBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                query();
            }
        });

        languageSelectFrom = (TextView) findViewById(R.id.languageSelectFrom);
        languageSelectFrom.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                selectLanguage(languageSelectFrom);
            }
        });
        languageSelectTo = (TextView) findViewById(R.id.languageSelectTo);
        languageSelectTo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                selectLanguage(languageSelectTo);
            }
        });
    }

    private void selectLanguage(final TextView languageSelect) {
        final String str[] = LanguageUtils.langs;
        List<String> items = new ArrayList<String>();
        for (String s : str) {
            items.add(s);
        }
        SwListDialog exitDialog = new SwListDialog(TranslateActivity.this,
                items);
        exitDialog.setItemListener(new ItemListener() {

            @Override
            public void click(int position, String title) {

                String language = languageSelect.getText().toString();
                languageSelect.setText(title);
                String from = languageSelectFrom.getText().toString();
                String to = languageSelectTo.getText().toString();

                if (!from.contains("中文") && !to.contains("中文")
                        && !to.contains("自动") && !from.contains("自动")) {
                    ToastUtils.show("源语言或者目标语言其中之一必须为中文");
                    languageSelect.setText(language);
                    return;
                }
            }
        });
        exitDialog.show();
    }

    private void query() {
        // 源语言或者目标语言其中之一必须为中文,目前只支持中文与其他几个语种的互译
        String from = languageSelectFrom.getText().toString();
        String to = languageSelectTo.getText().toString();
        String input = fanyiInputText.getText().toString();
        Language langFrom = LanguageUtils.getLangByName(from);
        // 若设置为自动，则查询自动识别源语言，自动识别不能保证完全正确，最好传源语言类型
        // Language langFrosm = LanguageUtils.getLangByName("自动");
        Language langTo = LanguageUtils.getLangByName(to);
        TranslateParameters tps = new TranslateParameters.Builder()
                .source("youdao").from(langFrom).to(langTo).build();// appkey可以省略
        translator = Translator.getInstance(tps);
        showLoadingView("正在查询");
        translator.lookup(input, new TranslateListener() {
            @Override
            public void onResult(Translate result, String input) {
                TranslateData td = new TranslateData(
                        System.currentTimeMillis(), result);
                list.add(td);
                adapter.notifyDataSetChanged();
                translateList.setSelection(list.size() - 1);
                dismissLoadingView();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        imm.hideSoftInputFromWindow(
                                fanyiInputText.getWindowToken(), 0);
                    }
                }, 100);
                fanyiInputText.setText("");
            }

            @Override
            public void onError(TranslateErrorCode error) {
                ToastUtils.show("查询错误:" + error.name());
                dismissLoadingView();
            }
        });
    }

    private void showLoadingView(final String text) {
        waitHandler.post(new Runnable() {

            @Override
            public void run() {
                if (progressDialog != null && !progressDialog.isShowing()) {
                    progressDialog.setMessage(text);
                    progressDialog.show();
                }
            }
        });

    }

    private void dismissLoadingView() {
        waitHandler.post(new Runnable() {

            @Override
            public void run() {
                if (progressDialog != null && progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });

    }

    public void postQuery(final Translate bean) {
        showLoadingView("正在翻译，请稍等");
    }

    public void loginBack(View view) {
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void finish() {
        super.finish();
    }

}
