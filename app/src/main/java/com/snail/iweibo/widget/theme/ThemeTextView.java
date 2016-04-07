package com.snail.iweibo.widget.theme;
import android.content.Context;
import android.content.res.Resources.Theme;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * ThemeTextView
 * Created by alexwan on 16/4/7.
 */
public class ThemeTextView extends TextView implements ThemeUIInterface {
    private int textColor = 0;
    public ThemeTextView(Context context) {
        super(context);
    }

    public ThemeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.textColor = ViewAttributeUtil.getTextColorAttribute(attrs);
    }

    public ThemeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.textColor = ViewAttributeUtil.getTextColorAttribute(attrs);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setTheme(Theme themeId) {
        if(textColor != 0){
            ViewAttributeUtil.applyTextColor(this , themeId , textColor);
        }
    }
}
