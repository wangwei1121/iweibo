package com.snail.iweibo.mvp.model;
import java.util.List;

/**
 * Created by alexwan on 16/1/30.
 */
public class Statuses {
    /**
     * statuses : [{"created_at":"Tue May 31 17:46:55 +0800 2011","id":11488058246,"text":"求关注。","favorited":false,"truncated":false,"in_reply_to_status_id":"","in_reply_to_user_id":"","in_reply_to_screen_name":"","geo":null,"mid":"5612814510546515491","reposts_count":8,"comments_count":9,"annotations":[],"user":{"id":1404376560,"screen_name":"zaku","name":"zaku","province":"11","city":"5","location":"北京 朝阳区","description":"人生五十年，乃如梦如幻；有生斯有死，壮士复何憾。","url":"http://blog.sina.com.cn/zaku","profile_image_url":"http://tp1.sinaimg.cn/1404376560/50/0/1","domain":"zaku","gender":"m","followers_count":1204,"friends_count":447,"statuses_count":2908,"favourites_count":0,"created_at":"Fri Aug 28 00:00:00 +0800 2009","following":false,"allow_all_act_msg":false,"remark":"","geo_enabled":true,"verified":false,"allow_all_comment":true,"avatar_large":"http://tp1.sinaimg.cn/1404376560/180/0/1","verified_reason":"","follow_me":false,"online_status":0,"bi_followers_count":215}}]
     * previous_cursor : 0
     * next_cursor : 11488013766
     * total_number : 81655
     */

    private int previous_cursor;
    private long next_cursor;
    private int total_number;
    /**
     * created_at : Tue May 31 17:46:55 +0800 2011
     * id : 11488058246
     * text : 求关注。
     * favorited : false
     * truncated : false
     * in_reply_to_status_id :
     * in_reply_to_user_id :
     * in_reply_to_screen_name :
     * geo : null
     * mid : 5612814510546515491
     * reposts_count : 8
     * comments_count : 9
     * annotations : []
     * user : {"id":1404376560,"screen_name":"zaku","name":"zaku","province":"11","city":"5","location":"北京 朝阳区","description":"人生五十年，乃如梦如幻；有生斯有死，壮士复何憾。","url":"http://blog.sina.com.cn/zaku","profile_image_url":"http://tp1.sinaimg.cn/1404376560/50/0/1","domain":"zaku","gender":"m","followers_count":1204,"friends_count":447,"statuses_count":2908,"favourites_count":0,"created_at":"Fri Aug 28 00:00:00 +0800 2009","following":false,"allow_all_act_msg":false,"remark":"","geo_enabled":true,"verified":false,"allow_all_comment":true,"avatar_large":"http://tp1.sinaimg.cn/1404376560/180/0/1","verified_reason":"","follow_me":false,"online_status":0,"bi_followers_count":215}
     */

    private List<StatusesBean> statuses;

    public void setPreviousCursor(int previous_cursor) {
        this.previous_cursor = previous_cursor;
    }

    public void setNextCursor(long next_cursor) {
        this.next_cursor = next_cursor;
    }

    public void setTotalNumber(int total_number) {
        this.total_number = total_number;
    }

    public void setStatuses(List<StatusesBean> statuses) {
        this.statuses = statuses;
    }

    public int getPreviousCursor() {
        return previous_cursor;
    }

    public long getNextCursor() {
        return next_cursor;
    }

    public int getTotalNumber() {
        return total_number;
    }

    public List<StatusesBean> getStatuses() {
        return statuses;
    }

    public void addStatuses(List<StatusesBean> statuses) {
        this.statuses.addAll(0 , statuses);
    }

}
