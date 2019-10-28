package com.example.flowergrass.adapter;

import android.app.Activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;

import com.example.flowergrass.Activity.Homepage;
import com.example.flowergrass.Activity.ItemDetailsActivity;
import com.example.flowergrass.R;
import com.example.flowergrass.fragments.HomeFragment;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
//import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
//import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class ImageSlideAdapter extends PagerAdapter {

    Homepage activity;
    int[] images;
    HomeFragment homeFragment;

    public ImageSlideAdapter(Homepage activity,int[] images,
                             HomeFragment homeFragment) {
        this.activity = activity;
        this.homeFragment = homeFragment;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public View instantiateItem(final ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.vp_image, container, false);

        ImageView mImageView = view.findViewById(R.id.image_display);
        mImageView.setImageResource(images[position]);
        TextView imgName = view.findViewById(R.id.img_name);
        imgName.setText("Items");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemId;
                if(position == 1){
                    itemId = "WT60EPoAYKb5tcffy67LlwS0nxH3Tue Oct 22 02:39:13 GMT+11:00 2019";
                }else if(position ==2){
                    itemId = "GJ9p9qDKnFW4sxTGbL6CTc51fVF2Tue Oct 29 02:25:57 GMT+11:00 2019";
                }else{
                    itemId = "WT60EPoAYKb5tcffy67LlwS0nxH3Tue Oct 22 02:32:17 GMT+11:00 2019";
                }

                activity.ShowItemDetail(itemId);
            }
        });


        container.addView(view);
        return view;
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}
