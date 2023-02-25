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
import com.sinprl.binq.constants.Constants;
import com.sinprl.binq.dataclasses.TimeSlots;
import com.sinprl.binq.dataclasses.User;
import com.sinprl.binq.pages.admin.Admin_Appointment_Display;
import com.sinprl.binq.pages.users.User_Appointment_Display;
import com.sinprl.binq.utils.Utils;
import com.sinprl.binq.utils.Validations;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    List<User> all_users;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        all_users = new ArrayList<>();
        get_all_users_from_database();

        Button but_login = findViewById(R.id.but_home_login);
        but_login.setOnClickListener(view -> {
            EditText edtuserID = findViewById(R.id.edt_home_username);
            String userID = edtuserID.getText().toString();
            EditText edtpassword = findViewById(R.id.edt_home_password);
            String password = edtpassword.getText().toString();

            if (userID.equals("55")){
                Intent intent = new Intent(Home.this, Admin_Appointment_Display.class);
                startActivity(intent);
                finish();
            }else{
                User user = new User("",userID,password);
                Validations validations = Validations.is_valid_user(all_users, user);
                if(validations.isValid()){
                    Intent intent = new Intent(Home.this, User_Appointment_Display.class);
                    intent.putExtra("userID", userID);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(this, validations.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }


        });

        TextView txt_new_user = findViewById(R.id.txt_home_register);
        txt_new_user.setOnClickListener(view -> {
            Intent intent = new Intent(Home.this, NewUser.class);
            startActivity(intent);
            finish();
        });





    }

    private void get_all_users_from_database() {
        //FirebaseDatabase database = FirebaseDatabase.getInstance(Constants.FIREBASE_DATABASE);
        //DatabaseReference databaseReference = database.getReference(Constants.USER_PROFILES_ENDPOINT);
        DatabaseReference databaseReference = Utils.FIREBASEDATABASEINSTANCE.getReference(Constants.USER_PROFILES_ENDPOINT);

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

    private void add_sample_data() {
        FirebaseDatabase database = FirebaseDatabase.getInstance(Constants.FIREBASE_DATABASE);
        DatabaseReference dbReference = database.getReference(Constants.TIMESLOT_ENDPOINT);

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