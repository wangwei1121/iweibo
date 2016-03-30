package com.snail.iweibo.mvp.model;
import java.util.List;

/**
 * Created by alexwan on 16/3/23.
 */
public class StatusesBean {
    private String created_at; // 微博创建时间
    private long id; // 微博ID
    private String text; // 微博信息内容
    private boolean favorited; // 是否已收藏，true：是，false：否
    private boolean truncated; // 是否被截断，true：是，false：否
    private String in_reply_to_status_id; //
    private String in_reply_to_user_id; //
    private String in_reply_to_screen_name; // 回复用户名
    private GeoBean geo; // 地理信息字段
    private String mid; // 微博MID
    private int reposts_count; // 转发数
    private int comments_count; // 评论数
    private UserBean user; // 微博作者的用户信息字段
    private List<?> annotations; //
    private String source; // 微博来源
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

    public void setAnnotations(List<?> annotations) {
        this.annotations = annotations;
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

    public List<?> getAnnotations() {
        return annotations;
    }
}
