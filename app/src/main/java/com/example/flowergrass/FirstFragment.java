package com.example.flowergrass;



import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class FirstFragment extends Fragment implements View.OnClickListener {
    ViewPager viewPager;
    SwipeAdapter swipeAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg1, container, false);

        return view;


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case 1:
                Log.d("Test", "onClickListener ist gestarted");
                Toast.makeText(getContext(),"you Clicked MP",Toast.LENGTH_LONG).show();

                break;
            default:
                break;
        }
    }
}


