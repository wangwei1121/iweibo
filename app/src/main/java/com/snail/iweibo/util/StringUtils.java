package com.snail.iweibo.util;

/**
 * Created by wang.weib on 2016/4/29.
 */
public class StringUtils {

    public static boolean isNotBlank(String str){
        if(null == str){
            return false;
        }
        if(str.trim().length() == 0){
            return false;
        }
        return true;

    }

    public static boolean isBlank(String str){
       return !isNotBlank(str);
    }
}
