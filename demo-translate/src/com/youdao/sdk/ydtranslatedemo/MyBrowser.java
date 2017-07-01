/**
 * @(#)MyWebView.java, 2015-8-28. Copyright 2015 Yodao, Inc. All rights
 *                     reserved. YODAO PROPRIETARY/CONFIDENTIAL. Use is subject
 *                     to license terms.
 */
package com.youdao.sdk.ydtranslatedemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * @author baijing
 */
public class MyBrowser extends Activity {
    /*
     * (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    //1....必须使用YouDaoWebView
    private WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String url = intent.getStringExtra("deeplink");

        // myWebView = new YouDaoWebView(this);
        // setContentView(myWebView);
        
        setContentView(R.layout.browser);
        myWebView = (WebView) findViewById(R.id.webView1);

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);
        myWebView.loadUrl(url);

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        //3........该方法必须调用，否则统计数据不正常
        myWebView.destroy();
    }

    @Override
    public void finish() {
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        if (view != null) {
            view.removeAllViews();
        }
        super.finish();
    }

}
