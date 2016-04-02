package com.snail.iweibo.mvp.model;
import java.io.Serializable;

/**
 * MusicInfo
 * Created by alexwan on 16/4/1.
 */
public class MusicInfo implements Serializable {
    //public boolean result;
    private String author;
    private String title;
    private String album;
    private String playUrl;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }
}
