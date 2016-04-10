package com.snail.iweibo.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.snail.iweibo.R;
import com.snail.iweibo.mvp.view.impl.activity.IMainActivityView;
import com.snail.iweibo.ui.base.BasePresenterActivity;

public class MainActivity extends BasePresenterActivity<IMainActivityView> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onBindView() {
        super.onBindView();
        view.initViews();
    }

    @Override
    protected void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        view.getDrawerToggle().syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        view.getDrawerToggle().onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(view.getDrawerToggle().onOptionsItemSelected(item)){
            return true;
        }
        int id = item.getItemId();
        switch (id){
            case R.id.main_frame:
//                transaction.add(new HomeFragment() , "home_fragment");
                break;
            case R.id.oauth_frame:
                Intent intent = new Intent(MainActivity.this, WBAuthActivity.class);
                MainActivity.this.startActivity(intent);
                break;
            case R.id.message_frame:
                intent = new Intent(MainActivity.this, NewsActivity.class);
                MainActivity.this.startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Class<IMainActivityView> getViewClass() {
        return IMainActivityView.class;
    }


}
