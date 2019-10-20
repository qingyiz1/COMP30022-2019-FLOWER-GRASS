package com.example.flowergrass.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flowergrass.Activity.MainActivity;
import com.example.flowergrass.DataModel.UserModel;
import com.example.flowergrass.R;
import com.example.flowergrass.adapter.UserAdapter;
import com.firebase.ui.auth.data.model.User;
import com.google.api.Distribution;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ThirdFragment extends Fragment {

    RecyclerView recyclerView;
    UserAdapter userAdapter;
    List<UserModel> userList;
    String TAG = "userList refreshing";

    //firebase auth
    FirebaseAuth firebaseAuth;

    public ThirdFragment() {
        //empty constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fg3, container, false);
        //init
        firebaseAuth = FirebaseAuth.getInstance();
        //init recyclerview
        recyclerView = view.findViewById(R.id.users_recyclerView);
        //set its properties
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //init user list
        userList = new ArrayList<>();

        //getAll users
        getAllUsers();

        return view;
    }

    private void getAllUsers() {
        //get current user
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //get path of database named "Users" containing users info
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        userList.clear();
                        for (QueryDocumentSnapshot doc : snapshots) {
                            UserModel userModel = doc.toObject(UserModel.class);
                            userModel.setUid(doc.getId());
                            if (!userModel.getUid().equals(firebaseUser.getUid())) {
                                userList.add(userModel);
                            }
                            //adapter
                            userAdapter = new UserAdapter(getActivity(), userList);
                            //set adapter to recycler view
                            recyclerView.setAdapter(userAdapter);
                        }
                        Log.d(TAG, "current users: " + userList);
                    }
                });
    }

    private void searchUsers(final String query) {
        //get current user
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //get path of database named "Users" containing users info
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        userList.clear();
                        for (QueryDocumentSnapshot doc : snapshots) {
                            UserModel userModel = doc.toObject(UserModel.class);
                            userModel.setUid(doc.getId());

                            //get all searched users except currently signed in user
                            if (!userModel.getUid().equals(firebaseUser.getUid())) {

                                if (userModel.nickName.toLowerCase().contains(query.toLowerCase()) ||
                                        userModel.email.toLowerCase().contains(query.toLowerCase())){
                                    userList.add(userModel);

                                }
                            }
                            //adapter
                            userAdapter = new UserAdapter(getActivity(), userList);
                            //refresh
                            userAdapter.notifyDataSetChanged();
                            //set adapter to recycler view
                            recyclerView.setAdapter(userAdapter);
                        }
                        Log.d(TAG, "current users: " + userList);
                    }
                });
    }

    private void checkUserStatus() {
        //get current user
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            //user is singed in stay here
            //set email of logged in user
            //mProfileTv.setText(user.setEmail());
        }
        else {
            //user not signed in, go to main activity
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //System.out.println("***BEEN HERE***");
        setHasOptionsMenu(true);//to show menu option in fragment
        super.onCreate(savedInstanceState);
    }

    /*inflate options menu*/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //inflating menu
        inflater.inflate(R.menu.menu_main, menu);

        //SearchView
        MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        //search listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //called when user press search button from keyboard
                //if search query is not empty then search
                if (!TextUtils.isEmpty(s.trim())) {
                    //search text contains text, search it
                    searchUsers(s);

                }
                else {
                    //search text empty, get all users
                    getAllUsers();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //called whenever user pass any single letter
                return false;
            }
        });

    }

    /*handle menu item clicks*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //get item id
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            firebaseAuth.signOut();
            checkUserStatus();
        }

        return super.onOptionsItemSelected(item);
    }
}
