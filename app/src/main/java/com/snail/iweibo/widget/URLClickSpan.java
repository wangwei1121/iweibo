package com.snail.iweibo.widget;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.snail.iweibo.R;

/**
 * URLClickSpan
 * Created by alexwan on 16/4/3.
 */
public class URLClickSpan extends ClickableSpan implements ParcelableSpan {
    private String url;
    private Context context;
    public URLClickSpan(Context context , String url){
        this.url = url;
        this.context = context;
    }
    @Override
    public void onClick(View widget) {
        // TODO

    }

    @Override
    public int getSpanTypeId() {
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
    }

    public String getUrl() {
        return url;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        if(VERSION.SDK_INT > VERSION_CODES.M){
            ds.setColor(context.getResources().getColor(R.color.main_blue , null));
        }else{
            ds.setColor(context.getResources().getColor(R.color.main_blue));
        }
        ds.setUnderlineText(false);
    }
}
