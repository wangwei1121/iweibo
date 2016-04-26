package com.snail.iweibo.mvp.view.impl;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.snail.iweibo.R;
import com.snail.iweibo.mvp.view.IBaseView;
import com.snail.iweibo.ui.base.BasePresenterActivity;
import com.snail.iweibo.util.SharePreferencesUtil;
import com.snail.iweibo.widget.X5WebView;
import com.snail.iweibo.widget.X5WebView.CallBack;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ISnailWebView
 * Created by alexwan on 16/4/26.
 */
public class ISnailWebView implements IBaseView , CallBack {
    private View view;
    private BasePresenterActivity context;
    @Bind(R.id.web_view)
    X5WebView webView;
    @Bind(R.id.tool_bar)
    Toolbar toolbar;
    private ViewHandler handler;
    @Override
    public void init(final Context context, LayoutInflater inflater, ViewGroup viewGroup) {
        this.context = (BasePresenterActivity) context;
        view = inflater.inflate(R.layout.activity_web_view , viewGroup , false);
        ButterKnife.bind(this , view);
        ((BasePresenterActivity) context).setSupportActionBar(toolbar);
        ActionBar actionBar = ((BasePresenterActivity) context).getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setTitle("...");
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BasePresenterActivity) context).finish();
            }
        });
        webView.setDayOrNight(!SharePreferencesUtil.isDarkTheme(context));
        webView.setCallBack(this);
        handler = new ViewHandler(new WeakReference<Activity>((BasePresenterActivity)context));
    }

    @Override
    public View getView() {
        return view;
    }

    public void loadUrl(String url){
        webView.loadUrl(url);
    }


    public boolean onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
            return false;
        }
        return true;
    }

    @Override
    public void onReceivedTitle(final String title) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                toolbar.setTitle(title);
            }
        });
    }

    public void destroyView(){
        if(handler != null){
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }
    private static class ViewHandler extends Handler{
        private WeakReference<Activity> activity;

        public ViewHandler(WeakReference<Activity> activity) {
            this.activity = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            if(msg == null || activity.get() == null){
                return;
            }
            super.handleMessage(msg);
        }
    }
}
