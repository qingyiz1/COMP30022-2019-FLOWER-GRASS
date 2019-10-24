package com.example.flowergrass.fragments;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.flowergrass.Activity.MainActivity;
import com.example.flowergrass.DataModel.Event;
import com.example.flowergrass.R;
import com.example.flowergrass.adapter.EventListAdapter;
import com.example.flowergrass.utils.CirclePageIndicator;
import com.example.flowergrass.utils.PageIndicator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FourthFragment extends Fragment implements View.OnClickListener {
    public static final String ARG_ITEM_ID = "fourth_fragment";

    private static final long ANIM_VIEWPAGER_DELAY = 10000;
    private static final long ANIM_VIEWPAGER_DELAY_USER_VIEW = 10000;
    private static final String TAG = "FOURTH_FRAGMENT";

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    TextView mEmail,mNickname,mBirthday;
    ImageView mAvatar;

    Button editBtn,logoutBtn;
    FragmentActivity activity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg4, container, false);
        Init(view);
        //loadWithGlide(view,R.id.);
        return view;
    }

    //Initialise Image slider and EventListView
    private void Init(View view) {
        mEmail = view.findViewById(R.id.me_email);
        mNickname = view.findViewById(R.id.me_nickname);
        mBirthday = view.findViewById(R.id.me_birthday);
        mAvatar = view.findViewById(R.id.me_avatar);
        //editBtn = view.findViewById(R.id.me_edit_btn);
        logoutBtn = view.findViewById(R.id.me_logout_btn);
        activity = getActivity();
        //editBtn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);
        getData();
    }



    public void getData(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot doc = task.getResult();
                                    mEmail.setText(doc.getString("email"));
                                    mNickname.setText(doc.getString("nickName"));
                                    mBirthday.setText(doc.getString("birthday"));
                                    mAvatar.setImageResource(Integer.parseInt(doc.get("avatarID").toString()));
                                }
                            }
                        });
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view == editBtn ){

        }else if(view == logoutBtn){
            mAuth.signOut();
            Intent intent = new Intent(activity, MainActivity.class);
            startActivity(intent);
            activity.finish();
        }
    }
}
