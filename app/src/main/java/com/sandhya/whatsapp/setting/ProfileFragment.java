package com.sandhya.whatsapp.setting;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sandhya.whatsapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    CircleImageView imgLogo,imgUpload;
    EditText edtName,edtAbout,edtMobile;
    TextView txtName,txtMobile;

    Uri imgUri;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        edtName = view.findViewById(R.id.edtName);
        edtAbout = view.findViewById(R.id.edtAbout);
        edtMobile = view.findViewById(R.id.edtMobile);
        imgLogo = view.findViewById(R.id.imgLogo);
        initData(edtAbout,edtMobile,edtName,imgLogo);
        initSelectImage(view);

        ImageView imgBack = view.findViewById(R.id.imgBack);
        imgBack.setOnClickListener(view1 -> startActivity(new Intent(getActivity(),SettingActivity.class)));





        return view;
    }

    private void initSelectImage(View view) {
        imgUpload= view.findViewById(R.id.imgUpload);
        imgUpload.setOnClickListener(view1 -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,100);
        });
    }

    private void initData(EditText edtAbout, EditText edtMobile, EditText edtName, CircleImageView imgLogo) {


        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String logo = snapshot.child("image").getValue().toString();
                    String categories = snapshot.child("categories").getValue().toString();
                    String mobile = snapshot.child("mobile").getValue().toString();
                    String name = snapshot.child("name").getValue().toString();

                    Glide.with(getContext()).load(logo).placeholder(R.drawable.ic_account).into(imgLogo);
                    edtMobile.setText(mobile);
                    edtAbout.setText(categories);
                    edtName.setText(name);
                }else {
//                    Glide.with(SettingActivity.this).load(logo).placeholder(R.drawable.ic_account).into(imgLogo);
//                    imgLogo.setImageResource(R.drawable.ic_account);
//                    edtAbout.setText("Abouts");
//                    edtName.setText("Username");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode==100){
                imgUri = data.getData();
                imgLogo.setImageURI(imgUri);
            }

        }
    }
}