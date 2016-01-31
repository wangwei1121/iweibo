package com.snail.iweibo.ui.fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snail.iweibo.mvp.view.impl.fragment.IRecyclerFragmentView;
import com.snail.iweibo.ui.base.BasePresenterFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerViewFragment
 * Created by alexwan on 16/1/30.
 */
public class RecyclerViewFragment extends BasePresenterFragment<IRecyclerFragmentView> {
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
        initData();
    }

    private void initData() {
        List<String> titles = new ArrayList<>();
        for (int i = 0 ; i < 40 ; i ++){
            titles.add("第 "+ i +" 条微博");
        }
        view.updateView(getActivity(), titles);
    }


    @Override
    protected Class<IRecyclerFragmentView> getViewClass() {
        return IRecyclerFragmentView.class;
    }

    public static Fragment newInstance() {
        return new RecyclerViewFragment();
    }
}
