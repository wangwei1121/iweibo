package com.snail.iweibo.ui.activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.snail.iweibo.R;
import com.snail.iweibo.api.ApiServiceHelper;
import com.snail.iweibo.api.WeiBoApiService;
import com.snail.iweibo.mvp.model.Status;
import com.snail.iweibo.mvp.view.impl.activity.IRetweetActivityView;
import com.snail.iweibo.oauth.AccessTokenKeeper;
import com.snail.iweibo.oauth.Constants;
import com.snail.iweibo.ui.base.BasePresenterActivity;
import com.snail.iweibo.util.LogUtils;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 微博转发
 * Created by alexwan on 16/4/27.
 */
public class RetweetActivity extends BasePresenterActivity<IRetweetActivityView> implements OnClickListener {
    private static final String INTENT_STATUS = "status";
    private Status status;
    private Subscription retweetSub;

    public static void start(Context context, @NonNull Status status) {
        Intent intent = new Intent(context, RetweetActivity.class);
        intent.putExtra(INTENT_STATUS, status);
        context.startActivity(intent);
    }

    @Override
    protected void onBindView() {
        super.onBindView();
        status = (Status) getIntent().getSerializableExtra(INTENT_STATUS);
        view.updateView(status , this);
    }

    @Override
    protected void onDestroyView() {
        super.onDestroyView();
        if (retweetSub != null) {
            retweetSub.unsubscribe();
        }
    }

    @Override
    protected Class<IRetweetActivityView> getViewClass() {
        return IRetweetActivityView.class;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (view.hidePanelAndKeyboard()) {
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.action_choose_image:
                // 从相册选择图片

                break;
            case R.id.action_at_friend:
                // 选择好友

                break;
            case R.id.action_add_topic:
                // 添加话题

                break;
            case R.id.action_add_emotion:
                // 添加表情

                break;
            case R.id.action_retweet:
                // 转发
                repostStatus();
                break;
        }
    }

    /**
     * 转发微博
     */
    private void repostStatus() {
        if(checkTextLength(view.getRepostContent())){
            return;
        }
        String text = view.getRepostContent();
        Oauth2AccessToken token = AccessTokenKeeper.readAccessToken(this);
        long id = status.getRetweetedStatus() == null ? status.getId() : status.getRetweetedStatus().getId();
        retweetSub = ApiServiceHelper
            .getApiService(Constants.WEIBO_BASE_URL, WeiBoApiService.class)
            .repostStatus(token.getToken(), id, text, 0, "")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<Status>() {
                @Override
                public void onCompleted() {
                    LogUtils.info("onCompleted");
                    finish();
                }

                @Override
                public void onError(Throwable e) {
                    LogUtils.error(e.getMessage());
                }

                @Override
                public void onNext(Status status) {

                }
            });
    }

    /**
     * 字符串长度是否超过150
     * @param content content
     * @return true 超过150
     */
    private boolean checkTextLength(String content) {
        return content.length() > 140;
    }

}
