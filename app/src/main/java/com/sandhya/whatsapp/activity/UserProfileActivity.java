package com.sandhya.whatsapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sandhya.whatsapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {

    CircleImageView imgLogo;
    TextView txtName,txtDisName,txtDisName2,txtNumber;
    LinearLayout llAudio,llVideo,llPay,llSearch;
    String logo,des,name;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        initIdFind();
        initBack();
        initBundle(imgLogo,txtNumber,txtName,txtDisName,txtDisName2);
    }

    private void initIdFind() {
        imgLogo = findViewById(R.id.imgLogo);
        txtName = findViewById(R.id.txtName);
        txtDisName = findViewById(R.id.txtDisName);
        txtDisName2 = findViewById(R.id.txtDisName2);
        txtNumber = findViewById(R.id.txtNumber);
    }

    private void initBundle(CircleImageView imgLogo, TextView txtNumber, TextView txtName, TextView txtDisName, TextView txtDisName2) {
        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("userName");
        des = bundle.getString("userDes");
        logo = bundle.getString("userLogo");
        txtName.setText(name);
        txtDisName.setText(name);
        txtDisName2.setText(name);
        Glide.with(this).load(logo).placeholder(R.drawable.ic_account).into(imgLogo);
    }

    private void initBack() {
        ImageView imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(view -> onBackPressed());
    }
}