package com.youdao.sdk.ydtranslatedemo;

/**
 * @(#)ListenForwardHelper.java, 2015�?4�?4�?. Copyright 2012 Yodao, Inc. All
 *                               rights reserved. YODAO
 *                               PROPRIETARY/CONFIDENTIAL. Use is subject to
 *                               license terms.
 */

import android.app.Activity;
import android.content.Intent;

/**
 * @author lukun 跳转
 */
public class TranslateForwardHelper {

    public static void toTranslateActivity(Activity activity) {
        Intent in = new Intent(activity, TranslateActivity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivityForResult(in, 0);
        activity.overridePendingTransition(R.anim.listen_activity_open_in_anim,
                R.anim.listen_activity_open_out_anim);

    }
    
    public static void toHanyuActivity(Activity activity) {
        Intent in = new Intent(activity, HanyuActivity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivityForResult(in, 0);
        activity.overridePendingTransition(R.anim.listen_activity_open_in_anim,
                R.anim.listen_activity_open_out_anim);

    }
    
    public static void toTranslateOfflineWordActivity(Activity activity) {
        Intent in = new Intent(activity, TranslateOfflineWordActivity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivityForResult(in, 0);
        activity.overridePendingTransition(R.anim.listen_activity_open_in_anim,
                R.anim.listen_activity_open_out_anim);

    }
    
    public static void toTranslateOfflineLineActivity(Activity activity) {
        Intent in = new Intent(activity, TranslateOfflineLineActivity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivityForResult(in, 0);
        activity.overridePendingTransition(R.anim.listen_activity_open_in_anim,
                R.anim.listen_activity_open_out_anim);

    }
    
    public static void toBrowser(Activity activity, String url) {
        Intent in = new Intent(activity, MyBrowser.class);
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        in.putExtra("deeplink", url);
        activity.startActivityForResult(in, 0);
        activity.overridePendingTransition(R.anim.listen_activity_open_in_anim,
                R.anim.listen_activity_open_out_anim);

    }
    

}
