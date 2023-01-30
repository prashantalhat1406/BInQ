package com.sinprl.binq.dataclasses;

public class Fruits {
    String name;
    String information;

    public void setName(String name) {
        this.name = name;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getName() {
        return name;
    }

    public String getInformation() {
        return information;
    }

    public Fruits(String name, String information) {
        this.name = name;
        this.information = information;
    }
}
