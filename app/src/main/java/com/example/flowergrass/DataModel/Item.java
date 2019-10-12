package com.example.flowergrass.DataModel;


import com.google.firebase.Timestamp;

public class Item extends Post {
    private String imageUrl;

    public Item(String id, String author, String title,String hashTag, Timestamp dateCreated, String content, String imageUrl) {
        super(id, author,title, hashTag,dateCreated, content);
        this.imageUrl = imageUrl;
    }

}
