package com.sinprl.binq.pages.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sinprl.binq.R;
import com.sinprl.binq.dataclasses.TimeSlots;
import com.sinprl.binq.pages.users.User_Appointment_Display;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button but_login = findViewById(R.id.but_home_login);
        but_login.setOnClickListener(view -> {
            EditText edtuserID = findViewById(R.id.edt_home_username);
            String userID = edtuserID.getText().toString();
            edtuserID.setText("");
            /*if( Validations.is_valid_phone_number(userID) ) {*/
            Intent intent = new Intent(Home.this, User_Appointment_Display.class);
            intent.putExtra("userID", userID);
            startActivity(intent);
           /* }else {
                Toast.makeText(view.getContext(), "Enter Valid Phone Number", Toast.LENGTH_SHORT).show();
            }*/
        });

        TextView txt_new_user = findViewById(R.id.txt_home_register);
        txt_new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, NewUser.class);
                startActivity(intent);
                finish();
            }
        });






       /* Button but_newuser = findViewById(R.id.but_front_new_user);
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

            //add_sample_data();
            Intent intent = new Intent(Front.this, Admin_Appointment_Display.class);
            startActivity(intent);
        });*/
    }

    private void add_sample_data() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://binq-1171a-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference dbReference = database.getReference("Timeslots/");

        List<TimeSlots> timeSlots = new ArrayList<>();

        timeSlots.add(new TimeSlots("09:00 AM", 3));
        timeSlots.add(new TimeSlots("10:00 AM", 3));
        timeSlots.add(new TimeSlots("11:00 AM", 3));
        timeSlots.add(new TimeSlots("12:00 PM", 3));
        timeSlots.add(new TimeSlots("01:00 PM", 3));
        timeSlots.add(new TimeSlots("02:00 PM", 3));
        timeSlots.add(new TimeSlots("03:00 PM", 3));
        timeSlots.add(new TimeSlots("04:00 PM", 3));
        timeSlots.add(new TimeSlots("05:00 PM", 3));
        timeSlots.add(new TimeSlots("06:00 PM", 3));
        timeSlots.add(new TimeSlots("07:00 PM", 3));
        timeSlots.add(new TimeSlots("08:00 PM", 3));
        timeSlots.add(new TimeSlots("09:00 PM", 3));
        timeSlots.add(new TimeSlots("10:00 PM", 3));

        for(TimeSlots r: timeSlots){
            //dbReference.child(dbReference.push().getKey()).setValue(r);
            dbReference.child(r.getTimeslot()).setValue(r);
        }
    }
}