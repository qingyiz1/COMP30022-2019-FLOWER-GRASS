package com.example.flowergrass.fragments;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.flowergrass.R;
import com.example.flowergrass.adapter.EventListAdapter;
import com.example.flowergrass.adapter.PostListAdapter;
import com.example.flowergrass.data.Event;
import com.example.flowergrass.data.Post;
import com.example.flowergrass.data.Product;
import com.example.flowergrass.utils.GlideApp;
import com.example.flowergrass.utils.PageIndicator;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class SecondFragment extends Fragment {
    //ImageView demoImage;
    int imagesToShow[] = { R.drawable.avatar_girl, R.drawable.avatar_girl3,R.drawable.avatar_girl2 };
    Handler handler;
    private Runnable animator;
    private static final long ANIM_DELAY = 10000;


    public static final String ARG_ITEM_ID = "home_fragment";

    private static final long ANIM_VIEWPAGER_DELAY = 10000;
    private static final long ANIM_VIEWPAGER_DELAY_USER_VIEW = 10000;
    private static final String TAG = "HOME_FRAGMENT";

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // UI References
    private ListView mItemListView;
    //TextView imgNameTxt;
    PageIndicator mIndicator;

    AlertDialog alertDialog;

    //Data - Temporary
    int[] products ={R.drawable.data1,R.drawable.data2,R.drawable.data3};
    ArrayList<Post> posts = new ArrayList<>();


    FragmentActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg2, container, false);
        Init(view);
        //loadWithGlide(view,R.id.);
        return view;
    }

    //Initialise Image slider and EventListView
    private void Init(View view) {
        //mViewPager = view.findViewById(R.id.view_pager);
        mItemListView = view.findViewById(R.id.ItemListView);
        getEventsData();
        //mIndicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
        //mIndicator.setOnPageChangeListener(new HomeFragment.PageChangeListener());
    }

    public void runnable() {
        handler = new Handler();
        animator = new Runnable() {
            public void run() {
                //animate(demoImage, imagesToShow, 0, false);
                handler.postDelayed(animator, ANIM_DELAY);
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        runnable();
        handler.postDelayed(animator, ANIM_DELAY);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (handler != null) {
            //Remove callback
            handler.removeCallbacks(animator);
        }
    }


    public void showAlertDialog(String message, final boolean finish) {
        alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (finish)
                            activity.finish();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void loadWithGlide(View container, int ID) {
        // [START storage_load_with_glide]
        // Reference to an image file in Cloud Storage
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/test.jpg");

        // ImageView in your Activity
        ImageView imageView = container.findViewById(ID);

        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)
        GlideApp.with(this /* context */)
                .load(storageReference)
                .into(imageView);
        // [END storage_load_with_glide]
    }


    public void getEventsData(){
        db.collection("events")
                .orderBy("dateCreated", Query.Direction.DESCENDING).limit(10)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        for (QueryDocumentSnapshot doc : value) {
                            Log.d(TAG,"Problem:"+doc.getData().get("dateCreated"));
                            Timestamp date = (Timestamp) doc.getData().get("dateCreated");

                            if(doc.getString("imageUrl") != null){
                                //posts.add(new Product(doc.getId(),doc.getString("title"),date.toDate().toString(),doc.getString("content"),doc.getString("imageUrl")));
                            }else{
                                posts.add(new Event(doc.getId(),doc.getString("title"),date.toDate().toString(),doc.getString("content")));

                            }

                        }
                        mItemListView.setAdapter(new PostListAdapter(getActivity().getApplicationContext(),R.layout.adapter_view_layout,posts));
                        Log.d(TAG, "Current posts in : " + posts.toString());
                    }

                });
    }




}
