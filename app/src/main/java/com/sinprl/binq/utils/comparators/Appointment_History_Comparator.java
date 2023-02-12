package com.sinprl.binq.utils.comparators;

import com.sinprl.binq.dataclasses.Appointment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Locale;

public class Appointment_History_Comparator implements Comparator<Appointment> {

    DateFormat timeformat = new SimpleDateFormat("hh:mm a", Locale.US);
    DateFormat dateformat = new SimpleDateFormat("yyyyddMM", Locale.US);
    @Override
    public int compare(Appointment a1, Appointment a2) {
        try {
            int result = dateformat.parse(a1.getDate_of_appointment()).compareTo(dateformat.parse(a2.getDate_of_appointment()));
            if(result == 0){
                return timeformat.parse(a1.getTime()).compareTo(timeformat.parse(a2.getTime()));
            }else{
                return result;
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
