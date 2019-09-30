package com.example.flowergrass;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.flowergrass.models.userModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SignupActivity extends MainActivity {
    Button signupBtn;
    private EditText mEmailField,mPasswordField,mNickName;
    private EditText fieldBirthday,fieldBirthMonth,fieldBirthYear;
    private userModel newUser;
    private List<String> birthday = new ArrayList<>();
    private String userUID;
    protected static final String TAG = "SignUpActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();

        signupBtn = findViewById(R.id.SignUpBtn2);
        mEmailField = findViewById(R.id.fieldEmail2);
        mPasswordField = findViewById(R.id.fieldPassword2);
        mNickName = findViewById(R.id.fieldNickname2);
        fieldBirthday = findViewById(R.id.fieldBirthDay);
        fieldBirthMonth = findViewById(R.id.fieldBirthMonth);
        fieldBirthYear = findViewById(R.id.fieldBirthYear);

        findViewById(R.id.SignUpBtn2).setOnClickListener(this);
    }


    public void updateDatabase(String filePath){
        birthday.add(fieldBirthMonth.getText().toString());
        birthday.add(fieldBirthday.getText().toString());
        birthday.add(fieldBirthYear.getText().toString());
        this.newUser = new userModel(birthday,mEmailField.getText().toString(),mNickName.getText().toString());

        // Create a new user with a first and last name
        Map<String, Object> newUser = new HashMap<>();
        newUser.put("Birthday", this.newUser.birthday.get(0)+"/"+ this.newUser.birthday.get(1)+"/"+ this.newUser.birthday.get(2));
        newUser.put("Email", this.newUser.email);
        newUser.put("Nickname", this.newUser.nickName);

        db.collection("users").document(filePath)
                .set(newUser)
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
                            FirebaseUser user = mAuth.getCurrentUser();
                            userUID = mAuth.getUid();
                            updateDatabase(userUID);
                            Toast.makeText(getApplicationContext(),userUID,Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SignupActivity.this,Homepage.class);
                            startActivity(intent);
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

        }
    }

}
