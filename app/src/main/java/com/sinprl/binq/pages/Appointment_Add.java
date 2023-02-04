package com.sinprl.binq.pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sinprl.binq.R;
import com.sinprl.binq.dataclasses.Appointment;
import com.sinprl.binq.utils.Utils;

public class Appointment_Add extends AppCompatActivity {

    String token_number = "";
    FirebaseDatabase database;

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
                finish();
            }
        });

        Button but_cancel_appointment = findViewById(R.id.but_appt_add_cancel);
        but_cancel_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void add_appointment_to_database() {

        EditText edt_user_name = findViewById(R.id.edt_appt_add_user_name);
        EditText edt_reason = findViewById(R.id.edt_appt_add_reason);
        EditText edt_time = findViewById(R.id.edt_appt_add_time);
        EditText edt_phone = findViewById(R.id.edt_appt_add_phone);

        get_token_number();

        Appointment appointment = new Appointment(token_number,
                edt_user_name.getText().toString(),
                edt_time.getText().toString(),
                edt_reason.getText().toString(), edt_phone.getText().toString());

        DatabaseReference databaseReference = database.getReference("Appointment/" + Utils.get_current_date_ddmmyy() );
        databaseReference.child(databaseReference.push().getKey()).setValue(appointment);
        database.getReference("TokenNumber").setValue(Integer.valueOf(token_number)+1);

    }

    private void get_token_number() {
        database.getReference("TokenNumber").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                token_number = String.valueOf(task.getResult().getValue());
            }
        });
    }
}