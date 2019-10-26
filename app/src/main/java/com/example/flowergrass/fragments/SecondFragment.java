package com.example.flowergrass.fragments;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.flowergrass.DataModel.Item;
import com.example.flowergrass.R;
import com.example.flowergrass.adapter.PostListAdapter;
import com.example.flowergrass.DataModel.Event;
import com.example.flowergrass.DataModel.Post;
import com.example.flowergrass.DataModel.UserModel;
import com.example.flowergrass.utils.PageIndicator;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class SecondFragment extends Fragment {
    //ImageView demoImage;
    int imagesToShow[] = { R.drawable.avatar_girl, R.drawable.avatar_girl3,R.drawable.avatar_girl2 };
    Handler handler;
    private Runnable animator;
    private static final long ANIM_DELAY = 10000;

    UserModel currentUser;
    public static final String ARG_ITEM_ID = "Second_fragment";

    private static final long ANIM_VIEWPAGER_DELAY = 10000;
    private static final long ANIM_VIEWPAGER_DELAY_USER_VIEW = 10000;
    private static final String TAG = "HOME_FRAGMENT";

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // UI References
    private ListView TimelineRight;
    private ListView TimelineLeft;


    //Data - Temporary
    ArrayList<Post> posts = new ArrayList<>();


    FragmentActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg2, container, false);
        Init(view);
        //loadWithGlide(view,R.id.);
        return view;
    }

    //Initialise Image slider and EventListView
    private void Init(View view) {
        //mViewPager = view.findViewById(R.id.view_pager);
        TimelineRight = view.findViewById(R.id.TIMELINERIGHT);
        TimelineLeft = view.findViewById((R.id.TIMELINELEFT));
        getEventsData();
        currentUser = new UserModel();
        currentUser.getNickname();
        //mIndicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
        //mIndicator.setOnPageChangeListener(new HomeFragment.PageChangeListener());
    }



    public void getEventsData(){
        db.collection("posts")
                .orderBy("dateCreated", Query.Direction.DESCENDING).limit(10)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        for (QueryDocumentSnapshot doc : value) {
                            Log.d(TAG,"Problem:"+doc.getData().get("dateCreated"));
                            Timestamp date = (Timestamp) doc.getData().get("dateCreated");

                            if(doc.getString("imageUrl") != null){
                                posts.add(new Item(doc.getId(),currentUser.nickName,doc.getString("title"),doc.getString("hashTag"),date,doc.getString("content")));
                            }else{
                                posts.add(new Event(doc.getId(),currentUser.nickName,doc.getString("title"),doc.getString("hashTag"),date,doc.getString("content")));

                            }

                        }

                        ArrayList<Post> right_items = new ArrayList<>();
                        ArrayList<Post> left_events = new ArrayList<>();

                        for (int i = 0; i < posts.size(); i++) {
                            if ((posts.get(i)) instanceof Item){
                                right_items.add(posts.get(i));
                            }
                            else {
                                left_events.add(posts.get(i));
                            }
                        }

                        TimelineRight.setAdapter(new PostListAdapter(getContext(),R.layout.timeline_right,right_items));
                        TimelineLeft.setAdapter(new PostListAdapter(getContext(),R.layout.timeline_left,left_events));
                        Log.d(TAG, "Current posts in : " + posts.toString());
                    }

                });
    }

}
