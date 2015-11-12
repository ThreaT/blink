package cool.blink.back.core;

import java.net.MalformedURLException;
import java.net.URL;

public class Http {

    public enum Protocol {

        HTTP("http://"),
        HTTPS("https://"),
        /**
         * websockets are expected to be supported by Q4 2015 - Q1 2016
         */
        WS("ws://"),
        WSS("wss://");

        private final String protocol;

        private Protocol(String protocol) {
            this.protocol = protocol;
        }

        @Override
        public String toString() {
            return protocol;
        }
    }

    public enum Method {

        GET,
        HEAD,
        CHECKOUT,
        SHOWMETHOD,
        PUT,
        DELETE,
        POST,
        LINK,
        UNLINK,
        CHECKIN,
        TEXTSEARCH,
        SPACEJUMP,
        SEARCH
    }

    public static final synchronized Protocol findProtocol(String url) throws MalformedURLException {
        URL temp = new URL(url);
        return getProtocolEnum(temp.getProtocol());
    }

    public static final synchronized Protocol getProtocolEnum(String protocol) {
        if (!protocol.contains("://")) {
            protocol += "://";
        }
        if (protocol.equalsIgnoreCase(Protocol.HTTP.toString())) {
            return Protocol.HTTP;
        } else if (protocol.equalsIgnoreCase(Protocol.HTTPS.toString())) {
            return Protocol.HTTPS;
        } else if (protocol.equalsIgnoreCase(Protocol.WS.toString())) {
            return Protocol.WS;
        } else if (protocol.equalsIgnoreCase(Protocol.WSS.toString())) {
            return Protocol.WSS;
        } else {
            return null;
        }
    }

}
