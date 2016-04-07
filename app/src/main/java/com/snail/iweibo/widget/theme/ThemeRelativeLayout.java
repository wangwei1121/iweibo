package com.snail.iweibo.widget.theme;
import android.content.Context;
import android.content.res.Resources.Theme;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * ThemeRelativeLayout
 * Created by alexwan on 16/4/7.
 */
public class ThemeRelativeLayout extends RelativeLayout implements ThemeUIInterface {
    private int attrBackGround = 0;
    public ThemeRelativeLayout(Context context) {
        super(context);
    }

    public ThemeRelativeLayout(Context context , AttributeSet attrs) {
        super(context , attrs);
        this.attrBackGround = ViewAttributeUtil.getBackgroundAttribute(attrs);

    }

    public ThemeRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs , defStyleAttr);
        this.attrBackGround = ViewAttributeUtil.getBackgroundAttribute(attrs);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setTheme(Theme themeId) {
        if(attrBackGround != 0){
            ViewAttributeUtil.applyBackgroundDrawable(this , themeId , attrBackGround);
        }
    }

}
