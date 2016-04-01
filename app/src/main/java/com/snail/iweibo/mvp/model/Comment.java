package com.snail.iweibo.mvp.model;
/**
 * 评论
 * Created by alexwan on 16/4/1.
 */
public class Comment {
    private long  id; // 评论ID
    private String created_at; // 创建时间
    private String text;// 内容
    private String source; // 来源
    private UserBean user; // 用户信息
    private String mid; // 微博MID
    private String idstr; // 字符串型的评论ID
    private StatusesBean  status; // 微博
    private Comment reply_comment; // 回复的评论

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getIdstr() {
        return idstr;
    }

    public void setIdstr(String idstr) {
        this.idstr = idstr;
    }

    public StatusesBean getStatus() {
        return status;
    }

    public void setStatus(StatusesBean status) {
        this.status = status;
    }

    public Comment getReplyComment() {
        return reply_comment;
    }

    public void setReplyComment(Comment reply_comment) {
        this.reply_comment = reply_comment;
    }
}
