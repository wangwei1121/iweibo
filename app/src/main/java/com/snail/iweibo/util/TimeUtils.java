package com.snail.iweibo.util;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * TimeUtils
 * Created by alexwan on 16/4/1.
 */
public class TimeUtils {
    public static String UTC_FORMAT_STRING = "EEE MMM dd HH:mm:ss zzz yyyy";
    public static String DEFAULT_FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";
    public static String MINUTE_SECOND = "mm:ss";

    public static String formatUTCTime(String time) {
        // week month day hours:minus:second year
        SimpleDateFormat format = new SimpleDateFormat(UTC_FORMAT_STRING, Locale.US);
        try {
            Date date = format.parse(time);
            SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_FORMAT_STRING, Locale.CHINA);
            return dateFormat.format(date);
        } catch (ParseException e) {
            return "";
        }
    }

    /**
     * 指定时间格式
     * @param time time
     * @param format format
     * @return String
     */
    public static String formatUTCTime(String time , String format){
        SimpleDateFormat utcFormat = new SimpleDateFormat(UTC_FORMAT_STRING, Locale.US);
        try {
            Date date = utcFormat.parse(time);
            String timeFormat = TextUtils.isEmpty(format) ? DEFAULT_FORMAT_STRING : format;
            SimpleDateFormat dateFormat = new SimpleDateFormat(timeFormat, Locale.CHINA);
            return dateFormat.format(date);
        } catch (ParseException e) {
            return "";
        }
    }
    public static String formatUTCTimes(String timeString) {
        SimpleDateFormat format = new SimpleDateFormat(UTC_FORMAT_STRING, Locale.US);
        try {
            Date date = format.parse(timeString);
            long time = date.getTime();
            long reduce = System.currentTimeMillis() - time; // 毫秒
            if(reduce < 60 * 1000){
                // 小于1分钟
                return reduce / 1000 + "秒前";
            }else if(reduce < 3600000){
                // 小于1小时
                return reduce / 60000 + "分钟前";
            }else if(reduce < 3600000 * 24){
                // 小于一天
                return reduce / 3600000 + "小时前";
            }else if(reduce < 3600000 * 24 * 2){
                // 昨天
                SimpleDateFormat dateFormat = new SimpleDateFormat(MINUTE_SECOND, Locale.CHINA);
                return "昨天  " + dateFormat.format(date);
            }else{
                SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_FORMAT_STRING, Locale.CHINA);
                return dateFormat.format(date);
            }
        } catch (ParseException e) {
            return "";
        }
    }
}
