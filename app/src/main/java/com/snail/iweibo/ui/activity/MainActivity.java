package com.snail.iweibo.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.snail.iweibo.R;
import com.snail.iweibo.api.AccountApiService;
import com.snail.iweibo.api.ApiServiceHelper;
import com.snail.iweibo.mvp.model.UserBean;
import com.snail.iweibo.mvp.view.impl.activity.IMainActivityView;
import com.snail.iweibo.oauth.AccessTokenKeeper;
import com.snail.iweibo.oauth.Constants;
import com.snail.iweibo.ui.base.BasePresenterActivity;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BasePresenterActivity<IMainActivityView> {
    private long last = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onBindView() {
        super.onBindView();
        view.initViews();
        getUserInfo();
    }

    private void getUserInfo() {
        Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(this);
        if(TextUtils.isEmpty(accessToken.getToken())){
            return;
        }
        ApiServiceHelper.getApiService(Constants.WEIBO_BASE_URL , AccountApiService.class)
                        .getUserInfo(accessToken.getToken() , accessToken.getUid() , "")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<UserBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("MainActivity" , "onError - error : " + e.getMessage());
                            }

                            @Override
                            public void onNext(UserBean userBean) {
                                if(userBean != null){
                                    view.updateUserInfo(userBean);
                                }
                            }
                        });
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

    @Override
    public void onBackPressed() {
//        long current = System.currentTimeMillis();
//        long minus = current - last;
//        if(minus > 1000){
//            last = current;
//            Toast.makeText(this , "再次点击退出程序" , Toast.LENGTH_SHORT).show();
//        }else{
//            super.onBackPressed();
//        }
        moveTaskToBack(true);
    }
}
