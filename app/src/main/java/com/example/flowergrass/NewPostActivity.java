package com.example.flowergrass;

import android.os.Bundle;

import com.example.flowergrass.utils.BaseActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;


import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class NewPostActivity extends BaseActivity {

    private static final String TAG = "NewPostActivity";
    private static final String REQUIRED = "Required";

    private EditText mTitleField;
    private EditText mBodyField;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);







//
//        mTitleField = findViewById(R.id.fieldTitle);
//        mBodyField = findViewById(R.id.fieldBody);
//        //mSubmitButton = findViewById(R.id.fabSubmitPost);
//
////        mSubmitButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                submitPost();
////            }
////        });
    }

    private void readData(){
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

}