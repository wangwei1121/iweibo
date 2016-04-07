package com.snail.iweibo.mvp.view.impl.activity;
import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

import com.snail.iweibo.R;
import com.snail.iweibo.mvp.view.IBaseView;
import com.snail.iweibo.ui.activity.MainActivity;
import com.snail.iweibo.ui.base.BasePresenterActivity;
import com.snail.iweibo.ui.fragment.HomeFragment;
import com.snail.iweibo.ui.fragment.SettingFragment;
import com.snail.iweibo.util.SharePreferencesUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * IMainActivityView
 * Created by alexwan on 16/1/28.
 */
public class IMainActivityView implements IBaseView {
    private View mView;
    @Bind(R.id.tool_bar)
    Toolbar mToolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.root_layout)
    CoordinatorLayout mRootLayout;
    ActionBarDrawerToggle drawerToggle;
    @Bind(R.id.navigation)
    NavigationView navigationView;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.fab_btn)
    FloatingActionButton fabBtn;
    private Fragment lastFragment;
    @Override
    public void init(Context context , LayoutInflater inflater, ViewGroup viewGroup) {
        mView = inflater.inflate(R.layout.activity_main, viewGroup, false);
        ButterKnife.bind(this, mView);
    }

    @Override
    public View getView() {
        return mView;
    }

    /**
     * 初始化界面
     *
     * @param activity activity
     */
    public void initViews(final MainActivity activity) {
        // toolbar
        activity.setSupportActionBar(mToolbar);
        final ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            // add back icon
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        // drawer toggle
        this.drawerToggle = new ActionBarDrawerToggle(activity, mDrawerLayout, R.string.tool_name, R.string.tool_name);
        drawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(drawerToggle);
        navigationView.setCheckedItem(R.id.main_frame);
        // fragment

        final HomeFragment homeFragment = new HomeFragment();
        homeFragment.setTabLayout(tabLayout);
        lastFragment = homeFragment;
        final SettingFragment settingFragment = new SettingFragment();
        final FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout , homeFragment).commit();
        View view = navigationView.getHeaderView(0);
        view.findViewById(R.id.theme_switch).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePreferencesUtil.setDarkTheme(activity);
                Window window = activity.getWindow();
                final View contentView = window.getDecorView().findViewById(android.R.id.content);

                contentView.setDrawingCacheEnabled(true);
                mView.buildDrawingCache();
                final Bitmap bitmap = Bitmap.createBitmap(contentView.getDrawingCache());
                contentView.setDrawingCacheEnabled(false);
                Log.i("IMainActivityView" , bitmap.toString());
                boolean isDark = SharePreferencesUtil.isDarkTheme(activity);
                final View animView = new View(contentView.getContext().getApplicationContext());
                animView.setVisibility(View.VISIBLE);
                if(VERSION.SDK_INT > VERSION_CODES.JELLY_BEAN){
                    animView.setBackground(new BitmapDrawable(activity.getResources() , bitmap));
                }else {
                    animView.setBackgroundDrawable(new BitmapDrawable(activity.getResources() , bitmap));
                }
                // 添加container 到当前屏幕
                ((ViewGroup)contentView).addView(animView , new LayoutParams(LayoutParams.MATCH_PARENT , LayoutParams.MATCH_PARENT));
                // 改变状态颜色
                if(VERSION.SDK_INT > VERSION_CODES.LOLLIPOP){
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

                    activity.getResources().newTheme().setTo(activity.getTheme());
                    window.setStatusBarColor(activity.getResources().getColor(isDark ? R.color.theme_primary_dark_inverse : R
                        .color.main_dark_blue));
                }
                //
                String color = isDark ? "#424242" : "#1976D2";
                ColorDrawable drawable = new ColorDrawable(Color.parseColor(color));
                if(actionBar != null){
                    actionBar.setBackgroundDrawable(drawable);
                }
                tabLayout.setBackgroundDrawable(drawable);
                navigationView.getHeaderView(0).setBackgroundDrawable(drawable);
                //
                ObjectAnimator animator = ObjectAnimator.ofFloat(animView , "alpha" , 0f).setDuration(200);
                animator.addListener(new AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ((ViewGroup) contentView).removeView(animView);
                        bitmap.recycle();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animator.start();

                mDrawerLayout.closeDrawers();
            }
        });
        // navigation
        navigationView.setNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                mDrawerLayout.closeDrawers();
                navigationView.setCheckedItem(id);
                // Fragment 切换
                switch (id) {
                    case R.id.main_frame:
                        tabLayout.setVisibility(View.VISIBLE);
                        switchFragment(activity , lastFragment, homeFragment);
                        break;
                    case R.id.message_frame:
                        tabLayout.setVisibility(View.GONE);
                        switchFragment(activity , lastFragment, settingFragment);
                        break;
                    case R.id.search_frame:
                        tabLayout.setVisibility(View.GONE);
                        switchFragment(activity , lastFragment, settingFragment);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    public void onPostCreate() {
        drawerToggle.syncState();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        drawerToggle.onConfigurationChanged(newConfig);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item);
    }

    /**
     * 切换Fragment
     * @param activity activity
     * @param from from
     * @param to to
     */
    public void switchFragment(BasePresenterActivity activity , Fragment from , Fragment to){
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        if (!to.isAdded()) {
            // 隐藏当前的fragment，add下一个到Activity中
            transaction.hide(from).add(R.id.frame_layout, to).commit();
        } else {
            // 隐藏当前的fragment，显示下一个
            transaction.hide(from).show(to).commit();
        }
        lastFragment = to;
    }
}
