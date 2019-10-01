package com.example.flowergrass.models;


// [START blog_user_class]

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.flowergrass.utils.BaseActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class userModel extends BaseActivity {

    public List<String> birthday;
    public String email;
    public String nickName;
    protected static final String TAG = "userModel";
    public Timestamp dateCreated;


    public userModel() {
        // Default constructor required for calls to DataSnapshot.getValue(userModel.class)
    }

    public userModel(List<String> birthday, String email, String nickName, Timestamp dateCreated) {
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
                        nickName =document.getString("Nickname");
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


    public void setNickName(String nickName){
        this.nickName = nickName;
        //Log.d(TAG,nickName);
    }

}
// [END blog_user_class]