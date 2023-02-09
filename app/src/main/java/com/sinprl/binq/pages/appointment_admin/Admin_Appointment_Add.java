package com.sinprl.binq.pages.appointment_admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.sinprl.binq.R;
import com.sinprl.binq.dataclasses.Appointment;
import com.sinprl.binq.pages.common.Reason_Display_Add;
import com.sinprl.binq.pages.common.TimeSlot_Display_Add;
import com.sinprl.binq.utils.Utils;
import com.sinprl.binq.utils.Validations;

public class Admin_Appointment_Add extends AppCompatActivity {

    String token_number = "";
    FirebaseDatabase database;

    EditText edt_user_name;
    EditText edt_reason;
    EditText edt_timeslot;
    EditText edt_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_add);


        database = FirebaseDatabase.getInstance("https://binq-1171a-default-rtdb.asia-southeast1.firebasedatabase.app");
        get_token_number();

        Button but_add_appointment = findViewById(R.id.but_appt_add_add);
        but_add_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_appointment_to_database();

            }
        });

        Button but_cancel_appointment = findViewById(R.id.but_appt_add_cancel);
        but_cancel_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        edt_reason = findViewById(R.id.edt_appt_add_reason);

        edt_reason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_Appointment_Add.this, Reason_Display_Add.class);
                //startActivity(intent);
                startActivityForResult(intent,100);
            }
        });

        edt_timeslot = findViewById(R.id.edt_appt_add_time);

        edt_timeslot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_Appointment_Add.this, TimeSlot_Display_Add.class);
                //startActivity(intent);
                startActivityForResult(intent,200);

            }
        });


    }

    private void add_appointment_to_database() {

        edt_user_name = findViewById(R.id.edt_appt_add_user_name);

        edt_timeslot = findViewById(R.id.edt_appt_add_time);
        edt_phone = findViewById(R.id.edt_appt_add_phone);

        get_token_number();
        Appointment appointment = new Appointment(token_number,
                edt_user_name.getText().toString(),
                edt_timeslot.getText().toString(),
                edt_reason.getText().toString(), edt_phone.getText().toString());

        if(Validations.is_not_blank_appointment(appointment)) {
            Utils.add_appointment_to_database(appointment, "Appointment/");
            database.getReference("TokenNumber").setValue(Integer.valueOf(token_number) + 1);
            finish();
        }else {
            Toast.makeText(this, "Empty field found", Toast.LENGTH_SHORT).show();
        }



    }

    private void get_token_number() {
        database.getReference("TokenNumber").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                token_number = String.valueOf(task.getResult().getValue());
            }
        });
    }

    @Override
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
                }
                break;
            }
        }
    }
}