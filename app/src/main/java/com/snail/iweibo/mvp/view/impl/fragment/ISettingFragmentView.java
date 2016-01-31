package com.snail.iweibo.mvp.view.impl.fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snail.iweibo.R;
import com.snail.iweibo.mvp.view.IBaseView;

/**
 * ISettingFragmentView
 * Created by alexwan on 16/1/30.
 */
public class ISettingFragmentView implements IBaseView {
    protected View mView;
    @Override
    public void init(LayoutInflater inflater, ViewGroup viewGroup) {
        mView = inflater.inflate(R.layout.fragment_setting , viewGroup , false);
    }

    @Override
    public View getView() {
        return mView;
    }

}
