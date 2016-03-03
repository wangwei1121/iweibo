package com.snail.iweibo.ui.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.snail.iweibo.R;
import com.snail.iweibo.api.ApiServiceHelper;
import com.snail.iweibo.mvp.model.PublicNews;
import com.snail.iweibo.mvp.model.Statuse;
import com.snail.iweibo.oauth.Constants;
import com.snail.iweibo.ui.adapter.PublicNewsAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class NewsActivity extends AppCompatActivity implements OnItemClickListener,OnScrollListener {

    private ListView listView;
    private SimpleAdapter simple_adapter;
    private List<Map<String, Object>> list;
    private int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        // 匹配布局文件中的ListView控件
        listView = (ListView) findViewById(R.id.listView);

        SharedPreferences sp = getSharedPreferences(Constants.PROJECT_NAME, Activity.MODE_PRIVATE);
        Observable<PublicNews> observable =  ApiServiceHelper.getPublicTimeLine(sp.getString(Constants.SINA_TOKEN,""), 20, 1, 0);
        observable.subscribe(
                new Subscriber<PublicNews>() {
                    @Override
                    public void onCompleted() {
                        Log.d("com.snail.iweibo","Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.e("com.snail.iweibo",e.getMessage());
                    }

                    @Override
                    public void onNext(PublicNews publicNews) {
                        SharedPreferences sp = getSharedPreferences(Constants.PROJECT_NAME, Activity.MODE_PRIVATE);
                        Observable<PublicNews> observable =  ApiServiceHelper.getPublicTimeLine(sp.getString(Constants.SINA_TOKEN,""), 20, 1, 0);
                        observable.subscribe(new Action1<PublicNews>() {
                            @Override
                            public void call(PublicNews publicNews) {
                                List<Statuse> list = publicNews.getStatuses();
                                if(null != list && list.size() > 0){
                                    final List<String>  textList = new ArrayList<String>(list.size());
                                    for(Statuse statuse:list){
                                        textList.add(statuse.getText());
                                    }

                                    PublicNewsAdapter adapter = new PublicNewsAdapter(NewsActivity.this,list);
                                    listView.setAdapter(adapter);
                                    listView.setOnItemClickListener(NewsActivity.this);
                                }
                            }
                        });
                    }
                }
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // (5)事件处理监听器方法
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        // TODO Auto-generated method stub
        String text = listView.getItemAtPosition(position) + "";
        Toast.makeText(NewsActivity.this, "position=" + position + " content=" + text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub
        // 手指离开屏幕前，用力滑了一下
        if (scrollState == SCROLL_STATE_FLING) {
            Toast.makeText(NewsActivity.this, "用力滑一下",Toast.LENGTH_SHORT).show();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("text", "滚动添加 "+i++);
            map.put("image", R.drawable.ic_launcher);
            list.add(map);
            listView.setAdapter(simple_adapter);
            simple_adapter.notifyDataSetChanged();
        } else
            // 停止滚动
            if (scrollState == SCROLL_STATE_IDLE) {
            } else if (scrollState == SCROLL_STATE_TOUCH_SCROLL) {// 正在滚动

            }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
        // TODO Auto-generated method stub

    }
}
