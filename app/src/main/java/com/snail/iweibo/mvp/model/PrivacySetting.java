package com.snail.iweibo.mvp.model;
/**
 * 个人隐私设置
 * Created by alexwan on 16/4/1.
 */
public class PrivacySetting {
    //
    private int comment; // 是否可以评论我的微博，0：所有人、1：关注的人、2：可信用户
    private int geo; // 是否开启地理信息，0：不开启、1：开启
    private int message; // 是否可以给我发私信，0：所有人、1：我关注的人、2：可信用户
    private int realname; // 是否可以通过真名搜索到我，0：不可以、1：可以
    private int badge; // 勋章是否可见，0：不可见、1：可见
    private int mobile; // 是否可以通过手机号码搜索到我，0：不可以、1：可以
    private int webim; // 是否开启webim， 0：不开启、1：开启

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getGeo() {
        return geo;
    }

    public void setGeo(int geo) {
        this.geo = geo;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public int getRealName() {
        return realname;
    }

    public void setRealName(int realname) {
        this.realname = realname;
    }

    public int getBadge() {
        return badge;
    }

    public void setBadge(int badge) {
        this.badge = badge;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public int getWebim() {
        return webim;
    }

    public void setWebim(int webim) {
        this.webim = webim;
    }
}
