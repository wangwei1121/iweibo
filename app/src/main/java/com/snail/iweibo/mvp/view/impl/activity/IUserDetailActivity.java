package com.snail.iweibo.mvp.view.impl.activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout.LayoutParams;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.snail.iweibo.R;
import com.snail.iweibo.mvp.model.UserBean;
import com.snail.iweibo.mvp.view.IBaseView;
import com.snail.iweibo.ui.base.BasePresenterActivity;
import com.snail.iweibo.util.ScreenInfo;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * IUserDetailActivity
 * Created by alexwan on 16/4/10.
 */
public class IUserDetailActivity implements IBaseView {
    private View view;
    private BasePresenterActivity context;
    @Bind(R.id.tool_bar)
    Toolbar toolbar;
    @Bind(R.id.header_bg)
    SimpleDraweeView headerBg;
    @Bind(R.id.user_avatar)
    SimpleDraweeView userAvatar;
    @Bind(R.id.tool_bar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @Override
    public void init(Context context, LayoutInflater inflater, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_user_detail , viewGroup);
        ButterKnife.bind(this , view);
        this.context = (BasePresenterActivity) context;
    }

    @Override
    public View getView() {
        return view;
    }

    public void initView(){

        context.setSupportActionBar(toolbar);
        ActionBar actionBar = context.getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                context.finish();
            }
        });
        ScreenInfo info = new ScreenInfo(context);
        LayoutParams params = new LayoutParams(info.getWidth() , info.getWidth() * 9 / 16);
        headerBg.setLayoutParams(params);


        Window window = context.getWindow();
        // 设置透明状态栏
        if (VERSION.SDK_INT > VERSION_CODES.LOLLIPOP) {
            // 需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // 设置透明状态栏,这样才能让 ContentView 向上
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        ViewGroup mContentView = (ViewGroup) context.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
            ViewCompat.setFitsSystemWindows(mChildView, false);
        }
    }

    public void updateView(UserBean userBean) {
        userAvatar.setImageURI(UriUtil.parseUriOrNull(userBean.getAvatar_hd()));
//        toolbar.setTitle(userBean.getScreen_name());

    }
}
