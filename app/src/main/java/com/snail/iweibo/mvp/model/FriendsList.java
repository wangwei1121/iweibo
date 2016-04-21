package com.snail.iweibo.mvp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by luckyliang on 16/4/21.
 */
public class FriendsList implements Serializable {
    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }

    List<Friend> friends;
    int next_cursor;//
    int previous_cursor;//

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

    int total_number;//
}
