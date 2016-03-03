package com.snail.iweibo.mvp.model;

import java.util.List;

/**
 * Created by wang.weib on 2016/3/2.
 */
public class PublicNews {
    private List<Statuse> statuses;
    private Integer previous_cursor;
    private Long next_cursor;
    private Integer total_number;

    public List<Statuse> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Statuse> statuses) {
        this.statuses = statuses;
    }

    public Integer getPrevious_cursor() {
        return previous_cursor;
    }

    public void setPrevious_cursor(Integer previous_cursor) {
        this.previous_cursor = previous_cursor;
    }

    public Long getNext_cursor() {
        return next_cursor;
    }

    public void setNext_cursor(Long next_cursor) {
        this.next_cursor = next_cursor;
    }

    public Integer getTotal_number() {
        return total_number;
    }

    public void setTotal_number(Integer total_number) {
        this.total_number = total_number;
    }
}
