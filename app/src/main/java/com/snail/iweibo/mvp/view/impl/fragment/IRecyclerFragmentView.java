package com.snail.iweibo.mvp.view.impl.fragment;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snail.iweibo.R;
import com.snail.iweibo.mvp.view.IBaseView;
import com.snail.iweibo.ui.adapter.CardViewAdapter;

import java.util.List;

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
    CardViewAdapter cardViewAdapter;
    @Override
    public void init(LayoutInflater inflater, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.frament_recycler , viewGroup , false);
        ButterKnife.bind(this, view);
    }

    @Override
    public View getView() {
        return view;
    }

    public void updateView(Context context , List<String> titles){
        cardViewAdapter = new CardViewAdapter(context, titles);
        recyclerView.setAdapter(cardViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

}
