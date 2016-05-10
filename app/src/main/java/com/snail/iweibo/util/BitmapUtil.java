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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wang.weib on 2016/5/4.
 */
public class BitmapUtil {

    private static final Map<String, SoftReference<Bitmap>> bitmapCacheMap = new HashMap<String,SoftReference<Bitmap>>();

    public static Bitmap getBitmap(String url) {
        Bitmap bitmap = getBitmapFromCache(url);
        if(null != bitmap){
            return bitmap;
        }
        String encodeURL = MD5Util.encode(url);
        File file = BitmapFileCache.getInstance().getFile(encodeURL);
        bitmap = decodeFile(file);
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
            addBitmapToCache(url,bitmap);
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
        try {
            if (!file.exists()) {
                return null;
            }
            return  BitmapFactory.decodeStream(new FileInputStream(file), null, null);

//            BitmapFactory.Options opt = new BitmapFactory.Options();
//            opt.inJustDecodeBounds = true;
//            BitmapFactory.decodeStream(new FileInputStream(file), null, opt);
//            final int REQUIRED_SIZE = 100;
//            int width_tmp = opt.outWidth, height_tmp = opt.outHeight;
//            Log.d(Keys.PACKAGE,"width-->" + width_tmp + ";height-->" + height_tmp);
//            int scale = 1;
//            while (true) {
//                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
//                    break;
//                width_tmp /= 2;
//                height_tmp /= 2;
//                scale *= 2;
//            }
//            BitmapFactory.Options opte = new BitmapFactory.Options();
//            opte.inSampleSize = scale;
//            return BitmapFactory.decodeStream(new FileInputStream(file), null, opte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap decodeFile2(File file) {
        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }

    public static void addBitmapToCache(String path,Bitmap bitmap) {
        SoftReference<Bitmap> softBitmap = new SoftReference<Bitmap>(bitmap);
        bitmapCacheMap.put(path, softBitmap);
    }

    public static Bitmap getBitmapFromCache(String path){
        SoftReference<Bitmap> softReference = bitmapCacheMap.get(path);
        if (null != softReference && null != softReference.get()) {
            return softReference.get();
        }
        return null;
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

    public static void clearCache(){

    }

}
