package com.snail.iweibo.util;

/**
 * Created by wang.weib on 2016/1/29.
 */
public class Constants {

    public static final String REDIRECT_URL = "http://www.sina.com";// 应用的回调页
    public static final String SCOPE = "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";

    public static final String WEIBO_BASE_URL = "https://api.weibo.com/";

    //最新的公共微博
    public static final String PUBLIC_TIMELINE = "2/statuses/public_timeline.json";

    //获取当前登录用户及其所关注用户的最新微博
    public static final String FRIENDS_TIMELINE = "2/statuses/friends_timeline.json";
}
