package com.movieknight.movieknight.CalendarClasses;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;


class DateTimeIntervalWeekTest {
    private DateTimeInterval dateTimeIntervalWeek=new DateTimeInterval();

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