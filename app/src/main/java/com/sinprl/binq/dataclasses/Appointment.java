package com.sinprl.binq.dataclasses;

public class Appointment {
    String token;
    String user_name;
    String time;

    String date_of_appointment;
    String reason;
    String phone;

    String id;

    String userID;
    int active;


    public String getDate_of_appointment() {
        return date_of_appointment;
    }

    public void setDate_of_appointment(String date_of_appointment) {
        this.date_of_appointment = date_of_appointment;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Appointment() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Appointment(String token, String user_name, String time, String reason, String phone, int active) {
        this.token = token;
        this.user_name = user_name;
        this.time = time;
        this.reason = reason;
        this.phone = phone;
        this.active = active;
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

}
