package com.example.logindemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    TextInputEditText email;
    TextInputEditText pass;
    ImageButton imagebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        email =(TextInputEditText) findViewById(R.id.log);
        pass = (TextInputEditText) findViewById(R.id.pass);
        imagebtn=findViewById(R.id.btn);
        imagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddr = email.getText().toString();
                String password = pass.getText().toString();
                if (emailAddr.length() == 0 || password.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please enter the data", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(v.getContext(), UserDetails.class);
                    intent.putExtra("emailAdr", emailAddr);
                    intent.putExtra("password", password);
                    startActivity(intent);
                }
            }
        });

        /*
      //  Window window = MainActivity.this.getWindow();
   //     window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
     //   window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
   //     window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorAccent));
        */

    }
}