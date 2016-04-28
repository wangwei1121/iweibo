package com.snail.iweibo.mvp.model;
import java.io.Serializable;
import java.util.List;

/**
 * UserBeanList
 * Created by alexwan on 16/4/27.
 */
public class UserBeanList implements Serializable {
    private List<UserBean> users;
    private int next_cursor;
    private long previous_cursor;
    private int total_number;
}
