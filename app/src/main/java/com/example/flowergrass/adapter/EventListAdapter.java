package com.example.flowergrass.adapter;

import android.content.Context;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.flowergrass.DataModel.UserModel;
import com.example.flowergrass.R;
import com.example.flowergrass.DataModel.Event;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class EventListAdapter extends ArrayAdapter<Event> {

    private static final String TAG = "EventListAdapter";
    private Context mContext;
    private int mResource;
    private Handler handler;
    private int lastPosition = -1;
    protected FirebaseFirestore db = FirebaseFirestore.getInstance();
    //ViewHolder object
    ViewHolder holder;
    String authorUid, author,title,content;
    Timestamp dateCreated;
    int avatarId;



    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView title;
        TextView author;
        TextView dateCreated;
        TextView content;
        ImageView avatar;
    }


    public EventListAdapter(Context context, int resource, ArrayList<Event> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //get event information
        authorUid = getItem(position).getAuthorUid();
         author = getItem(position).getAuthor();
         title = getItem(position).title;
         dateCreated = getItem(position).getDateCreated();
         content = getItem(position).getContent();
        //int avatarId = getItem(position).getAuthorAvatarId();
        //Log.d(TAG,""+avatarId);
        //int avatarId = 2131230816;




        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.title = convertView.findViewById(R.id.EventName);
            holder.author = convertView.findViewById(R.id.EventCreator);
            holder.dateCreated = convertView.findViewById(R.id.EventDate);
            holder.content = convertView.findViewById(R.id.EventDetails);
            holder.avatar = convertView.findViewById(R.id.EventAvatar);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.title.setText(title);
        holder.author.setText(author);
        holder.dateCreated.setText(dateCreated.toDate().toString());
        holder.content.setText(content);
        if(getItem(position).getAuthorAvatarId() ==0){
            holder.avatar.setImageResource(R.drawable.avatar_default);
        }else{
            holder.avatar.setImageResource(getItem(position).getAuthorAvatarId());
        }

        return convertView;
    }




}
