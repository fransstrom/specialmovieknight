package com.movieknight.movieknight.Database.entities;

import com.google.api.client.util.DateTime;

public class UnavailableDateTime2 {
    String start;
    String end;

    public UnavailableDateTime2(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public UnavailableDateTime2() {
        this.start = start;
        this.end = end;
    }


    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
