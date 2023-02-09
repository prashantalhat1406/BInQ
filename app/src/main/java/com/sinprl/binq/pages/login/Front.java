package com.sinprl.binq.pages.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sinprl.binq.R;
import com.sinprl.binq.dataclasses.TimeSlots;
import com.sinprl.binq.pages.appointment_admin.Admin_Appointment_Display;

import java.util.ArrayList;
import java.util.List;

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

            //add_sample_data();
            Intent intent = new Intent(Front.this, Admin_Appointment_Display.class);
            startActivity(intent);
        });
    }

    private void add_sample_data() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://binq-1171a-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference dbReference = database.getReference("Timeslots/");

        List<TimeSlots> timeSlots = new ArrayList<>();

        timeSlots.add(new TimeSlots("10:00AM", 3));
        timeSlots.add(new TimeSlots("11:00AM", 3));
        timeSlots.add(new TimeSlots("12:00AM", 3));
        timeSlots.add(new TimeSlots("01:00PM", 3));
        timeSlots.add(new TimeSlots("02:00PM", 3));
        timeSlots.add(new TimeSlots("03:00PM", 3));

        for(TimeSlots r: timeSlots){
            //dbReference.child(dbReference.push().getKey()).setValue(r);
            dbReference.child(r.getTimeslot()).setValue(r);
        }
    }
}