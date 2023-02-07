package com.sinprl.binq.pages.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sinprl.binq.R;
import com.sinprl.binq.pages.appointment_admin.Appointment_Add;
import com.sinprl.binq.pages.appointment_admin.Appointment_Display;

public class Front extends AppCompatActivity {

    Button but_newuser, but_existing_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);

        but_newuser = findViewById(R.id.but_front_new_user);
        but_newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Front.this, NewUser.class);
                startActivity(intent);
            }
        });
        but_existing_user = findViewById(R.id.but_front_existing_user);
        but_existing_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Front.this, ExistingUser.class);
                startActivity(intent);
            }
        });
    }
}