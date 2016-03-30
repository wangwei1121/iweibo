package com.snail.iweibo.ui.base;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.snail.iweibo.mvp.view.IBaseView;

/**
 * BaseAppCompatActivity
 * Created by alexwan on 16/1/28.
 */
public abstract class BasePresenterActivity<V extends IBaseView> extends AppCompatActivity {
    protected V view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        try {
            view = getViewClass().newInstance();
            view.init(this , getLayoutInflater(), null);
            setContentView(view.getView());
            onBindView();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
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
        view = null;
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
}
