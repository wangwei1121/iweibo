package com.snail.iweibo.ui.activity;

import android.text.TextUtils;
import android.view.View;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.snail.iweibo.api.ApiServiceHelper;
import com.snail.iweibo.api.FriendShipApiService;
import com.snail.iweibo.mvp.model.Follower;
import com.snail.iweibo.mvp.model.FollowersList;
import com.snail.iweibo.mvp.view.impl.activity.IFollowersActivity;
import com.snail.iweibo.oauth.AccessTokenKeeper;
import com.snail.iweibo.oauth.Constants;
import com.snail.iweibo.ui.adapter.FollowersListAdapter;
import com.snail.iweibo.ui.base.BasePresenterActivity;
import com.snail.iweibo.util.SharePreferencesUtil;

import java.util.ArrayList;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * FollowersActivity
 * Created by luckyliang on 16/4/16.
 */
public class FollowersActivity extends BasePresenterActivity<IFollowersActivity> implements View.OnClickListener, FollowersListAdapter.OnItemClickListener {

    private FollowersListAdapter adapter;

    @Override
    protected Class<IFollowersActivity> getViewClass() {
        return IFollowersActivity.class;
    }

    @Override
    protected void onBindView() {
        super.onBindView();
        view.initView(this);
//        Status status = (Status) getIntent().getSerializableExtra("status");
        initData();
    }

    private void initData() {
        String token;
        Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(this);
        if (!TextUtils.isEmpty(accessToken.getToken())) {
            token = accessToken.getToken();
        } else {
            token = Constants.TOKEN;
        }
        String name = SharePreferencesUtil.getUserBean(this).getScreen_name();
        int uid = Integer.parseInt(accessToken.getUid());
        ApiServiceHelper.getApiService(Constants.WEIBO_BASE_URL, FriendShipApiService.class)
                .getFollowers(token, uid, name, 200, 0, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<FollowersList>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(FollowersList followersList) {
                        ArrayList<Follower> followers = followersList.getFollowerArrayList();
                        if (followers != null && !followers.isEmpty()) {
                            view.initView(followers);

                        }
                    }
                });


    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(int position) {

    }
}
