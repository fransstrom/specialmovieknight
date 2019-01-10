package com.movieknight.movieknight.Database.entities;

import com.google.api.client.util.DateTime;

public class UnavailableDateTime2 {
    String startDate;
    String endDate;

    public UnavailableDateTime2(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public UnavailableDateTime2() {
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
