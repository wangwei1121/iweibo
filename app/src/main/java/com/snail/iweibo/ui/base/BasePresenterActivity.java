package com.snail.iweibo.ui.base;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.snail.iweibo.R;
import com.snail.iweibo.mvp.view.IBaseView;
import com.snail.iweibo.util.SharePreferencesUtil;

/**
 * BaseAppCompatActivity
 * Created by alexwan on 16/1/28.
 */
public abstract class BasePresenterActivity<V extends IBaseView> extends AppCompatActivity {
    protected V view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this).setDownsampleEnabled(true).build();
        if(SharePreferencesUtil.isDarkTheme(this)){
            setTheme(R.style.AppTheme_Dark);
        }
        Fresco.initialize(this , config);
        try {
            view = getViewClass().newInstance();
            view.init(this , getLayoutInflater(), null);
            setContentView(view.getView());
            onBindView();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        onDestroyView();
        super.onDestroy();
    }

    /**
     * 获取View对应的Class
     * @return View类
     */
    protected abstract Class<V> getViewClass();

    /**
     * 绑定View
     */
    protected void onBindView(){}

    /**
     * 清除View
     */
    protected void onDestroyView(){}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
