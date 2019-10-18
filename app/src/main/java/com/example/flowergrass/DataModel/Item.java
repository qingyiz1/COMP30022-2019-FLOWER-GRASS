package com.example.flowergrass.DataModel;


import com.google.firebase.Timestamp;

import java.util.Map;

public class Item extends Post {
    private String imageUrl;
    private String category = "Item";

    public Item(String id, String author, String title,String hashTag, Timestamp dateCreated, String content, String imageUrl) {
        super(id, author,title, hashTag,dateCreated, content);
        this.imageUrl = imageUrl;
    }

    public Map<String,Object> toMap() {
        Map<String, Object> newItem = super.toMap();
        newItem.put("category", category);
        return newItem;
    }


}
