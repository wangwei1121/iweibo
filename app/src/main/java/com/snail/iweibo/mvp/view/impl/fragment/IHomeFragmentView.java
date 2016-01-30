package com.snail.iweibo.mvp.view.impl.fragment;
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

/**
 * IMessageFragmentView
 * Created by alexwan on 16/1/30.
 */
public class IHomeFragmentView implements IBaseView {
    private View mView;
    ViewPager viewPager;
    HomePagerAdapter homePagerAdapter;
    TabLayout tabLayout;
    @Override
    public void init(LayoutInflater inflater, ViewGroup viewGroup) {
        mView = inflater.inflate(R.layout.fragment_home, viewGroup, false);
        viewPager = (ViewPager) mView.findViewById(R.id.view_pager);
        viewPager.setSaveEnabled(false);
    }

    @Override
    public View getView() {
        return mView;
    }

    /**
     * initView
     */
    public void initView(FragmentActivity activity) {
        FragmentManager manager = activity.getSupportFragmentManager();
        homePagerAdapter = new HomePagerAdapter(manager);
        viewPager.setAdapter(homePagerAdapter);
    }

    /**
     * @param tabLayout
     */
    public void setTabLayout(TabLayout tabLayout) {
        tabLayout.setupWithViewPager(viewPager);
    }

}
