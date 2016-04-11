package com.snail.iweibo.mvp.view.impl.activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.snail.iweibo.R;
import com.snail.iweibo.mvp.model.UserBean;
import com.snail.iweibo.mvp.view.IBaseView;
import com.snail.iweibo.ui.base.BasePresenterActivity;
import com.snail.iweibo.ui.fragment.UserDetailStatusFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * IUserDetailActivity
 * Created by alexwan on 16/4/10.
 */
public class IUserDetailActivity implements IBaseView {
    private View view;
    private BasePresenterActivity context;
    @Bind(R.id.frame_layout)
    FrameLayout frameLayout;
    @Bind(R.id.tool_bar)
    Toolbar toolbar;
    @Bind(R.id.header_bg)
    SimpleDraweeView headerBg;
    @Bind(R.id.user_avatar)
    SimpleDraweeView userAvatar;
    @Bind(R.id.tool_bar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.user_name)
    TextView userName;
    @Bind(R.id.follower_count)
    TextView followers;
    @Bind(R.id.friend_count)
    TextView friends;
    @Bind(R.id.user_description)
    TextView description;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    private FragmentManager fManager;
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
        fManager = context.getSupportFragmentManager();
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
//        ScreenInfo info = new ScreenInfo(context);
//        LayoutParams params = new LayoutParams(info.getWidth() , info.getWidth() * 9 / 24);
//        headerBg.setLayoutParams(params);
        Window window = context.getWindow();
        Rect rect = new Rect();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        int statusBarHeight = rect.height();
        // 设置透明状态栏
        if (VERSION.SDK_INT > VERSION_CODES.LOLLIPOP) {
            // 需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // 设置透明状态栏,这样才能让 ContentView 向上
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup mContentView = (ViewGroup) context.findViewById(Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
                ViewCompat.setFitsSystemWindows(mChildView, false);
//                DrawerLayout.LayoutParams lp = (DrawerLayout.LayoutParams) mChildView.getLayoutParams();
//                lp.topMargin = statusBarHeight;
//                mChildView.setLayoutParams(lp);
            }
        }else if(VERSION.SDK_INT > VERSION_CODES.KITKAT){
//            // 设置透明状态栏,这样才能让 ContentView 向上
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //避免多次调用该方法时,多次移除了 View
//            if (mChildView != null && mChildView.getLayoutParams() != null && mChildView.getLayoutParams().height == 46) {
//                //移除假的 View.
//                mContentView.removeView(mChildView);
//                mChildView = mContentView.getChildAt(0);
//            }
//            if (mChildView != null) {
//                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mChildView.getLayoutParams();
//                //清除 ChildView 的 marginTop 属性
//                if (lp != null && lp.topMargin >= 46) {
//                    lp.topMargin -= 46;
//                    mChildView.setLayoutParams(lp);
//                }
//            }
        }
//        toolbarLayout.setTitle("用户详情");
        toolbarLayout.setExpandedTitleColor(0);
        toolbarLayout.setCollapsedTitleTextColor(context.getResources().getColor(R.color.main_white));

    }

    public void updateView(UserBean userBean) {

        userAvatar.setImageURI(UriUtil.parseUriOrNull(userBean.getAvatar_hd()));
//        toolbar.setTitle(userBean.getScreen_name());
        userName.setText(userBean.getScreen_name());
        friends.setText(context.getString(R.string.string_user_friends_count , userBean.getFriends_count()));
        followers.setText(context.getString(R.string.string_user_follower_count , userBean.getFollowers_count()));
        description.setText(context.getString(R.string.string_user_description , userBean.getDescription()));
        tabLayout.addTab(tabLayout.newTab().setTag(TabIndicator.First).setText("主页"));
        tabLayout.addTab(tabLayout.newTab().setTag(TabIndicator.Second).setText("微博"));
        tabLayout.addTab(tabLayout.newTab().setTag(TabIndicator.Third).setText("相册"));

        FragmentTransaction transaction = fManager.beginTransaction();
        UserDetailStatusFragment statusFragment = new UserDetailStatusFragment();
        transaction.replace(R.id.frame_layout , statusFragment).commitAllowingStateLoss();

        tabLayout.setOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(Tab tab) {
                TabIndicator indicator = (TabIndicator) tab.getTag();
                if(indicator == null){
                    return;
                }
//                FragmentTransaction transaction = fManager.beginTransaction();
                switch (indicator){
                    case First:
                        //
                        break;
                    case Second:
                        //
                        break;
                    case Third:
                        //
                        break;
                }
//                transaction.commitAllowingStateLoss();
            }

            @Override
            public void onTabUnselected(Tab tab) {

            }

            @Override
            public void onTabReselected(Tab tab) {

            }
        });
    }

    private enum TabIndicator{
        First,
        Second,
        Third,
    }
}
