package com.sinprl.binq.utils;

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
