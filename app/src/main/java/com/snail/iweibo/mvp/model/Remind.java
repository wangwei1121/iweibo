package com.snail.iweibo.mvp.model;
/**
 * 未读消息
 * Created by alexwan on 16/4/1.
 */
public class Remind {
    private int status; // 新微博未读数
    private int follower; // 新粉丝数
    private int cmt; // 新评论数
    private int dm; // 新私信数
    private int mention_status; // 新提及我的微博数
    private int mention_cmt; // 新提及我的评论数
    private int group; // 微群消息未读数
    private int private_group; // 私有微群消息未读数
    private int notice; // 新通知未读数
    private int invite; // 新邀请未读数
    private int badge; // 新勋章数
    private int photo; // 相册消息未读数
    private int msgbox; // {{{3}}}

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getFollower() {
        return follower;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }

    public int getCmt() {
        return cmt;
    }

    public void setCmt(int cmt) {
        this.cmt = cmt;
    }

    public int getDm() {
        return dm;
    }

    public void setDm(int dm) {
        this.dm = dm;
    }

    public int getMentionStatus() {
        return mention_status;
    }

    public void setMentionStatus(int mention_status) {
        this.mention_status = mention_status;
    }

    public int getMentionCmt() {
        return mention_cmt;
    }

    public void setMentionCmt(int mention_cmt) {
        this.mention_cmt = mention_cmt;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getPrivateGroup() {
        return private_group;
    }

    public void setPrivateGroup(int private_group) {
        this.private_group = private_group;
    }

    public int getNotice() {
        return notice;
    }

    public void setNotice(int notice) {
        this.notice = notice;
    }

    public int getInvite() {
        return invite;
    }

    public void setInvite(int invite) {
        this.invite = invite;
    }

    public int getBadge() {
        return badge;
    }

    public void setBadge(int badge) {
        this.badge = badge;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public int getMsgBox() {
        return msgbox;
    }

    public void setMsgBox(int msgbox) {
        this.msgbox = msgbox;
    }
}
