package com.example.flowergrass.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.flowergrass.R;
import com.example.flowergrass.utils.GlideApp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ItemDetailsActivity extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView mTitle,mAuthor,mDate, mContent;
    ImageView mImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details);

        mTitle = findViewById(R.id.item_details_title);
        mAuthor = findViewById(R.id.item_details_author);
        mDate = findViewById(R.id.item_details_date);
        mContent = findViewById(R.id.item_details_content);
        mImage = findViewById(R.id.item_details_image);
        getData();

    }



    public void getData(){
        Intent intent = getIntent();
        String filePath = intent.getStringExtra("filePath");

        db.collection("posts").document(filePath).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot doc = task.getResult();
                            Timestamp date = (Timestamp)doc.getData().get("dateCreated");

                            mTitle.setText(doc.getString("title"));
                            mAuthor.setText(doc.getString("author"));
                            mDate.setText(date.toDate().toString());
                            mContent.setText(doc.getString("content"));

                            String imageUrl = doc.getString("imageUrl");

                            GlideApp.with(ItemDetailsActivity.this /* context */)
                                    .load(FirebaseStorage.getInstance().getReference().child("images/"+imageUrl))
                                    .into(mImage);

                        }
                    }
                });
    }



}
