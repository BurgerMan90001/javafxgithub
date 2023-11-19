package com.example.demo;

import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.miscellaneous.CurrentlyPlayingContext;
import se.michaelthelin.spotify.model_objects.miscellaneous.Device;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.User;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.data.artists.GetArtistRequest;
import se.michaelthelin.spotify.requests.data.player.AddItemToUsersPlaybackQueueRequest;
import se.michaelthelin.spotify.requests.data.player.GetInformationAboutUsersCurrentPlaybackRequest;
import se.michaelthelin.spotify.requests.data.player.GetUsersAvailableDevicesRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

import java.io.IOException;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;



public class SpotifyWebApi {
    private static final String clientId = "19fdd5c79e864b58aa166530b4a9feb4";
    private static final URI redirectUri = SpotifyHttpManager.makeUri("https://example.com/spotify-redirect");

    private final String clientSecret = "12fdc6d75c4d4cfeb0dadbea58b1cfb2";

    private String code = "";
    // the id of an item to get

    private final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setRedirectUri(redirectUri)
            .setClientSecret(clientSecret)
            .build();

    public void getAvailableDevice() {
        try {
            GetUsersAvailableDevicesRequest getUsersAvailableDevicesRequest = spotifyApi
                    .getUsersAvailableDevices()
                    .build();
            final Device[] devices = getUsersAvailableDevicesRequest.execute();

            System.out.println("Length: " + devices.length);
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void addTrackToPlaybackQueue(String trackUri) {
        try {
            final AddItemToUsersPlaybackQueueRequest addTrackToPlaybackQueueRequest = spotifyApi
                    .addItemToUsersPlaybackQueue(trackUri)
                    .build();
            addTrackToPlaybackQueueRequest.execute();
        } catch (IOException | ParseException | SpotifyWebApiException e) {
            System.out.println(e.getMessage());
        }

    }
    // gets artist
    public CurrentlyPlayingContext getCurrentPlayBack() {
        try {
            final GetInformationAboutUsersCurrentPlaybackRequest currentPlaybackRequest =
                    spotifyApi.getInformationAboutUsersCurrentPlayback().build();
            CurrentlyPlayingContext currentlyPlayingContext = currentPlaybackRequest.execute();

            System.out.println("Timestamp: " + currentlyPlayingContext.getTimestamp());
            return currentlyPlayingContext;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public Artist getArtist(String artistId) {
        try {
            GetArtistRequest getArtistRequest = spotifyApi.getArtist(artistId).build();
            return getArtistRequest.execute();

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getLocalizedMessage());
        }
        return null;
    }
    public User getCurrentUser() {
        try {
            GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = spotifyApi.getCurrentUsersProfile().build();
            return getCurrentUsersProfileRequest.execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getLocalizedMessage());
        }
        return null;
    }

    // checks if the person is on redirect uri and has signed in
    public boolean matchesRedirectURIPage(String url) {
        return url.contains(redirectUri.toString());
    }

    // step 1:
    public String getURIString() {
        final AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi
                .authorizationCodeUri()
       //         .authorizationCodePKCEUri(codeChallenge)
                .build();
        return authorizationCodeUriRequest.execute().toString();
    }
    // step 2:
    public void setToken() {
        try {
            AuthorizationCodeRequest request = spotifyApi.authorizationCode(code).build();
            final AuthorizationCodeCredentials authorizationCodeCredentials = request.execute();

            // Set access and refresh token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

            System.out.println("spotify accesstoken : " +spotifyApi.getAccessToken());
            System.out.println("spotify token successfully set!");

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    // step 3:
    public void refreshToken() {
        try {
            AuthorizationCodeRefreshRequest refreshRequest = spotifyApi.authorizationCodeRefresh().build();
            final AuthorizationCodeCredentials authorizationCodeCredentials = refreshRequest.execute();

            // Set access and refresh token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
            System.out.println("spotify token successfully refreshed!");
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // function that removes the first part of the url to get the code parameter
    public void getCodeFromUriString(String uri) {

        String partOfStringToReplace = redirectUri+"?code=";
        code = uri.replace(partOfStringToReplace,"");
        System.out.println(code);

    }
    // getters
    public String getAccessToken() {
        return spotifyApi.getAccessToken();
    }
    public URI getRedirectUri() {
        return redirectUri;
    }

    public SpotifyApi getSpotifyApi() {
        return spotifyApi;
    }
}

    /*
    private String createCodeChallenge() {
        // hashes the code verifier with the sha256 algorithm to create the code challenge
        try {
            byte[] bytes = codeVerifier.getBytes(StandardCharsets.US_ASCII);
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(bytes, 0, bytes.length);
            String challenge = Base64.encodeBase64URLSafeString(md.digest());
            System.out.println("code challenge: "+challenge);
            return challenge;
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
*/
/*
    private String createRandomCodeVerifier() {
        SecureRandom sr = new SecureRandom();
        byte[] code = new byte[32];
        sr.nextBytes(code);
        String verifier = Base64.encodeBase64URLSafeString(code);
        System.out.println("code verifier: "+verifier);
        return verifier;
    }

*/