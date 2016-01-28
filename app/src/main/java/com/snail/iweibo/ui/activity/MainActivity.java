package com.snail.iweibo.ui.activity;

import android.content.res.Configuration;
import android.os.Bundle;

import com.snail.iweibo.mvp.view.impl.IMainActivityView;

public class MainActivity extends BaseAppCompatActivity<IMainActivityView> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onBindView() {
        super.onBindView();
        view.initViews(this);
    }

    @Override
    protected void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        view.onPostCreate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        view.onConfigurationChanged(newConfig);
    }
    @Override
    protected Class<IMainActivityView> getViewClass() {
        return IMainActivityView.class;
    }


}
