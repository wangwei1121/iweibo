package com.snail.iweibo.widget.theme;
import android.content.Context;
import android.content.res.Resources.Theme;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * ThemeRecyclerView
 * Created by alexwan on 16/4/7.
 */
public class ThemeRecyclerView extends RecyclerView implements ThemeUIInterface {
    private int backgroundColor = 0;
    public ThemeRecyclerView(Context context) {
        super(context);
    }

    public ThemeRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        backgroundColor = ViewAttributeUtil.getBackgroundAttribute(attrs);
    }

    public ThemeRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
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
            // TODO
        }
    }

}
