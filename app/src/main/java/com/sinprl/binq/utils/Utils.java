package com.sinprl.binq.utils;

import android.app.Dialog;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sinprl.binq.R;
import com.sinprl.binq.dataclasses.Appointment;
import com.sinprl.binq.pages.appointment_admin.Admin_Appointment_Display;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String get_current_date_ddmmyy(){
        String date_ddmmyy = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyyddMM");
        date_ddmmyy = dateFormat.format(new Date());
        return date_ddmmyy;
    }

    public static void add_appointment_to_database(@NonNull Appointment appointment, int no_of_available_appointments){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://binq-1171a-default-rtdb.asia-southeast1.firebasedatabase.app");


        String userID = appointment.getUserID();

        if(userID.length() == 0){
            String admin_appointment_path = "Appointment/";
            DatabaseReference dbReference = database.getReference(admin_appointment_path + Utils.get_current_date_ddmmyy() );
            appointment.setActive(1);
            dbReference.child(dbReference.push().getKey()).setValue(appointment);
        }
        else{
            String admin_appointment_path = "Appointment/";
            String user_appointment_path = "Users/" + userID + "/Appointments/";

            DatabaseReference adminReference = database.getReference(admin_appointment_path + Utils.get_current_date_ddmmyy() );
            appointment.setActive(1);
            String key = adminReference.push().getKey();
            adminReference.child(key).setValue(appointment);
            DatabaseReference userReference = database.getReference(user_appointment_path + Utils.get_current_date_ddmmyy() );
            userReference.child(key).setValue(appointment);
        }

        DatabaseReference timeslot_no_of_available_appointment_ref = database.getReference("Timeslots/" + appointment.getTime() + "/" );
        timeslot_no_of_available_appointment_ref.child("no_of_appointments/").setValue(no_of_available_appointments - 1);
    }

    public static void cancel_appointment(String appointmentID, String userID ){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://binq-1171a-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference admin_appointment_ref = database.getReference("Appointment/" + Utils.get_current_date_ddmmyy() );
        DatabaseReference user_appointment_ref = database.getReference("Users/" + userID + "/Appointments/" + Utils.get_current_date_ddmmyy() );

        admin_appointment_ref.child(appointmentID + "/active/").setValue(0);
        if(userID.length() != 0)
            user_appointment_ref.child(appointmentID + "/active/").setValue(0);
    }

    public static void mark_appointment_done(String appointmentID, String userID ){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://binq-1171a-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference admin_appointment_ref = database.getReference("Appointment/" + Utils.get_current_date_ddmmyy() );
        DatabaseReference user_appointment_ref = database.getReference("Users/" + userID + "/Appointments/" + Utils.get_current_date_ddmmyy() );

        admin_appointment_ref.child(appointmentID + "/active/").setValue(2);
        if(userID.length() != 0)
            user_appointment_ref.child(appointmentID + "/active/").setValue(2);
    }



}
