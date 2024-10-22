package com.example.flowergrass.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.flowergrass.R;
import com.example.flowergrass.DataModel.Event;
import com.google.firebase.Timestamp;

import java.util.ArrayList;


public class ItemListAdapter extends ArrayAdapter<Event> {

    private static final String TAG = "EventListAdapter";

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
    }


    public ItemListAdapter(Context context, int resource, ArrayList<Event> objects) {
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
            holder.name = convertView.findViewById(R.id.EventName);
            holder.date = convertView.findViewById(R.id.EventDate);
            holder.details = convertView.findViewById(R.id.EventDetails);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(title);
        holder.date.setText(dateCreated.toDate().toString());
        holder.details.setText(content);


        return convertView;
    }



}
