package com.snail.iweibo.mvp.model;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * 评论列表结构体。
 * Created by alexwan on 16/4/1.
 */
public class CommentList implements Serializable {
    private ArrayList<Comment> comments;
    private String previous_cursor;
    private String next_cursor;
    private int total_number;

    public ArrayList<Comment> getCommentList() {
        return comments;
    }

    public void setCommentList(ArrayList<Comment> commentList) {
        this.comments = commentList;
    }

    public String getPrevious_cursor() {
        return previous_cursor;
    }

    public void setPrevious_cursor(String previous_cursor) {
        this.previous_cursor = previous_cursor;
    }

    public String getNext_cursor() {
        return next_cursor;
    }

    public void setNext_cursor(String next_cursor) {
        this.next_cursor = next_cursor;
    }

    public int getTotal_number() {
        return total_number;
    }

    public void setTotal_number(int total_number) {
        this.total_number = total_number;
    }
}
