package com.example.flowergrass.data;

import java.util.List;

public class Event {
    private String title;
    private String date;
    private String details;
    private List<String> comments;

    public Event(String title, String date,  String details) {
        this.title = title;
        this.date = date;
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String birthday) {
        this.date = birthday;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
