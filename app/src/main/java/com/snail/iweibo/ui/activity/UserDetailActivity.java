package com.snail.iweibo.ui.activity;
import android.util.Log;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.snail.iweibo.api.AccountApiService;
import com.snail.iweibo.api.ApiServiceHelper;
import com.snail.iweibo.mvp.model.UserBean;
import com.snail.iweibo.mvp.view.impl.activity.IUserDetailActivity;
import com.snail.iweibo.oauth.AccessTokenKeeper;
import com.snail.iweibo.oauth.Constants;
import com.snail.iweibo.ui.base.BasePresenterActivity;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * UserDetailActivity
 * Created by alexwan on 16/4/10.
 */
public class UserDetailActivity extends BasePresenterActivity<IUserDetailActivity>{
    @Override
    protected void onBindView() {
        super.onBindView();
        view.initView();

        initData();
    }

    private void initData() {

        Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(this);
        accessToken.getToken();
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
                    Log.i("UserDetailActivity" , "onError - error : " + e.getMessage());
                }

                @Override
                public void onNext(UserBean userBean) {
                    view.updateView(userBean);
                }
            });
    }

    @Override
    protected Class<IUserDetailActivity> getViewClass() {
        return IUserDetailActivity.class;
    }
}
