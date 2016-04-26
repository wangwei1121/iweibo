package com.snail.iweibo.service;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.tencent.smtt.sdk.QbSdk;

/**
 * TencentX5Service
 * Created by alexwan on 16/4/26.
 */
public class TencentX5Service extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        // 这里必须启用非主进程的service来预热X5内核
        QbSdk.preInit(this);
        super.onCreate();
    }
}
