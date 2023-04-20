package com.sandhya.whatsapp.homefragement;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sandhya.whatsapp.R;
import com.sandhya.whatsapp.adapter.ChatsAdapter;
import com.sandhya.whatsapp.model.ChatsModel;

import java.util.ArrayList;
import java.util.Collections;

public class ChatsFragment extends Fragment {

    public ChatsFragment() {
        // Required empty public constructor
    }

    RecyclerView chat_recycler;
    ArrayList<ChatsModel> models = new ArrayList<>();
    ChatsAdapter adapter;
    @SuppressLint({"MissingInflatedId"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

//        initChatRecycler(view);
        loadData(view);
        return view;
    }

    private void loadData(View view) {
        chat_recycler = view.findViewById(R.id.chat_recycler);
        chat_recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseDatabase.getInstance().getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    models.clear();
                    for (DataSnapshot dataSnapshot :snapshot.getChildren()){
                        ChatsModel model = dataSnapshot.getValue(ChatsModel.class);
                        model.getUid(dataSnapshot.getKey());
                        models.add(model);

                    }

                    Collections.shuffle(models);
                    adapter = new ChatsAdapter(getContext(),models);
                    chat_recycler.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initChatRecycler(View view) {
        chat_recycler = view.findViewById(R.id.chat_recycler);
        chat_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        models.clear();
        models.add(new ChatsModel("Sandhya Gautam","asdfghdsdfghnf","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQJIMJbqadzkYB8l-EAAFAgmkreqDGQHZH5vg&usqp=CAU","12:12AM",null,null,null));
        models.add(new ChatsModel("Abhay Gautam","mjguytrhnbasdfghdsdfghnfsdfgfds","https://img.freepik.com/premium-vector/instagram-social-media-icon-gradient-social-media-logo_197792-4682.jpg?w=2000","12:12AM",null,null,null));
        models.add(new ChatsModel("Alok Rathod","asdfghdsdfghnzxxdfvsdff","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTJADpS9PVXw_acnFZUoZp95O1iTaFBfwxQuw&usqp=CAU","12:12AM",null,null,null));
        models.add(new ChatsModel("Shivam Jatav","sdfghgfdsdfghgfdfghgasdfghdsdfghnf","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTbpCnBLtdMbCARtkFy9Wb75KKl64w5P6dMOQ&usqp=CAU","12:12AM",null,null,null));
        models.add(new ChatsModel("Ankit Jatav","xcvbcfbdasdfghdsdfghnf","https://storage.googleapis.com/exceedlms-external-uploads-production/uploads/categories/pictures/676/original/unnamed.png","12:12AM",null,null,null));
        models.add(new ChatsModel("SR App Ltd.","utrewetyuyrasdfghdsdfghnf","https://play-lh.googleusercontent.com/6iyA2zVz5PyyMjK5SIxdUhrb7oh9cYVXJ93q6DZkmx07Er1o90PXYeo6mzL4VC2Gj9s","12:12AM",null,null,null));
        adapter = new ChatsAdapter(getContext(),models);
        chat_recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}