package com.sinprl.binq.utils;

import com.sinprl.binq.dataclasses.Appointment;
import com.sinprl.binq.dataclasses.User;

public class Validations {
    
    public static boolean is_valid_phone_number(String phone_number){
        boolean valid_phone = false;
        if(phone_number.length() == 10 && phone_number.matches("[0-9]+")){
            valid_phone = true;
        }
        return valid_phone;
    }

    public static boolean is_valid_name(String name){
        boolean valid_name = false;
        if(name.length() != 0){
            valid_name = true;
        }
        return valid_name;
    }

    public static boolean is_not_blank_user(User user){
        boolean blank_user = true;

        if(user.getName().length() == 0 || user.getPhone().length() == 0 ||  user.getPassword().length() == 0)
            blank_user = false;

        return blank_user;
    }

    public static boolean is_not_blank_appointment(Appointment appointment){
        boolean blank_appointment = true;

        if( appointment.getUser_name().length() == 0 ||
                appointment.getPhone().length() == 0 ||
                appointment.getReason().length() == 0 ||
                appointment.getTime().length() == 0 )
            blank_appointment = false;

        return blank_appointment;
    }
}
