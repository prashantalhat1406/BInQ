package com.sinprl.binq.utils.comparators;

import com.sinprl.binq.dataclasses.Appointment;

import java.util.Comparator;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class AppointmentComparator implements Comparator<Appointment> {

    DateFormat fmt = new SimpleDateFormat("hh:mm a", Locale.US);

    @Override
    public int compare(Appointment a1, Appointment a2) {
        try {
            return fmt.parse(a1.getTime()).compareTo(fmt.parse(a2.getTime()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
