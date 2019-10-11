package com.example.flowergrass.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Event extends Post{
    private List<String> comments;


    public Event(String id,String author,String title, String date, String content) {
        super(id,author,title,date,content);
    }

    public Map<String,Object> toMap(){
        Map<String,Object> newEvent = new HashMap<>();
        newEvent.put("Id",this.getId());
        newEvent.put("author",this.getAuthor());
        newEvent.put("title",this.getTitle());
        newEvent.put("date",this.getDate());
        newEvent.put("content",this.getContent());
        return newEvent;
    }

}
