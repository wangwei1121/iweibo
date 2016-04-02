package com.snail.iweibo.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 屏幕像素信息
 */
public class ScreenInfo {
    private Context context;
    /**
     * 屏幕宽度（像素）
     */
    private int width;
    /**
     * 屏幕高度（像素）
     */
    private int height;
    /**
     * 屏幕密度（0.75 / 1.0 / 1.5）
     */
    private float density;
    /**
     * 屏幕密度DPI（120 / 160 / 240）
     */
    private int densityDpi;


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public int getDensityDpi() {
        return densityDpi;
    }

    public void setDensityDpi(int densityDpi) {
        this.densityDpi = densityDpi;
    }

    public ScreenInfo(Context context) {
        this.context = context;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metric = context.getResources().getDisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metric);
        width = metric.widthPixels;
        height = metric.heightPixels;
        density = metric.density;
        densityDpi = metric.densityDpi;
    }

}
