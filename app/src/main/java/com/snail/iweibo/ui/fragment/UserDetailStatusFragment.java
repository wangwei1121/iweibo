package com.snail.iweibo.ui.fragment;
import android.util.Log;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.snail.iweibo.api.ApiServiceHelper;
import com.snail.iweibo.api.WeiBoApiService;
import com.snail.iweibo.mvp.model.StatusList;
import com.snail.iweibo.mvp.view.impl.fragment.IUserDetailFragmentView;
import com.snail.iweibo.oauth.AccessTokenKeeper;
import com.snail.iweibo.oauth.Constants;
import com.snail.iweibo.ui.base.BasePresenterFragment;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * UserDetailStatusFragment
 * Created by alexwan on 16/4/11.
 */
public class UserDetailStatusFragment extends BasePresenterFragment<IUserDetailFragmentView> {


    @Override
    protected void onBindView() {
        super.onBindView();
        loadData();
    }

    /**
     * 加载用户最新微博信息
     */
    private void loadData() {
        Oauth2AccessToken token = AccessTokenKeeper.readAccessToken(getActivity());
        ApiServiceHelper
            .getApiService(Constants.WEIBO_BASE_URL, WeiBoApiService.class)
            .getUserTimeLine(token.getToken(), token.getUid(), 0, 0, 50, 1, 0, 0, 0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<StatusList>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("UserDetailFragment", "loadData - onError : " + e.getMessage());
                }

                @Override
                public void onNext(StatusList statuses) {
                    if (statuses != null && statuses.getStatuses() != null
                        && !statuses.getStatuses().isEmpty()) {
                        view.updateView(statuses.getStatuses());
                    }
                }
            });
    }

    @Override
    protected Class<IUserDetailFragmentView> getViewClass() {
        return IUserDetailFragmentView.class;
    }

    @Override
    protected void onDestroyVU() {
        super.onDestroyVU();
        view.unBindView();
        view = null;
    }
}
