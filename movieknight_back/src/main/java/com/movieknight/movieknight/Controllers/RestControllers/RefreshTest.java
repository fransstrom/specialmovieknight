package com.movieknight.movieknight.Controllers.RestControllers;

import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.movieknight.movieknight.Database.UnavailableDatesTest;
import com.movieknight.movieknight.Database.entities.UnavailableDateTime2;
import com.movieknight.movieknight.Database.entities.User;
import com.movieknight.movieknight.Database.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@RestController
public class RefreshTest {

    @Autowired
    UserRepository userRepository;

    private final String CLIENT_ID = "892035413711-k2fuimcicp4rkrp36auu2qt56kirnl12.apps.googleusercontent.com";
    private final String CLIENT_SECRET = "1VC8GEWAqWJ_WDR5cz71wt54";


    public static <T> T firstNonNull(T... params) {
        for (T param : params)
            if (param != null)
                return param;
        return null;
    }


    private GoogleCredential getRefreshedCredentials(String refreshCode) {
        try {
            GoogleTokenResponse response = new GoogleRefreshTokenRequest(
                    new NetHttpTransport(),
                    JacksonFactory.getDefaultInstance(),
                    refreshCode,
                    CLIENT_ID,
                    CLIENT_SECRET)
                    .execute();

            return new GoogleCredential().setAccessToken(response.getAccessToken());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private Calendar getCalendar(GoogleCredential credential) {
        return new Calendar.Builder(
                new NetHttpTransport(),
                JacksonFactory.getDefaultInstance(),
                credential)
                .setApplicationName("Movie Nights")
                .build();
    }

    private List<Event> getEvents(Calendar calendar) {
        return getEvents(calendar, 10);
    }

    private List<Event> getEvents(Calendar calendar, int maxNumResults) {
        try {
            Events events = calendar.events().list("primary")
                    .setTimeMin(new DateTime(System.currentTimeMillis()))
                    .setMaxResults(maxNumResults)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();

            List<Event> items = events.getItems();
            if (items.isEmpty()) {
                System.out.println("No upcoming events found.");
            } else {
                //  System.out.println("Upcoming events");
                for (Event event : items) {
                    DateTime start = event.getStart().getDateTime();
                    if (start == null) {
                        start = event.getStart().getDate();
                    }
                    // System.out.printf("%s (%s)\n", event.getSummary(), start);
                }
            }

            return items;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public ResponseEntity<List<UnavailableDatesTest>> restGetEvents() {
        String refreshToken;
        Iterable<User> userList = userRepository.findAll();
        GoogleCredential credential;
        String newAccessToken = null;
        List<Event> eventsToReturn = new ArrayList<>();
        Calendar calendar;
        List<Event> items;

        List<UnavailableDatesTest> unavailableDates = new ArrayList<>();
        List<Date> available = new ArrayList<>();
        for (User user : userList) {

            refreshToken = user.getRefreshToken();
            credential = getRefreshedCredentials(refreshToken);

            if (credential != null) {
                //new Expire DateTime
                Date expires = new Date(System.currentTimeMillis() + 3600 * 1000);
                Timestamp ts = new Timestamp(expires.getTime());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                //new accessToken
                newAccessToken = credential.getAccessToken();
                user.setExpires(formatter.format(ts));
                user.setAccessToken(newAccessToken);
                userRepository.save(user);
                calendar = getCalendar(credential);
                items = getEvents(calendar);

                UnavailableDatesTest datesTest;

                for (int i = 0, itemsSize = items.size(); i < itemsSize; i++) {
                    Event event = items.get(i);
                    DateTime start = firstNonNull(event.getStart().getDateTime(), event.getStart().getDate());
                    DateTime end = firstNonNull(event.getEnd().getDateTime(), event.getStart().getDate());
                    //   System.out.printf("%s %s (%s) - (%s)\n", "REGRESCHTEST", event.getSummary(), start, end);

                    eventsToReturn.add(event);
                    datesTest = new UnavailableDatesTest();
                    datesTest.setEndDate(new Date(end.getValue()));
                    datesTest.setStartDate(new Date(start.getValue()));
                    unavailableDates.add(datesTest);

                }
            } else {
                System.out.println("invalid refreshtoken for use with userID: " + user.getId());
            }


            Long newExpiresAt = System.currentTimeMillis() / 1000 + 3600; // timestamp in seconds



        }

        if (eventsToReturn == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(unavailableDates, HttpStatus.OK);
    }


    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public void listUsers() {


    }



}
