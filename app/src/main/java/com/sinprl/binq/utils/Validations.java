package com.sinprl.binq.utils;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sinprl.binq.dataclasses.Appointment;
import com.sinprl.binq.dataclasses.User;

import java.util.List;

public class Validations {

    boolean valid;
    String message;

    public Validations() {
    }

    public Validations(boolean is_valid, String message) {
        this.valid = is_valid;
        this.message = message;
    }



    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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

    public static Validations is_not_blank_user(User user){

        Validations validations = new Validations(true, "Valid User");

        if (user.getPassword().trim().length() < 4){
            validations.setValid(false);
            validations.setMessage("Password should be 4 digit !!");
        }

        if (user.getPhone().trim().length() < 10){
            validations.setValid(false);
            validations.setMessage("Phone should be 10 digit !!");
        }

        if (user.getName().trim().length() ==0){
            validations.setValid(false);
            validations.setMessage("Blank UserName !!");
        }
        return validations;
    }

    public static Validations is_valid_user(List<User> all_users, User user) {

        user.setName("Temp");
        Validations validations = is_not_blank_user(user);

        if(validations.isValid()){
            validations.setValid(false);
            validations.setMessage("");
            for (User dbuser: all_users) {
                if(user.getPhone().equals(dbuser.getPhone()) ){
                    if(user.getPassword().equals(dbuser.getPassword())){
                        validations.setValid(true);
                        validations.setMessage("Valid User");
                        break;
                    }else {
                        validations.setValid(false);
                        validations.setMessage("Invalid Password !!");
                        break;
                    }
                }
            }
            if (!validations.isValid()){
                if (validations.getMessage().length() == 0)
                    validations.setMessage("User does not exists !!");
            }
        }

        return validations;
    }

    public static boolean is_not_blank_appointment(Appointment appointment){
        boolean blank_appointment = true;

        if( appointment.getUser_name().trim().length() == 0 ||
                appointment.getPhone().trim().length() == 0 ||
                appointment.getReason().trim().length() == 0 ||
                appointment.getTime().trim().length() == 0 )
            blank_appointment = false;

        return blank_appointment;
    }
}
