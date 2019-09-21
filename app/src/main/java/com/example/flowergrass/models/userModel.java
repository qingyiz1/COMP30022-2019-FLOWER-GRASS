package com.example.flowergrass.models;


// [START blog_user_class]

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.flowergrass.utils.BaseActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.sql.Timestamp;
import java.util.List;

public class userModel extends BaseActivity{

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

    public void getNickname(){
        mAuth.getCurrentUser();
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("ok", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w("what", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}
// [END blog_user_class]