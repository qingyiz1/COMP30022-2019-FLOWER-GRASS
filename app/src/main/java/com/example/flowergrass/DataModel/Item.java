package com.example.flowergrass.DataModel;


import com.google.firebase.Timestamp;

import java.util.Map;

public class Item extends Post {
    private String imageUrl;
    private String category = "Item";

    public Item(String authorUid, String author, String title,String hashTag, Timestamp dateCreated, String content) {
        super(authorUid, author,title, hashTag,dateCreated, content);
        this.imageUrl = this.getId();
    }

    public Map<String,Object> toMap() {
        Map<String, Object> newItem = super.toMap();
        newItem.put("category", category);
        newItem.put("imageUrl",imageUrl);
        return newItem;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
