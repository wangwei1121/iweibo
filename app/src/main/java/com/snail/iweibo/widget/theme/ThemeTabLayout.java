package com.snail.iweibo.widget.theme;
import android.content.Context;
import android.content.res.Resources.Theme;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * ThemeTabLayout
 * Created by alexwan on 16/4/7.
 */
public class ThemeTabLayout extends TabLayout implements ThemeUIInterface {

    private int backgroundColor = 0;
    public ThemeTabLayout(Context context) {
        super(context);
    }

    public ThemeTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.backgroundColor = ViewAttributeUtil.getBackgroundAttribute(attrs);
    }

    public ThemeTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
    //
}
