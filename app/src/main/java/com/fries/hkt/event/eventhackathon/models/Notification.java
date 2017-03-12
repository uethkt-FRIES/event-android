package com.fries.hkt.event.eventhackathon.models;

/**
 * Created by hungtran on 3/12/17.
 */

public class Notification {
    private String content;
    private String title;
    private long created_at;

    public Notification() {
    }

    public Notification(String content, String title, long created_at) {
        this.content = content;
        this.title = title;
        this.created_at = created_at;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }
}
