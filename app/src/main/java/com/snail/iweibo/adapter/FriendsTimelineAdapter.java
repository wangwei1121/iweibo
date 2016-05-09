package com.snail.iweibo.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.support.v7.widget.GridLayout;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.snail.iweibo.R;
import com.snail.iweibo.network.HttpUtils;
import com.snail.iweibo.ui.activity.LargeImageActivity;
import com.snail.iweibo.util.BitmapFileCache;
import com.snail.iweibo.util.BitmapUtil;
import com.snail.iweibo.util.CommonUtil;
import com.snail.iweibo.util.DateUtil;
import com.snail.iweibo.util.Keys;
import com.snail.iweibo.util.MD5Util;
import com.snail.iweibo.util.NetworkUtil;
import com.snail.iweibo.util.PicassoHelper;
import com.snail.iweibo.util.StringUtils;
import com.squareup.picasso.Downloader;

import android.text.Spannable;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
            holder.imageGridLayout = (GridLayout)convertView.findViewById(R.id.grid_image);
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

        if(map.containsKey("pic_urls")){
            List<Map<String,Object>> picList = (List<Map<String,Object>>)map.get("pic_urls");
            String[] thumbnails = new String[picList.size()];
            for(int i = 0;i<picList.size();i++){
                thumbnails[i] = (String)((Map<String,Object>)picList.get(i)).get("thumbnail_pic");

            }
            int gridImageWidth = CommonUtil.getScreenWidth() / 3;
            holder.imageGridLayout.removeAllViews();
            if(thumbnails.length < 3) {
//                holder.imageGridLayout.setRowCount(1);
                for(int i=0;i<thumbnails.length;i++){
                    initGridImage(holder,thumbnails,i);
                }
            }else if(thumbnails.length % 3 == 0){
//                holder.imageGridLayout.setRowCount(thumbnails.length / 3);
                for(int i=0;i<thumbnails.length / 3;i++){
                    for(int j=0;j<3;j++){
                        initGridImage(holder, thumbnails, i);
                    }
                }
            }else{
//                holder.imageGridLayout.setRowCount(thumbnails.length / 3 + 1);
                for(int i=0;i<thumbnails.length / 3 + 1;i++){
                    int cols = 3;
                    if(i == thumbnails.length / 3){
                        cols = thumbnails.length%3;
                    }
                    Log.d(Keys.PACKAGE,cols + "");
                    for(int j=0;j<cols;j++){
                        initGridImage(holder, thumbnails, i);
                    }
                }
            }
        }
        return convertView;
    }


    private void initGridImage(ViewHolder holder,final String[] thumbnails,final int index){
        int gridImageWidth = CommonUtil.getScreenWidth() / 3;
        ImageView imageView = new ImageView(context);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(gridImageWidth, gridImageWidth);
        imageView.setLayoutParams(layoutParams);
        imageView.setPadding(5, 5, 5, 5);
        imageView.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
//                LayoutInflater inflater = LayoutInflater.from(context);
//                View imgEntryView = inflater.inflate(R.layout.dialog_image_entry, null); // 加载自定义的布局文件
//                final AlertDialog dialog = new AlertDialog.Builder(context).create();
//                ImageView largeImageView = (ImageView) imgEntryView.findViewById(R.id.large_image);
//                Log.e(Keys.PACKAGE, thumbnails[index].replace("thumbnail", "large"));
//                PicassoHelper.loadImage(context, thumbnails[index].replace("thumbnail", "large"), largeImageView);
//                dialog.setView(imgEntryView); // 自定义dialog
//                dialog.show();
//                imgEntryView.setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View paramView) {
//                        dialog.cancel();
//                    }
//                });
                Dialog largeImageDialog = new LargeImageDialog(context, thumbnails, index);
                largeImageDialog.show();
//                Intent intent = new Intent(context, LargeImageActivity.class);
//                intent.putExtra("imageUrls", thumbnails);
//                intent.putExtra("imageIndex",index);
//                context.startActivity(intent);
            }
        });
        BitmapUtil.initAsynBitmap(context, imageView, thumbnails[index].replace("thumbnail","bmiddle"));
