package com.sinprl.binq.dataclasses;

public class TimeSlots {
    String timeslot;
    Boolean available;

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
