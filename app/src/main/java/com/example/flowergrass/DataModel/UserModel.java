package com.example.flowergrass.DataModel;


// [START blog_user_class]

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.flowergrass.utils.BaseActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserModel {

    public String birthday;
    public String email;
    public String nickName;
    protected static final String TAG = "UserModel";
    public Timestamp dateCreated;
    public int avatarID;

    protected FirebaseFirestore db = FirebaseFirestore.getInstance();

    String uid;

    String image;

    public UserModel() {
        // Default constructor required for calls to DataSnapshot.getValue(UserModel.class)
    }

    public UserModel(int avatarID,String birthday, String email, String nickName, Timestamp dateCreated) {
        this.avatarID = avatarID;
        this.birthday = birthday;
        this.email = email;
        this.nickName = nickName;
        this.dateCreated = dateCreated;
    }

    public void getNickname() {

        DocumentReference docRef = db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        nickName =document.getString("nickName");
                        setNickName(nickName);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }


            }

        });

    }

    public Map<String, Object> toMap() {
        // Create a new user with a first and last name
        Map<String, Object> newUser = new HashMap<>();
        newUser.put("birthday", this.birthday);
        newUser.put("avatarID",this.avatarID);
        newUser.put("email", this.email);
        newUser.put("nickName", this.nickName);
        newUser.put("dateCreated",this.dateCreated);
        return newUser;
    }




    public void setNickName(String nickName){
        this.nickName = nickName;
        //Log.d(TAG,nickName);
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
// [END blog_user_class]