package com.team1458.turtleshell2.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * HTTP Request Utilities
 *
 * @author asinghani
 */
public class HttpRequest {

    public static String get(URL url) throws IOException {
        StringBuilder result = new StringBuilder();
        HttpURLConnection c = (HttpURLConnection) url.openConnection();
        c.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(c.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }

        reader.close();
        return result.toString();
    }

    public static String get(String url) throws IOException {
        return get(new URL(url));
    }
}
