package com.snail.iweibo.ui.fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snail.iweibo.mvp.view.impl.fragment.IHomeFragmentView;
import com.snail.iweibo.ui.base.BasePresenterFragment;

/**
 * MessageFragment
 * Created by alexwan on 16/1/30.
 */
public class HomeFragment extends BasePresenterFragment<IHomeFragmentView> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void onBindView() {
        super.onBindView();
        view.updateView();
    }



    @Override
    protected Class<IHomeFragmentView> getViewClass() {
        return IHomeFragmentView.class;
    }

    @Override
    public void setTabLayout(TabLayout tabLayout) {
        this.tabLayout = tabLayout;
    }
}
