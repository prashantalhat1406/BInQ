package com.sinprl.binq.pages.admin;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.FirebaseDatabase;
import com.sinprl.binq.R;
import com.sinprl.binq.dataclasses.Appointment;
import com.sinprl.binq.pages.common.Reason_Display_Add;
import com.sinprl.binq.pages.common.TimeSlot_Display_Add;
import com.sinprl.binq.utils.Utils;
import com.sinprl.binq.utils.Validations;

public class Admin_Appointment_Add extends AppCompatActivity  implements RadioGroup.OnCheckedChangeListener {

    String token_number = "";
    FirebaseDatabase database;

    int no_of_available_appointments;

    EditText edt_user_name;
    TextView edt_reason;
    TextView edt_timeslot;
    EditText edt_phone;

    RadioGroup gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_appointment_add);


        database = FirebaseDatabase.getInstance("https://binq-1171a-default-rtdb.asia-southeast1.firebasedatabase.app");
        get_token_number();

        Button but_cancel_appointment = findViewById(R.id.add_appointment_cancel);
        but_cancel_appointment.setOnClickListener(view -> finish());

        /*NumberPicker genderpicker = findViewById(R.id.add_appointment_gender);
        genderpicker.setMinValue(0);
        genderpicker.setMaxValue(2);
        genderpicker.setDisplayedValues( new String[] { "Male", "Female", "Other" } );*/

        NumberPicker agepicker = findViewById(R.id.add_appointment_age);
        agepicker.setMinValue(1);
        agepicker.setMaxValue(100);

        gender = findViewById(R.id.rdgroup_gender);
        //gender.setOnCheckedChangeListener(this);
        


        Button but_add_appointment = findViewById(R.id.add_appointment_add);
        but_add_appointment.setOnClickListener(view -> add_appointment_to_database());

        ImageButton select_reason = findViewById(R.id.add_appointment_select_reason_button);
        select_reason.setOnClickListener(view -> {
            Intent intent = new Intent(Admin_Appointment_Add.this, Reason_Display_Add.class);
            startActivityForResult(intent,100);
        });

        ImageButton select_timeslot = findViewById(R.id.add_appointment_select_timeslot_button);
        select_timeslot.setOnClickListener(view -> {
            Intent intent = new Intent(Admin_Appointment_Add.this, TimeSlot_Display_Add.class);
            startActivityForResult(intent,200);

        });

        edt_reason = findViewById(R.id.add_appointment_reason_display);
        edt_timeslot = findViewById(R.id.add_appointment_timeslot_display);




    }

    private void add_appointment_to_database() {

        edt_user_name = findViewById(R.id.add_appointment_username);
        edt_timeslot = findViewById(R.id.add_appointment_timeslot_display);
        edt_phone = findViewById(R.id.add_appointment_phone);

        get_token_number();
        Appointment appointment = new Appointment(token_number,
                edt_user_name.getText().toString(),
                edt_timeslot.getText().toString(),
                edt_reason.getText().toString(), edt_phone.getText().toString());

        if(Validations.is_valid_phone_number(appointment.getPhone())){
            if(Validations.is_not_blank_appointment(appointment) &&
                    no_of_available_appointments > 0 ) {
                appointment.setUserID("");
                Utils.add_appointment_to_database(appointment,  no_of_available_appointments);
                database.getReference("TokenNumber").setValue(Integer.parseInt(token_number) + 1);
                finish();
            }else {
                Toast.makeText(this, "Empty field found", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "InValid Phone Number", Toast.LENGTH_SHORT).show();
        }





    }

    private void get_token_number() {
        database.getReference("TokenNumber").get().addOnCompleteListener(task -> token_number = String.valueOf(task.getResult().getValue()));
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
                    no_of_available_appointments = data.getIntExtra("no_available_appointments",3);
                }
                break;
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
//        RadioButton maleButton = (RadioButton) gender.findViewById(R.id.rdbutton_male);
//        RadioButton femaleButton = (RadioButton) gender.findViewById(R.id.rdbutton_female);
//        RadioButton otherButton = (RadioButton) gender.findViewById(R.id.rdbutton_other);
//
//        switch (i){
//            case R.id.rdbutton_male:
//                maleButton.setBackgroundColor(Color.BLUE);
//                femaleButton.setBackgroundColor(Color.TRANSPARENT);
//                otherButton.setBackgroundColor(Color.TRANSPARENT);
//                break;
//            case R.id.rdbutton_female:
//                maleButton.setBackgroundColor(Color.TRANSPARENT);
//                femaleButton.setBackgroundColor(Color.BLUE);
//                otherButton.setBackgroundColor(Color.TRANSPARENT);
//                break;
//        }
    }
}