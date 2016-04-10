package com.snail.iweibo.ui.activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.snail.iweibo.R;
import com.snail.iweibo.mvp.view.impl.activity.ISplashActivityView;
import com.snail.iweibo.oauth.AccessTokenKeeper;
import com.snail.iweibo.oauth.Constants;
import com.snail.iweibo.ui.base.BasePresenterActivity;

/**
 * SplashActivity
 * Created by alexwan on 16/4/10.
 */
public class SplashActivity extends BasePresenterActivity<ISplashActivityView> implements OnClickListener , WeiboAuthListener{
    /** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
    private SsoHandler mSsoHandler;
    @Override
    protected void onBindView() {
        super.onBindView();
        view.initView(this);
        initData();
    }

    private void initData() {
        Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(this);
        if(TextUtils.isEmpty(accessToken.getToken())){
            view.setLoginViewVisible(true);
            AuthInfo mAuthInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
            mSsoHandler = new SsoHandler(this, mAuthInfo);
        }else{
            view.setLoginViewVisible(false);
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this , MainActivity.class);
                    startActivity(intent);
                    view.setProgressBarVisible(false);
                    finish();
                }
            } , 4000);

        }
    }


    @Override
    protected Class<ISplashActivityView> getViewClass() {
        return ISplashActivityView.class;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.action_login:
                mSsoHandler.authorize(this);
                break;
            case R.id.action_browse:
                Intent intent = new Intent(SplashActivity.this , MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
        if (null != mSsoHandler) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    @Override
    public void onComplete(Bundle bundle) {
        // 从 Bundle 中解析 Token
            /* 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能  */
        Oauth2AccessToken mAccessToken = Oauth2AccessToken.parseAccessToken(bundle);
        //从这里获取用户输入的 电话号码信息
        if (mAccessToken.isSessionValid()) {
            // 显示 Token
            // 保存 Token 到 SharedPreferences
            AccessTokenKeeper.writeAccessToken(SplashActivity.this, mAccessToken);
            Log.i(  "SplashActivity", "code : " + mAccessToken.getToken());
            Intent intent = new Intent(this , MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            // 以下几种情况，您会收到 Code：
            // 1. 当您未在平台上注册的应用程序的包名与签名时；
            // 2. 当您注册的应用程序包名与签名不正确时；
            // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
            String code = bundle.getString("code");
            String message = getString(R.string.weibosdk_demo_toast_auth_failed);
            if (!TextUtils.isEmpty(code)) {
                Log.e(  "SplashActivity", message + " code : " + code);
            }
        }
    }

    @Override
    public void onWeiboException(WeiboException e) {
        //
    }

    @Override
    public void onCancel() {
        //
    }
}
