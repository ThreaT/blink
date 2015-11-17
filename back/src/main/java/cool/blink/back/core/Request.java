package cool.blink.back.core;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.HashMap;
import java.util.Map;

public class Request {

    private final AsynchronousSocketChannel asynchronousSocketChannel;
    private final ByteBuffer byteBuffer;
    private final String data;
    private final Http.Method method;
    private final Map<String, String> headers;
    private final Url url;
    private final HashMap<String, String> parameters;

    public Request(final AsynchronousSocketChannel asynchronousSocketChannel, final ByteBuffer byteBuffer, final String data) {
        this.asynchronousSocketChannel = asynchronousSocketChannel;
        this.byteBuffer = byteBuffer;
        this.data = data;
        this.method = null;
        this.headers = null;
        this.url = null;
        this.parameters = null;
    }

    public final AsynchronousSocketChannel getAsynchronousSocketChannel() {
        return asynchronousSocketChannel;
    }

    public final ByteBuffer getByteBuffer() {
        return byteBuffer;
    }

    public final String getData() {
        return data;
    }

    public final Map<String, String> getHeaders() {
        return headers;
    }

    public final Url getUrl() {
        return url;
    }

    public final HashMap<String, String> getParameters() {
        return parameters;
    }

    public final Http.Method getMethod() {
        return method;
    }

    public enum HeaderFieldName {

        Accept("Accept"),
        Accept_Charset("Accept-Charset"),
        Accept_Datetime("Accept-Datetime"),
        Accept_Encoding("Accept-Encoding"),
        Accept_Language("Accept-Language"),
        Authorization("Authorization"),
        Cache_Control("Cache-Control"),
        Connection("Connection"),
        Content_Length("Content-Length"),
        Content_MD5("Content-MD5"),
        Content_Type("Content-Type"),
        Cookie("Cookie"),
        Date("Date"),
        Expect("Expect"),
        From("From"),
        Host("Host"),
        If_Match("If-Match"),
        If_Modified_Since("If-Modified-Since"),
        If_None_Match("If-None-Match"),
        If_Range("If-Range"),
        If_Unmodified_Since("If-Unmodified-Since"),
        Max_Forwards("Max-Forwards"),
        Origin("Origin"),
        Pragma("Pragma"),
        Proxy_Authorization("Proxy-Authorization"),
        Range("Range"),
        Referer("Referer"),
        TE("TE"),
        Upgrade("Upgrade"),
        User_Agent("User-Agent"),
        Via("Via"),
        Warning("Warning");

        private final String headerFieldName;

        private HeaderFieldName(final String headerFieldName) {
            this.headerFieldName = headerFieldName;
        }

        @Override
        public final String toString() {
            return headerFieldName;
        }

    }

    @Override
    public String toString() {
        return "Request{" + "asynchronousSocketChannel=" + asynchronousSocketChannel + ", byteBuffer=" + byteBuffer + ", data=" + data + ", headers=" + headers + ", url=" + url + ", parameters=" + parameters + ", method=" + method + '}';
    }

}
