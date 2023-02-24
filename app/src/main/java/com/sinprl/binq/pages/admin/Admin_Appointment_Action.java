package com.sinprl.binq.pages.admin;

import static java.lang.Integer.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.sinprl.binq.pages.login.Home;
import com.sinprl.binq.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Admin_Appointment_Action extends AppCompatActivity implements View.OnClickListener {

    User user;
    List<User> all_users;
    Appointment appointment;

    FirebaseDatabase database;
    String userID,appointmentID,userphone,username,reason;
    int userage, usergender;

    TextView followupdate;
    EditText treatmentgiven;
    EditText amount;
    RadioGroup payment;
    RadioButton cash, online, done, cancel;
    final Calendar myCalendar= Calendar.getInstance();
    String myFormat="ddMMMYYYY";
    SimpleDateFormat dateFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_appointment_action);

        database = FirebaseDatabase.getInstance("https://binq-1171a-default-rtdb.asia-southeast1.firebasedatabase.app");

        userID = getIntent().getExtras().getString("userID","");
        appointmentID = getIntent().getExtras().getString("appointmentID","");

        username = getIntent().getExtras().getString("username","");
        userage = getIntent().getExtras().getInt("userage");
        usergender = getIntent().getExtras().getInt("usergender");
        userphone = getIntent().getExtras().getString("userphone","");
        reason = getIntent().getExtras().getString("reason","");

        dateFormat=new SimpleDateFormat(myFormat, Locale.US);

        cash = findViewById(R.id.rdbutton_cash);
        online = findViewById(R.id.rdbutton_online);

        payment = findViewById(R.id.rdgroup_payment);

        treatmentgiven = findViewById(R.id.treatmentgiven);
        amount = findViewById(R.id.appointment_amount);

        followupdate = findViewById(R.id.txt_followupdate);
        followupdate.setText(dateFormat.format(myCalendar.getTime()));



        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH,month);
            myCalendar.set(Calendar.DAY_OF_MONTH,day);

            followupdate.setText(dateFormat.format(myCalendar.getTime()));
        };



        followupdate.setOnClickListener(view -> {
            DatePickerDialog d = new DatePickerDialog(Admin_Appointment_Action.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
            d.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
            d.show();
        });

        done = findViewById(R.id.rdbutton_done);
        done.setOnClickListener(view -> make_screen_enable(true));
        cancel = findViewById(R.id.rdbutton_cancel);
        cancel.setOnClickListener(view -> make_screen_enable(false));

        populate_appointment_user_information();

        Button savebutton = findViewById(R.id.button_appointment_details_save);
        savebutton.setOnClickListener(this);
    }



    private void make_screen_enable(boolean flag){
        if(!flag) {
            amount.setVisibility(View.GONE);
            followupdate.setVisibility(View.GONE);
            treatmentgiven.setVisibility(View.GONE);
            payment.setVisibility(View.GONE);
            cash.setVisibility(View.GONE);
            online.setVisibility(View.GONE);
        }else{
            amount.setVisibility(View.VISIBLE);
            followupdate.setVisibility(View.VISIBLE);
            treatmentgiven.setVisibility(View.VISIBLE);
            payment.setVisibility(View.VISIBLE);
            cash.setVisibility(View.VISIBLE);
            online.setVisibility(View.VISIBLE);
        }

    }

    private void populate_appointment_user_information() {
        TextView txtusername = findViewById(R.id.txt_apt_details_username);
        txtusername.setText(username);

        TextView phone = findViewById(R.id.txt_apt_details_phone);
        phone.setText(" "+userphone);

        TextView age = findViewById(R.id.txt_apt_details_age);
        age.setText(""+userage);

        TextView txtreason = findViewById(R.id.txt_apt_details_complaint);
        txtreason.setText(""+reason);

        TextView gender = findViewById(R.id.txt_apt_details_gender);
        if (usergender == 1)
        {
            gender.setCompoundDrawablesWithIntrinsicBounds(R.drawable.male, 0, 0, 0);
            gender.setText("Male");
        }
        else
        {
            gender.setCompoundDrawablesWithIntrinsicBounds(R.drawable.female, 0, 0, 0);
            gender.setText("Female");
        }
    }


    @Override
    public void onClick(View view) {
        RadioButton done = findViewById(R.id.rdbutton_done);
        RadioButton cancel = findViewById(R.id.rdbutton_cancel);

        if(cancel.isChecked()){
            Utils.cancel_appointment(appointmentID, userID);
            Intent intent = new Intent(Admin_Appointment_Action.this, Admin_Appointment_Display.class);
            startActivity(intent);
            finish();
        }

        if (done.isChecked()){
            if(payment.getCheckedRadioButtonId() != -1){
                if(amount.getText().toString().length() != 0){
                    if(treatmentgiven.getText().toString().trim().length() != 0){
                        int payment_method;
                        if (payment.getCheckedRadioButtonId() == R.id.rdbutton_cash)
                            payment_method = 1;
                        else
                            payment_method = 2;
                        Utils.mark_appointment_done(appointmentID, userID,payment_method, parseInt(amount.getText().toString()),treatmentgiven.getText().toString(),followupdate.getText().toString());
                        Intent intent = new Intent(Admin_Appointment_Action.this, Admin_Appointment_Display.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(this, "No Treatment mentioned !!!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "Amount not added !!!", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "Payment Method Not Selected !!!", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Status Not Selected !!!", Toast.LENGTH_SHORT).show();
        }


    }
}