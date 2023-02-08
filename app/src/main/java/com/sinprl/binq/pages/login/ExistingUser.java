package com.sinprl.binq.pages.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sinprl.binq.R;
import com.sinprl.binq.pages.appointment_admin.Appointment_Add;
import com.sinprl.binq.pages.users.User_Appointment_Display;
import com.sinprl.binq.utils.Utils;
import com.sinprl.binq.utils.Validations;

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
                EditText edtuserID = findViewById(R.id.edt_existing_user_name);
                String userID = edtuserID.getText().toString();
                if( Validations.is_valid_phone_number(userID) ) {
                    Intent intent = new Intent(ExistingUser.this, User_Appointment_Display.class);
                    intent.putExtra("userID", userID);
                    startActivity(intent);
                }else {
                    Toast.makeText(view.getContext(), "Enter Valid Phone Number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        but_cancel = findViewById(R.id.but_existing_user_cancel);
        but_cancel.setOnClickListener(view -> finish());
    }
}