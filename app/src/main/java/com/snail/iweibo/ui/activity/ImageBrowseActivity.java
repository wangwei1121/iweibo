package com.snail.iweibo.ui.activity;
import android.content.Context;
import android.content.Intent;

import com.snail.iweibo.mvp.model.Status;
import com.snail.iweibo.mvp.model.Status.ThumbnailPic;
import com.snail.iweibo.mvp.view.impl.ImageBrowseActivityView;
import com.snail.iweibo.ui.base.BasePresenterActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * ImageBrowseActivity
 * Created by alexwan on 16/4/21.
 */
public class ImageBrowseActivity extends BasePresenterActivity<ImageBrowseActivityView> {
    // 单图
    private static int INTENT_TYPE_SINGLE = 0x01;
    // 多图
    private static int INTENT_TYPE_MULTI = 0x02;
    // 微博
    private static int INTENT_TYPE_STATUS = 0x03;

    public static void start(Context context , Status status , int position){
        Intent intent = new Intent(context , ImageBrowseActivity.class);
        intent.putExtra("status" , status);
        intent.putExtra("type" , INTENT_TYPE_STATUS);
        intent.putExtra("position" , position);
        context.startActivity(intent);
    }

    public static void start(Context context , ArrayList<String> urls , int position){
        Intent intent = new Intent(context , ImageBrowseActivity.class);
        intent.putStringArrayListExtra("urls" , urls);
        intent.putExtra("type" , INTENT_TYPE_MULTI);
        intent.putExtra("position" , position);
        context.startActivity(intent);
    }

    public static void start(Context context , String url){
        Intent intent = new Intent(context , ImageBrowseActivity.class);
        intent.putExtra("url" , url);
        intent.putExtra("type" , INTENT_TYPE_SINGLE);
        context.startActivity(intent);
    }
    @Override
    protected void onBindView() {
        super.onBindView();
        initData();
    }

    private void initData() {
        int type = getIntent().getIntExtra("type" , INTENT_TYPE_MULTI);
        int position = getIntent().getIntExtra("position" , 0);
        List<String> urls = new ArrayList<String>();
        switch (type){
            case 0x01:
                // 单图
                String url = getIntent().getStringExtra("url");
                urls.add(url);
                break;
            case 0x02:
                // 多图
                urls = getIntent().getStringArrayListExtra("urls");
                break;
            case 0x03:
                // 单个微博
                Status status = (Status) getIntent().getSerializableExtra("status");
                List<ThumbnailPic> pics = status.getPicUrls();
                if(pics != null && !pics.isEmpty()){
                    for(int i = 0 ; i < pics.size() ; i++ ){
                        String pic = pics.get(i).getThumbnailPic().replace("thumbnail", "bmiddle");
                        urls.add(pic);
                    }
                }
                break;
        }

        view.updateView(urls , position);
    }

    @Override
    protected void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    protected Class<ImageBrowseActivityView> getViewClass() {
        return ImageBrowseActivityView.class;
    }

}
