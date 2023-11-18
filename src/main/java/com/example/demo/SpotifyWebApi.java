package com.example.demo;

import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.pkce.AuthorizationCodePKCERefreshRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.pkce.AuthorizationCodePKCERequest;

import java.io.IOException;
import java.net.URI;


public class SpotifyWebApi {
    private static final String clientId = "19fdd5c79e864b58aa166530b4a9feb4";
    private static final URI redirectUri = SpotifyHttpManager.makeUri("https://example.com/spotify-redirect");
    private static final String codeChallenge = "m6iZIj99vHGtEx_NVl9u3sthTN646vvkiP8OMCGfPmo";
    private static final String codeVerifier = "NlJx4kD4opk4HY7zBM6WfUHxX7HoF8A2TUhOIPGA74w";
    private String code;


    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setRedirectUri(redirectUri)
            .build();


    public boolean matchesRedirectURIPage(String url) {
        return url.contains(redirectUri.toString());
    }
    private static final AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi
            .authorizationCodePKCEUri(codeChallenge)
            .build();

    public void getAccessToken() {
        try {
            AuthorizationCodePKCERequest authorizationCodePKCERequest = spotifyApi.authorizationCodePKCE(code, codeVerifier).build();
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodePKCERequest.execute();

            // Set access and refresh token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void retriveNewAccessAndRefreshTokens() {
        try {
            AuthorizationCodePKCERefreshRequest authorizationCodePKCERefreshRequest = spotifyApi.authorizationCodePKCERefresh()
                    .build();
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodePKCERefreshRequest.execute();

            // Set access and refresh token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void setCodeString(String uri) {

        String partOfStringToReplace = redirectUri+"?code=";
        code = uri.replace(partOfStringToReplace,"");

    }

    public String getURIString() {
        final URI uri = authorizationCodeUriRequest.execute();

      //  System.out.println("URI: " + uri.toString());
        return uri.toString();
    }
    public URI getRedirectUri() {
        return redirectUri;
    }
}