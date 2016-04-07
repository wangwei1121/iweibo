package com.snail.iweibo.widget.theme;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * ViewAttributeUtil
 * Created by alexwan on 16/4/7.
 */
public class ViewAttributeUtil {
    public static int getAttributeValue(AttributeSet attr , int param){
        int value = 0 ;
        int count = attr.getAttributeCount();
        for (int i = 0 ; i < count ; i++){
            if(param == attr.getAttributeNameResource(i)){
                String str = attr.getAttributeValue(i);
                if(!TextUtils.isEmpty(str)&& str.startsWith("?")){
                    try {
                        return Integer.parseInt(str.substring(1, str.length()));
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                }
            }
        }
        return value;
    }

    /**
     * 背景颜色Attr
     * @param attr attr
     * @return int
     */
    public static int getBackgroundAttribute(AttributeSet attr){
        return getAttributeValue(attr , android.R.attr.background);
    }

    /**
     * 字体颜色Attr
     * @param attr attr
     * @return attr
     */
    public static int getTextColorAttribute(AttributeSet attr){
        return getAttributeValue(attr , android.R.attr.textColor);
    }

    /**
     * 应用背景颜色
     * @param ui ThemeUIInterface
     * @param theme Resources.Theme
     * @param attrs attrs
     */
    public static void applyBackgroundDrawable(ThemeUIInterface ui , Resources.Theme theme , int attrs){
        TypedArray ta = theme.obtainStyledAttributes(new int[]{attrs});
        Drawable drawable = ta.getDrawable(0);
        if(ui != null){
            if(VERSION.SDK_INT > VERSION_CODES.JELLY_BEAN){
                ui.getView().setBackground(drawable);
            }else{
                ui.getView().setBackgroundDrawable(drawable);
            }
        }
        ta.recycle();
    }

    /**
     * 应用字体颜色
     * @param ui ThemeUIInterface
     * @param theme Resources.Theme
     * @param attrs attrs
     */
    public static void applyTextColor(ThemeUIInterface ui , Resources.Theme theme , int attrs){
        TypedArray ta = theme.obtainStyledAttributes(new int[]{attrs});
        int resId = ta.getColor( 0 , 0);
        if(ui != null && ui instanceof TextView){
            ((TextView)ui.getView()).setTextColor(resId);
        }
        ta.recycle();
    }

}
