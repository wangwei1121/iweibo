package com.snail.iweibo.mvp.view.impl.fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snail.iweibo.R;
import com.snail.iweibo.mvp.view.IBaseView;

/**
 * IMessageFragmentView
 * Created by alexwan on 16/1/30.
 */
public class IHomeFragmentView implements IBaseView {
    private View mView;
    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    public void init(LayoutInflater inflater, ViewGroup viewGroup) {
        mView = inflater.inflate(R.layout.fragment_home, viewGroup , false);
        tabLayout = (TabLayout) mView.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) mView.findViewById(R.id.view_pager);
    }

    @Override
    public View getView() {
        return mView;
    }

}
