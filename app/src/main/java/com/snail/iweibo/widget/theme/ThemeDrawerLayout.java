package com.snail.iweibo.widget.theme;
import android.content.Context;
import android.content.res.Resources.Theme;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * ThemeDrawerLayout
 * Created by alexwan on 16/4/7.
 */
public class ThemeDrawerLayout extends DrawerLayout implements ThemeUIInterface {

    private int backgroundColor = 0 ;
    public ThemeDrawerLayout(Context context) {
        super(context);
    }

    public ThemeDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.backgroundColor = ViewAttributeUtil.getBackgroundAttribute(attrs);
    }

    public ThemeDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.backgroundColor = ViewAttributeUtil.getBackgroundAttribute(attrs);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setTheme(Theme themeId) {
        if(backgroundColor != 0){
            ViewAttributeUtil.applyBackgroundDrawable(this , themeId , backgroundColor);
        }
    }

}
