package com.snail.iweibo.mvp.model;
import java.util.List;

/**
 * Created by alexwan on 16/1/30.
 */
public class Statuses {

    private int previous_cursor;
    private long next_cursor;
    private int total_number;

    private List<StatusesBean> statuses;

    public void setPreviousCursor(int previous_cursor) {
        this.previous_cursor = previous_cursor;
    }

    public void setNextCursor(long next_cursor) {
        this.next_cursor = next_cursor;
    }

    public void setTotalNumber(int total_number) {
        this.total_number = total_number;
    }

    public void setStatuses(List<StatusesBean> statuses) {
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

    public List<StatusesBean> getStatuses() {
        return statuses;
    }

    public void addStatuses(List<StatusesBean> statuses) {
        this.statuses.addAll(0 , statuses);
    }

}
