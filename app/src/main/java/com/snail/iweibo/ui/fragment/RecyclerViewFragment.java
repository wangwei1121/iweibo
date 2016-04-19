package com.snail.iweibo.ui.fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.snail.iweibo.R;
import com.snail.iweibo.api.ApiServiceHelper;
import com.snail.iweibo.api.WeiBoApiService;
import com.snail.iweibo.mvp.model.Status;
import com.snail.iweibo.mvp.model.StatusList;
import com.snail.iweibo.mvp.view.impl.fragment.IRecyclerFragmentView;
import com.snail.iweibo.oauth.AccessTokenKeeper;
import com.snail.iweibo.oauth.Constants;
import com.snail.iweibo.ui.adapter.StatusListAdapter;
import com.snail.iweibo.ui.base.BasePresenterFragment;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * RecyclerViewFragment - FragmentPresenter
 * Created by alexwan on 16/1/30.
 */
public class RecyclerViewFragment extends BasePresenterFragment<IRecyclerFragmentView> implements OnRefreshListener,
    OnClickListener {
    private StatusListAdapter cardViewAdapter;
    private Status lastStatus;
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
        view.unBindView();
        view = null;
    }

    @Override
    protected void onBindView() {
        super.onBindView();
        view.setOnRefreshListener(this);
        initData();
    }

    private void initData() {
        String token;
        Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(getActivity());
        if (!TextUtils.isEmpty(accessToken.getToken())) {
            token = accessToken.getToken();
        } else {
            token = Constants.TOKEN;
        }
        view.refresh(true);
        ApiServiceHelper
            .getApiService(Constants.WEIBO_BASE_URL, WeiBoApiService.class)
            .getFriendsTimeLine(token, 0, 0, 50, 1, 0, 0, 0)
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
                        cardViewAdapter = new StatusListAdapter(getActivity(), statuses, RecyclerViewFragment
                            .this, view);
                        view.updateView(getActivity(), cardViewAdapter);
                        lastStatus = statuses.get(statuses.size() - 1);
                    }
                }
            });


    }

    public void loadMore() {
        //
        Oauth2AccessToken token = AccessTokenKeeper.readAccessToken(getActivity());
        long sinceID = lastStatus == null? 0 : lastStatus.getId();
        ApiServiceHelper
            .getApiService(Constants.WEIBO_BASE_URL, WeiBoApiService.class)
            .getFriendsTimeLine(token.getToken(), sinceID , 0, 50, 1, 0, 0, 0)
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
                        Log.i("RecyclerViewFragment ", "onNext : " + list.getStatuses().toString());
                        if (cardViewAdapter == null) {
                            cardViewAdapter = new StatusListAdapter(getActivity(),
                                list.getStatuses(), RecyclerViewFragment.this, view);
                            view.updateView(getActivity(), cardViewAdapter);
                        }else{
                            cardViewAdapter.addAll(list.getStatuses());
                        }
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
        // 下拉刷新
        view.refresh(true);
        loadMore();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.action_favorite_layout:
                // 收藏
                if (cardViewAdapter != null) {
                    // 调用收藏接口 用户是否登录
                    // status id
                    Toast.makeText(getContext(), "收藏", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.action_relay_layout:
                // 转发
                if (cardViewAdapter != null) {
                    // 用户是否登录
                    // status id
                    Toast.makeText(getContext(), "转发", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.action_comment_layout:
                // 评论跳转到微博正文
                if (cardViewAdapter != null) {
                    // 用户是否登录
                    // status id
                    Toast.makeText(getContext(), "评论", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.action_like_layout:
                // 点赞
                if (cardViewAdapter != null) {
                    // 用户是否登录
                    // status id
                    Toast.makeText(getContext(), "点赞", Toast.LENGTH_SHORT).show();
                    // 调用数据
                }
                break;
            default:
                Toast.makeText(getContext(), String.valueOf(v.getTag()), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
    }


}
