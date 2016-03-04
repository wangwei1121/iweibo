package com.snail.iweibo.ui.base;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snail.iweibo.mvp.view.IBaseView;

/**
 * BasePresenterFragment
 * Created by alexwan on 16/1/30.
 */
public abstract class BasePresenterFragment<V extends IBaseView> extends Fragment {
    protected V mView;
    protected TabLayout tabLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        try {
            this.mView = getViewClass().newInstance();
            this.mView.init(inflater , container);
            view = this.mView.getView();
            onBindView();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        onDestroyVU();
        this.mView = null;
        super.onDestroyView();
    }

    protected void onDestroyVU(){}
    protected void onBindView(){}
    protected void setTabLayout(TabLayout tabLayout){};
    protected abstract Class<V> getViewClass();
}
