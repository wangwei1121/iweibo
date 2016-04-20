package com.snail.iweibo.util;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * SharePreferenceUtil
 * Created by alexwan on 16/4/6.
 */
public class SharePreferencesUtil {
    public static final String THEME_CONFIG_KEY = "pref_dark_theme";

    /**
     * 是否为黑夜模式
     *
     * @param context context
     * @return boolean
     */
    public static boolean isDarkTheme(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(THEME_CONFIG_KEY, false);
    }

    /**
     * 更换主题
     *
     * @param context context
     */
    public static void setDarkTheme(Context context) {
        boolean isDark = isDarkTheme(context);
        Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean(THEME_CONFIG_KEY, !isDark);
        editor.apply();
    }

    public static void setUserBean(Context context, UserBean userBean) {
        Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("screen_name", userBean.getScreen_name());
        editor.apply();
    }

    public static UserBean getUserBean(Context context) {
        UserBean userBean = new UserBean();
        userBean.setScreen_name(PreferenceManager.getDefaultSharedPreferences(context).getString("screen_name", ""));
        return userBean;
    }
}
