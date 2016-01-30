package com.snail.iweibo.ui.fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snail.iweibo.mvp.view.impl.fragment.ISettingFragmentView;

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
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    protected Class<ISettingFragmentView> getViewClass() {
        return ISettingFragmentView.class;
    }
    //
}
