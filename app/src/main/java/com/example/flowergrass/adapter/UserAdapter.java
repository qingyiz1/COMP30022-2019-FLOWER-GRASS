package com.example.flowergrass.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flowergrass.Activity.ChatActivity;
import com.example.flowergrass.DataModel.UserModel;
import com.example.flowergrass.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyHolder>{

    Context context;
    List<UserModel> userList;

    //constructor


    public UserAdapter(Context context, List<UserModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //inflate layout(row_user.xml)
        View view = LayoutInflater.from(context).inflate(R.layout.row_users, viewGroup, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        //get data
        final String hisUID = userList.get(i).getUid();
        int userImage = userList.get(i).avatarID;
        String userName = userList.get(i).nickName;
        final String userEmail = userList.get(i).email;

        //set data
        myHolder.mNameTv.setText(userName);
        myHolder.mEmailTv.setText(userEmail);
        myHolder.mAvatarIv.setImageResource(userImage);
        try {
            Picasso.get().load(userImage)
                    .placeholder(R.drawable.avatar_boy)
                    .into(myHolder.mAvatarIv);
        }
        catch (Exception e) {

        }

        //handle item click
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Click user from user list to start chatting
                 * Start activity by putting UID of receiver
                 * we will use that UID to identify the user we are gona chat*/
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("hisUid", hisUID);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    //view holder class
    class MyHolder extends RecyclerView.ViewHolder{


        ImageView mAvatarIv;
        TextView mNameTv, mEmailTv;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            //init views

            mAvatarIv =itemView.findViewById(R.id.avatarIv);
            mNameTv =itemView.findViewById(R.id.nameTv);
            mEmailTv =itemView.findViewById(R.id.emailTv);

        }
    }
}
