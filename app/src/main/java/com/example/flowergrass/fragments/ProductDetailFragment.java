package com.example.flowergrass.fragments;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.flowergrass.R;
import com.example.flowergrass.data.Product;


public class ProductDetailFragment extends Fragment {

    TextView pdtIdTxt;
    TextView pdtNameTxt;
    ImageView pdtImg;
    Activity activity;



    Product product;

    public static final String ARG_ITEM_ID = "pdt_detail_fragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmet_pdt_detail, container,
                false);
        findViewById(view);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            product = bundle.getParcelable("singleProduct");
            setProductItem(product);
        }

        return view;
    }

    private void findViewById(View view) {

        pdtNameTxt = (TextView) view.findViewById(R.id.pdt_name);
        pdtIdTxt = (TextView) view.findViewById(R.id.product_id_text);

        pdtImg = (ImageView) view.findViewById(R.id.product_detail_img);
    }




    private void setProductItem(Product resultProduct) {
        pdtNameTxt.setText("" + resultProduct.getTitle());
        pdtIdTxt.setText("Product Id: " + resultProduct.getId());

    }
}