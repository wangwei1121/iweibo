package com.snail.iweibo.mvp.model;
/**
 * 我喜欢的微博标签（Tag）结构体。
 * Created by alexwan on 16/4/1.
 */
public class Tag {

    /** type 取值，0：普通微博，1：私密微博，3：指定分组微博，4：密友微博 */
    private int id;
    /** 分组的组号 */
    private String tag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
