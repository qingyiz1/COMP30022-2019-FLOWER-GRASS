package com.example.flowergrass.Activity;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.flowergrass.R;
import com.example.flowergrass.DataModel.Event;
import com.example.flowergrass.DataModel.UserModel;
import com.example.flowergrass.utils.BaseActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;

public class NewEventActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "NewEventActivity";
    private static final String REQUIRED = "Required";


    private EditText mTitleField;
    private EditText mHashtagField;
    private EditText mBodyField;
    private Event event;
    private UserModel currentUser;
    private Button submitBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        // Views
        mTitleField = findViewById(R.id.fieldTitle);
        mHashtagField = findViewById(R.id.fieldHashTag);
        mBodyField = findViewById(R.id.fieldBody);

        // Button
        submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(this);

        // Get current user
        currentUser = new UserModel();
        currentUser.getNickname();

    }



    @Override
    public void onClick(View view) {
        if(view == submitBtn){
            submitPost();
        }
    }


    private void submitPost(){

        this.event = new Event(mAuth.getUid(), currentUser.nickName,mTitleField.getText().toString(),mHashtagField.getText().toString(),
                Timestamp.now(),mBodyField.getText().toString());

        db.collection("posts").document(this.event.getId())
                .set(event.toMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

}
