package com.example.flowergrass.DataModel;

import com.example.flowergrass.utils.BaseActivity;
import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Post extends BaseActivity {


    private String authorUid;
    private String id;
    private String author;
    public String title;
    private String hashTag;


    private Timestamp dateCreated;
    private String content;
    private List<String> comments;
    public int thumbsUp;

    public Post(String authorUid,String author,String title,String hashTag, Timestamp date, String content) {
        this.authorUid = authorUid;
        this.author = author;
        this.title = title;
        this.dateCreated = date;
        this.content = content;
        this.id = authorUid+date.toDate().toString();
        this.hashTag = hashTag;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ID",id);
        result.put("authorUid",authorUid);
        result.put("author", author);
        result.put("title", title);
        result.put("hashTag",hashTag);
        result.put("content", content);
        result.put("dateCreated",dateCreated);
        return result;
    }

    public String getAuthorUid() {
        return authorUid;
    }

    public Timestamp getDateCreated() {return dateCreated;}

    public String getId(){return id;}

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
