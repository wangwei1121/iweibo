package com.snail.iweibo.base;
import android.app.Application;
import android.content.Intent;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.snail.iweibo.service.TencentX5Service;

/**
 * BaseApplication
 * Created by alexwan on 16/3/30.
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        preinitX5WithService();
    }

    /**
     * 开启额外进程 服务 预加载X5内核， 此操作必须在主进程调起X5内核前进行，否则将不会实现预加载
     */
    private void preinitX5WithService() {
        Intent intent = new Intent(this, TencentX5Service.class);
        startService(intent);
    }
}
