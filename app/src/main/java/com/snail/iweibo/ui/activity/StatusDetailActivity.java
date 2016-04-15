package com.snail.iweibo.ui.activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;

import com.snail.iweibo.api.ApiServiceHelper;
import com.snail.iweibo.mvp.model.Comment;
import com.snail.iweibo.mvp.model.CommentList;
import com.snail.iweibo.mvp.model.Status;
import com.snail.iweibo.mvp.view.impl.activity.IStatusDetailActivityView;
import com.snail.iweibo.oauth.Constants;
import com.snail.iweibo.ui.base.BasePresenterActivity;

import java.util.ArrayList;

import rx.Observer;
import rx.functions.Func1;

/**
 * 微博正文Activity
 * Created by alexwan on 16/4/4.
 */
public class StatusDetailActivity extends BasePresenterActivity<IStatusDetailActivityView> {

    public static void start(Context context , Status status){
        Intent intent = new Intent(context , StatusDetailActivity.class);
        intent.putExtra("status", status);
        context.startActivity(intent);
    }
    @Override
    protected Class<IStatusDetailActivityView> getViewClass() {
        return IStatusDetailActivityView.class;
    }

    @Override
    protected void onBindView() {
        super.onBindView();
        Status status = (Status) getIntent().getSerializableExtra("status");
        Log.i("StatusDetailActivity" , status.toString());
        view.initView(this);
        view.updateView(status);
        // 获取评论数
        ApiServiceHelper.getComments(Constants.TOKEN , status.getId() , 0 , 0 , 50 , 1 , 0)
                        .map(new Func1<CommentList, ArrayList<Comment>>() {
                            @Override
                            public ArrayList<Comment> call(CommentList commentList) {
                                return commentList.getCommentList();
                            }
                        })
                        .subscribe(new Observer<ArrayList<Comment>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(ArrayList<Comment> comments) {
                                view.updateComments(comments);
                            }
                        });


    }

    @Override
    protected void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
//        view.onConfigurationChanged(newConfig);
    }
}


