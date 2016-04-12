package com.snail.iweibo.ui.fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snail.iweibo.mvp.view.impl.fragment.ISettingFragmentView;
import com.snail.iweibo.ui.base.BasePresenterFragment;

/**
 * SettingFragment
 * Created by alexwan on 16/1/30.
 */
public class SettingFragment extends BasePresenterFragment<ISettingFragmentView> {
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
    }

    @Override
    protected void onDestroyVU() {
        super.onDestroyVU();
        view.unBindView();
        view = null;
    }

    @Override
    protected Class<ISettingFragmentView> getViewClass() {
        return ISettingFragmentView.class;
    }

    @Override
    protected void setTabLayout(TabLayout tabLayout) {
        this.tabLayout = tabLayout;
    }

}
