package com.movieknight.movieknight.Controllers.RestControllers;

import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import com.movieknight.movieknight.Controllers.GoogleCalendarController;
import com.movieknight.movieknight.Database.entities.UnavailableDateTime;
import com.movieknight.movieknight.Database.entities.User;
import com.movieknight.movieknight.Database.repositories.UnavalibleDateRepository;
import com.movieknight.movieknight.Database.repositories.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileReader;
import java.io.IOException;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
public class GoogleAuthController {
    private static com.google.api.services.calendar.Calendar client;


    @Autowired
    UserRepository userRepository;
    @Autowired
    UnavalibleDateRepository unavalibleDateRepository;

    @Value("${google.common-calendarId}")
    private String commonCalendarId;

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/google", method = RequestMethod.POST)
    public void test(@RequestBody String code, @RequestHeader("X-Requested-With") String encoding) throws IOException {
        // (Receive authCode via HTTPS POST)
        if (encoding == null || encoding.isEmpty()) {
            // Without the `X-Requested-With` header, this request could be forged. Aborts.
            System.out.println("Error, wrong headers");
        }

// Set path to the Web application client_secret_*.json file you downloaded from the
// Google API Console: https://console.developers.google.com/apis/credentials
// You can also find your Web application client ID and client secret from the
// console and specify them directly when you create the GoogleAuthorizationCodeTokenRequest
// object.
        java.io.File CLIENT_SECRET_FILE = new ClassPathResource("client_secret_892035413711-k2fuimcicp4rkrp36auu2qt56kirnl12.apps.googleusercontent.com.json").getFile();

// Exchange auth code for access token
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(
                        JacksonFactory.getDefaultInstance(), new FileReader(CLIENT_SECRET_FILE));
        GoogleTokenResponse tokenResponse =
                new GoogleAuthorizationCodeTokenRequest(
                        new NetHttpTransport(),
                        JacksonFactory.getDefaultInstance(),
                        "https://www.googleapis.com/oauth2/v4/token",
                        clientSecrets.getDetails().getClientId(),
                        clientSecrets.getDetails().getClientSecret(),
                        code,
                        "http://localhost:3000")  // Specify the same redirect URI that you use with your web
                        // app. If you don't have a web version of your app, you can
                        // specify an empty string.
                        .execute();

        String accessToken = tokenResponse.getAccessToken();
        System.out.println("accessToken: " + accessToken);
        String refreshToken = tokenResponse.getRefreshToken();
        Long expiresAt = System.currentTimeMillis() + (tokenResponse.getExpiresInSeconds() * 1000);
        System.out.println("refreshToken: " + refreshToken);
        System.out.println("expiresAt: " + expiresAt);

// Use access token to call API
/*        GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);
        Drive drive =
                new Drive.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential)
                        .setApplicationName("Auth Code Exchange Demo")
                        .build();*/
//        File file = drive.files().get("appfolder").execute();

// Get profile info from ID token


        GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);
        Calendar calendar =
                new Calendar.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential)
                        .setApplicationName("Movie Nights")
                        .build();

        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = null;
        try {
            events = calendar.events().list("primary")
                    .setMaxResults(10)
                    .setTimeMin(now)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Event> items = events.getItems();
        if (items.isEmpty()) {
            System.out.println("No upcoming events found.");
        } else {
            System.out.println("Upcoming events");
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) { // If it's an all-day-event - store the date instead
                    start = event.getStart().getDate();
                }
                DateTime end = event.getEnd().getDateTime();
                if (end == null) { // If it's an all-day-event - store the date instead
                    end = event.getStart().getDate();
                }
                System.out.printf("%s (%s) -> (%s)\n", event.getSummary(), start, end);
            }
            insertUnavailableDatesToDB(items);
        }

        GoogleIdToken idToken = tokenResponse.parseIdToken();
        GoogleIdToken.Payload payload = idToken.getPayload();
        System.out.println(payload);
        String userId = payload.getSubject();  // Use this value as a key to identify a user.
        String email = payload.getEmail();
        boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
        String name = (String) payload.get("name");
        String pictureUrl = (String) payload.get("picture");
        String locale = (String) payload.get("locale");
        String familyName = (String) payload.get("family_name");
        String givenName = (String) payload.get("given_name");
/*        System.out.println(givenName + " " + familyName);
        System.out.println();*/

        Date expire = new Date(System.currentTimeMillis() + 3600 * 1000);
        Timestamp ts=new Timestamp(expire.getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(formatter.format(ts));
        System.out.println("Expiresesese"+expire);

        User user = new User();
        user.setRefreshToken(refreshToken);
        user.setAccessToken(accessToken);
        user.setId(userId);
        user.setExpires(formatter.format(ts));

        userRepository.save(user);
        insertUnavailableDatesToDB(items);
        insertBusyDateTimeToCommonCalendar(calendar);
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/getdates", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<UnavailableDateTime>> dates() throws ParseException {
        ArrayList<UnavailableDateTime> dbDates = (ArrayList<UnavailableDateTime>) unavalibleDateRepository.findAll();
        ArrayList<UnavailableDateTime> dates = new ArrayList<>();
        String startDateTime;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

        for (int i = 0; i < dbDates.size(); i++) {
            startDateTime = dbDates.get(i).getStartDateTime().substring(0, 16);
            if (simpleDateFormat.parse(startDateTime).before(new Date())) {
                System.out.println("Date has passed");
            } else {
                System.out.println("Future date");
                dates.add(dbDates.get(i));
            }
        }

        if (dates.size() <= 0) {
            return new ResponseEntity<>(dates, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(dates, HttpStatus.OK);
        }

    }


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

    }

    private void insertBusyDateTimeToCommonCalendar( Calendar calendar) throws IOException {
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
                event = calendar.events().insert(commonCalendarId, event).execute();

                System.out.printf("Event created: %s\n", event.getHtmlLink());
            } catch (Exception e) {
                System.err.println("From insert to common calendar: "+e.getMessage());
            }
        }
    }

}
