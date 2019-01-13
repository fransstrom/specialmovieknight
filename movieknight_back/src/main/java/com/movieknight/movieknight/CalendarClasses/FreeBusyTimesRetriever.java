package com.movieknight.movieknight.CalendarClasses;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.java6.auth.oauth2.FileCredentialStore;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.Calendar.Freebusy;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.FreeBusyCalendar;
import com.google.api.services.calendar.model.FreeBusyRequest;
import com.google.api.services.calendar.model.FreeBusyRequestItem;
import com.google.api.services.calendar.model.FreeBusyResponse;
import com.google.api.services.calendar.model.TimePeriod;

/**
 * Retrieves the busy times from the Google Calendar API.
 */
public class FreeBusyTimesRetriever {

    /**
     * Be sure to specify the name of your application. If the application name
     * is {@code null} or blank, the application will log a warning. Suggested
     * format is "MyCompany-ProductName/1.0".
     */
    private static final String APPLICATION_NAME = "";

    /**
     * Global instance of the HTTP transport.
     */
    private static HttpTransport HTTP_TRANSPORT;

    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();

    private static Calendar service;

    public FreeBusyTimesRetriever(Calendar service) {
        this.service = service;
    }

    /**
     * Get busy times from the Calendar API.
     *
     * @param "attendees Attendees to retrieve busy times for.
     * @param "startDate Start date to retrieve busy times from.
     * @param "timeSpan  Number of days to retrieve busy times for.
     * @return Busy times for the selected attendees.
     * @throws "IOException
     */


    public List<TimeSlot> getBusyTimes(String attendee, Date startTime, Date endTime,
                                       String timeZone) throws IOException {

        List<TimePeriod> result = new ArrayList<TimePeriod>();

        List<FreeBusyRequestItem> requestItems = new ArrayList<FreeBusyRequestItem>();
        FreeBusyRequest request = new FreeBusyRequest();

        request.setTimeMin(getDateTime(startTime));
        request.setTimeMax(getDateTime(endTime));
        request.setTimeZone(timeZone);
        requestItems.add(new FreeBusyRequestItem().setId(attendee));
        request.setItems(requestItems);

        FreeBusyResponse busyTimes;
        try {
            Freebusy.Query query = service.freebusy().query(request);
            // Use partial GET to only retrieve needed fields.
            query.setFields("calendars");
            busyTimes = query.execute();

            for (Map.Entry<String, FreeBusyCalendar> busyCalendar : busyTimes
                    .getCalendars().entrySet()) {
                result = busyCalendar.getValue().getBusy();
            }

        } catch (IOException e) {
            System.out
                    .println("Exception occured while retrieving busy times: "
                            + e.toString());
            if (e instanceof HttpResponseException) {
                throw e;
            }
        }

        return convertToTimeSlot(result);
    }


    /**
     * Get free times from the Calendar API.
     *
     * @param "attendees Attendees to retrieve free times for.
     * @param "startDate Start date to retrieve free times from.
     * @param "timeSpan  Number of days to retrieve free times for.
     * @return Busy times for the selected attendees.
     * @throws IOException
     */


    public List<TimeSlot> getFreeTimes(String attendee, Date startTime, Date endTime,
                                       String timeZone) throws IOException {
        List<TimeSlot> busyTimes = getBusyTimes(attendee, startTime, endTime, timeZone);
        List<TimeSlot> freeTimes = new ArrayList<TimeSlot>();


        Date now = startTime;
        Date freeStart = null;
        Date freeEnd = null;

        for (TimeSlot busy : busyTimes) {

            if (now.before(busy.getStart())) {
                freeStart = now;
                freeEnd = busy.getStart();

                freeTimes.add(new TimeSlot(freeStart, freeEnd));

                now = busy.getEnd();
            } else {
                now = busy.getEnd();
            }
        }

        freeTimes.add(new TimeSlot(now, endTime));

        return freeTimes;
    }


    private List<TimeSlot> convertToTimeSlot(List<TimePeriod> timePeriods) {
        List<TimeSlot> timeSlots = new ArrayList<TimeSlot>();

        for (TimePeriod tp : timePeriods) {
            Date start = new Date(tp.getStart().getValue());
            Date end = new Date(tp.getEnd().getValue());

            timeSlots.add(new TimeSlot(start, end));
        }

        return timeSlots;
    }


    /**
     * Create a new DateTime object initialized at the current day +
     * {@code daysToAdd}.
     *
     * @param "startDate The date from which to compute the DateTime.
     * @param "daysToAdd The number of days to add to the result.
     * @return The new DateTime object initialized at the current day +
     * {@code daysToAdd}.
     ***/
    private DateTime getDateTime(Date date) {
        java.util.Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return new DateTime(cal.getTime().getTime(), 0);
    }

    public static Date getEndOfDay(Date date) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        c.set(java.util.Calendar.HOUR_OF_DAY, 23);
        c.set(java.util.Calendar.MINUTE, 59);
        c.set(java.util.Calendar.SECOND, 59);
        c.set(java.util.Calendar.MILLISECOND, 999);

        return c.getTime();
    }

   /* public static void main(String[] args) throws Exception {
        FreeBusyTimesRetriever service = new FreeBusyTimesRetriever();

        Date startTime = new Date();
        Date endTime = getEndOfDay(startTime);


        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(startTime);
        c.set(java.util.Calendar.HOUR_OF_DAY, 8);
        c.set(java.util.Calendar.MINUTE, 59);
        c.set(java.util.Calendar.SECOND, 59);
        c.set(java.util.Calendar.MILLISECOND, 999);
        startTime = c.getTime();

        // Busy Times
//		List<TimeSlot> busytimes = service.getBusyTimes("bestdayever.hackethon@gmail.com", startTime, endTime, "UTC-4:00");
//
//		for (TimeSlot busy : busytimes) {
//			System.out.println("Start: " + busy.getStart() + ", End: " + busy.getEnd());
//		}

        // Free Times
        List<TimeSlot> freetimes = service.getFreeTimes("bestdayever.hackethon@gmail.com", startTime, endTime, "UTC-4:00");

        for (TimeSlot free : freetimes) {
            System.out.println("Start: " + free.getStart() + ", End: " + free.getEnd());
        }

    }*/
}