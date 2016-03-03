package com.snail.iweibo.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wang.weib on 2016/3/3.
 */
public class SharedPreferencesUtil {

    private static final String FILE_NAME = "iweibo_save_file_name";

    /**
     * 保存数据到文件
     *
     * @param context
     * @param key
     * @param data
     */
    public static void saveData(Context context, String key, String data) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(key, data.toString());

        editor.commit();
    }

    /**
     * 从文件中读取数据
     *
     * @param context
     * @param key
     * @return
     */
    public static String getData(Context context, String key) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);

        return sharedPreferences.getString(key,"");
    }
}
