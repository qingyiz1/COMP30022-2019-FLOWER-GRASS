package com.example.flowergrass.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flowergrass.Activity.ChatActivity;
import com.example.flowergrass.DataModel.ChatModel;
import com.example.flowergrass.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyHolder> {

    private static final int MSG_TYPE_LEFT = 0;
    private static final int MSG_TYPE_RIGHT = 1;
    Context context;
    public static List<ChatModel> chatList;
    String imageUrl;

    FirebaseUser fUser;

    public ChatAdapter(Context context, List<ChatModel> chatList, String imageUrl) {
        this.context = context;
        this.chatList = chatList;
        this.imageUrl = imageUrl;
        System.out.println("!!!: "+this.chatList.size());

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        //inflate layouts: row_chat_left.xml for receiver, row_Chat_right.xml for sender
        if (i==MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(context).inflate(R.layout.row_chat_right, viewGroup,false);
            return new MyHolder(view);
        }
        else {
            View view = LayoutInflater.from(context).inflate(R.layout.row_chat_left, viewGroup,false);
            return new MyHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        //get data
        String message = chatList.get(i).getMessage();
        String timeStamp = chatList.get(i).getTimestamp();

        //convert time stamp to dd/mm/yyyy hh:mm am/pm
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(timeStamp));
        String dateTime = DateFormat.format("dd/mm/yyyy hh:mm aa", cal).toString();

        //set data
        myHolder.messageTv.setText(message);

        myHolder.timeTv.setText(dateTime);
        try{
            Picasso.get().load(imageUrl).into(myHolder.profileIv);
        }
        catch (Exception e) {

        }

        //set seen/delivered status of message
        if (i==chatList.size()-1) {
            if (chatList.get(i).getIsSeen()) {
                myHolder.isSeenTv.setText("Seen");
            }
            else {
                myHolder.isSeenTv.setText("Delivered");
            }
        }
        else {
            myHolder.isSeenTv.setVisibility(View.GONE);

        }

    }

    @Override
    public int getItemCount() {
        System.out.println("******getItemCount = "+chatList.size());
        return ChatActivity.chatList.size();
    }

    @Override
    public int getItemViewType(int position) {
        //get currently signed in user
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        if (chatList.get(position).getSender().equals(fUser.getUid())) {
            return MSG_TYPE_RIGHT;
        }
        else {
            return MSG_TYPE_LEFT;
        }
    }

    //view holder class
    class MyHolder extends RecyclerView.ViewHolder {

        ///views
        ImageView profileIv;
        TextView messageTv, timeTv, isSeenTv;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            //init views
            profileIv = itemView.findViewById(R.id.profileIv);
            messageTv = itemView.findViewById(R.id.messageTv);
            timeTv = itemView.findViewById(R.id.timeTv);
            isSeenTv = itemView.findViewById(R.id.isSeenTv);


        }
    }
}
