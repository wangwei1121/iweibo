package com.snail.iweibo.ui.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.snail.iweibo.R;
import com.snail.iweibo.mvp.model.Status.ThumbnailPic;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * StatusPicGridAdapter
 * Created by alexwan on 16/4/1.
 */
public class StatusPicGridAdapter extends BaseAdapter {
    private List<ThumbnailPic> urls;
    private Context context;
    public StatusPicGridAdapter(Context context,List<ThumbnailPic> urls) {
        this.context = context;
        this.urls = urls;
    }

    @Override
    public int getCount() {
        return urls == null ? 0 : urls.size();
    }

    @Override
    public ThumbnailPic getItem(int position) {
        return urls == null ? null : urls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_status_grid, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(urls.get(position) != null){
            holder.imageView.setImageURI(UriUtil.parseUriOrNull(urls.get(position).getThumbnailPic()));
        }
        return convertView;
    }

    public static class ViewHolder {
        @Bind(R.id.image)
        SimpleDraweeView imageView;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}
