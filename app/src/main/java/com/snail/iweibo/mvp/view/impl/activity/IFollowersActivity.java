package com.snail.iweibo.mvp.view.impl.activity;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snail.iweibo.R;
import com.snail.iweibo.mvp.model.Follower;
import com.snail.iweibo.mvp.view.IBaseView;
import com.snail.iweibo.ui.adapter.FollowersListAdapter;
import com.snail.iweibo.ui.base.BasePresenterActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by luckyliang on 16/4/16.
 */
public class IFollowersActivity implements IBaseView, View.OnClickListener, FollowersListAdapter.OnItemClickListener {

    private View view;
    private BasePresenterActivity context;
    private FollowersListAdapter adapter;
    @Bind(R.id.tool_bar)
    Toolbar toolbar;
    @Bind(R.id.list_view)
    RecyclerView listView;

    @Override
    public void init(Context context, LayoutInflater inflater, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_followers, viewGroup);
        ButterKnife.bind(this, view);
        this.context = (BasePresenterActivity) context;
        listView.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public View getView() {
        return view;
    }

    public void initView(final BasePresenterActivity context) {
        if (toolbar == null) {
            return;
        }
        context.setSupportActionBar(toolbar);
        ActionBar actionBar = context.getSupportActionBar();
        if (actionBar != null) {
            //
            actionBar.setDisplayShowHomeEnabled(true);
            // add titile
            actionBar.setDisplayShowTitleEnabled(true);
            // add back icon
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.finish();
            }
        });
//        toolbarLayout.setTitleEnabled(false);
    }

    public void initView(ArrayList<Follower> followers) {
        if (adapter == null)
            adapter = new FollowersListAdapter(context, followers, IFollowersActivity.this, IFollowersActivity.this);
        else
            adapter.addAll(followers);
        listView.setAdapter(adapter);
    }

    public void updateView() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(int position) {

    }
}
