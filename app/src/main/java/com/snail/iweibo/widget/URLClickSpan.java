package com.snail.iweibo.widget;
import android.content.Context;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.facebook.common.util.UriUtil;
import com.snail.iweibo.R;
import com.snail.iweibo.util.LogUtils;

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
        LogUtils.debug("View -> " + widget.getClass().getSimpleName() + " , url -> " + this.url);
        Uri uri = UriUtil.parseUriOrNull(url);
        if(uri.getScheme().startsWith("http")){
            LogUtils.debug(" Web Scheme -> " + uri.getScheme());
        }else {
            //
            LogUtils.debug(" Scheme -> " + uri.getScheme() + " , packageName -> " + context.getPackageName());
//            Intent intent = new Intent(Intent.ACTION_VIEW , uri);
//            intent.putExtra(Browser.EXTRA_APPLICATION_ID , context.getPackageName());
//            context.startActivity(intent);
        }
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
