package com.snail.iweibo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
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

public class MainActivity extends BasePresenterActivity<IMainActivityView> implements OnClickListener , WeiboAuthListener{
    private long last = 0;
    private Oauth2AccessToken token;
    private SsoHandler mSsoHandler;

    public static void start(Context context){
        Intent intent = new Intent(context , MainActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onBindView() {
        super.onBindView();
        view.initViews(this);
        getUserInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // TODO
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        token = AccessTokenKeeper.readAccessToken(this);
        if (TextUtils.isEmpty(token.getToken())) {
            return;
        }
        ApiServiceHelper
            .getApiService(Constants.WEIBO_BASE_URL, AccountApiService.class)
            .getUserInfo(token.getToken(), token.getUid(), "")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<UserBean>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("MainActivity", "onError - error : " + e.getMessage());
                }

                @Override
                public void onNext(UserBean userBean) {
                    if (userBean != null) {
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
        if (view.getDrawerToggle().onOptionsItemSelected(item)) {
            return true;
        }
        int id = item.getItemId();
        switch (id) {
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
        long current = System.currentTimeMillis();
        long minus = current - last;
        if (minus > 1000) {
            last = current;
        } else {
            moveTaskToBack(true);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.user_avatar:
                // 判断是否需要登录
                if(token == null || TextUtils.isEmpty(token.getToken())){
                    AuthInfo mAuthInfo = new AuthInfo(this, Constants.APP_KEY,
                        Constants.REDIRECT_URL, Constants.SCOPE);
                    mSsoHandler = new SsoHandler(this, mAuthInfo);
                    mSsoHandler.authorize(this);
                }else{
                    UserDetailActivity.start(this , token);
                }
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != mSsoHandler) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    @Override
    public void onComplete(Bundle bundle) {
        Oauth2AccessToken mAccessToken = Oauth2AccessToken.parseAccessToken(bundle);
        //从这里获取用户输入的 电话号码信息
        if (mAccessToken.isSessionValid()) {
            AccessTokenKeeper.writeAccessToken(this, mAccessToken);
            Log.i("SplashActivity", "code : " + mAccessToken.getToken());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            String code = bundle.getString("code");
            String message = getString(R.string.weibosdk_demo_toast_auth_failed);
            if (!TextUtils.isEmpty(code)) {
                Log.e("SplashActivity", message + " code : " + code);
            }
        }
    }

    @Override
    public void onWeiboException(WeiboException e) {

    }

    @Override
    public void onCancel() {

    }
}
