package com.snail.iweibo.ui.fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.snail.iweibo.R;
import com.snail.iweibo.api.ApiServiceHelper;
import com.snail.iweibo.mvp.model.Status;
import com.snail.iweibo.mvp.model.StatusList;
import com.snail.iweibo.mvp.view.impl.fragment.IRecyclerFragmentView;
import com.snail.iweibo.oauth.Constants;
import com.snail.iweibo.ui.activity.StatusDetailActivity;
import com.snail.iweibo.ui.adapter.StatusListAdapter;
import com.snail.iweibo.ui.adapter.StatusListAdapter.OnItemClickListener;
import com.snail.iweibo.ui.base.BasePresenterFragment;

import rx.Subscriber;

/**
 * RecyclerViewFragment - FragmentPresenter
 * Created by alexwan on 16/1/30.
 */
public class RecyclerViewFragment extends BasePresenterFragment<IRecyclerFragmentView> implements OnRefreshListener ,
    OnClickListener , OnItemClickListener{
    private StatusListAdapter cardViewAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    protected void onBindView() {
        super.onBindView();
        view.setOnRefreshListener(this);
        initData();
    }

    private void initData() {
        SharedPreferences preferences =  getActivity().getSharedPreferences(Constants.PROJECT_NAME , Context.MODE_PRIVATE);
//        String token = preferences.getString(Constants.SINA_TOKEN , "");
        Log.i("RecyclerViewFragment " , Constants.TOKEN);
        if(TextUtils.isEmpty(Constants.TOKEN)){
            return;
        }
        ApiServiceHelper.getPublicTimeLine(Constants.TOKEN, 50, 1, 0)
                        .subscribe(new Subscriber<StatusList>() {
            @Override
            public void onCompleted() {
                Log.i("RecyclerViewFragment " , "onCompleted : ");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("RecyclerViewFragment " , "onError - Error :" + e.getMessage());
            }

            @Override
            public void onNext(StatusList list) {
                if(list.getStatuses() != null && !list.getStatuses().isEmpty()){
                    Log.i("RecyclerViewFragment " , "onNext : " +list.getStatuses().toString());
                    cardViewAdapter = new StatusListAdapter(getActivity(), list.getStatuses() , RecyclerViewFragment
                        .this , RecyclerViewFragment.this);
                    view.updateView(getActivity(), cardViewAdapter);
                    view.refresh(false);
                }
            }
        });


    }


    @Override
    protected Class<IRecyclerFragmentView> getViewClass() {
        return IRecyclerFragmentView.class;
    }

    public static Fragment newInstance() {
        return new RecyclerViewFragment();
    }

    @Override
    public void onRefresh() {
        // 下拉刷新

        view.refresh(false);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.action_favorite_layout:
                // 收藏
                if(cardViewAdapter != null){
                    // 调用收藏接口 用户是否登录
                    // status id
                    Toast.makeText(getContext() , "收藏" , Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.action_relay_layout:
                // 转发
                if(cardViewAdapter != null){
                    // 用户是否登录
                    // status id
                    Toast.makeText(getContext() , "转发" , Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.action_comment_layout:
                // 评论跳转到微博正文
                if(cardViewAdapter != null){
                    // 用户是否登录
                    // status id
                    Toast.makeText(getContext() , "评论" , Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.action_like_layout:
                // 点赞
                if(cardViewAdapter != null){
                    // 用户是否登录
                    // status id
                    Toast.makeText(getContext() , "点赞" , Toast.LENGTH_SHORT).show();
                    // 调用数据
                }
                break;
            default:
                Toast.makeText(getContext() , String.valueOf(v.getTag()) , Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        Status status = cardViewAdapter.getStatus(position);
        Intent intent = new Intent(this.getActivity() , StatusDetailActivity.class);
        intent.putExtra("status" , status);
        this.startActivity(intent);
    }
}
