package com.snail.iweibo.ui.activity;
import android.content.res.Configuration;
import android.util.Log;

import com.snail.iweibo.mvp.model.Status;
import com.snail.iweibo.mvp.view.impl.activity.IStatusDetailActivityView;
import com.snail.iweibo.ui.base.BasePresenterActivity;

/**
 * 微博正文Activity
 * Created by alexwan on 16/4/4.
 */
public class StatusDetailActivity extends BasePresenterActivity<IStatusDetailActivityView> {

    @Override
    protected Class<IStatusDetailActivityView> getViewClass() {
        return IStatusDetailActivityView.class;
    }

    @Override
    protected void onBindView() {
        super.onBindView();
        Status status = (Status) getIntent().getSerializableExtra("status");
        Log.i("StatusDetailActivity" , status.toString());
        view.initView(this);
        view.updateView(status);
    }

    @Override
    protected void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
//        view.onConfigurationChanged(newConfig);
    }
}


