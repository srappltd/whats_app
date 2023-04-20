package com.sandhya.whatsapp.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sandhya.whatsapp.MainActivity;
import com.sandhya.whatsapp.R;
import com.sandhya.whatsapp.auth.LoginActivity;
import com.sandhya.whatsapp.otpAuth.GenerateOTPActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initSlashScreen();
    }

    private void initSlashScreen() {
        new Handler().postDelayed(() -> {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            if (auth.getCurrentUser()!=null){
                startActivity(new Intent(this,MainActivity.class));
            }else {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
            finish();
        },2000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseAuth auth = FirebaseAuth.getInstance();
////        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (auth.getCurrentUser()==null){
//            startActivity(new Intent(this,MainActivity.class));
//        }
//    }
}