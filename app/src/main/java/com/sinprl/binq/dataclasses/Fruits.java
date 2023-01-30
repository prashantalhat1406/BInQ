package com.sinprl.binq.dataclasses;

public class Fruits {
    String Name;
    String Info;

    public Fruits() {
    }

    public Fruits(String Name, String Info) {
        this.Name = Name;
        this.Info = Info;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String Info) {
        this.Info = Info;
    }
}
