package com.example.flowergrass.DataModel;

import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Event extends Post{
    private List<String> comments;
    private String category = "Event";

    public int getAuthorAvatarId() {
        return authorAvatarId;
    }

    public void setAuthorAvatarId(int authorAvatarId) {
        this.authorAvatarId = authorAvatarId;
    }

    private int authorAvatarId;

    public Event(String authorUid, String author, String title,String hashTag,Timestamp date, String content) {
        super(authorUid,author,title,hashTag,date,content);
    }

    public Map<String,Object> toMap(){
        Map<String,Object> newEvent = super.toMap();
        newEvent.put("category",category);
        return newEvent;
//        newEvent.put("Id",this.getId());
//        newEvent.put("author",this.getAuthor());
//        newEvent.put("title",this.getTitle());
//        newEvent.put("dateCreated",this.getDate());
//        newEvent.put("content",this.getContent());
//        return newEvent;
    }

}
