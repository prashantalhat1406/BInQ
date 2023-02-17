package com.sinprl.binq.pages.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sinprl.binq.R;
import com.sinprl.binq.dataclasses.Appointment;
import com.sinprl.binq.dataclasses.User;
import com.sinprl.binq.pages.users.User_Appointment_Display;
import com.sinprl.binq.utils.Utils;
import com.sinprl.binq.utils.Validations;

import java.util.ArrayList;
import java.util.List;

public class NewUser extends AppCompatActivity {

    Button but_register, but_cancel;
    FirebaseDatabase database;

    List<User> all_users;

    EditText edt_phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        database = FirebaseDatabase.getInstance("https://binq-1171a-default-rtdb.asia-southeast1.firebasedatabase.app");

        but_register = findViewById(R.id.but_register_register);
        but_register.setOnClickListener(view -> add_user_details_to_database());

        all_users = new ArrayList<>();
        get_all_users_from_database();

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

    private void get_all_users_from_database() {

        DatabaseReference databaseReference = database.getReference("Users/Profiles/");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                all_users.clear();
                for (DataSnapshot s : snapshot.getChildren()){
                    User user = s.getValue(User.class);
                    all_users.add(user);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

        Validations validations = Validations.is_not_blank_user(user);

        if(validations.isValid()) {
            if(!user_exists_in_database(user)){
                DatabaseReference databaseReference = database.getReference("Users/Profiles/");
                databaseReference.child(edt_phone.getText().toString()).setValue(user);
                Intent intent = new Intent(NewUser.this, User_Appointment_Display.class);
                intent.putExtra("userID", edt_phone.getText().toString());
                startActivity(intent);
                finish();
            }else
                Toast.makeText(this, "User already exists !!", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(this, validations.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean user_exists_in_database(User user) {
        boolean user_exists = false;

        for (User dbuser: all_users) {
            if(user.getPhone().equals(dbuser.getPhone()))
            {
                user_exists = true;
                break;
            }
        }
        return user_exists;
    }
}

