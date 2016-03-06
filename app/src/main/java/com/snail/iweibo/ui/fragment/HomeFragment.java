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
import com.snail.iweibo.ui.base.RefreshListView;
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

    @Bind(R.id.list_home)
    RefreshListView listView;

    private int count = 5;

    private int page = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, v);

        listView.setIRefreshListener(new RefreshListView.IRefreshListener() {
            @Override
            public void onReflash() {
                page = 1;
                initData(1);
            }
        });
        listView.setILoadListener(new RefreshListView.ILoadListener(){
            @Override
            public void onLoad() {
                page++;
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
        Log.e("com.snail.iweibo", "token-->" + token);
        if (TextUtils.isEmpty(token)) {
            return;
        }
        if(page > 1){
            try{
                Thread.sleep(3 * 1000);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        Log.e("com.snail.iweibo", "page-->" + page);
        Observable<PublicNews> observable = ApiServiceHelper.getFriendsTimeline(token,null,null,count,page,null,null,null);
        observable.subscribe(
                new Subscriber<PublicNews>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.e("com.snail.iweibo", e.getMessage());
                    }

                    @Override
                    public void onNext(PublicNews publicNews) {
                        Log.e("com.snail.iweibo", "size-->" + publicNews.getStatuses().size());
                        if (null != publicNews && null != publicNews.getStatuses() && publicNews.getStatuses().size() > 0) {
                            if(null == adapter){
                                adapter = new HomeAdapter(getActivity(),publicNews.getStatuses());
                                listView.setAdapter(adapter);
                            }else{
                                List<Statuse> list = adapter.getStatuseList();
                                for(Statuse statuse:publicNews.getStatuses()){
                                    list.add(statuse);
                                }
                                adapter.notifyDataSetChanged();
                            }
                            listView.reflashComplete();
                            //通知listview加载完毕
                            listView.loadComplete();
                        }
                    }
                });
    }

}
