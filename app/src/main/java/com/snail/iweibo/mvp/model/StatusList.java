package com.snail.iweibo.mvp.model;
import java.io.Serializable;
import java.util.List;

/**
 * 微博列表结构。
 * @see <a href="http://t.cn/zjM1a2W">常见返回对象数据结构</a>
 * Created by alexwan on 16/1/30.
 */
public class StatusList implements Serializable {

    private int previous_cursor;
    private long next_cursor;
    private int total_number;
    private List<Status> statuses;
    private boolean hasvisible;
    private Object[] advertises;

    public boolean isHasvisible() {
        return hasvisible;
    }

    public void setHasvisible(boolean hasvisible) {
        this.hasvisible = hasvisible;
    }

    public Object[] getAdvertises() {
        return advertises;
    }

    public void setAdvertises(Object[] advertises) {
        this.advertises = advertises;
    }
    public void setPreviousCursor(int previous_cursor) {
        this.previous_cursor = previous_cursor;
    }

    public void setNextCursor(long next_cursor) {
        this.next_cursor = next_cursor;
    }

    public void setTotalNumber(int total_number) {
        this.total_number = total_number;
    }

    public void setStatuses(List<Status> statuses) {
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

    public List<Status> getStatuses() {
        return statuses;
    }

    public void addStatuses(List<Status> statuses) {
        this.statuses.addAll(0 , statuses);
    }

}
