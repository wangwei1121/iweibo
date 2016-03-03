package com.snail.iweibo.ui.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.snail.iweibo.R;
import com.snail.iweibo.api.ApiServiceHelper;
import com.snail.iweibo.mvp.model.PublicNews;
import com.snail.iweibo.mvp.model.Statuse;
import com.snail.iweibo.mvp.view.impl.fragment.IHomeFragmentView;
import com.snail.iweibo.oauth.Constants;
import com.snail.iweibo.ui.adapter.HomeAdapter;
import com.snail.iweibo.ui.adapter.PublicNewsAdapter;
import com.snail.iweibo.ui.base.BasePresenterFragment;
import com.snail.iweibo.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * MessageFragment
 * Created by alexwan on 16/1/30.
 */
public class HomeFragment extends ListFragment {
    private HomeAdapter adapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        initData();
        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }

    private void initData() {
        String token = SharedPreferencesUtil.getData(getContext(), Constants.SINA_TOKEN);
        if(TextUtils.isEmpty(token)){
            return;
        }
        Observable<PublicNews> observable = ApiServiceHelper.getPublicTimeLine(token, 20, 1, 0);
        observable.subscribe(
                new Subscriber<PublicNews>() {
                    @Override
                    public void onCompleted() {
                        Log.d("com.snail.iweibo", "Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.e("com.snail.iweibo", e.getMessage());
                    }

                    @Override
                    public void onNext(PublicNews publicNews) {
                        List<Statuse> list = publicNews.getStatuses();
                        if (null != list && list.size() > 0) {
                            adapter = new HomeAdapter(getActivity(), list);
                            setListAdapter(adapter);
                        }
                    }
                });
    }

}
