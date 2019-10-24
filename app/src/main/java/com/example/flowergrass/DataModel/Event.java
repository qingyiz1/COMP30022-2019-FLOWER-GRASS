package com.example.flowergrass.DataModel;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

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
        getAuthorAvatar();
    }

    public Map<String,Object> toMap(){
        Map<String,Object> newEvent = super.toMap();
        newEvent.put("category",category);
        return newEvent;
    }

    private void getAuthorAvatar(){
        DocumentReference docRef = db.collection("users").document(this.getAuthorUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
              @Override
              public void onSuccess(DocumentSnapshot documentSnapshot) {
                  try {
                      authorAvatarId = Integer.parseInt(documentSnapshot.get("avatarID").toString());
                  } catch (NullPointerException e) {
                      e.printStackTrace();
                  }
              }
          }
        );
    }

}
