package com.snail.iweibo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.snail.iweibo.R;
import com.snail.iweibo.model.ContentModel;

import java.util.List;
import java.util.Map;

/**
 * Created by wang.weib on 2016/4/28.
 */
public class FriendsTimelineListAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String,Object>> list;

    public FriendsTimelineListAdapter(Context context, List<Map<String,Object>> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (list != null) {
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return convertView;
    }

}
