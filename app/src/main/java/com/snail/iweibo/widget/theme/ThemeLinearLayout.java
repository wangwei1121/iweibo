package com.snail.iweibo.widget.theme;
import android.content.Context;
import android.content.res.Resources.Theme;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * ThemeLinearLayout
 * Created by alexwan on 16/4/20.
 */
public class ThemeLinearLayout extends LinearLayout implements ThemeUIInterface{
    private int backgroundColor = 0;
    public ThemeLinearLayout(Context context) {
        super(context);
    }

    public ThemeLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        backgroundColor = ViewAttributeUtil.getBackgroundAttribute(attrs);
    }

    public ThemeLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        backgroundColor = ViewAttributeUtil.getBackgroundAttribute(attrs);
    }

    public ThemeLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
