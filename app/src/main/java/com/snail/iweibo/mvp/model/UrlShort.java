package com.snail.iweibo.mvp.model;
import java.io.Serializable;

/**
 * 短链
 * Created by alexwan on 16/4/1.
 */
public class UrlShort implements Serializable {
    //
    private String url_short; // 短链接
    private String url_long; // 原始长链接
    private int type; // 链接的类型，0：普通网页、1：视频、2：音乐、3：活动、5、投票
    private boolean result; // 短链的可用状态，true：可用、false：不可用。

    public String getUrlShort() {
        return url_short;
    }

    public void setUrlShort(String url_short) {
        this.url_short = url_short;
    }

    public String getUrlLong() {
        return url_long;
    }

    public void setUrlLong(String url_long) {
        this.url_long = url_long;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
