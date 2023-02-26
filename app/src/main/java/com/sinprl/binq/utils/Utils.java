package com.sinprl.binq.utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sinprl.binq.constants.Constants;
import com.sinprl.binq.dataclasses.Appointment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static final FirebaseDatabase FIREBASEDATABASEINSTANCE = FirebaseDatabase.getInstance(Constants.FIREBASE_DATABASE);
    public static final String TIMEFORMAT = "hh:mm a";


    public static String get_current_date_ddmmyy(){
        return new SimpleDateFormat("yyyyddMM").format(new Date());
        //Log.d("Previous Day", "" + new SimpleDateFormat("yyyyddMM").format(new Date(System.currentTimeMillis() - 24*60*60*1000)));
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
        FirebaseDatabase database = FirebaseDatabase.getInstance(Constants.FIREBASE_DATABASE);


        String userID = appointment.getUserID();

        if(userID.length() == 0){
            DatabaseReference dbReference = database.getReference(Constants.APPOINTMENT_ENDPOINT + Utils.get_current_date_ddmmyy() );
            appointment.setActive(1);
            dbReference.child(dbReference.push().getKey()).setValue(appointment);
        }
        else{
            DatabaseReference adminReference = database.getReference(Constants.APPOINTMENT_ENDPOINT + Utils.get_current_date_ddmmyy() );
            appointment.setActive(1);
            String key = adminReference.push().getKey();
            adminReference.child(key).setValue(appointment);
            DatabaseReference userReference = database.getReference(Constants.USER_APPOINTMENT_ENDPOINT + userID + "/" + Utils.get_current_date_ddmmyy() );
            userReference.child(key).setValue(appointment);
        }

        DatabaseReference timeslot_no_of_available_appointment_ref = database.getReference(Constants.TIMESLOT_ENDPOINT + appointment.getTime() + "/" );
        timeslot_no_of_available_appointment_ref.child("no_of_appointments/").setValue(no_of_available_appointments - 1);
    }

    public static void cancel_appointment(String appointmentID, String userID ){
        FirebaseDatabase database = FirebaseDatabase.getInstance(Constants.FIREBASE_DATABASE);
        DatabaseReference admin_appointment_ref = database.getReference(Constants.APPOINTMENT_ENDPOINT + Utils.get_current_date_ddmmyy() );
        DatabaseReference user_appointment_ref = database.getReference(Constants.USER_APPOINTMENT_ENDPOINT + userID + "/" + Utils.get_current_date_ddmmyy() );

        admin_appointment_ref.child(appointmentID + "/active/").setValue(2);
        if(userID.length() != 0)
            user_appointment_ref.child(appointmentID + "/active/").setValue(2);
    }

    public static void mark_appointment_done(String appointmentID, String userID ){
        FirebaseDatabase database = FirebaseDatabase.getInstance(Constants.FIREBASE_DATABASE);
        DatabaseReference admin_appointment_ref = database.getReference(Constants.APPOINTMENT_ENDPOINT + Utils.get_current_date_ddmmyy() );
        DatabaseReference user_appointment_ref = database.getReference(Constants.USER_APPOINTMENT_ENDPOINT + userID + "/" + Utils.get_current_date_ddmmyy() );

        admin_appointment_ref.child(appointmentID + "/active/").setValue(0);
        if(userID.length() != 0)
            user_appointment_ref.child(appointmentID + "/active/").setValue(0);
    }

    public static void mark_appointment_done(String appointmentID, String userID, int payment_method, int amount, String treatmentgiven, String followupdate ){
        FirebaseDatabase database = FirebaseDatabase.getInstance(Constants.FIREBASE_DATABASE);
        DatabaseReference admin_appointment_ref = database.getReference(Constants.APPOINTMENT_ENDPOINT + Utils.get_current_date_ddmmyy() );
        DatabaseReference user_appointment_ref = database.getReference(Constants.USER_APPOINTMENT_ENDPOINT + userID + "/" + Utils.get_current_date_ddmmyy() );

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

    public static void update_user_password(String password, String userID ){
        FirebaseDatabase database = FirebaseDatabase.getInstance(Constants.FIREBASE_DATABASE);
        DatabaseReference user_profiles_ref = database.getReference(Constants.USER_PROFILES_ENDPOINT + userID + "/");
        user_profiles_ref.child( "password/").setValue(password);


    }



}
