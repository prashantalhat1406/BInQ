package com.sinprl.binq.dataclasses;

public class TimeSlots {
    String timeslot;
    Boolean available;

    int no_of_appointments;

    public int getNo_of_appointments() {
        return no_of_appointments;
    }

    public void setNo_of_appointments(int no_of_appointments) {
        this.no_of_appointments = no_of_appointments;
    }

    public TimeSlots(String timeslot, Boolean available, int no_of_appointments) {
        this.timeslot = timeslot;
        this.available = available;
        this.no_of_appointments = no_of_appointments;
    }

    public TimeSlots() {
    }

    public TimeSlots(String timeslot, Boolean available) {
        this.timeslot = timeslot;
        this.available = available;
    }

    public String getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(String timeslot) {
        this.timeslot = timeslot;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
