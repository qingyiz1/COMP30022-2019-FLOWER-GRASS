package com.example.flowergrass.Activity;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.flowergrass.DataModel.Item;
import com.example.flowergrass.R;
import com.example.flowergrass.adapter.SpinnerAdapter;
import com.example.flowergrass.fragments.FourthFragment;
import com.example.flowergrass.fragments.HomeFragment;
import com.example.flowergrass.fragments.SecondFragment;
import com.example.flowergrass.fragments.ThirdFragment;



public class Homepage extends FragmentActivity implements View.OnClickListener {


    // Initialise Top Bar
    private Spinner titleSpinner;
    private TextView titleTv;

    // Dine four Fragment objects
    private HomeFragment fg1;
    private SecondFragment fg2;
    private ThirdFragment fg3;
    private FourthFragment fg4;

    // Store Fragment objects
    private FrameLayout frameLayout;

    // Define Fragment widgets
    private RelativeLayout firstLayout;
    private RelativeLayout secondLayout;
    private RelativeLayout thirdLayout;
    private RelativeLayout fourthLayout;
    private ImageView firstImage;
    private ImageView secondImage;
    private ImageView thirdImage;
    private ImageView fourthImage;
    private TextView firstText;
    private TextView secondText;
    private TextView thirdText;
    private TextView fourthText;

    // Define colors
    private int white = 0xFFFFFFFF;
    private int gray = 0xFF7597B3;
    private int dark = 0xff000000;

    // Define FragmentManager
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_main_lay);
        fragmentManager = getSupportFragmentManager();
        initView();
        setChioceItem(0); // Set default Fragment

    }
    /**
     * Initialisation
     */
    private void initView() {
        // Title bar
        titleSpinner = findViewById(R.id.title_spinner);
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(Homepage.this,R.layout.simple_spinner_item,getResources().getStringArray(R.array.title_spinner),0);
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        titleSpinner.setAdapter(spinnerAdapter);

        titleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (adapterView.getItemAtPosition(position).equals("Create New Item")) {
                    Intent intent = new Intent(Homepage.this,NewItemActivity.class);
                    startActivity(intent);
                    titleSpinner.setSelection(0,false);
                }else if (adapterView.getItemAtPosition(position).equals("Create New Event")) {
                    Intent intent = new Intent(Homepage.this,NewEventActivity.class);
                    startActivity(intent);
                    titleSpinner.setSelection(0,false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        titleTv = findViewById(R.id.title_text_tv);
        titleTv.setText("Activity");

        // Bottom Nav Bar
        firstImage = findViewById(R.id.first_image);
        secondImage = findViewById(R.id.second_image);
        thirdImage = findViewById(R.id.third_image);
        fourthImage = findViewById(R.id.fourth_image);
        firstText = findViewById(R.id.first_text);
        secondText = findViewById(R.id.second_text);
        thirdText = findViewById(R.id.third_text);
        fourthText = findViewById(R.id.fourth_text);
        firstLayout = findViewById(R.id.first_layout);
        secondLayout = findViewById(R.id.second_layout);
        thirdLayout = findViewById(R.id.third_layout);
        fourthLayout = findViewById(R.id.fourth_layout);
        firstLayout.setOnClickListener(Homepage.this);
        secondLayout.setOnClickListener(Homepage.this);
        thirdLayout.setOnClickListener(Homepage.this);
        fourthLayout.setOnClickListener(Homepage.this);
    }


    /**
     * Bottom tab selection
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.first_layout:
                setChioceItem(0);
                break;
            case R.id.second_layout:
                setChioceItem(1);
                break;
            case R.id.third_layout:
                setChioceItem(2);
                break;
            case R.id.fourth_layout:
                setChioceItem(3);
                break;
            default:
                setChioceItem(4);
                break;
        }
    }

    /**
     * set fragment view based on index
     *
     * @param index Fragment：0, 1, 2, 3
     */
    public void setChioceItem(int index) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        clearChioce(); // Hide all Fragments
        hideFragments(fragmentTransaction);

        switch (index) {
            case 0:

                // firstImage.setImageResource(R.drawable.XXXX);
                firstText.setTextColor(dark);
                firstLayout.setBackgroundColor(gray);
                titleTv.setText("Activity");
                // Create fragment and add to view if null
                if (fg1 == null) {
                    fg1 = new HomeFragment();
                    fragmentTransaction.add(R.id.content, fg1);
                } else {
                    // Show fragment if exists
                    fragmentTransaction.show(fg1);
                }
                break;
            case 1:

                // secondImage.setImageResource(R.drawable.XXXX);
                secondText.setTextColor(dark);
                secondLayout.setBackgroundColor(gray);
                titleTv.setText("Timeline");

                if (fg2 == null) {
                    fg2 = new SecondFragment();
                    fragmentTransaction.add(R.id.content, fg2);
                } else {
                    fragmentTransaction.show(fg2);
                }
                break;
            case 2:

                // thirdImage.setImageResource(R.drawable.XXXX);
                thirdText.setTextColor(dark);
                thirdLayout.setBackgroundColor(gray);
                titleTv.setText("Message");
                if (fg3 == null) {
                    fg3 = new ThirdFragment();
                    fragmentTransaction.add(R.id.content, fg3);
                } else {
                    fragmentTransaction.show(fg3);
                }
                break;
            case 3:

                // fourthImage.setImageResource(R.drawable.XXXX);
                fourthText.setTextColor(dark);
                fourthLayout.setBackgroundColor(gray);
                titleTv.setText("Me");
                if (fg4 == null) {
                    fg4 = new FourthFragment();
                    fragmentTransaction.add(R.id.content, fg4);
                } else {
                    fragmentTransaction.show(fg4);
                }
                break;

        }
        fragmentTransaction.commit();
    }

    /**
     * Setting all display to default
     */
    private void clearChioce() {
        // firstImage.setImageResource(R.drawable.XXX);
        firstText.setTextColor(gray);
        firstLayout.setBackgroundColor(white);

        // secondImage.setImageResource(R.drawable.XXX);
        secondText.setTextColor(gray);
        secondLayout.setBackgroundColor(white);

        // thirdImage.setImageResource(R.drawable.XXX);
        thirdText.setTextColor(gray);
        thirdLayout.setBackgroundColor(white);

        // fourthImage.setImageResource(R.drawable.XXX);
        fourthText.setTextColor(gray);
        fourthLayout.setBackgroundColor(white);
    }

    /**
     * Hide Fragment
     *
     * @param fragmentTransaction
     */
    private void hideFragments(FragmentTransaction fragmentTransaction) {
        if (fg1 != null) {
            fragmentTransaction.hide(fg1);
        }
        if (fg2 != null) {
            fragmentTransaction.hide(fg2);
        }
        if (fg3 != null) {
            fragmentTransaction.hide(fg3);
        }
        if (fg4 != null) {
            fragmentTransaction.hide(fg4);
        }
    }

    public void ShowItemDetail(String itemId) {
        Intent intent = new Intent(Homepage.this,ItemDetailsActivity.class);
        intent.putExtra("filePath",itemId);
        startActivity(intent);
    }
}

