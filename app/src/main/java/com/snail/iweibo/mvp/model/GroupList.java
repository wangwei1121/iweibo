package com.snail.iweibo.mvp.model;
import java.util.List;

/**
 * GroupList
 * Created by alexwan on 16/4/11.
 */
public class GroupList {

    private List<Group> lists; // 组列表
    private long total_number; // 总数

    public List<Group> getLists() {
        return lists;
    }

    public long getTotal_number() {
        return total_number;
    }
}
