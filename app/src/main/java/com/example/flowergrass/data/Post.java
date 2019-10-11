package com.example.flowergrass.data;

import java.util.List;

public class Post {
    private String id;
    private String title;
    private String date;
    private String content;
    private List<String> comments;


    public Post(String id,String title, String date, String content) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.content = content;
    }

    public String getId(){return id;}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
