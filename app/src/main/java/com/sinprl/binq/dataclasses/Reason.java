package com.sinprl.binq.dataclasses;

public class Reason {

    String reason_text;

    public Reason() {
    }

    public Reason(String reason_text) {
        this.reason_text = reason_text;
    }

    public String getReason_text() {
        return reason_text;
    }

    public void setReason_text(String reason_text) {
        this.reason_text = reason_text;
    }
}
