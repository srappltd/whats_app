package com.sandhya.whatsapp.setting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sandhya.whatsapp.R;

import java.util.HashMap;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    CircleImageView imgLogo,imgUpload;
    EditText edtName,edtAbout,edtMobile;
    ProgressBar progressBar;

    Uri imgUri;
    String image;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        image = getIntent().getExtras().getString("logo");
        edtName = findViewById(R.id.edtName);
        edtAbout = findViewById(R.id.edtAbout);
        edtMobile = findViewById(R.id.edtMobile);
        imgLogo = findViewById(R.id.imgLogo);

        initBack();
        initSelectImage();
        initData(edtAbout,edtMobile,edtName,imgLogo);
        initSave(edtAbout,edtMobile,edtName);


    }


    private void initSave(EditText edtAbout, EditText edtMobile, EditText edtName) {
       AppCompatButton btnSave = findViewById(R.id.btnSave);
       progressBar = findViewById(R.id.progress);
       btnSave.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String name = edtName.getText().toString().trim();
               String about = edtAbout.getText().toString().trim();
               String mobile = edtMobile.getText().toString().trim();
               if (imgUri!=null&&!name.isEmpty()&&!mobile.isEmpty()&&!about.isEmpty()){
                   progressBar.setVisibility(View.VISIBLE);
                   StorageReference reference = FirebaseStorage.getInstance().getReference().child("Users");
                   StorageReference storageReference = reference.child(System.currentTimeMillis()+"."+ MimeTypeMap.getFileExtensionFromUrl(getContentResolver().getType(imgUri)));
                   storageReference.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                               @Override
                               public void onSuccess(Uri uri) {
                                   HashMap<String, Object> map = new HashMap<>();
                                   map.put("name",name);
                                   map.put("mobile",mobile);
                                   map.put("image",uri.toString());
                                   map.put("categories",about);

                                   FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                       @Override
                                       public void onComplete(@NonNull Task<Void> task) {
                                           if (task.isSuccessful()){
                                               progressBar.setVisibility(View.GONE);
                                               Toast.makeText(ProfileActivity.this, "Successfully update", Toast.LENGTH_SHORT).show();
                                               startActivity(new Intent(getApplicationContext(),SettingActivity.class));
                                               finish();
                                           }else{
                                               progressBar.setVisibility(View.GONE);
//                                               Toast.makeText(ProfileActivity.this, "Please try again", Toast.LENGTH_SHORT).show();

                                           }
                                       }
                                   });
                               }
                           });
                       }
                   });
               }else {
                   Toast.makeText(ProfileActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
               }
           }
       });

    }

    private void initSelectImage() {
        imgUpload= findViewById(R.id.imgUpload);
        imgUpload.setOnClickListener(view1 -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,1000);
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

                    Glide.with(ProfileActivity.this).load(logo).placeholder(R.drawable.ic_account).into(imgLogo);
                    edtMobile.setText(mobile);
                    edtAbout.setText(categories);
                    edtName.setText(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == 1000){
                imgUri = Objects.requireNonNull(data).getData();
                imgLogo.setImageURI(imgUri);
            }

        }
    }

    public void initBack(){
        ImageView imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(view1 -> {
            onBackPressed();
        });
    }
}