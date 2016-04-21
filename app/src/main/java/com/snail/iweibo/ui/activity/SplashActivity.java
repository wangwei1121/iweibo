package com.snail.iweibo.ui.activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.snail.iweibo.R;
import com.snail.iweibo.api.ApiServiceHelper;
import com.snail.iweibo.api.OAuth2ApiService;
import com.snail.iweibo.mvp.model.TokenInfo;
import com.snail.iweibo.mvp.view.impl.activity.ISplashActivityView;
import com.snail.iweibo.oauth.AccessTokenKeeper;
import com.snail.iweibo.oauth.Constants;
import com.snail.iweibo.ui.base.BasePresenterActivity;
import com.snail.iweibo.util.LogUtils;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * SplashActivity
 * Created by alexwan on 16/4/10.
 */
public class SplashActivity extends BasePresenterActivity<ISplashActivityView>
    implements OnClickListener, WeiboAuthListener {
    // 注意：SsoHandler 仅当 SDK 支持 SSO 时有效
    private SsoHandler mSsoHandler;
    private Subscription subscription;
    @Override
    protected void onBindView() {
        super.onBindView();
        view.initView(this);
        initData();
    }

    private void initData() {
        Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(this);
        if (TextUtils.isEmpty(accessToken.getToken())) {
            view.setLoginViewVisible(true);
        } else {
            view.setLoginViewVisible(false);
            LogUtils.info("initData");
            // 判断token是否过期
            subscription = ApiServiceHelper
                .getApiService(Constants.WEIBO_BASE_URL, OAuth2ApiService.class)
                .getTokenInfo(accessToken.getToken()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TokenInfo>() {
                    @Override
                    public void onCompleted() {
                        view.setProgressBarVisible(false);
                    }
                    @Override
                    public void onError(Throwable e) {
                        // 加载失败
                        view.setProgressBarVisible(false);
                        MainActivity.start(SplashActivity.this);
                        finish();
                    }
                    @Override
                    public void onNext(TokenInfo tokenInfo) {
                        if (tokenInfo != null && Integer.parseInt(tokenInfo.getExpireIn()) <= 0) {
                            view.setLoginViewVisible(true);
                        } else {
                            view.setProgressBarVisible(false);
                            MainActivity.start(SplashActivity.this);
                            finish();
                        }
                    }
                });
        }
    }

    @Override
    protected Class<ISplashActivityView> getViewClass() {
        return ISplashActivityView.class;
    }

    @Override
    protected void onDestroyView() {
        if(subscription != null){
            subscription.unsubscribe();
        }
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.action_login:
                AuthInfo mAuthInfo = new AuthInfo(SplashActivity.this, Constants.APP_KEY,
                    Constants.REDIRECT_URL, Constants.SCOPE);
                mSsoHandler = new SsoHandler(SplashActivity.this, mAuthInfo);
                mSsoHandler.authorize(this);
                break;
            case R.id.action_browse:
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
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
        /* 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能  */
        Oauth2AccessToken mAccessToken = Oauth2AccessToken.parseAccessToken(bundle);
        if (mAccessToken.isSessionValid()) {
            AccessTokenKeeper.writeAccessToken(SplashActivity.this, mAccessToken);
            MainActivity.start(this);
            finish();
        } else {
            String code = bundle.getString("code");
            String message = getString(R.string.weibosdk_demo_toast_auth_failed);
            if (!TextUtils.isEmpty(code)) {
                LogUtils.error(message + " code : " + code);
            }
        }
    }

    @Override
    public void onWeiboException(WeiboException e) {
        // 授权失败
    }

    @Override
    public void onCancel() {
        // 取消授权
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
