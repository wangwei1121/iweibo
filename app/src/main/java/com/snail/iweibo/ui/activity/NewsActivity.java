package com.snail.iweibo.ui.activity;

import android.app.Activity;
import android.content.SharedPreferences;
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
import com.snail.iweibo.R;
import com.snail.iweibo.oauth.Constants;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
        // 数据适配器的定义
        String[] data = new String[] { "java", "C++", "JavaScript", "Php","Python" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(NewsActivity.this, android.R.layout.simple_list_item_1, data);
        // 给ListView设置数据适配器
        listView.setAdapter(adapter);
        // 设置ListView的元素被选中时的事件处理监听器
        listView.setOnItemClickListener(this);

        new Thread(){
            @Override
            public void run(){
                OkHttpClient okHttpClient = new OkHttpClient();
                SharedPreferences sp = getSharedPreferences(Constants.PROJECT_NAME, Activity.MODE_PRIVATE);
                Log.d("com.snail.iweibo",sp.getString(Constants.SINA_TOKEN,""));
                Request request = new Request.Builder().url(Constants.PUBLIC_TIMELINE + "?access_token=" + sp.getString(Constants.SINA_TOKEN,"")).build();
                try{
                    Response response = okHttpClient.newCall(request).execute();
                    Log.i("com.snail.iweibo","###################" + response.body().string());
                    if(response.isSuccessful()){
                        Log.i("com.snail.iweibo","###################" + response.body().string());
                    }
                }catch(IOException e){
                    e.printStackTrace();
                    Log.e("com.snail.iweibo",e.getMessage());
                }
            }
        }.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // (5)事件处理监听器方法
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        // TODO Auto-generated method stub
        // 获取点击ListView item中的内容信息
        String text = listView.getItemAtPosition(position) + "";
        // 弹出Toast信息显示点击位置和内容
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
