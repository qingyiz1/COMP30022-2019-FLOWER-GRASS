package com.example.flowergrass.fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.flowergrass.R;
import com.example.flowergrass.utils.CirclePageIndicator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ItemDetailFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    protected FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static final String TAG = "Item_detail_FRAGMENT";
    TextView mTitle,mDate,mAuthor,mDetails;
    String uid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg1, container, false);
        uid = mAuth.getUid();
        Init(view);
        //loadWithGlide(view,R.id.);
        return view;
    }

    //Initialise Image slider and EventListView
    private void Init(View view) {
        mTitle = view.findViewById(R.id.item_details_eventName);
        mAuthor = view.findViewById(R.id.item_details_eventCreator);
        mDate = view.findViewById(R.id.item_details_eventDate);
        mDetails = view.findViewById(R.id.item_details_eventDetails);
        getData();
    }

    public void getData(){
        DocumentReference docRef = db.collection("users").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Timestamp date = (Timestamp)document.getData().get("dateCreated");

                        mTitle.setText(document.getString("title"));
                        mAuthor.setText(document.getString("author"));
                        mDate.setText(date.toDate().toString());
                        mDetails.setText(document.getString("content"));
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }


}
