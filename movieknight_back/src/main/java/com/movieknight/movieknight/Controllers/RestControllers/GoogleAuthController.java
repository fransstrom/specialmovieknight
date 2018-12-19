package com.movieknight.movieknight.Controllers.RestControllers;

import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


@RestController
public class GoogleAuthController {

    @CrossOrigin(origins="http://localhost:3000")
    @RequestMapping(value="/google", method = RequestMethod.POST)
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
        java.io.File CLIENT_SECRET_FILE =  new ClassPathResource("client_secret_892035413711-k2fuimcicp4rkrp36auu2qt56kirnl12.apps.googleusercontent.com.json").getFile();;

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
        GoogleIdToken idToken = tokenResponse.parseIdToken();
        GoogleIdToken.Payload payload = idToken.getPayload();
        String userId = payload.getSubject();  // Use this value as a key to identify a user.
        String email = payload.getEmail();
        boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
        String name = (String) payload.get("name");
        String pictureUrl = (String) payload.get("picture");
        String locale = (String) payload.get("locale");
        String familyName = (String) payload.get("family_name");
        String givenName = (String) payload.get("given_name");
        System.out.println(givenName+" "+familyName);
    }

}