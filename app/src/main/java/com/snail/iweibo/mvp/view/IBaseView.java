package com.snail.iweibo.mvp.view;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * View - IBaseView
 * Created by alexwan on 16/1/28.
 */
public interface IBaseView {

    void init(Context context , LayoutInflater inflater , ViewGroup viewGroup);

    View getView();
}
