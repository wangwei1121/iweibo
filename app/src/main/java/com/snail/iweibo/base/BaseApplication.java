package com.snail.iweibo.base;
import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * BaseApplication
 * Created by alexwan on 16/3/30.
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
