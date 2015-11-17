package cool.blink.back.core;

import cool.blink.back.utilities.Headers;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class Response {

    private final Map<String, String> headers;
    /**
     * Generally contains the objects that build the response payload.
     *
     * @param httpExchange httpExchange
     * @param request request
     * @return response response
     */
    private final String payload;
    private final String data;
    private final byte[] bytes;

    public Response() {
        this.headers = new HashMap<>();
        this.payload = "";
        this.data = "";
        this.bytes = this.data.getBytes();
    }

    public Response(final Map<String, String> headers, final String payload) {
        this.headers = headers;
        this.payload = payload;
        this.data = Headers.headersToString(headers) + "\\r\\n" + payload;
        this.bytes = this.data.getBytes();
    }

    public final Map<String, String> getHeaders() {
        return headers;
    }

    public final String getPayload() {
        return payload;
    }

    public final String getData() {
        return data;
    }

    public final byte[] getBytes() {
        return bytes;
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
        $306("(Reserved)"),
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
        $5xx("Server Error"),
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
            return "Status{" + "status=" + status + '}';
        }

    }

    @Override
    public final String toString() {
        return "Response{" + "headers=" + headers + ", payload=" + payload + ", data=" + data + ", bytes=" + Arrays.toString(bytes) + '}';
    }

}
