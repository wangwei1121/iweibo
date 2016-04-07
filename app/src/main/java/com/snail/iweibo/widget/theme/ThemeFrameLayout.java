package com.snail.iweibo.widget.theme;
import android.content.Context;
import android.content.res.Resources.Theme;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * ThemeFrameLayout
 * Created by alexwan on 16/4/7.
 */
public class ThemeFrameLayout extends FrameLayout implements ThemeUIInterface {
    private int backgroundColor = 0;
    public ThemeFrameLayout(Context context) {
        super(context);
    }

    public ThemeFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        backgroundColor = ViewAttributeUtil.getBackgroundAttribute(attrs);
    }

    public ThemeFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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
    //
}
