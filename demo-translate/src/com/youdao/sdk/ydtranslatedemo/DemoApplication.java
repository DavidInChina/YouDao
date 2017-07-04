/**
 * @(#)DemoApplication.java, 2015年4月3日. Copyright 2012 Yodao, Inc. All rights
 * reserved. YODAO PROPRIETARY/CONFIDENTIAL. Use is
 * subject to license terms.
 */
package com.youdao.sdk.ydtranslatedemo;

import android.app.Application;

import com.youdao.sdk.app.YouDaoApplication;

/**
 * @author lukun
 */
public class DemoApplication extends Application {

    private static DemoApplication youAppction;
    private String appKey = "";

    @Override
    public void onCreate() {
        super.onCreate();
        
        YouDaoApplication.init(this, appKey);//创建应用，每个应用都会有一个Appid，绑定对应的翻译服务实例，即可使用
        youAppction = this;
    }

    public static DemoApplication getInstance() {
        return youAppction;
    }

}
