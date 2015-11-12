package cool.blink.back.core;

import cool.blink.back.core.Http.Protocol;
import cool.blink.back.utilities.Urls;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

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
        this.absoluteUrl = Urls.removeExcessForwardSlashes(tempUrl.toString());
    }

    public Url(final String url, final Boolean includeTrailing) throws MalformedURLException {
        URL tempUrl = new URL(url);
        this.protocol = Http.getProtocolEnum(tempUrl.getProtocol());
        this.hostname = tempUrl.getHost();
        this.port = tempUrl.getPort();
        this.path = (tempUrl.getPath().equals("") ? "/" : tempUrl.getPath());
        this.query = tempUrl.getQuery();
        this.includeTrailing = includeTrailing;
        this.absoluteUrl = Urls.removeExcessForwardSlashes(tempUrl.toString());
    }

    public Url(final Protocol protocol, final String hostname, final Integer port, final String path, final String query, final Boolean includeTrailing) throws MalformedURLException {
        this.protocol = protocol;
        this.hostname = hostname;
        this.hostname = hostname;
        this.port = port;
        this.path = path;
        this.query = query;
        this.includeTrailing = includeTrailing;
        this.absoluteUrl = Urls.removeExcessForwardSlashes(protocol + hostname + ":" + port + "/" + path + ((query == null || query.isEmpty()) ? "" : ("?" + query)));
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

    @Override
    public String toString() {
        return "Url{" + "protocol=" + protocol + ", hostname=" + hostname + ", port=" + port + ", path=" + path + ", query=" + query + ", includeTrailing=" + includeTrailing + ", absoluteUrl=" + absoluteUrl + '}';
    }

}
