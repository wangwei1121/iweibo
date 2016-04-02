package com.snail.iweibo.mvp.model;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * 我喜欢的微博信息结构体。
 * Created by alexwan on 16/4/1.
 */
public class Favorite implements Serializable {
    /** 我喜欢的微博信息 */
    public Status status;
    /** 我喜欢的微博的 Tag 信息 */
    public ArrayList<Tag> tags;
    /** 创建我喜欢的微博信息的时间 */
    public String favorited_time;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    public String getFavorited_time() {
        return favorited_time;
    }

    public void setFavorited_time(String favorited_time) {
        this.favorited_time = favorited_time;
    }
}