//        BitmapUtil.initAsynBitmap(context, imageView, thumbnails[index]);
//        PicassoHelper.loadImage(context, thumbnails[index], imageView);

        holder.imageGridLayout.addView(imageView);
    }

    private static class ViewHolder{
        public ImageView profileImage;
        public TextView userNameText;
        public ImageView smallImage;
        public TextView publishTimeText;
        public  Button follow;
        public TextView comeFromText;
        public TextView itemContentText;
        public GridLayout imageGridLayout;

    }

    class MyURLSpan extends ClickableSpan {
        private String url;
        private Context context;
        MyURLSpan(String url,Context context) {
            this.url =url;
            this.context = context;
        }
        @Override
        public void onClick(View widget) {
            Toast.makeText(context, "hello " + url,Toast.LENGTH_LONG).show();
        }
    }

    class LargeImageDialog extends Dialog{
        private String[] imageUrls;
        private int imageIndex;
        public LargeImageDialog(Context context,String[] imageUrls,int imageIndex) {
            super(context,R.style.NobackDialog);
            this.imageUrls = imageUrls;
            this.imageIndex = imageIndex;
        }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            super.setContentView(R.layout.dialog_image_entry);
            ImageView imageView = (ImageView)this.findViewById(R.id.large_image);
//            PicassoHelper.loadImage(this.getContext(), imageUrls[imageIndex], imageView);
            BitmapUtil.initAsynBitmap(context, imageView, imageUrls[imageIndex].replace("thumbnail","bmiddle"));
            AsyncTask asyncTask = new DownloadFilesTask(imageView);
            asyncTask.execute(imageUrls[imageIndex].replace("thumbnail", "large"));

//            PicassoHelper.loadImage(this.getContext(), imageUrls[imageIndex].replace("thumbnail", "large"), img);
        }
    }

    private class DownloadFilesTask extends AsyncTask<Object, Integer, Bitmap> {
        private ImageView imageView;
        public DownloadFilesTask(ImageView imageView){
            this.imageView = imageView;
        }
        protected Bitmap doInBackground(Object... urls) {
            String url = (String)urls[0];
            String encodeURL = MD5Util.encode(url);
            HttpURLConnection conn = null;
            InputStream inputStream = null;
            InputStream is = null;
            OutputStream os = null;
            int buffer_size = 1024;
            try {
                File file = BitmapFileCache.getInstance().getFile(encodeURL);
                Bitmap bitmap = BitmapUtil.decodeFile(file);
                if(null != bitmap){
                    return bitmap;
                }
                URL ImageUrl = new URL(url);
                conn = (HttpURLConnection) ImageUrl.openConnection();
                conn.setConnectTimeout(50000);
                conn.setReadTimeout(50000);
                conn.setInstanceFollowRedirects(true);
                int length = conn.getContentLength();
                Log.d(Keys.PACKAGE,url + "-->" + length);
                is = conn.getInputStream();
                os = new FileOutputStream(file);
                byte[] bytes=new byte[buffer_size];
                for(;;){
                    int count = is.read(bytes, 0, buffer_size);
                    if(count == -1)
                        break;
                    os.write(bytes, 0, count);
                    publishProgress((int) ((count / (float) length) * 100));
                }
                os.flush();
                bitmap = BitmapUtil.decodeFile(file);
                BitmapFileCache.getInstance().put(encodeURL, bitmap);
                return bitmap;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != inputStream) {
                        inputStream.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if(null != conn){
                    conn.disconnect();
                }
            }
            return BitmapUtil.getBitmap(url);
        }

        protected void onProgressUpdate(Integer progress) {
            Log.d(Keys.PACKAGE,progress + "");
            Toast.makeText(context,progress,Toast.LENGTH_SHORT);
        }

        protected void onPostExecute(Bitmap result) {
            this.imageView.setImageBitmap(result);
        }
    }

}
