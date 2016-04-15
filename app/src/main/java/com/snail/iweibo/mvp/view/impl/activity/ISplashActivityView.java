package com.snail.iweibo.mvp.view.impl.activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.snail.iweibo.R;
import com.snail.iweibo.mvp.view.IBaseView;
import com.snail.iweibo.ui.base.BasePresenterActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ISplashActivityView
 * Created by alexwan on 16/4/10.
 */
public class ISplashActivityView implements IBaseView {
    private View view;
    private BasePresenterActivity context;
    @Bind(R.id.logo)
    SimpleDraweeView imageView;
    @Bind(R.id.action_login)
    TextView loginBtn;
    @Bind(R.id.action_browse)
    TextView browseBtn;
    @Bind(R.id.progress)
    ProgressBar progressBar;
    @Override
    public void init(Context context, LayoutInflater inflater, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_splash , viewGroup);
        ButterKnife.bind(this , view);
        this.context = (BasePresenterActivity) context;
    }

    public void initView(OnClickListener onClickListener){
        Window window = context.getWindow();
        // 设置透明状态栏
        if (VERSION.SDK_INT > VERSION_CODES.KITKAT) {
            // 需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            if(VERSION.SDK_INT > VERSION_CODES.LOLLIPOP){
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }
            // 设置透明状态栏,这样才能让 ContentView 向上
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        ViewGroup mContentView = (ViewGroup) context.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
            ViewCompat.setFitsSystemWindows(mChildView, false);
        }
        imageView.setImageURI(UriUtil.parseUriOrNull("https://dn-ioliu.qbox.me/bing/CabodeGata_ZH-CN12858688851_1280x768.jpg"));
        loginBtn.setOnClickListener(onClickListener);
        browseBtn.setOnClickListener(onClickListener);
    }
    @Override
    public View getView() {
        return view;
    }

    public void setLoginViewVisible(boolean visible){
        loginBtn.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
//        browseBtn.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setProgressBarVisible(boolean visible){
        progressBar.setVisibility(visible ? View.VISIBLE : View.GONE);
    }
}
