package com.snail.iweibo.widget.theme;
import android.content.Context;
import android.content.res.Resources.Theme;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;

import com.snail.iweibo.util.LogUtils;

/**
 * ThemeCardView
 * Created by alexwan on 16/4/15.
 */
public class ThemeCardView extends CardView implements ThemeUIInterface {
    private int backgroundColor = 0;
    public ThemeCardView(Context context) {
        super(context);
    }

    public ThemeCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        backgroundColor = ViewAttributeUtil.getCardViewBackAttribute(attrs);
    }

    public ThemeCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        backgroundColor = ViewAttributeUtil.getCardViewBackAttribute(attrs);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setTheme(Theme themeId) {
        if(backgroundColor != 0){
            LogUtils.info("ThemeCardView : " + backgroundColor);
            ViewAttributeUtil.applyCardBackgroundColor(this , themeId , backgroundColor);
        }
    }

}
