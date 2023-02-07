package com.sinprl.binq.utils;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
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

}
