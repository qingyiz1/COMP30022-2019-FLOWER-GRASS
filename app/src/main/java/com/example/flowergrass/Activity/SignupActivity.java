package com.example.flowergrass.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.flowergrass.R;
import com.example.flowergrass.DataModel.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class SignupActivity extends MainActivity {
    Button signupBtn,chooseBtn;
    ImageView avatar;
    private EditText mEmailField,mPasswordField,mNickName;
    private EditText fieldBirthday,fieldBirthMonth,fieldBirthYear;
    private UserModel newUser;
    private String userUID;
    protected static final String TAG = "SignUpActivity";
    private int currentImgId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        currentImgId = R.drawable.avatar_boy;

        signupBtn = findViewById(R.id.SignUpBtn2);
        chooseBtn = findViewById(R.id.signup_avator_choose_btn);
        avatar = findViewById(R.id.signup_avatar_icon);
        mEmailField = findViewById(R.id.fieldEmail2);
        mPasswordField = findViewById(R.id.fieldPassword2);
        mNickName = findViewById(R.id.fieldNickname2);
        fieldBirthday = findViewById(R.id.fieldBirthDay);
        fieldBirthMonth = findViewById(R.id.fieldBirthMonth);
        fieldBirthYear = findViewById(R.id.fieldBirthYear);

        signupBtn.setOnClickListener(this);
        chooseBtn.setOnClickListener(this);
    }


    public void updateDatabase(String filePath){
        String birthday = fieldBirthday.getText().toString()+"/"+fieldBirthMonth.getText().toString()+"/"+fieldBirthYear.getText().toString();

        this.newUser = new UserModel(currentImgId,birthday,mEmailField.getText().toString(),mNickName.getText().toString(), Timestamp.now());



        db.collection("users").document(filePath)
                .set(newUser.toMap())
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


    protected void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            userUID = mAuth.getUid();
                            updateDatabase(userUID);

                            new AlertDialog.Builder(SignupActivity.this)
                                    .setTitle("Success!")
                                    .setMessage("New account Successfully created!")
                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(SignupActivity.this,Homepage.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    })
                                    // A null listener allows the button to dismiss the dialog and take no further action.
                                    //.setNegativeButton(android.R.string.no, null)
                                    .setIcon(R.drawable.ic_create_success)
                                    .show();

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "create failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }


    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.SignUpBtn2) {
            createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
        }else if(i == R.id.signup_avator_choose_btn){
            Intent intent = new Intent(SignupActivity.this,AvatarActivity.class);
            startActivityForResult(intent,0x01);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0x01 || requestCode==0x02){
            int id = data.getIntExtra("avatar",currentImgId);
            if(id != currentImgId){
                currentImgId = id;
            }
            avatar.setImageResource(id);
        }
    }
}
