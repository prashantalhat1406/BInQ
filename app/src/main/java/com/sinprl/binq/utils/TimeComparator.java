package com.sinprl.binq.utils;

import com.sinprl.binq.dataclasses.TimeSlots;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Locale;

public class TimeComparator implements Comparator<TimeSlots> {

    DateFormat fmt = new SimpleDateFormat("hh:mm a", Locale.US);

    @Override
    public int compare(TimeSlots t1, TimeSlots t2) {
        try {
            return fmt.parse(t1.getTimeslot()).compareTo(fmt.parse(t2.getTimeslot()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
