package com.sinprl.binq.pages.users;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sinprl.binq.R;
import com.sinprl.binq.dataclasses.Appointment;
import com.sinprl.binq.pages.common.Reason_Display_Add;
import com.sinprl.binq.pages.common.TimeSlot_Display_Add;
import com.sinprl.binq.utils.Utils;
import com.sinprl.binq.utils.Validations;
import com.sinprl.binq.utils.comparators.Appointment_Comparator;

import java.util.ArrayList;
import java.util.List;

public class User_Appointment_Add extends AppCompatActivity {

    String token_number = "";
    FirebaseDatabase database;
    String userID, userName, userPhone;

    List<Appointment> users_daily_appointments;

    int no_of_available_appointments;

    EditText edt_user_name;
    TextView edt_reason;
    TextView edt_timeslot;
    EditText edt_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_appointment_add);

        userID = getIntent().getExtras().getString("userID","");
        userName = getIntent().getExtras().getString("userName","");
        userPhone = getIntent().getExtras().getString("userPhone","");

        database = FirebaseDatabase.getInstance("https://binq-1171a-default-rtdb.asia-southeast1.firebasedatabase.app");
        get_token_number();
        users_daily_appointments = new ArrayList<>();
        get_users_daily_appointments();

        Button but_cancel_appointment = findViewById(R.id.add_appointment_cancel);
        but_cancel_appointment.setOnClickListener(view -> finish());

        Button but_add_appointment = findViewById(R.id.add_appointment_add);
        but_add_appointment.setOnClickListener(view -> add_appointment_to_database());

        ImageButton select_reason = findViewById(R.id.add_appointment_select_reason_button);
        select_reason.setOnClickListener(view -> {
            Intent intent = new Intent(User_Appointment_Add.this, Reason_Display_Add.class);
            startActivityForResult(intent,100);
        });

        ImageButton select_timeslot = findViewById(R.id.add_appointment_select_timeslot_button);
        select_timeslot.setOnClickListener(view -> {
            Intent intent = new Intent(User_Appointment_Add.this, TimeSlot_Display_Add.class);
            startActivityForResult(intent,200);

        });

        edt_reason = findViewById(R.id.add_appointment_reason_display);
        edt_timeslot = findViewById(R.id.add_appointment_timeslot_display);
        edt_user_name = findViewById(R.id.add_appointment_username);
        edt_user_name.setText(userName);
        edt_phone = findViewById(R.id.add_appointment_phone);
        edt_phone.setText(userPhone);

    }

    private void add_appointment_to_database() {


        edt_timeslot = findViewById(R.id.add_appointment_timeslot_display);

        get_token_number();

        Appointment appointment = new Appointment(token_number,
                edt_user_name.getText().toString(),
                edt_timeslot.getText().toString(),
                edt_reason.getText().toString(),
                edt_phone.getText().toString());

        if(Validations.is_valid_phone_number(appointment.getPhone())) {

            if (Validations.is_not_blank_appointment(appointment) && no_of_available_appointments > 0) {
                appointment.setUserID(userID);
                if(!appointment_exists_for_day()){
                    Utils.add_appointment_to_database(appointment, no_of_available_appointments);
                    database.getReference("TokenNumber").setValue(Integer.parseInt(token_number) + 1);
                    Toast.makeText(this, "Appointment Added", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    finish();
                    Toast.makeText(this, "Appointment already exists for user", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Empty field found", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "InValid Phone Number", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean appointment_exists_for_day() {
        boolean appointment_exists_for_day = false;

        for (Appointment appointment : users_daily_appointments){
            if (appointment.getActive() == 1)
            {
                appointment_exists_for_day=true;
                break;
            }
        }

        return appointment_exists_for_day;
    }

    private void get_users_daily_appointments() {

        DatabaseReference databaseReference = database.getReference("Users/Appointments/"+userID+"/" + Utils.get_current_date_ddmmyy());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    users_daily_appointments.clear();
                    for (DataSnapshot s : snapshot.getChildren()){
                        Log.d("Regular", "" + s.getValue());
                        Appointment appointment = s.getValue(Appointment.class);
                        appointment.setId(s.getKey());
                        users_daily_appointments.add(appointment);
                    }
                    users_daily_appointments.sort(new Appointment_Comparator());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void get_token_number() {
        database.getReference("TokenNumber").get().addOnCompleteListener(task -> token_number = String.valueOf(task.getResult().getValue()));
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (100) : {
                if (resultCode == Reason_Display_Add.RESULT_OK) {
                    edt_reason.setText(data.getStringExtra("reason"));
                }
                break;
            }
            case (200) : {
                if (resultCode == TimeSlot_Display_Add.RESULT_OK) {
                    edt_timeslot.setText(data.getStringExtra("timeslot"));
                    no_of_available_appointments = data.getIntExtra("no_available_appointments",3);
                }
                break;
            }
        }
    }
}