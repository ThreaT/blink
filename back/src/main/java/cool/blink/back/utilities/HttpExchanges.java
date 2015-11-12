package cool.blink.back.utilities;

import com.sun.net.httpserver.HttpExchange;
import cool.blink.back.core.Http;
import cool.blink.back.core.Http.Protocol;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;
import org.apache.commons.io.IOUtils;

public class HttpExchanges {

    public static final synchronized String getAbsoluteUrl(final HttpExchange httpExchange) {
        Protocol protocol = getHttpExchangeProtocol(httpExchange);
        String hostname = getHttpExchangeHostName(httpExchange);
        Integer port = getHttpExchangePort(httpExchange);
        String query = getHttpExchangeQueryString(httpExchange);
        String path = getHttpExchangePath(httpExchange);
        return protocol.toString() + hostname + ":" + port + path + query;
    }

    public static final synchronized Protocol getHttpExchangeProtocol(final HttpExchange httpExchange) {
        if (httpExchange.getProtocol().equals("HTTP/1.1")) {
            return Http.Protocol.HTTP;
        }
        return null;
    }

    public static final synchronized String getHttpExchangeHostName(final HttpExchange httpExchange) {
        return httpExchange.getRequestHeaders().get("Host").get(0).replaceAll("[\\:]{1}[\\d]+", "");
    }

    public static final synchronized Integer getHttpExchangePort(final HttpExchange httpExchange) {
        return httpExchange.getLocalAddress().getPort();
    }

    public static final synchronized String getHttpExchangePath(final HttpExchange httpExchange) {
        String requestUrl = httpExchange.getRequestURI().toString();
        String url;
        if (requestUrl.contains("?")) {
            url = requestUrl.substring(0, requestUrl.indexOf("?"));
        } else {
            url = requestUrl;
        }
        return url;
    }

    public static final synchronized String getHttpExchangeQueryString(final HttpExchange httpExchange) {
        String queryString = "";
        if (httpExchange.getRequestURI().getQuery() != null) {
            queryString = httpExchange.getRequestURI().getQuery();
        }
        if (queryString.length() >= 4) {
            queryString = "?" + queryString;
        }
        return queryString;
    }

    public static final synchronized HashMap<String, String> getHttpExchangeParameters(final HttpExchange httpExchange) throws IOException {
        HashMap<String, String> hashMap = new HashMap<>();
        String requestBody = IOUtils.toString(httpExchange.getRequestBody(), "UTF-8");
        String requestQuery = httpExchange.getRequestURI().getQuery();
        if ((requestBody != null) && (!requestBody.equals(""))) {
            StringTokenizer stringTokenizer = new StringTokenizer(requestBody, "&");
            while (stringTokenizer.hasMoreTokens()) {
                String currentToken = stringTokenizer.nextToken();
                hashMap.put(currentToken.substring(0, currentToken.indexOf("=")).toLowerCase(), currentToken.substring(currentToken.indexOf("=") + 1, currentToken.length()));
            }
        }
        if ((requestQuery != null) && (!requestQuery.equals(""))) {
            StringTokenizer stringTokenizer = new StringTokenizer(requestQuery, "&");
            while (stringTokenizer.hasMoreTokens()) {
                String currentToken = stringTokenizer.nextToken();
                hashMap.put(currentToken.substring(0, currentToken.indexOf("=")).toLowerCase(), currentToken.substring(currentToken.indexOf("=") + 1, currentToken.length()));
            }
        }
        return hashMap;
    }

    public static final synchronized Http.Method getHttpExchangeMethod(final HttpExchange httpExchange) {
        switch (httpExchange.getRequestMethod().toUpperCase()) {
            case "GET":
                return Http.Method.GET;
            case "POST":
                return Http.Method.POST;
            case "PUT":
                return Http.Method.PUT;
            case "DELETE":
                return Http.Method.DELETE;
            default:
                return Http.Method.GET;
        }
    }

}
