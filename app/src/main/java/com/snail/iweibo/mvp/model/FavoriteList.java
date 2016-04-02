package com.snail.iweibo.mvp.model;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * 我喜欢的微博信息列表结构体。
 * Created by alexwan on 16/4/1.
 */
public class FavoriteList implements Serializable {
    public ArrayList<Favorite> favoriteList;
    public int total_number;

    public ArrayList<Favorite> getFavoriteList() {
        return favoriteList;
    }

    public void setFavoriteList(ArrayList<Favorite> favoriteList) {
        this.favoriteList = favoriteList;
    }

    public int getTotal_number() {
        return total_number;
    }

    public void setTotal_number(int total_number) {
        this.total_number = total_number;
    }
}
