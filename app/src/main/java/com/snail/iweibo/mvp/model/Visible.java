package com.snail.iweibo.mvp.model;
import java.io.Serializable;

/**
 * 微博可见性结构体。
 * Created by alexwan on 16/4/1.
 */
public class Visible implements Serializable {
    //
    public static final int VISIBLE_NORMAL  = 0;
    public static final int VISIBLE_PRIVACY = 1;
    public static final int VISIBLE_GROUPED = 2;
    public static final int VISIBLE_FRIEND  = 3;
    /** type 取值，0：普通微博，1：私密微博，3：指定分组微博，4：密友微博 */
    private int type;
    /** 分组的组号 */
    private int list_id;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getListId() {
        return list_id;
    }

    public void setListId(int list_id) {
        this.list_id = list_id;
    }
}
