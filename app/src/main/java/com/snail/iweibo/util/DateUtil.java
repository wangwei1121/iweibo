package com.snail.iweibo.util;

import android.text.TextUtils;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by wang.weib on 2016/3/2.
 */
public class DateUtil {

    public static String parse(String dateStr){
        return parse(dateStr, "yyyy-MM-dd HH:mm:ss");
    }

    public static String parse(String dateStr,String parttern){
        if(TextUtils.isEmpty(dateStr)){
            return "";
        }
        try{
            DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy", Locale.US);
            Date date = formatter.parse(dateStr);
            formatter = new SimpleDateFormat(parttern);
            return formatter.format(date);
        }catch(Exception e){
            e.printStackTrace();
            return "";
        }

    }

    public static long hourDiff(Date date1,Date date2){
        if(null == date1 || null == date2){
            return Long.MAX_VALUE;
        }
        long diff = (date1.getTime() - date2.getTime()) / (1000 * 60 * 60);
        return diff;
    }

}
