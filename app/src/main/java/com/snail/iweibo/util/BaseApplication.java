package com.snail.iweibo.util;

import android.app.Application;
import android.content.Context;

/**
 * Created by wang.weib on 2016/5/4.
 */
public class BaseApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }
}
