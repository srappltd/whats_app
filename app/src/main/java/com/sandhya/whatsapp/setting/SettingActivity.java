package com.sandhya.whatsapp.setting;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sandhya.whatsapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends AppCompatActivity {

    TextView txtName, txtAbout;
    CircleImageView imgLogo;
    FrameLayout frameLayout;
    String logo,categories,name;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        frameLayout = findViewById(R.id.frame);
        imgLogo = findViewById(R.id.imgLogo);
        txtName = findViewById(R.id.txtName);
        txtAbout = findViewById(R.id.txtAbout);
        ImageView imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(view -> onBackPressed());
        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String logo = snapshot.child("image").getValue().toString();
                    String categories = snapshot.child("categories").getValue().toString();
                    String name = snapshot.child("name").getValue().toString();

                    Glide.with(SettingActivity.this).load(logo).placeholder(R.drawable.ic_account).into(imgLogo);
                    txtAbout.setText(categories);
                    txtName.setText(name);
                }else {
//                    Glide.with(SettingActivity.this).load(logo).placeholder(R.drawable.ic_account).into(imgLogo);
                    imgLogo.setImageResource(R.drawable.ic_account);
                    txtAbout.setText("Abouts");
                    txtName.setText("User name");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        LinearLayout linearLayout2 = findViewById(R.id.linearLayout2);
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ProfileFragment profileFragment = new ProfileFragment();
//                selectFragment(profileFragment);
//                frameLayout.setVisibility(View.VISIBLE);
                Intent intent = new Intent(SettingActivity.this,ProfileActivity.class);
                intent.putExtra("logo",logo);
                startActivity(intent);
                finish();
            }
        });

    }
    public void selectFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame,fragment);
        transaction.commit();
    }
}