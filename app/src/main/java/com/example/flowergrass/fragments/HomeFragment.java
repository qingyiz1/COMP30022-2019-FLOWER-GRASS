package com.example.flowergrass.fragments;


import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.flowergrass.Activity.Homepage;
import com.example.flowergrass.DataModel.UserModel;
import com.example.flowergrass.R;
import com.example.flowergrass.adapter.ClickableViewPager;
import com.example.flowergrass.adapter.EventListAdapter;
import com.example.flowergrass.adapter.ImageSlideAdapter;
import com.example.flowergrass.DataModel.Event;
import com.example.flowergrass.utils.CirclePageIndicator;
import com.example.flowergrass.utils.GlideApp;
import com.example.flowergrass.utils.PageIndicator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class HomeFragment extends Fragment {

    public static final String ARG_ITEM_ID = "home_fragment";

    private static final long ANIM_VIEWPAGER_DELAY = 10000;
    private static final long ANIM_VIEWPAGER_DELAY_USER_VIEW = 10000;
    private static final String TAG = "HOME_FRAGMENT";

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // UI References
    private ViewPager mViewPager;
    private ListView mEventListView;
    //TextView imgNameTxt;
    PageIndicator mIndicator;


    AlertDialog alertDialog;

    //Data - Temporary
    int[] products ={R.drawable.data1,R.drawable.data2,R.drawable.data3};
    ArrayList<Event> events = new ArrayList<>();
    Event newEvent;

    //Sliding Animation
    boolean stopSliding = false;
    private Runnable animateViewPager;
    private Handler handler;

    Homepage activity;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (Homepage)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg1, container, false);
        Init(view);
        //loadWithGlide(view,R.id.);
        return view;
    }


    //Initialise Image slider and EventListView
    private void Init(View view) {
        mViewPager = view.findViewById(R.id.view_pager);
        mViewPager.setAdapter(new ImageSlideAdapter(activity, products,HomeFragment.this));
        mEventListView = view.findViewById(R.id.EventListView);
        getData();
        mIndicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
        mIndicator.setOnPageChangeListener(new PageChangeListener());
        mIndicator.setViewPager(mViewPager);
    }

    public void runnable(final int size) {
        handler = new Handler();
        animateViewPager = new Runnable() {
            public void run() {
                if (!stopSliding) {
                    if (mViewPager.getCurrentItem() == size - 1) {
                        mViewPager.setCurrentItem(0);

                    } else {
                        mViewPager.setCurrentItem(
                                mViewPager.getCurrentItem() + 1, true);
                    }
                    handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
                }
            }
        };
    }


    @Override
    public void onResume() {



        //runnable(products.length);
        //Re-run callback
        //handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);

        super.onResume();
    }


    @Override
    public void onPause() {

        if (handler != null) {
            //Remove callback
            handler.removeCallbacks(animateViewPager);
        }
        super.onPause();
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



    private class PageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                if (products != null) {

                }
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
        }
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

    public void getData(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                db.collection("posts").whereEqualTo("category","Event")
                        .orderBy("dateCreated", Query.Direction.DESCENDING).limit(10)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value,
                                                @Nullable FirebaseFirestoreException e) {
                                if (e != null) {
                                    Log.w(TAG, "Listen failed.", e);
                                    return;
                                }
                                events.clear();

                                for (QueryDocumentSnapshot doc : value) {
                                    Timestamp date = (Timestamp)doc.getData().get("dateCreated");
                                    newEvent = new Event(doc.getString("authorUid"),doc.getString("author"),doc.getString("title"),doc.getString("hashTag"),date,doc.getString("content"));
                                    events.add(newEvent);
                                    mEventListView.setAdapter(new EventListAdapter(activity, R.layout.event_list_view, events));
                                }
                            }
                        });
            }
        });



    }

}