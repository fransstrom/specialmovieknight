package com.movieknight.movieknight.Database;

import java.util.Date;
import java.util.List;


public class UnavailableDatesTest {


    List<Date> unavailableDates;
    Date startDate;
    Date endDate;


    public UnavailableDatesTest(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public UnavailableDatesTest() {
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return endDate;
    }


    public int getEndDay() {
        return endDate.getDay();
    }

    public int getEndMonth() {
        return endDate.getMonth();
    }

    public int getEndHours() {
        return endDate.getHours();
    }


    public int getStartDay() {
        return startDate.getDay();
    }

    public int getStartMonth() {
        return startDate.getMonth();
    }

    public int getStartHours() {
        return startDate.getHours();
    }

    public long getStartTime() {
        return startDate.getTime();
    }


    public int getEndTimeStamp() {
        int time;
        int hours = endDate.getHours();
        int min = endDate.getMinutes();
        String temp;
        if (hours < 10) {
            temp = "0" + Integer.toString(hours);
        } else
            temp = Integer.toString(hours);
        if (min < 10) {
            temp = temp + "0" + Integer.toString(min);
        } else
            temp = temp + Integer.toString(min);

        time = Integer.parseInt(temp);
        return time;
    }

    public long getEndTime() {
        return endDate.getTime();
    }


}
