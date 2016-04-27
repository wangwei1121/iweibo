package com.snail.iweibo.ui.activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.snail.iweibo.mvp.model.Status;
import com.snail.iweibo.mvp.view.impl.activity.IRetweetActivityView;
import com.snail.iweibo.ui.base.BasePresenterActivity;

/**
 * 微博转发
 * Created by alexwan on 16/4/27.
 */
public class RetweetActivity extends BasePresenterActivity<IRetweetActivityView>{
    private static final String INTENT_STATUS = "status";
    private Status status;
    public static void start(Context context , @NonNull Status status){
        Intent intent = new Intent(context , RetweetActivity.class);
        intent.putExtra(INTENT_STATUS , status);
        context.startActivity(intent);
    }

    @Override
    protected void onBindView() {
        super.onBindView();
        status = (Status) getIntent().getSerializableExtra(INTENT_STATUS);
    }

    @Override
    protected Class<IRetweetActivityView> getViewClass() {
        return IRetweetActivityView.class;
    }
}
