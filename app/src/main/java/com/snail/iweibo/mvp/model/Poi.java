package com.snail.iweibo.mvp.model;
import java.io.Serializable;

/**
 * 位置信息结构体。
 * Created by alexwan on 16/4/1.
 */
public class Poi implements Serializable {
    /** Poi id */
    public String poiid;
    /** 名称 */
    public String title;
    /** 地址 **/
    public String address;
    /** 经度 **/
    public String lon;
    /** 纬度 **/
    public String lat;
    /** 分类 **/
    public String category;
    /** 城市 **/
    public String city;
    /** 省 **/
    public String province;
    /** 国家 **/
    public String country;
    /** 链接 **/
    public String url;
    /** 电话**/
    public String phone;
    /** 邮政编码 **/
    public String postcode;
    /** 微博ID **/
    public String weibo_id;
    /** 分类码 **/
    public String categorys;
    /** 分类名称 **/
    public String category_name;
    /** 图标 **/
    public String icon;
    /** 签到数 **/
    public String checkin_num;
    /** 签到用户数 **/
    public String checkin_user_num;
    /** tip数 **/
    public String tip_num;
    /** 照片数 **/
    public String photo_num;
    /** todo数量 **/
    public String todo_num;
    /** 距离 **/
    public String distance;

    public String getPoiid() {
        return poiid;
    }

    public void setPoiid(String poiid) {
        this.poiid = poiid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getWeibo_id() {
        return weibo_id;
    }

    public void setWeibo_id(String weibo_id) {
        this.weibo_id = weibo_id;
    }

    public String getCategorys() {
        return categorys;
    }

    public void setCategorys(String categorys) {
        this.categorys = categorys;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCheckin_num() {
        return checkin_num;
    }

    public void setCheckin_num(String checkin_num) {
        this.checkin_num = checkin_num;
    }

    public String getCheckin_user_num() {
        return checkin_user_num;
    }

    public void setCheckin_user_num(String checkin_user_num) {
        this.checkin_user_num = checkin_user_num;
    }

    public String getTip_num() {
        return tip_num;
    }

    public void setTip_num(String tip_num) {
        this.tip_num = tip_num;
    }

    public String getPhoto_num() {
        return photo_num;
    }

    public void setPhoto_num(String photo_num) {
        this.photo_num = photo_num;
    }

    public String getTodo_num() {
        return todo_num;
    }

    public void setTodo_num(String todo_num) {
        this.todo_num = todo_num;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
