package com.example.flowergrass.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.flowergrass.R;
import com.example.flowergrass.data.Event;
import com.example.flowergrass.models.Post;
import com.example.flowergrass.models.userModel;
import com.example.flowergrass.utils.BaseActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.UploadTask;

public class NewEventActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "NewEventActivity";
    private static final String REQUIRED = "Required";


    private EditText mTitleField;
    private EditText mBodyField;
    private Event event;
    private userModel currentUser;
    private Button submitBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        mTitleField = findViewById(R.id.fieldTitle);
        mBodyField = findViewById(R.id.fieldBody);

        submitBtn = findViewById(R.id.submitBtn);

        submitBtn.setOnClickListener(this);
        currentUser = new userModel();
        currentUser.getNickname();

    }



    @Override
    public void onClick(View view) {
        if(view == submitBtn){
            submitPost();
        }
    }


    private void submitPost(){

        this.event = new Event(mAuth.getUid(), currentUser.nickName,mTitleField.getText().toString(),""+System.currentTimeMillis(),mBodyField.getText().toString());
        db.collection("posts").document(mTitleField.getText().toString())
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
