package com.example.flowergrass.models;


// [START blog_user_class]

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.flowergrass.utils.BaseActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.sql.Timestamp;
import java.util.List;

public class userModel extends BaseActivity {

    public List<String> birthday;
    public String email;
    public String nickName;
    protected static final String TAG = "userModel";

    public userModel() {
        // Default constructor required for calls to DataSnapshot.getValue(userModel.class)
    }

    public userModel(List<String> birthday, String email, String nickName) {
        this.birthday = birthday;
        this.email = email;
        this.nickName = nickName;
    }

    public String getNickname() {
        DocumentReference docRef = db.collection("users").document(mAuth.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        this.nickName = docRef.get().getResult().getData().get("nickName").toString();
        return this.nickName;
    }
}
// [END blog_user_class]