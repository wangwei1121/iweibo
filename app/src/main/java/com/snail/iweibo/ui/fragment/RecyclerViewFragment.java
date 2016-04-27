package com.snail.iweibo.ui.fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.snail.iweibo.api.ApiServiceHelper;
import com.snail.iweibo.api.WeiBoApiService;
import com.snail.iweibo.mvp.model.Status;
import com.snail.iweibo.mvp.model.StatusList;
import com.snail.iweibo.mvp.view.impl.fragment.IRecyclerFragmentView;
import com.snail.iweibo.oauth.AccessTokenKeeper;
import com.snail.iweibo.oauth.Constants;
import com.snail.iweibo.ui.base.BasePresenterFragment;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * RecyclerViewFragment - FragmentPresenter
 * Created by alexwan on 16/1/30.
 */
public class RecyclerViewFragment extends BasePresenterFragment<IRecyclerFragmentView> implements OnRefreshListener {
    private Status lastStatus;
    private Subscription subscription;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void onDestroyVU() {
        super.onDestroyVU();
        if(subscription != null){
            subscription.unsubscribe();
        }
        view.unBindView();
    }

    @Override
    protected void onBindView() {
        super.onBindView();
        view.setOnRefreshListener(this);
        initData(0);
    }

    private void initData(long sinceId) {
        String token;
        Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(getActivity());
        if (!TextUtils.isEmpty(accessToken.getToken())) {
            token = accessToken.getToken();
        } else {
            token = Constants.TOKEN;
        }
        view.refresh(true);
        subscription = ApiServiceHelper
            .getApiService(Constants.WEIBO_BASE_URL, WeiBoApiService.class)
            .getFriendsTimeLine(token, sinceId , 0, 50, 1, 0, 0, 0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<StatusList>() {
                @Override
                public void onCompleted() {
                    view.refresh(false);
                    Log.i("RecyclerViewFragment ", "onCompleted : ");
                }

                @Override
                public void onError(Throwable e) {
                    view.refresh(false);
                    Log.i("RecyclerViewFragment ", "onError - Error :" + e.getMessage());
                }

                @Override
                public void onNext(StatusList list) {
                    if (list.getStatuses() != null && !list.getStatuses().isEmpty()) {
                        List<Status> statuses =  list.getStatuses();
                        Log.i("RecyclerViewFragment ", "onNext : " + list.getStatuses().toString());
                        view.updateView(statuses);
                        lastStatus = statuses.get(0);
                    }
                }
            });


    }

    @Override
    protected Class<IRecyclerFragmentView> getViewClass() {
        return IRecyclerFragmentView.class;
    }

    public static Fragment newInstance(int position) {
        return new RecyclerViewFragment();
    }

    @Override
    public void onRefresh() {
        view.refresh(true);
        long sinceID = lastStatus == null? 0 : lastStatus.getId();
        initData(sinceID);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }


}
