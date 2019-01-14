package com.movieknight.movieknight.CalendarClasses;

import com.movieknight.movieknight.Database.entities.UnavailableDateTime2;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;


class DateTimeIntervalWeekTest {
    private DateTimeIntervalWeek dateTimeIntervalWeek=new DateTimeIntervalWeek();
    private List<UnavailableDateTime2> unavailableDateTime2List=new ArrayList<>();

    @Test
    void setUp() {

        System.out.println(dateTimeIntervalWeek.getBookingIntervals());

    }

    @Test
    void getBookingDate() {


    }

    @Test
    void setBookingDate() {


    }

    @Test
    void getValidIntervals() {

        System.out.println(dateTimeIntervalWeek.getBookingIntervals().get(1));


    }
}