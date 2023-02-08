package com.sinprl.binq.utils;

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
}
