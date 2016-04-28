package com.snail.iweibo.mvp.view.impl;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snail.iweibo.mvp.view.IBaseView;

/**
 * Created by alexwan on 16/4/27.
 */
public class IFriendListActivityView implements IBaseView {
    private View view;
    @Override
    public void init(Context context, LayoutInflater inflater, ViewGroup viewGroup) {
        
    }

    @Override
    public View getView() {
        return view;
    }
}
