package com.example.flowergrass.Activity;

import androidx.annotation.NonNull;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flowergrass.R;
import com.example.flowergrass.DataModel.UserModel;
import com.example.flowergrass.utils.BaseActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    protected static final String TAG = "Login System";
    private EditText mEmailField, mPasswordField;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();
        checkUserState();
    }

    /**
     * Initialise view and buttons
     */
    private void Init(){
        // Views
        mEmailField = findViewById(R.id.fieldEmail);
        mPasswordField = findViewById(R.id.fieldPassword);

        // Buttons
        findViewById(R.id.LogInBtn).setOnClickListener(this);
        findViewById(R.id.SignUpBtn).setOnClickListener(this);
    }

    /**
     * move to homepage if user already logged in
     */
    private void checkUserState(){
        if(mAuth.getCurrentUser() != null){
            Intent intent= new Intent(getApplicationContext(),Homepage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    /**
     * User sign in
     * @param email
     * @param password
     */
    private void signIn(String email, String password) {
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("Success!")
                                    .setMessage("Successfully logged in")
                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Direct to Homepage if log in successfully
                                            Intent intent= new Intent(MainActivity.this,Homepage.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    })
                                    // A null listener allows the button to dismiss the dialog and take no further action.
                                    //.setNegativeButton(android.R.string.no, null)
                                    .setIcon(R.drawable.ic_create_success)
                                    .show();
                        } else {
                            // If sign in fails, display a message to the user.
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("Error!")
                                    .setMessage("Authentication failed.")
                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    .setPositiveButton(android.R.string.yes, null)
                                    // A null listener allows the button to dismiss the dialog and take no further action.
                                    //.setNegativeButton(android.R.string.no, null)
                                    .setIcon(R.drawable.ic_error)
                                    .show();
                        }

                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
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
        if (i == R.id.SignUpBtn) {
            // start SignUp activity
            Intent intent= new Intent(getApplicationContext(),SignupActivity.class);
            startActivity(intent);
        } else if (i == R.id.LogInBtn) {
            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
        }
    }



}
