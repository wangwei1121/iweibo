package com.snail.iweibo.mvp.view.impl.activity;
import android.content.res.Configuration;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.snail.iweibo.R;
import com.snail.iweibo.mvp.view.IBaseView;
import com.snail.iweibo.ui.activity.MainActivity;
import com.snail.iweibo.ui.fragment.HomeFragment;
import com.snail.iweibo.ui.fragment.SettingFragment;

/**
 * IMainActivityView
 * Created by alexwan on 16/1/28.
 */
public class IMainActivityView implements IBaseView {
    private View mView;
    Toolbar mToolbar;
    DrawerLayout mDrawerLayout;
    CoordinatorLayout mRootLayout;
    ActionBarDrawerToggle drawerToggle;
    FrameLayout frameLayout;
    NavigationView navigationView;
    TabLayout tabLayout;
    @Override
    public void init(LayoutInflater inflater, ViewGroup viewGroup) {
        mView = inflater.inflate(R.layout.activity_main, viewGroup, false);
        mToolbar = (Toolbar) mView.findViewById(R.id.tool_bar);
        mDrawerLayout = (DrawerLayout) mView.findViewById(R.id.drawer_layout);
        mRootLayout = (CoordinatorLayout) mView.findViewById(R.id.root_layout);
        frameLayout = (FrameLayout) mView.findViewById(R.id.frame_layout);
        navigationView = (NavigationView) mView.findViewById(R.id.navigation);
        tabLayout = (TabLayout) mView.findViewById(R.id.tab_layout);
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
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        // drawer toggle
        this.drawerToggle = new ActionBarDrawerToggle(activity, mDrawerLayout, R.string.tool_name, R.string.tool_name);
        drawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(drawerToggle);
        navigationView.setCheckedItem(R.id.main_frame);
        // fragment
        final HomeFragment homeFragment = new HomeFragment();
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
                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                switch (id) {
                    case R.id.main_frame:
                        tabLayout.setVisibility(View.VISIBLE);
                        transaction.replace(R.id.frame_layout , homeFragment).commit();
                        break;
                    case R.id.message_frame:
                        tabLayout.setVisibility(View.GONE);
                        transaction.replace(R.id.frame_layout , settingFragment).commit();
                        break;
                    case R.id.search_frame:
                        tabLayout.setVisibility(View.GONE);
                        transaction.replace(R.id.frame_layout , settingFragment).commit();
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
}
