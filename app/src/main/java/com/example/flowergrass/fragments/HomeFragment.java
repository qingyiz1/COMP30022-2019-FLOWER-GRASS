package com.example.flowergrass.fragments;



import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import org.json.JSONObject;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.flowergrass.Homepage;
import com.example.flowergrass.NewPostActivity;
import com.example.flowergrass.R;
import com.example.flowergrass.adapter.ImageSlideAdapter;
import com.example.flowergrass.data.Product;
import com.example.flowergrass.utils.CirclePageIndicator;
import com.example.flowergrass.utils.GlideApp;
import com.example.flowergrass.utils.PageIndicator;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class HomeFragment extends Fragment {

    public static final String ARG_ITEM_ID = "home_fragment";

    private static final long ANIM_VIEWPAGER_DELAY = 5000;
    private static final long ANIM_VIEWPAGER_DELAY_USER_VIEW = 10000;

    // UI References
    private ViewPager mViewPager;
    TextView imgNameTxt;
    PageIndicator mIndicator;


    AlertDialog alertDialog;

    //Data - Temporary
    int[] products ={R.drawable.data1,R.drawable.data2,R.drawable.data3};

    boolean stopSliding = false;

    //Sliding Animation
    private Runnable animateViewPager;
    private Handler handler;

    FragmentActivity activity;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg1, container, false);
        InitImgSlider(view);
        loadWithGlide(view);
        return view;
    }

    private void InitImgSlider(View view) {
        mViewPager = view.findViewById(R.id.view_pager);
        mIndicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
        imgNameTxt = view.findViewById(R.id.img_name);
        mIndicator.setOnPageChangeListener(new PageChangeListener());
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
            mViewPager.setAdapter(new ImageSlideAdapter(activity, products,HomeFragment.this));

            mIndicator.setViewPager(mViewPager);
            imgNameTxt.setText("TEST");
            runnable(products.length);
            //Re-run callback
            handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);

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
                    imgNameTxt.setText("TEST");
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

    public void loadWithGlide(View container) {
        // [START storage_load_with_glide]
        // Reference to an image file in Cloud Storage
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/test.jpg");

        // ImageView in your Activity
        ImageView imageView = container.findViewById(R.id.activity_image);

        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)
        GlideApp.with(this /* context */)
                .load(storageReference)
                .into(imageView);
        // [END storage_load_with_glide]
    }



}