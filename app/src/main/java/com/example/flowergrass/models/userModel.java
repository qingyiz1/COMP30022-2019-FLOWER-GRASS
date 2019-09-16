package com.example.flowergrass.models;


// [START blog_user_class]

import java.sql.Timestamp;
import java.util.List;

public class userModel {

    public List<Integer> birthday;
    public String email;
    public String nickName;


    public userModel() {
        // Default constructor required for calls to DataSnapshot.getValue(userModel.class)
    }

    public userModel(List<Integer> birthday, String email, String nickName) {
        this.birthday = birthday;
        this.email = email;
        this.nickName = nickName;
    }

}
// [END blog_user_class]