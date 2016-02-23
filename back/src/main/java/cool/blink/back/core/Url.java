package cool.blink.back.core;

import cool.blink.back.core.Http.Protocol;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public final class Url implements Serializable {

    private Protocol protocol;
    private String hostname;
    private Integer port;
    private String path;
    private String query;
    private Boolean includeTrailing;
    private String absoluteUrl;

    public Url(final String url) throws MalformedURLException {
        URL tempUrl = new URL(url);
        this.protocol = Http.getProtocolEnum(tempUrl.getProtocol());
        this.hostname = tempUrl.getHost();
        this.port = tempUrl.getPort();
        this.path = (tempUrl.getPath().equals("") ? "/" : tempUrl.getPath());
        this.query = tempUrl.getQuery();
        this.includeTrailing = false;
        this.absoluteUrl = removeExcessForwardSlashes(tempUrl.toString());
    }

    public Url(final String url, final Boolean includeTrailing) throws MalformedURLException {
        URL tempUrl = new URL(url);
        this.protocol = Http.getProtocolEnum(tempUrl.getProtocol());
        this.hostname = tempUrl.getHost();
        this.port = tempUrl.getPort();
        this.path = (tempUrl.getPath().equals("") ? "/" : tempUrl.getPath());
        this.query = tempUrl.getQuery();
        this.includeTrailing = includeTrailing;
        this.absoluteUrl = removeExcessForwardSlashes(tempUrl.toString());
    }

    public Url(final Protocol protocol, final String hostname, final Integer port, final String path, final String query, final Boolean includeTrailing) throws MalformedURLException {
        this.protocol = protocol;
        this.hostname = hostname;
        this.hostname = hostname;
        this.port = port;
        this.path = path;
        this.query = query;
        this.includeTrailing = includeTrailing;
        this.absoluteUrl = removeExcessForwardSlashes(protocol + hostname + ":" + port + "/" + path + ((query == null || query.isEmpty()) ? "" : "?" + query));
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * The relative url that the request is tied to.
     *
     * @return relativeUrl i.e. /foo, /user, /employee, /patient, /parent, etc.
     */
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * @return true if URL should serve all trailing paths, i.e.
     * http://example.com/path/* will also serve http://example.com/path/etc
     */
    public Boolean getIncludeTrailing() {
        return includeTrailing;
    }

    public void setIncludeTrailing(Boolean includeTrailing) {
        this.includeTrailing = includeTrailing;
    }

    /**
     * The absolute url that the request is tied to.
     *
     * @return absoluteUrl i.e. http://www.example.com/foo
     */
    public String getAbsoluteUrl() {
        return absoluteUrl;
    }

    public void setAbsoluteUrl(String absoluteUrl) {
        this.absoluteUrl = absoluteUrl;
    }

    public static final synchronized Boolean containAllAbsoluteUrls(final List<Url> list1, final List<Url> list2) {
        if (list1.size() != list2.size()) {
            return false;
        }

        Boolean url1ExistsInList2;
        for (Url url1 : list1) {
            url1ExistsInList2 = false;
            for (Url url2 : list2) {
                if (url1.getAbsoluteUrl().equalsIgnoreCase(url2.getAbsoluteUrl())) {
                    url1ExistsInList2 = true;
                    break;
                }
            }
            if (!url1ExistsInList2) {
                return false;
            }
        }

        Boolean url2ExistsInList1;
        for (Url url2 : list2) {
            url2ExistsInList1 = false;
            for (Url url1 : list1) {
                if (url2.getAbsoluteUrl().equalsIgnoreCase(url1.getAbsoluteUrl())) {
                    url2ExistsInList1 = true;
                    break;
                }
            }
            if (!url2ExistsInList1) {
                return false;
            }
        }
        return true;
    }

    public static final synchronized Boolean hasMatchingAbsoluteUrls(final Url clientUrl, final List<Url> serverUrls) {
        for (Url serverUrl : serverUrls) {
            if (hasMatchingAbsoluteUrls(serverUrl, clientUrl)) {
                return true;
            }
        }
        return false;
    }

    public static final synchronized Boolean hasMatchingAbsoluteUrls(final Url server, final Url client) {
        if (hasQueryString(server)) {
            return ((hasMatchingAbsoluteUrlsWithoutQueryString(server, client)) && (hasMatchingParameters(server, client)));
        } else {
            return hasMatchingAbsoluteUrlsWithoutQueryString(server, client);
        }
    }

    public static final synchronized Boolean hasMatchingAbsoluteUrlsWithoutQueryString(final Url clientUrl, final List<Url> serverUrls) {
        for (Url serverUrl : serverUrls) {
            if (hasMatchingAbsoluteUrlsWithoutQueryString(serverUrl, clientUrl)) {
                return true;
            }
        }
        return false;
    }

    public static final synchronized Boolean hasMatchingAbsoluteUrlsWithoutQueryString(Url server, Url client) {
        server = removeQueryString(server);
        client = removeQueryString(client);
        if (server.getAbsoluteUrl().length() > client.getAbsoluteUrl().length()) {
            return false;
        }
        if (server.getIncludeTrailing()) {
            return client.getAbsoluteUrl().substring(0, server.getAbsoluteUrl().length()).equalsIgnoreCase(server.getAbsoluteUrl());
        } else {
            return removeTrailingForwardSlash(server.getAbsoluteUrl()).equalsIgnoreCase(removeTrailingForwardSlash(client.getAbsoluteUrl()));
        }
    }

    public static final synchronized Boolean hasMatchingParameters(final String url1, final String url2) throws MalformedURLException {
        return hasMatchingParameters(new Url(url1), new Url(url2));
    }

    public static final synchronized Boolean hasMatchingParameters(final Url url1, final Url url2) {
        Boolean containsAll = true;
        Map map1 = queryStringToHashMap(url1.getQuery());
        Map map2 = queryStringToHashMap(url2.getQuery());
        if (map1.size() != map2.size()) {
            containsAll = false;
        }
        outer:
        for (Object map1Object : map1.entrySet()) {
            Map.Entry<String, String> map1Entry = (Map.Entry<String, String>) map1Object;
            Boolean containsThis = false;
            inner:
            for (Object map2Object : map2.entrySet()) {
                Map.Entry<String, String> map2Entry = (Map.Entry<String, String>) map2Object;
                if ((map1Entry.getKey().equalsIgnoreCase(map2Entry.getKey())) && (map1Entry.getValue().equalsIgnoreCase(map2Entry.getValue()))) {
                    containsThis = true;
                    break;
                }
            }
            if (!containsThis) {
                containsAll = false;
                break;
            }
        }
        return containsAll;
    }

    public static final synchronized HashMap<String, String> queryStringToHashMap(final String queryString) {
        HashMap<String, String> hashMap = new HashMap<>();
        if ((queryString != null) && (!queryString.equals(""))) {
            StringTokenizer stringTokenizer = new StringTokenizer(queryString, "&");
            while (stringTokenizer.hasMoreTokens()) {
                String currentToken = stringTokenizer.nextToken();
                hashMap.put(currentToken.substring(0, currentToken.indexOf("=")).toLowerCase(), currentToken.substring(currentToken.indexOf("=") + 1, currentToken.length()).toLowerCase());
            }
        }
        return hashMap;
    }

    public static final synchronized String removeTrailingForwardSlash(final String url) {
        String completeUrl = url;
        if (url.charAt(url.length() - 1) == '/') {
            completeUrl = url.substring(0, url.length() - 1);
        }
        return completeUrl;
    }

    public static final synchronized String removeLeadingForwardSlash(final String url) {
        if (url.charAt(0) == '/') {
            return url.replaceFirst("/", "");
        }
        return url;
    }

    public static final synchronized String removeExcessForwardSlashes(final String url) throws MalformedURLException {
        Protocol protocol = Http.findProtocol(url);
        String temp = url;
        if (url.contains(protocol.toString())) {
            temp = url.replaceFirst(protocol.toString(), "");
        }
        temp = temp.replaceAll("//", "/");
        return protocol.toString() + temp;
    }

    public static final synchronized String removeQueryString(final String url) {
        String flatUrl = url;
        if (url.contains("?")) {
            flatUrl = flatUrl.substring(0, url.indexOf("?"));
        }
        return flatUrl;
    }

    public static final synchronized Url removeQueryString(final Url url) {
        String flatUrl = url.getAbsoluteUrl();
        if (url.getAbsoluteUrl().contains("?")) {
            flatUrl = flatUrl.substring(0, url.getAbsoluteUrl().indexOf("?"));
        }
        url.setAbsoluteUrl(flatUrl);
        return url;
    }

    public static final synchronized String mergeQueryStrings(final Url url1, final Url url2) {
        String token;
        if ((url1 == null) || (url1.getQuery() == null) || (url1.getQuery().isEmpty())) {
            token = "?";
        } else {
            token = "&";
        }
        if ((url2 == null) || (url2.getQuery() == null) || (url2.getQuery().isEmpty())) {
            token = "";
        }
        return (url1 == null || url1.getQuery() == null ? "" : url1.getQuery()) + token + (url2 == null || url2.getQuery() == null ? "" : url2.getQuery());
    }

    public static final Boolean hasQueryString(final Url url) {
        return url.getAbsoluteUrl().contains("?");
    }

    public static final Boolean hasQueryString(final String url) {
        return url.contains("?");
    }

    @Override
    public String toString() {
        return "Url{" + "protocol=" + protocol + ", hostname=" + hostname + ", port=" + port + ", path=" + path + ", query=" + query + ", includeTrailing=" + includeTrailing + ", absoluteUrl=" + absoluteUrl + '}';
    }

}
