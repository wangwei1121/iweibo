package com.snail.iweibo.mvp.view.impl;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.snail.iweibo.R;
import com.snail.iweibo.mvp.view.IBaseView;
import com.snail.iweibo.ui.adapter.ImageBrowseAdapter;
import com.snail.iweibo.ui.base.BasePresenterActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 图片查看View
 * Created by alexwan on 16/4/21.
 */
public class ImageBrowseActivityView implements IBaseView {
    private View view;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.tool_bar)
    Toolbar toolbar;
    private BasePresenterActivity context;
    private ImageBrowseAdapter adapter;
    @Override
    public void init(Context context, LayoutInflater inflater, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_image_browse , viewGroup , false);
        ButterKnife.bind(this , view);
        this.context = (BasePresenterActivity) context;
    }

    @Override
    public View getView() {
        return view;
    }

    public void updateView(List<String> urls , int position){

        context.setSupportActionBar(toolbar);
        ActionBar actionBar = context.getSupportActionBar();
        if (actionBar != null) {
            //
            actionBar.setDisplayShowHomeEnabled(true);
            // add titile
            actionBar.setDisplayShowTitleEnabled(false);
            // add back icon
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                context.finish();
            }
        });
        adapter = new ImageBrowseAdapter(context , urls);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
    }
}
