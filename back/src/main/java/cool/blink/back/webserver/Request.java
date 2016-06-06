package cool.blink.back.webserver;

import cool.blink.back.exception.CorruptHeadersException;
import cool.blink.back.exception.CorruptMethodException;
import cool.blink.back.exception.CorruptProtocolException;
import cool.blink.back.utilities.HttpUtilities;
import cool.blink.back.utilities.ListUtilities;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;

public final class Request {

    private final AsynchronousSocketChannel asynchronousSocketChannel;
    private final ByteBuffer byteBuffer;
    private final String rawData;
    private final String data;
    private final HttpUtilities.Method method;
    private final Map<HeaderFieldName, String> headers;
    private final Integer port;
    private final Map<String, String> parameters;
    private final Url url;
    private final String body;
    private WebServer webServer;

    public Request(final WebServer webServer, final AsynchronousSocketChannel asynchronousSocketChannel, final ByteBuffer byteBuffer, final String rawData) throws MalformedURLException, CorruptHeadersException, CorruptProtocolException, CorruptMethodException {
        this.webServer = webServer;
        this.asynchronousSocketChannel = asynchronousSocketChannel;
        this.byteBuffer = byteBuffer;
        this.rawData = rawData;
        this.data = StringEscapeUtils.escapeJava(rawData);
        if (this.data.contains("\\uFFFD")) {
            this.method = HttpUtilities.Method.GET;
            this.headers = new HashMap<>();
            this.headers.put(HeaderFieldName.Host, "127.0.0.1");
            this.port = this.webServer.getHttpPort();
            this.parameters = new HashMap<>();
            this.url = new Url(HttpUtilities.Protocol.HTTP, Url.removePort(this.headers.get(HeaderFieldName.Host)), this.port, "/fail", "", false);
        } else {
            this.method = getMethodFromRequestData();
            this.headers = getHeadersFromRequestData();
            this.port = this.webServer.getHttpPort();
            this.parameters = getParametersFromRequestData();
            this.url = new Url(getProtocolFromRequestData(), Url.removePort(this.headers.get(HeaderFieldName.Host)), this.port, getPathFromRequestData(), parametersToQueryString(this.parameters), false);
        }
        if ((rawData != null) && (!rawData.isEmpty()) && rawData.contains("\r\n\r\n")) {
            this.body = rawData.substring(rawData.indexOf("\r\n\r\n") + 4, rawData.length());
        } else {
            this.body = rawData;
        }
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

    public String getRawData() {
        return rawData;
    }

    public final HttpUtilities.Method getMethod() {
        return method;
    }

    public final Map<HeaderFieldName, String> getHeaders() {
        return headers;
    }

    public final Integer getPort() {
        return port;
    }

    public final Map<String, String> getParameters() {
        return parameters;
    }

    public final Url getUrl() {
        return url;
    }

    public String getBody() {
        return body;
    }

    public WebServer getWebServer() {
        return webServer;
    }

    public void setWebServer(WebServer webServer) {
        this.webServer = webServer;
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

    public static final synchronized HeaderFieldName getHeaderFieldNameFromString(final String string) {
        List<HeaderFieldName> headerFieldNames = new ArrayList<>(Arrays.asList(HeaderFieldName.values()));
        for (HeaderFieldName headerFieldName : headerFieldNames) {
            if (headerFieldName.toString().equalsIgnoreCase(string)) {
                return headerFieldName;
            }
        }
        return null;
    }

    public final HttpUtilities.Protocol getProtocolFromRequestData() throws CorruptProtocolException {
        List<HttpUtilities.Protocol> httpProtocols = new ArrayList<>(Arrays.asList(HttpUtilities.Protocol.values()));
        for (HttpUtilities.Protocol httpProtocol : httpProtocols) {
            if ((this.data.contains("\\r") && (this.data.substring(0, this.data.indexOf("\\r")).toLowerCase().contains((httpProtocol.name() + "/").toLowerCase())))) {
                return httpProtocol;
            }
        }
        throw new CorruptProtocolException();
    }

    public final String getPathFromRequestData() {
        String firstLineOfRequest = this.data.substring(0, this.data.indexOf("\\r\\n"));
        if ((StringUtils.countMatches(firstLineOfRequest, " ") == 2) && (firstLineOfRequest.contains("?"))) {
            return firstLineOfRequest.substring(firstLineOfRequest.indexOf(" ") + 1, firstLineOfRequest.indexOf("?"));
        } else if (StringUtils.countMatches(firstLineOfRequest, " ") == 2) {
            return firstLineOfRequest.substring(firstLineOfRequest.indexOf(" ") + 1, firstLineOfRequest.indexOf(" ", firstLineOfRequest.indexOf(" ") + 2));
        } else {
            return "";
        }
    }

    public final HttpUtilities.Method getMethodFromRequestData() throws CorruptMethodException {
        List<HttpUtilities.Method> httpMethods = new ArrayList<>(Arrays.asList(HttpUtilities.Method.values()));
        for (HttpUtilities.Method httpMethod : httpMethods) {
            if ((this.data.length() >= httpMethod.toString().length()) && httpMethod.toString().equalsIgnoreCase(this.data.substring(0, httpMethod.toString().length()))) {
                return httpMethod;
            }
        }
        throw new CorruptMethodException(this.data);
    }

    public final Map<HeaderFieldName, String> getHeadersFromRequestData() throws CorruptHeadersException {
        Map<HeaderFieldName, String> headers1 = new HashMap<>();
        List<HeaderFieldName> headerFieldNames = new ArrayList<>(Arrays.asList(HeaderFieldName.values()));
        //Isolate headers
        List<String> isolatedHeaders;
        if ((!this.data.contains("\\r\\n")) || (!this.data.contains("\\r\\n\\r\\n"))) {
            throw new CorruptHeadersException();
        } else {
            isolatedHeaders = new ArrayList<>();
            String temp = this.data.substring(this.data.indexOf("\\r\\n") + 4, this.data.indexOf("\\r\\n\\r\\n"));
            while (temp.contains("\\r\\n\\r\\n")) {
                temp = temp.replaceAll("\\r\\n\\r\\n", "\\r\\n");
            }
            while (!temp.equals("")) {
                String header;
                if (temp.contains("\\r\\n")) {
                    header = temp.substring(0, temp.indexOf("\\r\\n") + 4);
                } else {
                    header = temp.substring(0, temp.length());
                }
                if (header.contains(":")) {
                    if (temp.contains("\\r\\n")) {
                        isolatedHeaders.add(temp.substring(0, temp.indexOf("\\r\\n")));
                    } else {
                        isolatedHeaders.add(temp.substring(0, temp.length()));
                    }
                }
                if (temp.contains("\\r\\n")) {
                    temp = temp.substring(temp.indexOf("\\r\\n") + 4, temp.length());
                } else {
                    temp = "";
                }
            }
            for (String isolatedHeader : isolatedHeaders) {
                if (ListUtilities.containsIgnoreCase(isolatedHeader, headerFieldNames)) {
                    HeaderFieldName key = getHeaderFieldNameFromString(isolatedHeader.substring(0, isolatedHeader.indexOf(":")));
                    String value = isolatedHeader.substring(isolatedHeader.indexOf(":") + 2, isolatedHeader.length());
                    headers1.put(key, value);
                }
            }
        }
        return headers1;
    }

    public final Map<String, String> getParametersFromRequestData() throws CorruptProtocolException {
        Map<String, String> parameters1 = new HashMap<>();
        if ((this.data == null) || (!this.data.contains("\\r\\n\\r\\n")) || ((!this.data.contains("?")) && (!this.data.contains("&")))) {
            return parameters1;
        }
        String firstLine = this.data.substring(0, this.data.indexOf("\\r\\n"));
        String parameterString;
        if (firstLine.contains("?")) {
            parameterString = firstLine.substring(firstLine.indexOf("?"), firstLine.indexOf(" ", firstLine.indexOf("?")));
        } else {
            parameterString = "";
        }
        if ("".equals(parameterString)) {
            String lastLine = this.data.substring(this.data.indexOf("\\r\\n\\r\\n") + 8, this.data.length());
            if (!lastLine.contains("?")) {
                lastLine = "?" + lastLine;
            }
            if (lastLine.contains("=")) {
                parameterString = lastLine;
            }
        }
        String key;
        String value;
        String cutString = parameterString;
        while (cutString.contains("=")) {
            if ((cutString.charAt(0) == '&') || (cutString.charAt(0) == '?')) {
                cutString = cutString.substring(1);
            }
            Integer keyStart = 0;
            Integer keyEnd = cutString.indexOf("=");
            key = cutString.substring(keyStart, keyEnd);

            Integer valueStart;
            Integer valueEnd;
            if (cutString.contains("&")) {
                valueStart = cutString.indexOf("=") + 1;
                valueEnd = cutString.indexOf("&");
            } else {
                valueStart = cutString.indexOf("=") + 1;
                valueEnd = cutString.length();
            }
            value = cutString.substring(valueStart, valueEnd);

            parameters1.put(key, value);
            cutString = cutString.substring(cutString.indexOf(value) + value.length(), cutString.length());
        }
        return parameters1;
    }

    public static final synchronized String parametersToQueryString(final Map<String, String> parameters) {
        String queryString = "";
        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            if (queryString.isEmpty()) {
                queryString += "";
            } else {
                queryString += "&";
            }
            queryString += parameter.getKey() + "=" + parameter.getValue();
        }
        return queryString;
    }

    @Override
    public String toString() {
        return "Request{" + "asynchronousSocketChannel=" + asynchronousSocketChannel + ", byteBuffer=" + byteBuffer + ", rawData=" + rawData + ", data=" + data + ", method=" + method + ", headers=" + headers + ", port=" + port + ", parameters=" + parameters + ", url=" + url + ", body=" + body + ", webServer=" + webServer + '}';
    }

}
