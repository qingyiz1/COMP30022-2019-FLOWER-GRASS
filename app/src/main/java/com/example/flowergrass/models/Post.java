package com.example.flowergrass.models;



import com.google.type.Date;

import java.util.HashMap;
import java.util.Map;

// [START post_class]

public class Post {

    public String uid;
    public String author;
    public String title;
    public String content;
    public String dateCreated;
    public int thumbsUp = 0;
    public String imgSrc;
    public HashMap<String,Object> comments;

    public Post(String uid, String author, String title, String content) {
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.content = content;
        this.dateCreated = Date.getDefaultInstance().toString();
    }

    // [START post_to_map]

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("title", title);
        result.put("content", content);
        result.put("thumbsUp", thumbsUp);
        result.put("dateCreated",dateCreated);
        return result;
    }
    // [END post_to_map]

}
// [END post_class]
