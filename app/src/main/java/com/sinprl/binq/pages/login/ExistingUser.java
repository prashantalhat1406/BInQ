package com.sinprl.binq.pages.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sinprl.binq.R;
import com.sinprl.binq.pages.appointment_admin.Appointment_Add;
import com.sinprl.binq.pages.users.User_Appointment_Display;

public class ExistingUser extends AppCompatActivity {

    Button but_login, but_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_user);


        but_login = findViewById(R.id.but_existing_user_login);
        but_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText userID = findViewById(R.id.edt_existing_user_name);
                Intent intent = new Intent(ExistingUser.this, User_Appointment_Display.class);
                intent.putExtra("userID", userID.getText().toString());
                startActivity(intent);
            }
        });

        but_cancel = findViewById(R.id.but_existing_user_cancel);
        but_cancel.setOnClickListener(view -> finish());
    }
}