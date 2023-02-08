package com.sinprl.binq.pages.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.sinprl.binq.R;
import com.sinprl.binq.pages.appointment_admin.Admin_Appointment_Display;

public class Front extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);

        Button but_newuser = findViewById(R.id.but_front_new_user);
        but_newuser.setOnClickListener(view -> {
            Intent intent = new Intent(Front.this, NewUser.class);
            startActivity(intent);
        });
        Button but_existing_user = findViewById(R.id.but_front_existing_user);
        but_existing_user.setOnClickListener(view -> {
            Intent intent = new Intent(Front.this, ExistingUser.class);
            startActivity(intent);
        });

        Button but_admin = findViewById(R.id.but_front_admin);
        but_admin.setOnClickListener(view -> {
            Intent intent = new Intent(Front.this, Admin_Appointment_Display.class);
            startActivity(intent);
        });
    }
}