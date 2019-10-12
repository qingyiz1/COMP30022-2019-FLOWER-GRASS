package com.example.flowergrass.Activity;

import androidx.annotation.NonNull;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

                        new AlertDialog.Builder(NewEventActivity.this)
                                .setTitle("Success!")
                                .setMessage("New event successfully created!")
                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                // The dialog is automatically dismissed when a dialog button is clicked.
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                // A null listener allows the button to dismiss the dialog and take no further action.
                                //.setNegativeButton(android.R.string.no, null)
                                .setIcon(R.drawable.ic_create_success)
                                .show();

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
