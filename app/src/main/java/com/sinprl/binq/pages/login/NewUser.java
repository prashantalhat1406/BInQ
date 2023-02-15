package com.sinprl.binq.pages.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sinprl.binq.R;
import com.sinprl.binq.dataclasses.User;
import com.sinprl.binq.pages.users.User_Appointment_Display;
import com.sinprl.binq.utils.Validations;

public class NewUser extends AppCompatActivity {

    Button but_register, but_cancel;
    FirebaseDatabase database;

    EditText edt_phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        database = FirebaseDatabase.getInstance("https://binq-1171a-default-rtdb.asia-southeast1.firebasedatabase.app");

        but_register = findViewById(R.id.but_register_register);
        but_register.setOnClickListener(view -> add_user_details_to_database());

        TextView txt_existinguser = findViewById(R.id.txt_register_existinguser);
        txt_existinguser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewUser.this, Home.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void add_user_details_to_database() {
        EditText edt_user_name = findViewById(R.id.edt_register_username);
        edt_phone = findViewById(R.id.edt_register_phone);
        EditText edt_password = findViewById(R.id.edt_register_password);

        User user = new User(edt_user_name.getText().toString(),
                edt_phone.getText().toString(),
                edt_password.getText().toString());

        if(Validations.is_not_blank_user(user)) {
            DatabaseReference databaseReference = database.getReference("Users/");
            databaseReference.child(edt_phone.getText().toString()).setValue(user);
            Intent intent = new Intent(NewUser.this, User_Appointment_Display.class);
            intent.putExtra("userID", edt_phone.getText().toString());
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this, "Empty field found", Toast.LENGTH_SHORT).show();
        }
    }
}