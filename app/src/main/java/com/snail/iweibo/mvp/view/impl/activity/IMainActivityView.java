package com.snail.iweibo.mvp.view.impl.activity;
import android.content.Context;
import android.content.res.Configuration;
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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.snail.iweibo.R;
import com.snail.iweibo.mvp.view.IBaseView;
import com.snail.iweibo.ui.activity.MainActivity;
import com.snail.iweibo.ui.base.BasePresenterActivity;
import com.snail.iweibo.ui.fragment.HomeFragment;
import com.snail.iweibo.ui.fragment.SettingFragment;

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
        ActionBar actionBar = activity.getSupportActionBar();
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
