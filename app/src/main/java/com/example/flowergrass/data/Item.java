package com.example.flowergrass.data;


import android.os.Parcel;
import android.os.Parcelable;

public class Item extends Post {
    private String imageUrl;

    public Item(String id, String author, String title, String date, String content, String imageUrl) {
        super(id, author,title, date, content);
        this.imageUrl = imageUrl;
    }

}
