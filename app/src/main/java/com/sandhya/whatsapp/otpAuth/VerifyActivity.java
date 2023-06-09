package com.sandhya.whatsapp.otpAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.sandhya.whatsapp.MainActivity;
import com.sandhya.whatsapp.R;
import com.sandhya.whatsapp.auth.SignUpActivity;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class VerifyActivity extends AppCompatActivity {

    private String verificationId;
    EditText otp;
    private FirebaseAuth mAuth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        mAuth = FirebaseAuth.getInstance();

        final String phonenumber = getIntent().getStringExtra("phone");
        sendVerificationCode(phonenumber);

        //Back
        findViewById(R.id.imageView).setOnClickListener(v -> onBackPressed());

        //EditText
        otp = findViewById(R.id.otp);

        //Button
        findViewById(R.id.signIn).setOnClickListener(v -> {
            findViewById(R.id.progress).setVisibility(View.VISIBLE);
            String code = otp.getText().toString().trim();
            if (code.isEmpty() || code.length() < 6){
                Snackbar.make(v,"Enter OTP", Snackbar.LENGTH_LONG).show();
                findViewById(R.id.progress).setVisibility(View.INVISIBLE);
            }else {
                verifyCode(code);
            }

        });
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phonenumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }


    private  void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerifyActivity.this, task -> {
                    if (task.isSuccessful()) {
                        final String phone = getIntent().getStringExtra("phone");

                        Query userPhone = FirebaseFirestore.getInstance().collection("users").whereEqualTo("phone", phone);
                        userPhone.addSnapshotListener((value, error) -> {
                            for (DocumentSnapshot ds : Objects.requireNonNull(value)){
                                if (ds != null){
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class ));
                                }else {
                                    startActivity(new Intent(getApplicationContext(), SignUpActivity.class ));
                                }
                                finish();
                            }
                            if (value.isEmpty()){
                                startActivity(new Intent(getApplicationContext(), SignUpActivity.class ));
                                finish();
                            }
                        });

                    } else {
                        String msg = Objects.requireNonNull(task.getException()).getMessage();
                        Toast.makeText(VerifyActivity.this, msg, Toast.LENGTH_SHORT).show();
                        findViewById(R.id.progress).setVisibility(View.INVISIBLE);
                    }
                });
    }

    private void sendVerificationCode(String phonenumber) {
        //noinspection deprecation
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phonenumber,
                60,
                TimeUnit.SECONDS,
                VerifyActivity.this,
                mCallbacks);
    }

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null){
                otp.setText(code);
                verifyCode(code);
                findViewById(R.id.progress).setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerifyActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            findViewById(R.id.progress).setVisibility(View.INVISIBLE);
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
            findViewById(R.id.progress).setVisibility(View.INVISIBLE);
        }
    };

}