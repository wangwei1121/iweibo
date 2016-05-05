package com.snail.iweibo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.snail.iweibo.R;
import com.snail.iweibo.util.BitmapTask;
import com.snail.iweibo.util.BitmapUtil;
import com.snail.iweibo.util.DateUtil;
import com.snail.iweibo.util.Keys;
import com.snail.iweibo.util.StringUtils;

import android.text.Spannable;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wang.weib on 2016/4/28.
 */
public class FriendsTimelineAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String,Object>> list;

    public FriendsTimelineAdapter(Context context, List<Map<String, Object>> list) {
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
        Map<String,Object> map = list.get(position);
        Map<String,Object> userMap = (Map<String,Object>)list.get(position).get("user");
        ViewHolder holder = null;
        if (null == convertView) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.main_item, null);
            holder.profileImage = (ImageView) convertView.findViewById(R.id.profile_image);
            holder.userNameText = (TextView) convertView.findViewById(R.id.user_name);
            holder.smallImage = (ImageView) convertView.findViewById(R.id.my_small_image);
            holder.publishTimeText = (TextView) convertView.findViewById(R.id.publish_time);
            holder.comeFromText = (TextView) convertView.findViewById(R.id.come_from);
            holder.follow = (Button) convertView.findViewById(R.id.follow);
            holder.itemContentText = (TextView) convertView.findViewById(R.id.item_content);
            holder.itemContentImg = (ImageView) convertView.findViewById(R.id.item_content_img);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        if(null != userMap.get("profile_image_url")){
            BitmapUtil.initAsynBitmap(this.context,holder.profileImage,userMap.get("profile_image_url").toString());
        }
        holder.userNameText.setText(null == userMap.get("name") ? "" : userMap.get("name").toString());
        Date publicTime = DateUtil.formatUS(map.get("created_at").toString());
        int secondDiff = DateUtil.secondDiff(Calendar.getInstance().getTime(), publicTime);
        if(secondDiff / 3600 > 12){
            holder.publishTimeText.setText(DateUtil.parseUS(map.get("created_at").toString()));
        }else if(secondDiff / 3600 == 0){
            if(secondDiff / 60 == 0){
                holder.publishTimeText.setText(secondDiff + "秒前");
            }else{
                holder.publishTimeText.setText(secondDiff / 60 + "分钟前");
            }
        }else{
            holder.publishTimeText.setText(secondDiff / 3600 + "小时前");
        }
        String source = null == map.get("source") ? "" : map.get("source").toString();
        if(StringUtils.isNotBlank(source)){
            holder.itemContentText.setText(Html.fromHtml(source));
            holder.itemContentText.setMovementMethod(LinkMovementMethod.getInstance());
            CharSequence text =  holder.itemContentText.getText();
            if(text instanceof Spannable){
                int end = text.length();
                Spannable sp = (Spannable)holder.itemContentText.getText();
                URLSpan[] urls=sp.getSpans(0, end, URLSpan.class);
                SpannableStringBuilder style = new SpannableStringBuilder(text);
                style.clearSpans();
                for(URLSpan url : urls){
                    MyURLSpan myURLSpan = new MyURLSpan(url.getURL(),context);
                    style.setSpan(myURLSpan,sp.getSpanStart(url),sp.getSpanEnd(url),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                holder.itemContentText.setText(style);
            }
        }else{
            holder.comeFromText.setText("");
        }
        holder.itemContentText.setText(null == map.get("text") ? "" : map.get("text").toString());
        return convertView;
    }

    static class ViewHolder{
        public ImageView profileImage;
        public TextView userNameText;
        public ImageView smallImage;
        public TextView publishTimeText;
        public  Button follow;
        public TextView comeFromText;
        public TextView itemContentText;
        public ImageView itemContentImg;

    }

    private static class MyURLSpan extends ClickableSpan {
        private String mUrl;
        private Context context;
        MyURLSpan(String url,Context context) {
            mUrl =url;
            context = context;
        }
        @Override
        public void onClick(View widget) {
            Toast.makeText(context, "hello " + mUrl,Toast.LENGTH_LONG).show();
        }
    }

}
