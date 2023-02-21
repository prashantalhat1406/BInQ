package com.sinprl.binq.utils.comparators;

import com.sinprl.binq.dataclasses.Appointment;
import com.sinprl.binq.dataclasses.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Locale;

public class User_Comparator implements Comparator<User> {

    @Override
    public int compare(User a1, User a2) {
        return a1.getPhone().compareTo(a2.getPhone());
    }
}
