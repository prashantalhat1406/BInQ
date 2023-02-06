package com.sinprl.binq.pages.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.sinprl.binq.R;

public class Front extends AppCompatActivity {

    Button but_newuser, but_existing_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);

        but_newuser = findViewById(R.id.but_front_new_user);
        but_existing_user = findViewById(R.id.but_front_existing_user);
    }
}