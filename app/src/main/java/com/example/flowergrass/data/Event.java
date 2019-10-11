package com.example.flowergrass.data;

import java.util.List;

public class Event extends Post{
    private List<String> comments;


    public Event(String id,String title, String date, String content) {
        super(id,title,date,content);

    }

}
