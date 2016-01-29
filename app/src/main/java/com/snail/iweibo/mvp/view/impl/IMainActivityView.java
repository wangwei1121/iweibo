package com.snail.iweibo.mvp.view.impl;
import android.content.res.Configuration;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.snail.iweibo.R;
import com.snail.iweibo.mvp.view.IBaseActivityView;
import com.snail.iweibo.ui.activity.MainActivity;

/**
 * IMainActivityView
 * Created by alexwan on 16/1/28.
 */
public class IMainActivityView implements IBaseActivityView {
    private View mView;
    Toolbar mToolbar;
    DrawerLayout mDrawerLayout;
    CoordinatorLayout mRootLayout;
    ActionBarDrawerToggle drawerToggle;

    @Override
    public void init(LayoutInflater inflater, ViewGroup viewGroup) {
        mView = inflater.inflate(R.layout.activity_main, viewGroup, false);
        mToolbar = (Toolbar) mView.findViewById(R.id.tool_bar);
        mDrawerLayout = (DrawerLayout) mView.findViewById(R.id.drawer_layout);
        mRootLayout = (CoordinatorLayout) mView.findViewById(R.id.root_layout);
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
    public void initViews(MainActivity activity) {
        activity.setSupportActionBar(mToolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        this.drawerToggle = new ActionBarDrawerToggle(activity, mDrawerLayout, R.string.tool_name, R.string.tool_name);
        drawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(drawerToggle);
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
