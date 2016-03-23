package com.snail.iweibo.mvp.model;
import java.util.List;

/**
 * Created by alexwan on 16/3/23.
 */
public class StatusesBean {
    //
    private String created_at;
    private long id;
    private String text;
    private boolean favorited;
    private boolean truncated;
    private String in_reply_to_status_id;
    private String in_reply_to_user_id;
    private String in_reply_to_screen_name;
    private Object geo;
    private String mid;
    private int reposts_count;
    private int comments_count;
    /**
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
     */

    private UserBean user;
    private List<?> annotations;

    public void setCreated_at(String created_at) { this.created_at = created_at;}

    public void setId(long id) { this.id = id;}

    public void setText(String text) { this.text = text;}

    public void setFavorited(boolean favorited) { this.favorited = favorited;}

    public void setTruncated(boolean truncated) { this.truncated = truncated;}

    public void setIn_reply_to_status_id(String in_reply_to_status_id) {
        this.in_reply_to_status_id = in_reply_to_status_id;
    }

    public void setIn_reply_to_user_id(String in_reply_to_user_id) {
        this.in_reply_to_user_id = in_reply_to_user_id;
    }

    public void setIn_reply_to_screen_name(String in_reply_to_screen_name) {
        this.in_reply_to_screen_name = in_reply_to_screen_name;
    }

    public void setGeo(Object geo) { this.geo = geo;}

    public void setMid(String mid) { this.mid = mid;}

    public void setReposts_count(int reposts_count) { this.reposts_count = reposts_count;}

    public void setComments_count(int comments_count) { this.comments_count = comments_count;}

    public void setUser(UserBean user) { this.user = user;}

    public void setAnnotations(List<?> annotations) { this.annotations = annotations;}

    public String getCreated_at() { return created_at;}

    public long getId() { return id;}

    public String getText() { return text;}

    public boolean isFavorited() { return favorited;}

    public boolean isTruncated() { return truncated;}

    public String getIn_reply_to_status_id() { return in_reply_to_status_id;}

    public String getIn_reply_to_user_id() { return in_reply_to_user_id;}

    public String getIn_reply_to_screen_name() { return in_reply_to_screen_name;}

    public Object getGeo() { return geo;}

    public String getMid() { return mid;}

    public int getReposts_count() { return reposts_count;}

    public int getComments_count() { return comments_count;}

    public UserBean getUser() { return user;}

    public List<?> getAnnotations() { return annotations;}
}
