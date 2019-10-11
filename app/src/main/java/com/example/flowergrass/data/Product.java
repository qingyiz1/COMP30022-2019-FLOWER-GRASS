package com.example.flowergrass.data;


import android.os.Parcel;
import android.os.Parcelable;

public class Product extends Post {
    private String imageUrl;

    public Product(String id, String title, String date, String content,String imageUrl) {
        super(id, title, date, content);
        this.imageUrl = imageUrl;
    }

}
