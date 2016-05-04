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

    public static String parseUS(String dateStr){
        return parseUS(dateStr, "yyyy-MM-dd HH:mm:ss");
    }

    public static String parseUS(String dateStr,String parttern){
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

    public static Date formatUS(String dateStr){
        if(TextUtils.isEmpty(dateStr)){
            return null;
        }
        try{
            DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy", Locale.US);
            return  formatter.parse(dateStr);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static Integer hourDiff(Date date1,Date date2){
        if(null == date1 || null == date2){
            return null;
        }
        long diff = (date1.getTime() - date2.getTime()) / (1000 * 60 * 60);
        return (int)diff;
    }

    public static Integer minuteDiff(Date date1,Date date2){
        if(null == date1 || null == date2){
            return null;
        }
        long diff = (date1.getTime() - date2.getTime()) / (1000 * 60);
        return (int)diff;
    }

    public static Integer secondDiff(Date date1,Date date2){
        if(null == date1 || null == date2){
            return null;
        }
        long diff = (date1.getTime() - date2.getTime()) / 1000;
        return (int)diff;
    }

}
