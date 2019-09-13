package com.example.flowergrass.adapter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;

import com.example.flowergrass.FirstFragment;
import com.example.flowergrass.R;
import com.example.flowergrass.data.Product;
import com.example.flowergrass.fragments.HomeFragment;
import com.example.flowergrass.fragments.ProductDetailFragment;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
//import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
//import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class ImageSlideAdapter extends PagerAdapter {

    FragmentActivity activity;
    int[] images;
    HomeFragment homeFragment;

    public ImageSlideAdapter(FragmentActivity activity,int[] images,
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
    public View instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.vp_image, container, false);

        ImageView mImageView = view.findViewById(R.id.image_display);
        mImageView.setImageResource(images[position]);

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
