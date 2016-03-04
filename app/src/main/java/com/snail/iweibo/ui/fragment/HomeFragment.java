package com.snail.iweibo.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.snail.iweibo.oauth.Constants;
import com.snail.iweibo.ui.adapter.HomeAdapter;
import com.snail.iweibo.ui.base.RefreshLayout;
import com.snail.iweibo.util.SharedPreferencesUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;

/**
 * MessageFragment
 * Created by alexwan on 16/1/30.
 */
public class HomeFragment extends ListFragment{

    private HomeAdapter adapter = null;

    private static final int REFRESH_COMPLETE = 0X110;

    private Handler mHandler = null;

    private int count = 20;

    private int page = 1;

    @Bind(R.id.swipe_layout_home)
    RefreshLayout mSwipeLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case REFRESH_COMPLETE:
                        mSwipeLayout.setRefreshing(false);
                        break;
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, v);

        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeLayout.setSize(SwipeRefreshLayout.LARGE);
        mSwipeLayout.setProgressViewEndTarget(true, 100);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("com.snail.iweibo", "mSwipeLayout refresh");
                page = 1;
                initData(page);
            }

        });

        // 加载监听器
        mSwipeLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                page++;
                Log.d("com.snail.iweibo", "mSwipeLayout load more " + page);
                initData(page);
            }
        });
        initData(1);
        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }

    private void initData(int page) {
        String token = SharedPreferencesUtil.getData(getContext(), Constants.SINA_TOKEN);
        Log.d("com.snail.iweibo", "token-->" + token);
        if (TextUtils.isEmpty(token)) {
            return;
        }
        Observable<PublicNews> observable = ApiServiceHelper.getFriendsTimeline(token,null,null,20,page,null,null,null);
        observable.subscribe(
                new Subscriber<PublicNews>() {
                    @Override
                    public void onCompleted() {
                        Log.d("com.snail.iweibo", "Completed");
                        mHandler.sendEmptyMessage(REFRESH_COMPLETE);
                        mSwipeLayout.setLoading(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.e("com.snail.iweibo", e.getMessage());
                        mSwipeLayout.setRefreshing(false);
                        mSwipeLayout.setLoading(false);

                    }

                    @Override
                    public void onNext(PublicNews publicNews) {
                        Log.d("com.snail.iweibo", "size-->" + publicNews.getStatuses().size());
                        if (null != publicNews && null != publicNews.getStatuses() && publicNews.getStatuses().size() > 0) {
                            if(null == adapter){
                                adapter = new HomeAdapter(getActivity(),publicNews.getStatuses());
                                setListAdapter(adapter);
                            }else{
                                List<Statuse> list = adapter.getStatuseList();
                                for(Statuse statuse:publicNews.getStatuses()){
                                    list.add(statuse);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

}
