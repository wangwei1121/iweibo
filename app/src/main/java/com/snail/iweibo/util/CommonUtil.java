package com.snail.iweibo.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.sina.weibo.sdk.api.share.Base;

/**
 * Created by wang.weib on 2016/5/6.
 */
public class CommonUtil {
    public static int getScreenWidth(){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return metrics.widthPixels;
    }

    public static int getScreenHeight(){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return metrics.heightPixels;
    }
}
