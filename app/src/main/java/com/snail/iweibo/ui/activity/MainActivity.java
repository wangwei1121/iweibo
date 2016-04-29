package com.snail.iweibo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.snail.iweibo.R;
import com.snail.iweibo.network.HttpUtils;
import com.snail.iweibo.oauth.Constants;
import com.snail.iweibo.ui.BaseActivity;
import com.snail.iweibo.util.Keys;
import com.snail.iweibo.util.SharedPreferencesUtil;
import com.snail.iweibo.util.StringUtils;

import retrofit2.http.HTTP;


public class MainActivity extends BaseActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private TextView noSignText;
    private LinearLayout linearLayout;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("首页");
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,R.string.drawer_close);
        drawerToggle.syncState();
        drawerLayout.setDrawerListener(drawerToggle);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawerLayout.closeDrawers();
                switch (menuItem.getItemId()){
                    case R.id.nav_auth:
                        Intent intent = new Intent(MainActivity.this,WBAuthActivity.class);
                        startActivity(intent);
                }
                return false;
            }
        });
        noSignText = (TextView)findViewById(R.id.no_sign_text);
        linearLayout = (LinearLayout)findViewById(R.id.include_content);
        listView = (ListView)linearLayout.findViewById(R.id.list_view);
    }

    private void initData(){
        String token = SharedPreferencesUtil.getData(this, Constants.SINA_TOKEN);
        if(StringUtils.isBlank(token)){
            noSignText.setText("请先登录");
            noSignText.setVisibility(TextView.VISIBLE);
            return;
        }

        HttpUtils.doGetAsyn(Constants.WEIBO_BASE_URL + Constants.FRIENDS_TIMELINE ,new HttpUtils.CallBack(){
            public void onRequestComplete(String result){
                Log.e(Constants.PACKAGE,result);
            }
        });
    }

}
