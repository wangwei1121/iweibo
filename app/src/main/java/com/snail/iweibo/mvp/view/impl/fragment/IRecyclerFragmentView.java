package com.snail.iweibo.mvp.view.impl.fragment;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snail.iweibo.R;
import com.snail.iweibo.mvp.view.IBaseView;
import com.snail.iweibo.ui.adapter.CardViewAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * IRecyclerFragmentView
 * Created by alexwan on 16/1/30.
 */
public class IRecyclerFragmentView implements IBaseView {
    protected View view;
    @Bind(R.id.recycler_layout)
    RecyclerView recyclerView;
    @Bind(R.id.swipe_refresh_container)
    SwipeRefreshLayout refreshLayout;

    @Override
    public void init(Context context , LayoutInflater inflater, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.frament_recycler , viewGroup , false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public View getView() {
        return view;
    }

    public void updateView(Context context , CardViewAdapter adapter){
        recyclerView.setAdapter(adapter);
    }

    /**
     * 停止或开始刷新
     * @param refresh refresh
     */
    public void refresh(boolean refresh){
        if((refresh && refreshLayout.isRefreshing()) || !refresh){
            refreshLayout.setRefreshing(refresh);
        }
    }

    public void setOnRefreshListener(OnRefreshListener listener){
        refreshLayout.setOnRefreshListener(listener);
    }
}
