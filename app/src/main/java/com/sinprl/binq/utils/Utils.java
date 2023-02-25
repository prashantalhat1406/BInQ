package com.sinprl.binq.utils;

import androidx.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sinprl.binq.dataclasses.Appointment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static String get_current_date_ddmmyy(){
        return new SimpleDateFormat("yyyyddMM").format(new Date());
    }

    public static Date parseDate(String date) {

        final String inputFormat = "hh:mm a";
        SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat, Locale.US);
        try {
            return inputParser.parse(date);
        } catch (java.text.ParseException e) {
            return new Date(0);
        }
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
            String user_appointment_path = "Users/Appointments/" + userID + "/";

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
        DatabaseReference user_appointment_ref = database.getReference("Users/Appointments/" + userID + "/" + Utils.get_current_date_ddmmyy() );

        admin_appointment_ref.child(appointmentID + "/active/").setValue(2);
        if(userID.length() != 0)
            user_appointment_ref.child(appointmentID + "/active/").setValue(2);
    }

    public static void mark_appointment_done(String appointmentID, String userID ){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://binq-1171a-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference admin_appointment_ref = database.getReference("Appointment/" + Utils.get_current_date_ddmmyy() );
        DatabaseReference user_appointment_ref = database.getReference("Users/Appointments/" + userID + "/" + Utils.get_current_date_ddmmyy() );

        admin_appointment_ref.child(appointmentID + "/active/").setValue(0);
        if(userID.length() != 0)
            user_appointment_ref.child(appointmentID + "/active/").setValue(0);
    }

    public static void mark_appointment_done(String appointmentID, String userID, int payment_method, int amount, String treatmentgiven, String followupdate ){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://binq-1171a-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference admin_appointment_ref = database.getReference("Appointment/" + Utils.get_current_date_ddmmyy() );
        DatabaseReference user_appointment_ref = database.getReference("Users/Appointments/" + userID + "/" + Utils.get_current_date_ddmmyy() );

        admin_appointment_ref.child(appointmentID + "/active/").setValue(0);
        admin_appointment_ref.child(appointmentID + "/amount/").setValue(amount);
        admin_appointment_ref.child(appointmentID + "/paymentmethod/").setValue(payment_method);
        admin_appointment_ref.child(appointmentID + "/treatment/").setValue(treatmentgiven);
        admin_appointment_ref.child(appointmentID + "/followupdate/").setValue(followupdate);
        if(userID.length() != 0) {
            user_appointment_ref.child(appointmentID + "/active/").setValue(0);
            user_appointment_ref.child(appointmentID + "/amount/").setValue(amount);
            user_appointment_ref.child(appointmentID + "/paymentmethod/").setValue(payment_method);
            user_appointment_ref.child(appointmentID + "/treatment/").setValue(treatmentgiven);
            user_appointment_ref.child(appointmentID + "/followupdate/").setValue(followupdate);
        }
    }



}
