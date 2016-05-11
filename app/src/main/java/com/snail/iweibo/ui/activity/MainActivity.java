package com.snail.iweibo.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.snail.iweibo.R;
import com.snail.iweibo.adapter.FriendsTimelineAdapter;
import com.snail.iweibo.network.HttpUtils;
import com.snail.iweibo.ui.BaseActivity;
import com.snail.iweibo.ui.view.LoadSwipeRefreshLayout;
import com.snail.iweibo.util.Constants;
import com.snail.iweibo.util.Keys;
import com.snail.iweibo.util.NetworkUtil;
import com.snail.iweibo.util.SharedPreferencesUtil;
import com.snail.iweibo.util.StringUtils;
import com.snail.iweibo.util.ThreadPoolUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainActivity extends BaseActivity implements LoadSwipeRefreshLayout.OnRefreshListener, LoadSwipeRefreshLayout.OnLoadListener {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private TextView noSignText;
    private ListView listView;
    private FriendsTimelineAdapter adapter;

    private LoadSwipeRefreshLayout swipeRefreshLayout;

    private Handler handler = null;

    private Runnable networkRunnable;

    private static final int LIST_UPDATE = 0x1;
    private static final int NETWORK_CONNECTED = 0x2;
    private static final int EXPIRED_TOKEN = 0x3;
    private static final int NETWORK_DISCONNECTED = 0x4;

    private int page = 1;
    private int pageCount = 10;
    List<Map<String, Object>> dataList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MainActivity.LIST_UPDATE:
                        noSignText.setVisibility(TextView.GONE);
                        listView.setVisibility(ListView.VISIBLE);
                        List<Map<String, Object>> list = (List<Map<String, Object>>) msg.obj;
                        if(null == adapter){
                            dataList = new ArrayList<>(list);
                            adapter = new FriendsTimelineAdapter(MainActivity.this, dataList);
                            listView.setAdapter(adapter);
                        }else if (null != list && list.size() > 0) {
                            if (page == 1) {
                                dataList.clear();
                                dataList.addAll(list);
                            } else {
                                dataList.addAll(list);
                            }
                            adapter.notifyDataSetChanged();
                        }else{
                            dataList = new ArrayList<>(0);
                            adapter.notifyDataSetChanged();
                        }
                        swipeRefreshLayout.setLoading(false);
                        swipeRefreshLayout.setRefreshing(false);
                        break;
                    case NETWORK_CONNECTED:
                        if(null != networkRunnable){
                            ThreadPoolUtil.getInstance().getThreadPoolExecutor().remove(networkRunnable);
                        }
                        noSignText.setVisibility(TextView.GONE);
                        listView.setVisibility(ListView.VISIBLE);
                        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
                        swipeRefreshLayout.setRefreshing(true);
                        initData(page);
                        break;
                    case EXPIRED_TOKEN:
                        noSignText.setVisibility(TextView.VISIBLE);
                        listView.setVisibility(ListView.GONE);
                        noSignText.setText("密码过期，请先登录");
                        swipeRefreshLayout.setLoading(false);
                        swipeRefreshLayout.setRefreshing(false);
                    case NETWORK_DISCONNECTED:
                        swipeRefreshLayout.setLoading(false);
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(MainActivity.this,"网络连接超时", Toast.LENGTH_LONG);
                }
            }
        };
        initView();
    }

    public void onResume(){
        super.onResume();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("首页");
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerToggle.syncState();
        drawerLayout.setDrawerListener(drawerToggle);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.nav_auth:
                        Intent intent = new Intent(MainActivity.this, WBAuthActivity.class);
                        startActivity(intent);
                }
                return false;
            }
        });
        noSignText = (TextView) findViewById(R.id.no_sign_text);
        swipeRefreshLayout = (LoadSwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setOnLoadListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        listView = (ListView) findViewById(R.id.list_view);

        if (NetworkUtil.isNetConnected()) {
            String token = SharedPreferencesUtil.getData(this, Keys.SINA_TOKEN);
            Log.d(Keys.PACKAGE, token);
            if (StringUtils.isBlank(token)) {
                noSignText.setText("请先登录");
                noSignText.setVisibility(TextView.VISIBLE);
                listView.setVisibility(ListView.GONE);
            } else {
                noSignText.setVisibility(TextView.GONE);
                listView.setVisibility(ListView.VISIBLE);
                swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
                swipeRefreshLayout.setRefreshing(true);
                this.initData(this.page);
            }
        }else{
            noSignText.setVisibility(TextView.VISIBLE);
            noSignText.setText(getText(R.string.connect_network));
            listView.setVisibility(ListView.GONE);
            Toast.makeText(this,getText(R.string.connect_network),Toast.LENGTH_LONG).show();

            networkRunnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        while(true){
                            Thread.sleep(5 * 1000);
                            if(NetworkUtil.isNetConnected()){
                                Message msg = new Message();
                                msg.what = NETWORK_CONNECTED;
                                MainActivity.this.handler.sendMessage(msg);
                                break;
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            ThreadPoolUtil.getInstance().execute(networkRunnable);
        }
    }

    private void initData(int page) {
        String token = SharedPreferencesUtil.getData(this, Keys.SINA_TOKEN);
        HttpUtils.doGetAsyn(Constants.WEIBO_BASE_URL + Constants.FRIENDS_TIMELINE + "?access_token="
                + token + "&page=" + page + "&count=" + pageCount, new HttpUtils.CallBack() {
            public void onRequestComplete(String result) {
                List<Map<String, Object>> list = null;
                if (StringUtils.isNotBlank(result)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if(jsonObject.has("error_code")){
                            Message msg = new Message();
                            msg.obj = jsonObject.getString("error");
                            if(jsonObject.getString("error_code").equals("1000")){
                                msg.what = NETWORK_DISCONNECTED;
                            }else{
                                msg.what = EXPIRED_TOKEN;
                            }

                            MainActivity.this.handler.sendMessage(msg);
                        }else{
                            JSONArray jsonArray = jsonObject.getJSONArray("statuses");
                            Gson gson = new Gson();
                            list = gson.fromJson(jsonArray.toString(), new TypeToken<List<Map>>() {
                            }.getType());
                            Message msg = new Message();
                            msg.obj = list;
                            msg.what = LIST_UPDATE;
                            MainActivity.this.handler.sendMessage(msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    @Override
    public void onRefresh() {
        this.page = 1;
        initData(this.page);
    }

    @Override
    public void onLoad() {
        if (!swipeRefreshLayout.isLoading()) {
            swipeRefreshLayout.setLoading(true);
            initData(++this.page);
        }
    }
}
