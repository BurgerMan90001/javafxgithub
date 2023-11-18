package com.example.demo;

import org.apache.commons.codec.binary.Base64;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.pkce.AuthorizationCodePKCERefreshRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.pkce.AuthorizationCodePKCERequest;
import se.michaelthelin.spotify.requests.data.artists.GetArtistRequest;

import java.io.IOException;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;



public class SpotifyWebApi {
    private static final String clientId = "19fdd5c79e864b58aa166530b4a9feb4";
    private static final URI redirectUri = SpotifyHttpManager.makeUri("https://example.com/spotify-redirect");
    private final String codeVerifier = createRandomCodeVerifier();
    private final String codeChallenge = createCodeChallenge();

    private String code;
    // the id of an item to get
    private static final String id = "0LcJLqbBmaGUft1e9Mm8HV";



    private String createRandomCodeVerifier() {
        SecureRandom sr = new SecureRandom();
        byte[] code = new byte[32];
        sr.nextBytes(code);
        String verifier = Base64.encodeBase64URLSafeString(code);
        System.out.println("code verifier: "+verifier);
        return verifier;
    }
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
    // used to remove the last letter



    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setRedirectUri(redirectUri)
            .build();

    public void getArtist_Sync() {
        try {
            GetArtistRequest getArtistRequest = spotifyApi.getArtist(id).build();
            final Artist artist = getArtistRequest.execute();

            System.out.println("Name: " + artist.getName());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public boolean matchesRedirectURIPage(String url) {
        return url.contains(redirectUri.toString());
    }
    private final AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi
            .authorizationCodePKCEUri(codeChallenge)
            .build();

    public void getAccessToken() {
        try {
            AuthorizationCodePKCERequest authorizationCodePKCERequest = spotifyApi.authorizationCodePKCE(code, codeVerifier).build();
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodePKCERequest.execute();

            // Set access and refresh token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
            System.out.println("spotify accesstoken : " +spotifyApi.getAccessToken());

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void retriveNewAccessAndRefreshTokens() {
        try {
            AuthorizationCodePKCERefreshRequest PKCERefreshRequest = spotifyApi.authorizationCodePKCERefresh().build();
            final AuthorizationCodeCredentials authorizationCodeCredentials = PKCERefreshRequest.execute();

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
        System.out.println(code);

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