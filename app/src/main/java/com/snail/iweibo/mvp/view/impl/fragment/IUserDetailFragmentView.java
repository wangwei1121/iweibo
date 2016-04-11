package com.snail.iweibo.mvp.view.impl.fragment;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.snail.iweibo.R;
import com.snail.iweibo.mvp.model.Status;
import com.snail.iweibo.mvp.view.IBaseView;
import com.snail.iweibo.ui.adapter.StatusListAdapter;
import com.snail.iweibo.ui.adapter.StatusListAdapter.OnItemClickListener;
import com.snail.iweibo.ui.base.BasePresenterActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * IUserDetailFragmentView
 * Created by alexwan on 16/4/11.
 */
public class IUserDetailFragmentView implements IBaseView , OnClickListener , OnItemClickListener{
    private View view;
    private BasePresenterActivity context;
    private StatusListAdapter adapter;
    @Bind(R.id.list_view)
    RecyclerView recyclerView;

    @Override
    public void init(Context context, LayoutInflater inflater, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.fragment_common_list, viewGroup, false);
        ButterKnife.bind(this, view);
        this.context = (BasePresenterActivity) context;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public View getView() {
        return view;
    }

    public void updateView(List<Status> statusList) {
        if (adapter == null) {
            adapter = new StatusListAdapter(context, statusList, this, this);
        } else {
            adapter.addAll(statusList);
        }
        recyclerView.setAdapter(adapter);
    }

    public void unBindView() {
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(int position) {

    }
}
