package com.snail.iweibo.ui.activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.snail.iweibo.api.ApiServiceHelper;
import com.snail.iweibo.mvp.model.CommentList;
import com.snail.iweibo.mvp.model.Status;
import com.snail.iweibo.mvp.view.impl.activity.IStatusDetailActivityView;
import com.snail.iweibo.oauth.AccessTokenKeeper;
import com.snail.iweibo.ui.base.BasePresenterActivity;
import com.snail.iweibo.util.LogUtils;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * 微博正文Activity
 * Created by alexwan on 16/4/4.
 */
public class StatusDetailActivity extends BasePresenterActivity<IStatusDetailActivityView> {
    private Subscription subscription;
    public static void start(Context context, Status status) {
        Intent intent = new Intent(context, StatusDetailActivity.class);
        intent.putExtra("status", status);
        context.startActivity(intent);
    }

    @Override
    protected Class<IStatusDetailActivityView> getViewClass() {
        return IStatusDetailActivityView.class;
    }

    @Override
    protected void onBindView() {
        super.onBindView();
        Status status = (Status) getIntent().getSerializableExtra("status");
        Log.i("StatusDetailActivity", status.toString());
        view.initView(this);
        view.updateView(status);
        // 获取评论数
        Oauth2AccessToken token = AccessTokenKeeper.readAccessToken(this);
        Observable<CommentList> comment =
            ApiServiceHelper.getComments(token.getToken(), status.getId(), 0, 0, 50, 1, 0);

        subscription = comment.retry(3)
               .subscribe(new Observer<CommentList>() {
                   @Override
                   public void onCompleted() {
                       view.setProgressBarVisible(false);
                   }

                   @Override
                   public void onError(Throwable e) {
                       LogUtils.error(e.getMessage());
                       view.setProgressBarVisible(false);
                   }

                   @Override
                   public void onNext(CommentList comments) {
                       view.updateComments(comments.getCommentList());
                   }
               });

    }

    @Override
    protected void onDestroyView() {
        super.onDestroyView();
        if(subscription != null){
            subscription.unsubscribe();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
//        view.onConfigurationChanged(newConfig);
    }
}


