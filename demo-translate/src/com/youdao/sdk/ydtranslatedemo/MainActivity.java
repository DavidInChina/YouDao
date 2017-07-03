package com.youdao.sdk.ydtranslatedemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity {
    private Handler mHandler = new Handler();

    TextView startWelcomeCopyright;

    int alpha = 255;

    int b = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View startBtn = findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                TranslateForwardHelper.toTranslateActivity(MainActivity.this);
            }
        });

        View startBtn1 = findViewById(R.id.startBtn1);
        startBtn1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                TranslateForwardHelper.toHanyuActivity(MainActivity.this);
            }
        });

        View startBtn2 = findViewById(R.id.startBtn2);
        startBtn2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                TranslateForwardHelper
                        .toTranslateOfflineWordActivity(MainActivity.this);
            }
        });

        View startBtn3 = findViewById(R.id.startBtn3);
        startBtn3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                TranslateForwardHelper
                        .toTranslateOfflineLineActivity(MainActivity.this);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
