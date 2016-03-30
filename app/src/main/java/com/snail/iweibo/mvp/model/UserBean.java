package com.snail.iweibo.mvp.model;
/**
 * UserBean
 * id : 1404376560
 * screen_name : zaku
 * name : zaku
 * province : 11
 * city : 5
 * location : 北京 朝阳区
 * description : 人生五十年，乃如梦如幻；有生斯有死，壮士复何憾。
 * url : http://blog.sina.com.cn/zaku
 * profile_image_url : http://tp1.sinaimg.cn/1404376560/50/0/1
 * domain : zaku
 * gender : m
 * followers_count : 1204
 * friends_count : 447
 * statuses_count : 2908
 * favourites_count : 0
 * created_at : Fri Aug 28 00:00:00 +0800 2009
 * following : false
 * allow_all_act_msg : false
 * remark :
 * geo_enabled : true
 * verified : false
 * allow_all_comment : true
 * avatar_large : http://tp1.sinaimg.cn/1404376560/180/0/1
 * verified_reason :
 * follow_me : false
 * online_status : 0
 * bi_followers_count : 215
 * Created by alexwan on 16/3/23.
 */
public class UserBean {
    private long id;
    private String screen_name;
    private String name;
    private String province;
    private String city;
    private String location;
    private String description;
    private String url;
    private String profile_image_url;
    private String domain;
    private String gender;
    private int followers_count;
    private int friends_count;
    private int statuses_count;
    private int favourites_count;
    private String created_at;
    private boolean following;
    private boolean allow_all_act_msg;
    private String remark;
    private boolean geo_enabled;
    private boolean verified;
    private boolean allow_all_comment;
    private String avatar_large;
    private String verified_reason;
    private boolean follow_me;
    private int online_status;
    private int bi_followers_count;

    public void setId(int id) { this.id = id;}

    public void setScreen_name(String screen_name) { this.screen_name = screen_name;}

    public void setName(String name) { this.name = name;}

    public void setProvince(String province) { this.province = province;}

    public void setCity(String city) { this.city = city;}

    public void setLocation(String location) { this.location = location;}

    public void setDescription(String description) { this.description = description;}

    public void setUrl(String url) { this.url = url;}

    public void setProfile_image_url(String profile_image_url) { this.profile_image_url = profile_image_url;}

    public void setDomain(String domain) { this.domain = domain;}

    public void setGender(String gender) { this.gender = gender;}

    public void setFollowers_count(int followers_count) { this.followers_count = followers_count;}

    public void setFriends_count(int friends_count) { this.friends_count = friends_count;}

    public void setStatuses_count(int statuses_count) { this.statuses_count = statuses_count;}

    public void setFavourites_count(int favourites_count) { this.favourites_count = favourites_count;}

    public void setCreated_at(String created_at) { this.created_at = created_at;}

    public void setFollowing(boolean following) { this.following = following;}

    public void setAllow_all_act_msg(boolean allow_all_act_msg) { this.allow_all_act_msg = allow_all_act_msg;}

    public void setRemark(String remark) { this.remark = remark;}

    public void setGeo_enabled(boolean geo_enabled) { this.geo_enabled = geo_enabled;}

    public void setVerified(boolean verified) { this.verified = verified;}

    public void setAllow_all_comment(boolean allow_all_comment) { this.allow_all_comment = allow_all_comment;}

    public void setAvatar_large(String avatar_large) { this.avatar_large = avatar_large;}

    public void setVerified_reason(String verified_reason) { this.verified_reason = verified_reason;}

    public void setFollow_me(boolean follow_me) { this.follow_me = follow_me;}

    public void setOnline_status(int online_status) { this.online_status = online_status;}

    public void setBi_followers_count(int bi_followers_count) { this.bi_followers_count = bi_followers_count;}

    public long getId() { return id;}

    public String getScreen_name() { return screen_name;}

    public String getName() { return name;}

    public String getProvince() { return province;}

    public String getCity() { return city;}

    public String getLocation() { return location;}

    public String getDescription() { return description;}

    public String getUrl() { return url;}

    public String getProfile_image_url() { return profile_image_url;}

    public String getDomain() { return domain;}

    public String getGender() { return gender;}

    public int getFollowers_count() { return followers_count;}

    public int getFriends_count() { return friends_count;}

    public int getStatuses_count() { return statuses_count;}

    public int getFavourites_count() { return favourites_count;}

    public String getCreated_at() { return created_at;}

    public boolean isFollowing() { return following;}

    public boolean isAllow_all_act_msg() { return allow_all_act_msg;}

    public String getRemark() { return remark;}

    public boolean isGeo_enabled() { return geo_enabled;}

    public boolean isVerified() { return verified;}

    public boolean isAllow_all_comment() { return allow_all_comment;}

    public String getAvatar_large() { return avatar_large;}

    public String getVerified_reason() { return verified_reason;}

    public boolean isFollow_me() { return follow_me;}

    public int getOnline_status() { return online_status;}

    public int getBi_followers_count() { return bi_followers_count;}
}
