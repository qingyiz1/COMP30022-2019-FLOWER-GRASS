package com.example.flowergrass.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flowergrass.DataModel.ChatModel;
import com.example.flowergrass.R;
import com.example.flowergrass.adapter.ChatAdapter;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private static final String TAG = "ChatActivity";

    //views from xml
    Toolbar toolbar;
    RecyclerView recyclerView;
    ImageView profileIv;
    TextView nameTv, userStatusTv;
    EditText messageEt;
    ImageButton sendBtn;

    //firebase auth
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    //for checking if user has seen message or not
    ListenerRegistration seenListener;

    public static List<ChatModel> chatList = new ArrayList<>();
    ChatAdapter chatAdapter;


    String hisUid;
    String myUid;
    String hisImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //init views
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        recyclerView = findViewById(R.id.chat_recyclerView);
        profileIv = findViewById(R.id.profileIv);
        nameTv = findViewById(R.id.nameTv);
        userStatusTv = findViewById(R.id.userStatusTv);
        messageEt = findViewById(R.id.messageEt);
        sendBtn = findViewById(R.id.sendBtn);

        //Layout (LinearLayout) for RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        //recyclerview properties
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        getUserInfo();

    }

    @Override
    protected void onStart() {
        super.onStart();
        handleMessage();
    }

    private void getUserInfo() {
        Intent intent = getIntent();
        hisUid = intent.getStringExtra("hisUid");

        //firebase auth instance
        myUid = firebaseAuth.getUid();

        //search user to get that user's info
        Query userQuery = db.collection("users").whereEqualTo(FieldPath.documentId(), hisUid);
        //get user picture and name
        userQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                //check until required info is received
                for (QueryDocumentSnapshot doc: snapshots) {
                    String name =""+ doc.get("nickName");
                    hisImage =""+ doc.get("avatarID");

                    //set data
                    nameTv.setText(name);
                    try {
                        //image received, set it to imageview in toolbar
                        profileIv.setImageResource(Integer.parseInt(hisImage));
                    }
                    catch (Exception e2) {
                        //there is exception getting picture, set default picture
                        Picasso.get().load(R.drawable.ic_default).into(profileIv);
                    }
                }
            }
        });
    }

    private void handleMessage(){
        //click button to send message
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get text from edit text
                String message = messageEt.getText().toString();
                //check if text is empty or not
                if (TextUtils.isEmpty(message)){
                    //text empty
                    Toast.makeText(ChatActivity.this, "Cannot send empty messageg...", Toast.LENGTH_SHORT).show();
                }
                else{
                    //text not empty
                    sendMessage(message);
                }
            }
        });

        readMessages();
        //seenMessage();
    }

    private void seenMessage() {
        seenListener = db.collection("chats")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                        chatList.clear();
                        for (QueryDocumentSnapshot doc: snapshots) {
                            ChatModel chat = doc.toObject(ChatModel.class);
                            if (chat.getReceiver().equals(myUid) && chat.getSender().equals(hisUid)) {
                                HashMap<String, Object> hasSeenHashMap = new HashMap<>();
                                hasSeenHashMap.put("isSeen", true);
                                doc.getReference().update(hasSeenHashMap);
                            }
                        }
                    }
                });
    }

    private void readMessages() {
        db.collection("chats").orderBy("dateCreated", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                        chatList.clear();
                        for (QueryDocumentSnapshot doc: snapshots) {
                            Timestamp date = (Timestamp)doc.getData().get("dateCreated");

                            ChatModel chat = new ChatModel(doc.getString("message"),doc.getString("receiver"),doc.getString("sender"),
                                    date,doc.getBoolean("isSeen"));

                                Log.d(TAG,myUid);
                                Log.d(TAG,hisUid);
                            if ((chat.getReceiver().equals(myUid) && chat.getSender().equals(hisUid)) ||
                                    (chat.getReceiver().equals(hisUid) && chat.getSender().equals(myUid))) {
                                Log.d(TAG,chat.toString());
                                chatList.add(chat);
                            }
                            //adapter
                            chatAdapter = new ChatAdapter(ChatActivity.this, chatList, hisImage);
                            chatAdapter.notifyDataSetChanged();
                            //set adapter to recyclerview
                            recyclerView.setAdapter(chatAdapter);
                            System.out.println("******chatActivity = "+ ChatAdapter.chatList.size());

                        }
                    }
                });
    }

    private void sendMessage(String message) {

        Timestamp timestamp = Timestamp.now();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", myUid);
        hashMap.put("receiver", hisUid);
        hashMap.put("message", message);
        hashMap.put("dateCreated", timestamp);
        hashMap.put("isSeen", false);
        db.collection("chats").add(hashMap);

        //reset edit text after sending message
        messageEt.setText("");
    }



    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
