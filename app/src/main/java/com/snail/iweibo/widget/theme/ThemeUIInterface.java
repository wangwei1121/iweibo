package com.snail.iweibo.widget.theme;
import android.content.res.Resources;
import android.view.View;

/**
 * ThemeUIInterface
 * Created by alexwan on 16/4/7.
 */
public interface ThemeUIInterface {
    View getView();
    void setTheme(Resources.Theme themeId);
}
