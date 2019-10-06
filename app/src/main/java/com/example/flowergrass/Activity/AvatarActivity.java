package com.example.flowergrass.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.example.flowergrass.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AvatarActivity extends AppCompatActivity {
    GridView gridView;
    int[] avatars = new int[]{R.drawable.avatar_boy,R.drawable.avatar_girl,R.drawable.avatar_girl2,R.drawable.avatar_girl2,R.drawable.avatar_girl3,R.drawable.avatar_girl4,R.drawable.avatar_man};
    boolean isClicked = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);
        gridView = findViewById(R.id.avatar_grid);

        //load images for gridView
        List<Map<String,Object>> list = new ArrayList<>();
        for(int i =0;i<avatars.length;i++){
            Map<String,Object> map = new HashMap<>();
            map.put("avatar",avatars[i]);
            list.add(map);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(AvatarActivity.this,list,R.layout.avatar_item,new String[] {"avatar"},new int[]{R.id.avatar_item});
        gridView.setAdapter(simpleAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                isClicked = true;
                Intent intent = new Intent();
                intent.putExtra("avatar",avatars[i]);
                setResult(0x02,intent);
                finish();
            }
        });

        if(!isClicked){
            setResult(0x02,getIntent());
        }

    }
}
