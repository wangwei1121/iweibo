package com.snail.iweibo.mvp.model;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * 好友分组信息。
 * Created by alexwan on 16/4/1.
 */
public class Group implements Serializable {
    public static final String GROUP_ID_ALL = "1";

    /** 微博分组ID **/
    public String id;
    /** 微博分组字符串ID **/
    public String idStr;
    /** 分组名称 **/
    public String name;
    /** 类型（不公开分组等） **/
    public String mode;
    /** 是否公开 **/
    public int visible;
    /** 喜欢数 **/
    public int like_count;
    /** 分组成员数 **/
    public int member_count;
    /** 分组描述 **/
    public String description;
    /** 分组的Tag 信息 **/
    public ArrayList<Tag> tags;
    /** 头像信息 **/
    public String profile_image_url;
    /** 分组所属用户信息 **/
    public UserBean user;
    /** 分组创建时间 **/
    public String createAtTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getMember_count() {
        return member_count;
    }

    public void setMember_count(int member_count) {
        this.member_count = member_count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url = profile_image_url;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getCreateAtTime() {
        return createAtTime;
    }

    public void setCreateAtTime(String createAtTime) {
        this.createAtTime = createAtTime;
    }
}
