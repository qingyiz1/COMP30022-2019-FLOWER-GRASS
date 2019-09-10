package com.example.flowergrass;



import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class FirstFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg1, container, false);

        Button btn1 = view.findViewById(R.id.MP_button);
        btn1.setOnClickListener(this);

        return view;


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.MP_button:
                Log.d("Test", "onClickListener ist gestarted");
                Toast.makeText(getContext(),"you Clicked MP",Toast.LENGTH_LONG).show();

                break;
            default:
                break;
        }
    }
}


