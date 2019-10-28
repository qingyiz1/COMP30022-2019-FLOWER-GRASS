package com.example.flowergrass.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import com.example.flowergrass.DataModel.Item;
import com.example.flowergrass.DataModel.Post;
import com.example.flowergrass.R;
import com.example.flowergrass.DataModel.UserModel;
import com.example.flowergrass.utils.BaseActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class NewItemActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "NewItemActivity";
    private static final String REQUIRED = "Required";
    private static final int PICK_IMAGE_REQUEST = 234;

    private EditText mTitleField,mBodyField,mHashTag;
    private Item item;
    private UserModel currentUser;
    private Button chooseBtn,uploadBtn,submitBtn;
    private ImageView uploadImg;
    UploadTask uploadTask;
    Uri filePath;
    String nickName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);


        mTitleField = findViewById(R.id.fieldTitle);
        mBodyField = findViewById(R.id.fieldBody);
        mHashTag = findViewById(R.id.fieldHashTag);
        uploadImg = findViewById(R.id.uploadImg);

        chooseBtn = findViewById(R.id.chooseBtn);
        uploadBtn = findViewById(R.id.uploadBtn);
        submitBtn = findViewById(R.id.submitBtn);

        chooseBtn.setOnClickListener(this);
        uploadBtn.setOnClickListener(this);
        submitBtn.setOnClickListener(this);


        currentUser = new UserModel();
        currentUser.getNickname();

    }

    private void submitPost(){

        this.item = new Item(mAuth.getUid(),currentUser.nickName,mTitleField.getText().toString(),mHashTag.getText().toString(),Timestamp.now(),
                mBodyField.getText().toString());
        String imageUrl = item.getImageUrl();
        db.collection("posts").document(item.getId())
                .set(item.toMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        uploadImg();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    private void uploadImg(){
        if(filePath!=null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference uploadRef = mStorageImagesRef.child(item.getId());
            uploadTask = uploadRef.putFile(filePath);

            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),exception.getMessage(),Toast.LENGTH_LONG).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    progressDialog.dismiss();

                    new AlertDialog.Builder(NewItemActivity.this)
                            .setTitle("Success!")
                            .setMessage("Successfully posted new item!")
                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Direct to Homepage
                                    finish();
                                }
                            })
                            // A null listener allows the button to dismiss the dialog and take no further action.
                            //.setNegativeButton(android.R.string.no, null)
                            .setIcon(R.drawable.ic_create_success)
                            .show();


                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100* taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                    progressDialog.setMessage((int) progress+"% finished");
                }
            });
        }


    }

    private void chooseFile(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select an Image"),PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && data.getData() != null){
            filePath = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                uploadImg.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NewItemActivity.this,Homepage.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }


    @Override
    public void onClick(View view){
        if(view == chooseBtn){
            // open file chooser
            chooseFile();
        }else if(view == submitBtn){
            submitPost();
        }
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

}