package com.snail.iweibo.mvp.view.impl.fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snail.iweibo.R;
import com.snail.iweibo.mvp.view.IBaseView;

import butterknife.ButterKnife;

/**
 * ISettingFragmentView
 * Created by alexwan on 16/1/30.
 */
public class ISettingFragmentView implements IBaseView {
    protected View mView;
    @Override
    public void init(Context context , LayoutInflater inflater, ViewGroup viewGroup) {
        mView = inflater.inflate(R.layout.fragment_setting , viewGroup , false);
        ButterKnife.bind(this, mView);
    }

    @Override
    public View getView() {
        return mView;
    }

}
