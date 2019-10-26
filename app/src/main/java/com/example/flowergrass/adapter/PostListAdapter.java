package com.example.flowergrass.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.flowergrass.R;
import com.example.flowergrass.DataModel.Event;
import com.example.flowergrass.DataModel.Post;

import com.example.flowergrass.DataModel.Item;
import com.google.firebase.Timestamp;

import java.util.ArrayList;


public class PostListAdapter extends ArrayAdapter<Post> {

    private static final String TAG = "PostListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView name;
        TextView date;
        TextView details;
        ImageView image;
    }


    public PostListAdapter(Context context, int resource, ArrayList<Post> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get event information
        String id = getItem(position).getId();
        String author = getItem(position).getAuthor();
        String title = getItem(position).title;

        Timestamp dateCreated = getItem(position).getDateCreated();


        String content = getItem(position).getContent();

        //ViewHolder object
        ViewHolder holder;

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.name = convertView.findViewById(R.id.PostName);
            holder.date = convertView.findViewById(R.id.PostDate);
            holder.details = convertView.findViewById(R.id.PostDetails);
            holder.image = convertView.findViewById(R.id.ItemImage);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        String[] dateCut = dateCreated.toDate().toString().split(" ");
        String dateFinalised = new String();
        for (int i = 0; i < dateCut.length; i++) {
            if (i == 4){}
            else {
                dateFinalised += dateCut[i];
                dateFinalised += ' ';
            }
        }

        holder.name.setText(title);
        holder.date.setText(dateFinalised);
        holder.details.setText(content);
        /*
        if(getItem(position) instanceof Item){

        }*/


        return convertView;
    }



}
