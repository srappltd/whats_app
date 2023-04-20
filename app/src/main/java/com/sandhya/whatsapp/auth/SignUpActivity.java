package com.sandhya.whatsapp.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sandhya.whatsapp.MainActivity;
import com.sandhya.whatsapp.R;
import com.sandhya.whatsapp.model.UserModel;

import java.sql.Time;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    LinearLayout llLogin;
    EditText edtName,edtEmail,edtPassword;
    AppCompatButton btnSignup;
    ProgressBar progressBar;
    FirebaseAuth auth;
    FirebaseUser user;

    ArrayList<UserModel> models = new ArrayList<>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
//        btnSignup = findViewById(R.id.btnSignUp);
        btnSignup = findViewById(R.id.btnSignUp);
        progressBar = findViewById(R.id.progressBar);
        llLogin = findViewById(R.id.llLogin);
        llLogin.setOnClickListener(view -> startActivity(new Intent(SignUpActivity.this,LoginActivity.class)));
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                if (!name.isEmpty()&&!email.isEmpty()&&!password.isEmpty()){
                    progressBar.setVisibility(View.VISIBLE);
                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                               DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
                               String id = reference.push().getKey();
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                String date = DateFormat.getDateInstance().format(new Date());
                                String time = DateFormat.getTimeInstance().format(new Date());
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("name",name);
                                map.put("email",email);
                                map.put("password",password);
                                map.put("mobile","");
                                map.put("join_date",date);
                                map.put("time",time);
                                map.put("image","https://cdn.pixabay.com/photo/2016/08/31/11/54/icon-1633249__340.png");
                                map.put("banner","");
                                map.put("activity","");
                                map.put("uid",user.getUid().toLowerCase());
                                map.put("categories","");
                                map.put("id",id);
//                                models.add(new UserModel(name,email,password,user.getUid(),id,null,null,null,date,time,null));
                                reference.child(user.getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            progressBar.setVisibility(View.GONE);
                                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                            finish();
                                            Toast.makeText(SignUpActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                                        }else {
                                            progressBar.setVisibility(View.GONE);
                                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                            finish();
                                            Toast.makeText(SignUpActivity.this, "Please try again", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });

                            }
                        }
                    });

//

                }else {
                    Toast.makeText(SignUpActivity.this, "Please enter login name, email and password", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}