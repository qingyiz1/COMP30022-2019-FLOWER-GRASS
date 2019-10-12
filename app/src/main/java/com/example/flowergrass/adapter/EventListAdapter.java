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


public class EventListAdapter extends ArrayAdapter<Event> {

    private static final String TAG = "EventListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView title;
        TextView dateCreated;
        TextView content;
    }


    public EventListAdapter(Context context, int resource, ArrayList<Event> objects) {
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
            holder.title = convertView.findViewById(R.id.EventName);
            holder.dateCreated = convertView.findViewById(R.id.EventDate);
            holder.content = convertView.findViewById(R.id.EventDetails);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(title);
        holder.dateCreated.setText(dateCreated.toDate().toString());
        holder.content.setText(content);


        return convertView;
    }



}
