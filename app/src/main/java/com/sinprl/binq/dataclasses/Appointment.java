package com.sinprl.binq.dataclasses;

public class Appointment {
    String token;
    String user_name;
    String time;
    String reason;
    String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Appointment() {
    }

    public Appointment(String token, String user_name, String time, String reason, String phone) {
        this.token = token;
        this.user_name = user_name;
        this.time = time;
        this.reason = reason;
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean is_not_blank_appointment(){
        boolean blank_appointment = true;

        if(this.user_name.length() == 0 || this.reason.length() == 0 || this.time.length() == 0 || this.phone.length() == 0)
            blank_appointment = false;

        return blank_appointment;
    }

}
