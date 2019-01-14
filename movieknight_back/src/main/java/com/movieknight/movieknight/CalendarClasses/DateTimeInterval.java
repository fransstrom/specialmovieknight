package com.movieknight.movieknight.CalendarClasses;


import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.List;

public class DateTimeInterval {

    List<Interval> bookingIntervals = new ArrayList<>();
    List<Interval> unavailableIntervals = new ArrayList<>();

    public DateTimeInterval() {
        for (int i = 0; i < 10; i++) {
            DateTime dateTimeStart1 = new DateTime().plusDays(i).withTime(18, 0, 0, 0);
            DateTime dateTimeEnd1 = new DateTime().plusDays(i).withTime(20, 0, 0, 0);

            DateTime dateTimeStart2 = new DateTime().plusDays(i).withTime(21, 0, 0, 0);
            DateTime dateTimeEnd2 = new DateTime().plusDays(i).withTime(23, 0, 0, 0);

            bookingIntervals.add(new Interval(dateTimeStart2, dateTimeEnd2));
            bookingIntervals.add(new Interval(dateTimeStart1, dateTimeEnd1));
        }
    }


    public List<Interval> getValidInterVals() {
        List<Interval> validInterval = bookingIntervals;
        for (int i = 0; i < unavailableIntervals.size(); i++) {
            for (int j = 0; j < bookingIntervals.size(); j++) {
              if(unavailableIntervals.get(i).overlaps(validInterval.get(j))){
                  validInterval.remove(j);
              }
            }
        }
        return validInterval;
    }


    public List<Interval> getBookingIntervals() {
        return bookingIntervals;
    }

    public void setBookingIntervals(List<Interval> bookingIntervals) {
        this.bookingIntervals = bookingIntervals;
    }

    public void addToUnavailableIntervals(String start, String end) {
        unavailableIntervals.add(new Interval(new DateTime(start), new DateTime(end)));
    }

    public List<Interval> getUnavailableIntervals() {
        return unavailableIntervals;
    }


}
