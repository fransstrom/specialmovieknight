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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@RestController
public class GoogleRequests {

    @Autowired
    UserRepository userRepository;
    private final String CLIENT_ID = "892035413711-k2fuimcicp4rkrp36auu2qt56kirnl12.apps.googleusercontent.com";
    private final String CLIENT_SECRET = "1VC8GEWAqWJ_WDR5cz71wt54";

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public ResponseEntity<List<UnavailableDateTime2>> restGetEvents() throws ParseException {
        Iterable<User> userList = userRepository.findAll();

        String refreshToken;
        String accessToken;
        GoogleCredential credential;
        String newAccessToken = null;
        List<Event> eventsToReturn = new ArrayList<>();
        Calendar calendar;
        List<Event> items;
        List<UnavailableDateTime2> unavailableDates = new ArrayList<>();
        List<Date> available = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (User user : userList) {


            Date now = new Date(System.currentTimeMillis());
            Date expire = formatter.parse(user.getExpires());
            System.out.println("EXPIRES " + expire);
            refreshToken = user.getRefreshToken();
            accessToken = user.getAccessToken();

            if (expire.before(now)) {
                credential = getRefreshedCredentials(refreshToken);
                //new Expire DateTime
                Date expires = new Date(System.currentTimeMillis() + 3600 * 1000);
                Timestamp ts = new Timestamp(expires.getTime());
                //new accessToken
                newAccessToken = credential.getAccessToken();
                user.setExpires(formatter.format(ts));
                user.setAccessToken(newAccessToken);
                userRepository.save(user);
            } else {
                credential = new GoogleCredential().setAccessToken(accessToken);
            }


            if (credential != null) {
                calendar = getCalendar(credential);
                items = getEvents(calendar);
                UnavailableDateTime2 datesTest;

                for (int i = 0, itemsSize = items.size(); i < itemsSize; i++) {
                    datesTest = new UnavailableDateTime2();
                    Event event = items.get(i);
                    DateTime start = event.getStart().getDateTime();
                    DateTime end = event.getEnd().getDateTime();
                    if (start == null || end == null) {
                        start = new DateTime(event.getStart().getDate().toString() + "T00:00:00.000+01:00");
                        end = new DateTime(event.getEnd().getDate().toString() + "T00:00:00.000+01:00");
                        datesTest.setStartDate(start.toString());
                        datesTest.setEndDate(end.toString());
                        unavailableDates.add(datesTest);

                    } else {
                        eventsToReturn.add(event);
                        datesTest.setEndDate(end.toString());
                        datesTest.setStartDate(start.toString());
                        unavailableDates.add(datesTest);
                    }

                }

                Long newExpiresAt = System.currentTimeMillis() / 1000 + 3600; // timestamp in seconds


            }

            if (eventsToReturn == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }
        return new ResponseEntity<>(unavailableDates, HttpStatus.OK);
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


    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public void listUsers() {


    }


}
