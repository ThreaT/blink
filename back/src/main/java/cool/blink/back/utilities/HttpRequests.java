package cool.blink.back.utilities;

import cool.blink.back.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequests {

    public static final synchronized Response sendGet(final String url) {
        Response response = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL obj = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();
            httpURLConnection.setRequestMethod("GET");
            int responseCode = httpURLConnection.getResponseCode();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }
            } catch (IOException ex) {

            }
            response = new Response(responseCode, stringBuilder.toString());
        } catch (IOException ex) {

        }
        if (response == null) {
            response = new Response(404, "");
        }
        return response;
    }

    public static final synchronized Response sendGet(final String url, final Integer timeout) {
        Response response = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL obj = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();
            httpURLConnection.setReadTimeout(timeout);
            httpURLConnection.setRequestMethod("GET");
            int responseCode = httpURLConnection.getResponseCode();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }
            } catch (IOException ex) {

            }
            response = new Response(responseCode, stringBuilder.toString());
        } catch (IOException ex) {

        } catch (java.lang.IllegalArgumentException ex) {

        } catch (java.lang.RuntimeException ex) {

        }
        if (response == null) {
            response = new Response(404, "");
        }
        return response;
    }

    public static final synchronized Response sendPost(final String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();
        httpURLConnection.setRequestMethod("POST");
        int responseCode = httpURLConnection.getResponseCode();
        StringBuilder response;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))) {
            String inputLine;
            response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        return new Response(responseCode, response.toString());

    }

    public static final synchronized Response sendPost(final String url, final Integer timeout) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();
        httpURLConnection.setReadTimeout(timeout);
        httpURLConnection.setRequestMethod("POST");
        int responseCode = httpURLConnection.getResponseCode();
        StringBuilder response;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))) {
            String inputLine;
            response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        return new Response(responseCode, response.toString());

    }
}
