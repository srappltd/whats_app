package com.sandhya.whatsapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sandhya.whatsapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserViewActivity extends AppCompatActivity {

    TextView txtName;
    CircleImageView imgLogo;
    String name,des,logo;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);


        initIdFind();
        initBundle(imgLogo,txtName);
        initUserProfileView(imgLogo,txtName);
        initBack();
    }

    private void initIdFind() {
        imgLogo = findViewById(R.id.imgLogo);
        txtName = findViewById(R.id.txtName);
    }

    private void initBundle(CircleImageView imgLogo, TextView txtName) {
        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("name");
        des = bundle.getString("des");
        logo = bundle.getString("logo");
        txtName.setText(name);
        Glide.with(this).load(logo).placeholder(R.drawable.ic_account).into(imgLogo);
    }

    private void initUserProfileView(CircleImageView imgLogo, TextView txtName) {
        txtName.setOnClickListener(view -> {
            Intent intent = new Intent(UserViewActivity.this,UserProfileActivity.class);
            intent.putExtra("userName",name);
            intent.putExtra("userDes",des);
            intent.putExtra("userLogo",logo);
            startActivity(intent);
        });
        imgLogo.setOnClickListener(view -> {
            Intent intent = new Intent(UserViewActivity.this,UserProfileActivity.class);
            intent.putExtra("userName",name);
            intent.putExtra("userDes",des);
            intent.putExtra("userLogo",logo);
            startActivity(intent);
        });
    }

    private void initBack() {
        ImageView imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(view -> onBackPressed());


    }

}