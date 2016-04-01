package com.snail.iweibo.mvp.model;
/**
 * 微博详情
 * Created by alexwan on 16/3/23.
 */
public class StatusesBean {
    private String created_at; // 微博创建时间
    private long id; // 微博ID
    private String mid; // 微博MID
    private String idstr; // 字符串型的微博ID
    private String text; // 微博信息内容
    private String source; // 微博来源
    private boolean favorited; // 是否已收藏，true：是，false：否
    private boolean truncated; // 是否被截断，true：是，false：否
    private String in_reply_to_status_id; // （暂未支持）回复ID
    private String in_reply_to_user_id; // (暂未支持）回复人UID
    private String in_reply_to_screen_name; // 暂未支持）回复人昵称
    private String thumbnail_pic; // 缩略图片地址，没有时不返回此字段
    private String bmiddle_pic; // 中等尺寸图片地址，没有时不返回此字段
    private String original_pic; // 原始图片地址，没有时不返回此字段
    private UserBean user; // 微博作者的用户信息字段
    private GeoBean geo; // 地理信息字段
    private StatusesBean retweeted_status; // 被转发的原微博信息字段，当该微博为转发微博时返回
    private int reposts_count; // 转发数
    private int comments_count; // 评论数
    private int attitudes_count; // 表态数
    private int mlevel; // 暂未支持
    // 微博配图ID。多图时返回多图ID，用来拼接图片url。
    // 用返回字段thumbnail_pic的地址配上该返回字段的图片ID，即可得到多个图片url。
//    private pic_ids;
    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public void setTruncated(boolean truncated) {
        this.truncated = truncated;
    }

    public void setInReplyToStatusId(String in_reply_to_status_id) {
        this.in_reply_to_status_id = in_reply_to_status_id;
    }

    public void setInReplyToUserId(String in_reply_to_user_id) {
        this.in_reply_to_user_id = in_reply_to_user_id;
    }

    public void setInReplyToScreenName(String in_reply_to_screen_name) {
        this.in_reply_to_screen_name = in_reply_to_screen_name;
    }

    public void setGeo(GeoBean geo) {
        this.geo = geo;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public void setRepostsCount(int reposts_count) {
        this.reposts_count = reposts_count;
    }

    public void setCommentsCount(int comments_count) {
        this.comments_count = comments_count;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public boolean isTruncated() {
        return truncated;
    }

    public String getInReplyToStatusId() {
        return in_reply_to_status_id;
    }

    public String getInReplyToUserId() {
        return in_reply_to_user_id;
    }

    public String getInReplyToScreenName() {
        return in_reply_to_screen_name;
    }

    public Object getGeo() {
        return geo;
    }

    public String getMid() {
        return mid;
    }

    public int getRepostsCount() {
        return reposts_count;
    }

    public int getCommentsCount() {
        return comments_count;
    }

    public UserBean getUser() {
        return user;
    }

    public String getOriginalPic() {
        return original_pic;
    }

    public void setOriginalPic(String original_pic) {
        this.original_pic = original_pic;
    }

    public String getIdstr() {
        return idstr;
    }

    public void setIdstr(String idstr) {
        this.idstr = idstr;
    }

    public String getThumbnailPic() {
        return thumbnail_pic;
    }

    public void setThumbnailPic(String thumbnail_pic) {
        this.thumbnail_pic = thumbnail_pic;
    }

    public String getBmiddlePic() {
        return bmiddle_pic;
    }

    public void setBmiddlePic(String bmiddle_pic) {
        this.bmiddle_pic = bmiddle_pic;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public StatusesBean getRetweetedStatus() {
        return retweeted_status;
    }

    public void setRetweetedStatus(StatusesBean retweeted_status) {
        this.retweeted_status = retweeted_status;
    }

    public int getAttitudesCount() {
        return attitudes_count;
    }

    public void setAttitudesCount(int attitudes_count) {
        this.attitudes_count = attitudes_count;
    }
}
