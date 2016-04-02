package com.snail.iweibo.ui.fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snail.iweibo.api.ApiServiceHelper;
import com.snail.iweibo.mvp.model.StatusList;
import com.snail.iweibo.mvp.view.impl.fragment.IRecyclerFragmentView;
import com.snail.iweibo.oauth.Constants;
import com.snail.iweibo.ui.adapter.StatusListAdapter;
import com.snail.iweibo.ui.base.BasePresenterFragment;

import rx.Subscriber;

/**
 * RecyclerViewFragment
 * Created by alexwan on 16/1/30.
 */
public class RecyclerViewFragment extends BasePresenterFragment<IRecyclerFragmentView> implements OnRefreshListener{
    private StatusListAdapter cardViewAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    protected void onBindView() {
        super.onBindView();
        view.setOnRefreshListener(this);
        initData();
    }

    private void initData() {
        SharedPreferences preferences =  getActivity().getSharedPreferences(Constants.PROJECT_NAME , Context.MODE_PRIVATE);
//        String token = preferences.getString(Constants.SINA_TOKEN , "");
        Log.i("RecyclerViewFragment " , Constants.TOKEN);
        if(TextUtils.isEmpty(Constants.TOKEN)){
            return;
        }
        ApiServiceHelper.getPublicTimeLine(Constants.TOKEN, 50, 1, 0)
                        .subscribe(new Subscriber<StatusList>() {
            @Override
            public void onCompleted() {
                Log.i("RecyclerViewFragment " , "onCompleted : ");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("RecyclerViewFragment " , "onError - Error :" + e.getMessage());
            }

            @Override
            public void onNext(StatusList list) {
                if(list.getStatuses() != null && !list.getStatuses().isEmpty()){
                    Log.i("RecyclerViewFragment " , "onNext : " +list.getStatuses().toString());
                    cardViewAdapter = new StatusListAdapter(getActivity(), list.getStatuses());
                    view.updateView(getActivity(), cardViewAdapter);
                    view.refresh(false);
                }
            }
        });


    }


    @Override
    protected Class<IRecyclerFragmentView> getViewClass() {
        return IRecyclerFragmentView.class;
    }

    public static Fragment newInstance() {
        return new RecyclerViewFragment();
    }

    @Override
    public void onRefresh() {
        // 下拉刷新

        view.refresh(false);
    }
}
