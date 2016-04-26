package com.snail.iweibo.ui.activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;

import com.snail.iweibo.mvp.view.impl.ISnailWebView;
import com.snail.iweibo.ui.base.BasePresenterActivity;
import com.tencent.smtt.sdk.QbSdk;

/**
 * SnailWebViewActivity
 * Created by alexwan on 16/4/26.
 */
public class SnailWebViewActivity extends BasePresenterActivity<ISnailWebView> {
    public static void start(Context context , String url){
        Intent intent = new Intent(context , SnailWebViewActivity.class);
        intent.putExtra("url" , url);
        context.startActivity(intent);
    }


    @Override
    protected void onBindView() {
        super.onBindView();
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        preinitX5WebCore();
        String url = getIntent().getStringExtra("url");

        view.loadUrl(url);
    }

    @Override
    protected void onDestroyView() {
        super.onDestroyView();
        view.destroyView();
    }

    @Override
    protected Class<ISnailWebView> getViewClass() {
        return ISnailWebView.class;
    }

    /**
     * X5内核在使用preinit接口之后，对于首次安装首次加载没有效果
     * 实际上，X5webview的preinit接口只是降低了webview的冷启动时间；
     * 因此，现阶段要想做到首次安装首次加载X5内核，必须要让X5内核提前获取到内核的加载条件
     */
    private void preinitX5WebCore(){
        if(!QbSdk.isTbsCoreInited()){//preinit只需要调用一次，如果已经完成了初始化，那么就直接构造view
            QbSdk.preInit(this);//设置X5初始化完成的回调接口  第三个参数为true：如果首次加载失败则继续尝试加载；
        }else{
//            handler.sendEmptyMessage(MSG_WEBVIEW_CONSTRUCTOR);
        }
    }

    @Override
    public void onBackPressed() {
        if(view.onBackPressed()){
            super.onBackPressed();
        }
    }
}
