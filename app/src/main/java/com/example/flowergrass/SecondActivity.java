package com.example.flowergrass;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecondActivity extends AppCompatActivity {
    ListView listView;
    SimpleAdapter simpleAdapter;
    List<Map<String, Object>> list;
    int[] images = new int[]{R.drawable.data1,R.drawable.data2,R.drawable.data3,R.drawable.data4,R.drawable.data5};
    String[] name = new String[]{"pika","ball","fire","fighter","game"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        listView = findViewById(R.id.listView1);
        list = new ArrayList<>();
        for (int i=0;i<images.length;i++)
        {
            Map<String,Object>map = new HashMap<>();
            map.put("image",images[i]);
            map.put("name",name[i]);
            list.add(map);
        }
        simpleAdapter = new SimpleAdapter(SecondActivity.this,list,R.layout.horizontal_main_scroll,new String[]{"image","name"},new int[]{R.id.imageView1,R.id.textView1});
        listView.setAdapter(simpleAdapter);
    }
}
