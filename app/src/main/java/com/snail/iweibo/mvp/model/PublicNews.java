package com.snail.iweibo.mvp.model;

import java.util.List;

/**
 * Created by wang.weib on 2016/3/2.
 */
public class PublicNews {
    private List<Statuse> statuses;
    private Long previous_cursor;
    private Long next_cursor;
    private Long total_number;

    public List<Statuse> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Statuse> statuses) {
        this.statuses = statuses;
    }

    public Long getNext_cursor() {
        return next_cursor;
    }

    public void setNext_cursor(Long next_cursor) {
        this.next_cursor = next_cursor;
    }

    public Long getPrevious_cursor() {
        return previous_cursor;
    }

    public void setPrevious_cursor(Long previous_cursor) {
        this.previous_cursor = previous_cursor;
    }

    public Long getTotal_number() {
        return total_number;
    }

    public void setTotal_number(Long total_number) {
        this.total_number = total_number;
    }
}
