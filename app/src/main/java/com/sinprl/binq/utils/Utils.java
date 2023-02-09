package com.sinprl.binq.utils;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sinprl.binq.dataclasses.Appointment;

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

    public static void add_appointment_to_database(Appointment appointment, String databaseReference, int no_of_available_appointments){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://binq-1171a-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference dbReference = database.getReference(databaseReference + Utils.get_current_date_ddmmyy() );
        dbReference.child(dbReference.push().getKey()).setValue(appointment);

        DatabaseReference timeslot_no_of_available_appointment_ref = database.getReference("Timeslots/" + appointment.getTime() + "/" );
        timeslot_no_of_available_appointment_ref.child("no_of_appointments/").setValue(no_of_available_appointments - 1);
    }

}
