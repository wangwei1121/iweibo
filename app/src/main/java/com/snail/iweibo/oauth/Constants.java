package com.snail.iweibo.oauth;

/**
 * Created by wang.weib on 2016/1/29.
 */
public interface Constants {
    String PROJECT_NAME = "iweibo";
    String SINA_TOKEN = "sina_token";

    String APP_KEY = "2160936024";           // 应用的APP_KEY
    String REDIRECT_URL = "http://www.sina.com";// 应用的回调页
    String SCOPE = "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";

    String WEIBO_BASE_URL = "https://api.weibo.com/";

    //最新的公共微博
    String PUBLIC_TIMELINE = "2/statuses/public_timeline.json";

}
