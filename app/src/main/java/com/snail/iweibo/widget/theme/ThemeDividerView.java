package com.snail.iweibo.widget.theme;
import android.content.Context;
import android.content.res.Resources.Theme;
import android.util.AttributeSet;
import android.view.View;

/**
 * ThemeDividerView
 * Created by alexwan on 16/4/7.
 */
public class ThemeDividerView extends View implements ThemeUIInterface{
    private int backgroundColor = 0;
    public ThemeDividerView(Context context) {
        super(context);
    }

    public ThemeDividerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        backgroundColor = ViewAttributeUtil.getBackgroundAttribute(attrs);
    }

    public ThemeDividerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        backgroundColor = ViewAttributeUtil.getBackgroundAttribute(attrs);
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
