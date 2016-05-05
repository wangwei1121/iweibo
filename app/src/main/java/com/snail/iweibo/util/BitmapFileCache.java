package com.snail.iweibo.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Config;
import android.util.Log;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by wang.weib on 2016/5/4.
 */
public class BitmapFileCache {

    private File cacheDir;

    private BitmapFileCache(){
        this(BaseApplication.getContext());
    }

    private BitmapFileCache(Context context) {
        if (android.os.Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED)) {
            cacheDir = new File(android.os.Environment.getExternalStorageDirectory(),"cache");
        }else {
            cacheDir = context.getCacheDir();
        }
        if (!cacheDir.exists()){
            cacheDir.mkdirs();
        }
        Log.d(Keys.PACKAGE, "cache dir: " + cacheDir.getAbsolutePath());
    }

    public static BitmapFileCache getInstance(){
        return SingletonHolder.instance;
    }


    /**
     * Search the specific image file with a unique key.
     *
     * @param key Should be unique.
     * @return Returns the image file corresponding to the key.
     */
    public File getFile(String key) {
        File f = new File(cacheDir, key);
        if (f.exists()) {
//            Log.i(Keys.PACKAGE, "the file you wanted exists " + f.getAbsolutePath());
        } else {
//            Log.w(Keys.PACKAGE, "the file you wanted does not exists: " + f.getAbsolutePath());
        }
        return f;
    }

    /**
     * Put a bitmap into cache with a unique key.
     *
     * @param key   Should be unique.
     * @param value A bitmap.
     */
    public void put(String key, Bitmap value) {
        File f = new File(cacheDir, key);
        if (!f.exists())
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        if (saveBitmap(f, value)) {
            Log.d(Keys.PACKAGE, "Save file to sdcard successfully!");
        }else{
            Log.w(Keys.PACKAGE, "Save file to sdcard failed!");
        }
    }

    /**
     * Clear the cache directory on sdcard.
     */
    public void clear() {
        File[] files = cacheDir.listFiles();
        for (File file : files){
            file.delete();
        }
    }

    public boolean saveBitmap(File file, Bitmap bitmap){
        if(file == null || bitmap == null)
            return false;
        try {
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            return bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public File getCacheDir(){
        return this.cacheDir;
    }

    private static class SingletonHolder{
        private static BitmapFileCache instance = new BitmapFileCache();
    }
}
