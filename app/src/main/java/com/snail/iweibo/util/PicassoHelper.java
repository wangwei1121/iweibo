package com.snail.iweibo.util;
import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * PicassoHelper  图片加载帮助类
 * Created by alexwan on 16/1/30.
 */
public class PicassoHelper {

    private static Picasso picasso(Context context) {
        Picasso picasso = Picasso.with(context);
        picasso.setLoggingEnabled(Configuration.DEBUG);
        picasso.setIndicatorsEnabled(Configuration.DEBUG);
        return picasso;
    }

    /**
     * @param context   context
     * @param url       url
     * @param imageView imageView
     */
    public static void loadImage(Context context, String url, ImageView imageView) {
        picasso(context).load(url).into(imageView);
    }

    /**
     * @param context   context
     * @param url       url
     * @param imageView imageView
     */
    public static void loadImage(Context context, String url, ImageView imageView,int[] resize) {
        picasso(context).load(url).resize(resize[0], resize[1]).centerCrop().into(imageView);
    }

    /**
     * @param context    context
     * @param resourceId resourceId
     * @param imageView  imageView
     */
    public static void loadImage(Context context, int resourceId, ImageView imageView) {
        picasso(context).load(resourceId).into(imageView);
    }

    /**
     * @param context   context
     * @param file      file
     * @param imageView imageView
     */
    public static void loadImage(Context context, File file, ImageView imageView) {
        picasso(context).load(file).into(imageView);
    }

    /**
     * @param context   context
     * @param imageView imageView
     */
    public static void cancelRequest(Context context, ImageView imageView) {
        picasso(context).cancelRequest(imageView);
    }

    /**
     *
     * @param context context
     * @param url url
     * @param errorResId errorResId
     * @param defaultResId defaultResId
     * @param imageView imageView
     */
    public static void loadImage(Context context, String url, int errorResId, int defaultResId, ImageView imageView) {
        picasso(context).load(url).placeholder(defaultResId).error(errorResId).into(imageView);
    }
}
