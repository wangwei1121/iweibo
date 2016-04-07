package com.snail.iweibo.mvp.view.impl.activity;
import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
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
import android.widget.ImageView;

import com.snail.iweibo.R;
import com.snail.iweibo.mvp.view.IBaseView;
import com.snail.iweibo.ui.base.BasePresenterActivity;
import com.snail.iweibo.ui.fragment.HomeFragment;
import com.snail.iweibo.ui.fragment.SettingFragment;
import com.snail.iweibo.util.SharePreferencesUtil;
import com.snail.iweibo.widget.theme.ThemeUIInterface;

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
    @Bind(R.id.navigation)
    NavigationView navigationView;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.fab_btn)
    FloatingActionButton fabBtn;
    private Fragment lastFragment;
    private BasePresenterActivity context;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    public void init(Context context, LayoutInflater inflater, ViewGroup viewGroup) {
        mView = inflater.inflate(R.layout.activity_main, viewGroup, false);
        ButterKnife.bind(this, mView);
        this.context = (BasePresenterActivity) context;
    }

    @Override
    public View getView() {
        return mView;
    }

    /**
     * 初始化View
     */
    public void initViews() {
        // toolbar
        context.setSupportActionBar(mToolbar);
        final android.support.v7.app.ActionBar actionBar = context.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            // add back icon
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        // drawer toggle
        this.drawerToggle = new ActionBarDrawerToggle(context, mDrawerLayout, R.string.tool_name, R.string.tool_name);
        drawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(drawerToggle);
        navigationView.setCheckedItem(R.id.main_frame);
        // fragment
        final HomeFragment homeFragment = new HomeFragment();
        homeFragment.setTabLayout(tabLayout);
        lastFragment = homeFragment;
        final SettingFragment settingFragment = new SettingFragment();
        final FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, homeFragment).commit();
        View view = navigationView.getHeaderView(0);
        final ImageView themeWitch = (ImageView) view.findViewById(R.id.theme_switch);
        themeWitch.setImageResource(SharePreferencesUtil.isDarkTheme(context) ? R.drawable.icon_theme_day :
            R.drawable.icon_theme_night);
        themeWitch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePreferencesUtil.setDarkTheme(context);
                themeWitch.setImageResource(SharePreferencesUtil.isDarkTheme(context) ? R.drawable.icon_theme_day :
                    R.drawable.icon_theme_night);
                switchTheme();
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
                        switchFragment(context, lastFragment, homeFragment);
                        break;
                    case R.id.message_frame:
                        tabLayout.setVisibility(View.GONE);
                        switchFragment(context, lastFragment, settingFragment);
                        break;
                    case R.id.search_frame:
                        tabLayout.setVisibility(View.GONE);
                        switchFragment(context, lastFragment, settingFragment);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 切换Fragment
     *
     * @param activity activity
     * @param from     from
     * @param to       to
     */
    public void switchFragment(BasePresenterActivity activity, Fragment from, Fragment to) {
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

    /**
     * 切换主题
     */
    public void switchTheme() {
        Window window = context.getWindow();
        // get current window decor view
        final View contentView = window.getDecorView().findViewById(android.R.id.content);
        contentView.setDrawingCacheEnabled(true);
        mView.buildDrawingCache();
        // build decor view bitmap
        final Bitmap bitmap = Bitmap.createBitmap(contentView.getDrawingCache());
        contentView.setDrawingCacheEnabled(false);
        // theme config
        boolean isDark = SharePreferencesUtil.isDarkTheme(context);
        // generate animation view
        final View animView = new View(contentView.getContext().getApplicationContext());
        if (VERSION.SDK_INT > VERSION_CODES.JELLY_BEAN) {
            animView.setBackground(new BitmapDrawable(context.getResources(), bitmap));
        } else {
            animView.setBackgroundDrawable(new BitmapDrawable(context.getResources(), bitmap));
        }
        // add animation view to window's decor
        ((ViewGroup) contentView)
            .addView(animView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        // change theme
        context.setTheme(isDark ? R.style.AppTheme_Dark : R.style.AppTheme);
        changeTheme(mDrawerLayout, context.getTheme());
        // change status bar
        int statusColor =
            context.getResources().getColor(isDark ? R.color.color_primary_dark_inverse : R.color.main_dark_blue);
        if (VERSION.SDK_INT > VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(statusColor);
        }
        // action bar
        ActionBar actionBar = context.getSupportActionBar();
        int actionBarColor =
            context.getResources().getColor(isDark ? R.color.color_primary_dark_inverse : R.color.main_blue);
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));
        }
        int colorId = isDark ? R.color.color_primary_dark_inverse : R.color.main_white;
        navigationView.setBackgroundColor(context.getResources().getColor(colorId));
        // navigation view
        ObjectAnimator animator = ObjectAnimator.ofFloat(animView, "alpha", 0f).setDuration(200);
        animator.addListener(new AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ((ViewGroup) contentView).removeView(animView);
                bitmap.recycle();
                Runtime.getRuntime().gc();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    public void changeTheme(View view, Resources.Theme theme) {

        if (view instanceof ThemeUIInterface) {
            Log.i("IMainActivityView" , view.toString() +" - ");
            ((ThemeUIInterface) view).setTheme(theme);
        }
        if (view instanceof ViewGroup) {
            int count = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < count; i++) {
                changeTheme(((ViewGroup) view).getChildAt(i), theme);
            }
        }
    }

    public ActionBarDrawerToggle getDrawerToggle() {
        return drawerToggle;
    }
}
