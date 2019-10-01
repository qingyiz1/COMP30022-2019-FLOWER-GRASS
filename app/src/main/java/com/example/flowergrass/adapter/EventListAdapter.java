package com.example.flowergrass.adapter;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.flowergrass.R;
import com.example.flowergrass.data.Event;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EventListAdapter extends ArrayAdapter<Event> {

    private static final String TAG = "EventListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView name;
        TextView date;
        TextView details;
    }


    public EventListAdapter(Context context, int resource, ArrayList<Event> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String title = getItem(position).getTitle();
        String date = getItem(position).getDate();
        String details = getItem(position).getDetails();

        //Create the Event object with the information
        Event event = new Event(title,date,details);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.name = convertView.findViewById(R.id.EventName);
            holder.date = convertView.findViewById(R.id.EventDate);
            holder.details = convertView.findViewById(R.id.EventDetails);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.name.setText(event.getTitle());
        holder.date.setText(event.getDate());
        holder.details.setText(event.getDetails());


        return convertView;
    }


    public void getData(){
        db.collection("events")
                .orderBy("dateCreated").limit(5)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        Map<String,Object> events = new HashMap<>();
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.get("name") != null) {
                                events = doc.getData();
                            }
                        }
                        Log.d(TAG, "Current events in : " + events.toString());
                    }
                });
    }
}
