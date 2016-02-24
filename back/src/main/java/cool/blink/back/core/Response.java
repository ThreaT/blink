package cool.blink.back.core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class Response {

    private final Map<HeaderFieldName, String> headers;
    private Status status;
    private final byte[] payload;
    private String data;

    public Response(final Status status, final String payload) {
        this.headers = new HashMap<>();
        this.headers.put(HeaderFieldName.Status, status.toString());
        this.headers.put(HeaderFieldName.Content_Type, "text/html; charset=UTF-8");
        this.status = status;
        this.payload = payload.getBytes();
        this.data = "HTTP/1.0" + " " + this.status.name().substring(1) + " " + this.headers.get(HeaderFieldName.Status) + "\r\n" + getHeadersFromHeaderMap(this.headers) + "\r\n";
    }

    public Response(final Status status, final byte[] payload, final String contentType) {
        this.headers = new HashMap<>();
        this.headers.put(HeaderFieldName.Status, status.toString());
        this.headers.put(HeaderFieldName.Content_Type, contentType);
        this.status = status;
        this.payload = payload;
        this.data = "HTTP/1.0" + " " + this.status.name().substring(1) + " " + this.headers.get(HeaderFieldName.Status) + "\r\n" + getHeadersFromHeaderMap(this.headers) + "\r\n";
    }

    public final Map<HeaderFieldName, String> getHeaders() {
        return headers;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public final byte[] getPayload() {
        return payload;
    }

    public final String getData() {
        this.data = "HTTP/1.0" + " " + this.status.name().substring(1) + " " + this.headers.get(HeaderFieldName.Status) + "\r\n" + getHeadersFromHeaderMap(this.headers) + "\r\n";
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public enum HeaderFieldName {

        Access_Control_Allow_Origin("Access-Control-Allow-Origin"),
        Accept_Patch("Accept-Patch"),
        Accept_Ranges("Accept-Ranges"),
        Age("Age"),
        Allow("Allow"),
        Cache_Control("Cache-Control"),
        Connection("Connection"),
        Content_Disposition("Content-Disposition"),
        Content_Encoding("Content-Encoding"),
        Content_Language("Content-Language"),
        Content_Length("Content-Length"),
        Content_Location("Content-Location"),
        Content_MD5("Content-MD5"),
        Content_Range("Content-Range"),
        Content_Type("Content-Type"),
        Date("Date"),
        ETag("ETag"),
        Expires("Expires"),
        Last_Modified("Last-Modified"),
        Link("Link"),
        Location("Location"),
        P3P("P3P"),
        Pragma("Pragma"),
        Proxy_Authenticate("Proxy-Authenticate"),
        Public_Key_Pins("Public-Key-Pins"),
        Refresh("Refresh"),
        Retry_After("Retry-After"),
        Server("Server"),
        Set_Cookie("Set-Cookie"),
        Status("Status"),
        Strict_Transport_Security("Strict-Transport-Security"),
        Trailer("Trailer"),
        Transfer_Encoding("Transfer-Encoding"),
        Upgrade("Upgrade"),
        Vary("Vary"),
        Via("Via"),
        Warning("Warning"),
        WWW_Authenticate("WWW-Authenticate"),
        X_Frame_Options("X-Frame-Options");

        private final String headerFieldName;

        private HeaderFieldName(final String headerFieldName) {
            this.headerFieldName = headerFieldName;
        }

        @Override
        public final String toString() {
            return headerFieldName;
        }

    }

    public enum Status {

        $100("Continue"),
        $101("Switching Protocols"),
        $200("OK"),
        $201("Created"),
        $202("Accepted"),
        $203("Non-Authoritative Information"),
        $204("No Content"),
        $205("Reset Content"),
        $206("Partial Content"),
        $207("Multi-Status"),
        $300("Multiple Choices"),
        $301("Moved Permanently"),
        $302("Found"),
        $303("See Other"),
        $304("Not Modified"),
        $305("Use Proxy"),
        $306("Switch Proxy"),
        $307("Temporary Redirect"),
        $400("Bad Request"),
        $401("Unauthorized"),
        $402("Payment Required"),
        $403("Forbidden"),
        $404("Not Found"),
        $405("Method Not"),
        $406("Not Acceptable"),
        $407("Proxy Authentication"),
        $408("Request Timeout"),
        $409("Conflict"),
        $410("Gone"),
        $411("Length Required"),
        $412("Precondition Failed"),
        $413("Request Entity Too Large"),
        $414("Request-URI Too Long"),
        $415("Unsupported Media Type"),
        $416("Requested Range Not Satisfiable"),
        $417("Expectation Failed"),
        $422("Unprocessable Entity"),
        $423("Locked"),
        $424("Failed Dependency"),
        $500("Internal Server Error"),
        $501("Not Implemented"),
        $502("Bad Gateway"),
        $503("Service Unavailable"),
        $504("Gateway Timeout"),
        $505("HTTP Version Not Supported"),
        $507("Insufficient Storage");

        private final String status;

        private Status(final String status) {
            this.status = status;
        }

        @Override
        public final String toString() {
            return this.status;
        }

    }

    public static final synchronized Status getStatusFromInteger(final Integer integer) {
        for (Status status : Status.values()) {
            if (("$" + integer).equals(status.name())) {
                return status;
            }
        }
        return null;
    }

    public static final synchronized String getHeadersFromHeaderMap(final Map<HeaderFieldName, String> headerMap) {
        String headers = "";
        for (Map.Entry<HeaderFieldName, String> header : headerMap.entrySet()) {
            if (header.getKey() != HeaderFieldName.Status) {
                headers += header.getKey().toString() + ": " + header.getValue() + "\r\n";
            }
        }
        return headers;
    }

    @Override
    public String toString() {
        return "Response{" + "headers=" + headers + ", status=" + status + ", payload=" + Arrays.toString(payload) + ", data=" + data + '}';
    }

}
