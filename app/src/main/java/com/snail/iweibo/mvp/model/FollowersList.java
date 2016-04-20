package com.snail.iweibo.mvp.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 粉丝列表结构
 * Created by luckyliang on 16/4/16.
 */
public class FollowersList implements Serializable {

    public ArrayList<Follower> getFollowerArrayList() {
        return users;
    }

    public void setFollowerArrayList(ArrayList<Follower> followerArrayList) {
        this.users = followerArrayList;
    }

    public int getNext_cursor() {
        return next_cursor;
    }

    public void setNext_cursor(int next_cursor) {
        this.next_cursor = next_cursor;
    }

    public int getPrevious_cursor() {
        return previous_cursor;
    }

    public void setPrevious_cursor(int previous_cursor) {
        this.previous_cursor = previous_cursor;
    }

    public int getTotal_number() {
        return total_number;
    }

    public void setTotal_number(int total_number) {
        this.total_number = total_number;
    }

    ArrayList<Follower> users;
    int next_cursor;
    int previous_cursor;
    int total_number;
}
