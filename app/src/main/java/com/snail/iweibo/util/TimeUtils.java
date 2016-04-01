package com.snail.iweibo.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by alexwan on 16/4/1.
 */
public class TimeUtils {

    public static String  formatUTCTime(String time){
        // week month day hours:minus:second year
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy" , Locale.US);
        try {
            Date date = format.parse(time);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" , Locale.CHINA);
            return dateFormat.format(date);
        } catch (ParseException e) {
            return "";
        }
    }
}
