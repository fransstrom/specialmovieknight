package com.movieknight.movieknight.Controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import com.movieknight.movieknight.Database.entities.UnavailableDateTime;
import com.movieknight.movieknight.Database.repositories.UnavalibleDateRepository;
import com.movieknight.movieknight.Database.entities.User;
import com.movieknight.movieknight.Database.repositories.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;


/*
@RestController
public class GoogleCalendarController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UnavalibleDateRepository unavalibleDateRepository;
    private final static Log logger = LogFactory.getLog(GoogleCalendarController.class);
    private static final String APPLICATION_NAME = "MovieNight";
    private static HttpTransport httpTransport;
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static com.google.api.services.calendar.Calendar client;

    GoogleClientSecrets clientSecrets;
    private GoogleAuthorizationCodeFlow flow;
    private Credential credential;

    @Value("${google.client.client-id}")
    private String clientId;
    @Value("${google.client.client-secret}")
    private String clientSecret;
    @Value("${google.client.redirectUri}")
    private String redirectURI;

    @Value("${google.common-calendarId}")
    private String commonCalendarId;

    private Set<Event> events = new HashSet<>();

    private final DateTime date1 = new DateTime(String.valueOf(LocalDateTime.now()));

    private final DateTime date2 = new DateTime("2019-12-28T16:30:00.000+05:30");

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    @RequestMapping(value = "/login/google", method = RequestMethod.GET)
    public RedirectView googleConnectionStatus(HttpServletRequest request) throws Exception {
        System.out.println(commonCalendarId);
        return new RedirectView(authorize());
    }



    @RequestMapping(value = "/login/google", method = RequestMethod.GET, params = "code")
    public ResponseEntity<String> oauth2Callback(@RequestParam(value = "code") String code) {
        Events eventList;
        String message;
        try {
            TokenResponse response = flow.newTokenRequest(code).setRedirectUri(redirectURI).execute();
            credential = flow.createAndStoreCredential(response, "userID");
            client = new com.google.api.services.calendar.Calendar.Builder(httpTransport, JSON_FACTORY, credential)
                    .setApplicationName(APPLICATION_NAME).build();

            Calendar.Events events = client.events();
            eventList = events.list("primary").setTimeMin(date1).setTimeMax(date2).execute();
            message = eventList.getItems().toString();
            List<Event> items = eventList.getItems();
*/
/*

            insertUnavailableDatesToDB(items);
            insertBusyDateTimeToCommonCalendar();

*//*


        } catch (Exception e) {
            logger.warn("Exception while handling OAuth2 callback (" + e.getMessage() + ")."
                    + " Redirecting to google connection status page.");
            message = "Exception while handling OAuth2 callback (" + e.getMessage() + ")."
                    + " Redirecting to google connection status page.";
        }

        System.out.println("cal message:" + message);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }
*/
/*

    private void insertUnavailableDatesToDB(List<Event> items) throws IOException {
        for (Event item : items) {
            if (item.getStart().getDateTime() != null) {
                DateTime startDateTime = item.getStart().getDateTime();
                DateTime endDateTime = item.getEnd().getDateTime();
                UnavailableDateTime unavailableDateTime = new UnavailableDateTime();

                unavailableDateTime.setStartDateTime(startDateTime.toString());
                System.out.println("title: " + item.getSummary());
                System.out.println("Start: " + startDateTime);
                System.out.println("End: " + endDateTime);
                unavailableDateTime.setEndDateTime(endDateTime.toString());
                unavailableDateTime.setId(item.getId());
                try {
                    unavalibleDateRepository.save(unavailableDateTime);
                } catch (Exception ignored) {

                }
            }
        }
        insertBusyDateTimeToCommonCalendar();
    }

    private void insertBusyDateTimeToCommonCalendar() throws IOException {
        //insert dates into common calendar

        Iterable<UnavailableDateTime> unavalibleDates = unavalibleDateRepository.findAll();

        for (UnavailableDateTime date : unavalibleDates) {
            try {
                Event event = new Event()
                        .setSummary("Busy");

                EventDateTime start = new EventDateTime()
                        .setDateTime(DateTime.parseRfc3339(date.getStartDateTime()))
                        .setTimeZone("Europe/Stockholm");
                event.setStart(start);

                EventDateTime end = new EventDateTime()
                        .setDateTime(DateTime.parseRfc3339(date.getEndDateTime()))
                        .setTimeZone("Europe/Stockholm");
                event.setEnd(end);
                event.setColorId("3");
                event.setId(date.getId());
                event = client.events().insert(commonCalendarId, event).execute();

                System.out.printf("Event created: %s\n", event.getHtmlLink());
            } catch (Exception e) {
                System.err.println("From inser to common calendar: "+e.getMessage());
            }
        }
    }

*//*


    public Set<Event> getEvents() throws IOException {
        return this.events;
    }

    private String authorize() throws Exception {
        AuthorizationCodeRequestUrl authorizationUrl;
        if (flow == null) {
            GoogleClientSecrets.Details web = new GoogleClientSecrets.Details();
            web.setClientId(clientId);
            web.setClientSecret(clientSecret);
            clientSecrets = new GoogleClientSecrets().setWeb(web);
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets,
                    Collections.singleton(CalendarScopes.CALENDAR)).build();
        }
        authorizationUrl = flow.newAuthorizationUrl().setRedirectUri(redirectURI);
        System.out.println("cal authorizationUrl->" + authorizationUrl);
        return authorizationUrl.build();
    }
}
*/
