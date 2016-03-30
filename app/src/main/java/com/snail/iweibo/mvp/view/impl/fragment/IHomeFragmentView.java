package com.snail.iweibo.mvp.view.impl.fragment;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snail.iweibo.R;
import com.snail.iweibo.mvp.view.IBaseView;
import com.snail.iweibo.ui.adapter.HomePagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * IMessageFragmentView
 * Created by alexwan on 16/1/30.
 */
public class IHomeFragmentView implements IBaseView {
    private View mView;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    HomePagerAdapter pagerAdapter;
    @Override
    public void init(Context context , LayoutInflater inflater, ViewGroup viewGroup) {
        mView = inflater.inflate(R.layout.fragment_home, viewGroup, false);
        ButterKnife.bind(this , mView);
    }

    @Override
    public View getView() {
        return mView;
    }

    public void initView(FragmentActivity activity) {
        viewPager.setSaveEnabled(true);
        FragmentManager manager = activity.getSupportFragmentManager();
        pagerAdapter = new HomePagerAdapter(manager);
        viewPager.setAdapter(pagerAdapter);
    }

    /**
     * setTabLayout
     * @param tabLayout tabLayout
     */
    public void setTabLayout(TabLayout tabLayout) {
        tabLayout.setupWithViewPager(viewPager);
    }

}
