package com.snail.iweibo.mvp.view;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by alexwan on 16/1/28.
 */
public interface IBaseActivityView {
    void init(LayoutInflater inflater , ViewGroup viewGroup);
    View getView();
}
