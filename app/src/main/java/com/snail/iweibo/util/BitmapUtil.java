package com.snail.iweibo.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wang.weib on 2016/5/4.
 */
public class BitmapUtil {


    public static Bitmap getBitmap(String url) {
        String encodeURL = MD5Util.encode(url);
        File file = BitmapFileCache.getInstance().getFile(encodeURL);
        Bitmap bitmap = decodeFile(file);
        if(null != bitmap){
            return bitmap;
        }
        HttpURLConnection conn = null;
        InputStream is = null;
        OutputStream os = null;
        int buffer_size = 1024;
        try {
            URL ImageUrl = new URL(url);
            conn = (HttpURLConnection) ImageUrl.openConnection();
            conn.setConnectTimeout(50000);
            conn.setReadTimeout(50000);
            conn.setInstanceFollowRedirects(true);
            is = conn.getInputStream();
            os = new FileOutputStream(file);
            Log.e(Keys.PACKAGE,is.available() + "");
            byte[] bytes=new byte[buffer_size];
            for(;;){
                int count = is.read(bytes, 0, buffer_size);
                if(count == -1)
                    break;
                os.write(bytes, 0, count);
            }
            os.flush();
            bitmap = decodeFile(file);
            BitmapFileCache.getInstance().put(encodeURL, bitmap);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            try{
                if(null != os){
                    os.close();
                }
                if(null != is){
                    is.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }

            if(null != conn){
                conn.disconnect();
            }
        }
    }

    public static Bitmap decodeFile(File file) {
        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }

    public static void initAsynBitmap(final Context context,final ImageView imageView,final String url){
        ThreadPoolUtil.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = getBitmap(url);
                if (null != bitmap) {
                    Activity activity = (Activity)context;
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bitmap);
                        }
                    });
                }
            }
        });
    }

}
