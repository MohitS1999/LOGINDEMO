package com.example.logindemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class UserDetails extends AppCompatActivity {
    Button coverBtn;
    ImageButton profileBtn;
    ImageView coverImage;
    ImageView profileImage;
    Integer Request_camera_cover=101,Select_file_cover=102,Request_camera_profile=103,Select_file_profile=104;
    TextView username;
    TextView password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        coverBtn=findViewById(R.id.coverBtn);
        profileBtn=findViewById(R.id.profileBtn);
        coverImage=(ImageView)findViewById(R.id.coverImage);
        profileImage=(ImageView)findViewById(R.id.profileImage);
        username=(TextView) findViewById(R.id.username);
        password=(TextView) findViewById(R.id.password);
        Intent intent=getIntent();
        String name=intent.getStringExtra("emailAdr");
        String passGet=intent.getStringExtra("password");
        username.setText(name);
        password.setText(passGet);
        coverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"cover",Toast.LENGTH_SHORT).show();
                //SelectImageCover();
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, Request_camera_cover);
                }else SelectImageCover();
            }
        });
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, Request_camera_profile);
                }else SelectImageProfile();
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Request_camera_cover) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_SHORT).show();
                SelectImageCover();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == Request_camera_profile) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_SHORT).show();
                SelectImageProfile();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void SelectImageCover(){
        final CharSequence[] items={"Camera","Galery","Cancel"};
        AlertDialog.Builder builder=new AlertDialog.Builder(UserDetails.this);
        builder.setTitle("Edit Cover Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (items[i].equals("Camera")){
                    Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,Request_camera_cover);
                }else if (items[i].equals("Galery")){
                    Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent,"Select File"),Select_file_cover);
                }else if (items[i].equals("Cancel")){
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    private void SelectImageProfile(){
        final CharSequence[] items={"Camera","Galery","Cancel"};
        AlertDialog.Builder builder=new AlertDialog.Builder(UserDetails.this);
        builder.setTitle("Edit Profile Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (items[i].equals("Camera")){
                    Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,Request_camera_profile);
                }else if (items[i].equals("Galery")){
                    Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent,"Select File"),Select_file_profile);
                }else if (items[i].equals("Cancel")){
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK){
            if (requestCode==Request_camera_cover){
                Bundle bundle=data.getExtras();
                final Bitmap bmp=(Bitmap) bundle.get("data");
                coverImage.setImageBitmap(bmp);

            }else if (requestCode==Select_file_cover) {
                Uri selectedImageUri = data.getData();
                coverImage.setImageURI(selectedImageUri);
            }else if (requestCode==Request_camera_profile){
                Bundle bundle=data.getExtras();
                final Bitmap btm=(Bitmap)bundle.get("data");
                profileImage.setImageBitmap(btm);
            }else if (requestCode==Select_file_profile){
                Uri selectedImageUri=data.getData();
                profileImage.setImageURI(selectedImageUri);
            }
        }
    }
}