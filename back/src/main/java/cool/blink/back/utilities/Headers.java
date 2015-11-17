package cool.blink.back.utilities;

import java.util.HashMap;
import java.util.Map;

public final class Headers {

    public static final synchronized String headersToString(final Map<String, String> headers) {
        String headerString = "";
        if ((headers == null) || (headers.isEmpty())) {
            return headerString;
        }
        for (Map.Entry<String, String> header : headers.entrySet()) {
            if ("".equals(headerString)) {
                headerString = header.getKey() + "=" + header.getValue();
            } else {
                headerString += "&" + header.getKey() + "=" + header.getValue();
            }
        }
        return headerString;
    }

    public static final synchronized Map<String, String> stringToHeaders(final String string) {
        Map<String, String> headers = new HashMap<>();
        if ((string == null) || (string.isEmpty()) || (!string.contains("="))) {
            return headers;
        }
        String key;
        String value;
        String cutString = string;
        while (cutString.contains("=")) {
            if (cutString.charAt(0) == '&') {
                key = cutString.substring(0, cutString.indexOf("="));
            } else {
                key = cutString.substring(1, cutString.indexOf("="));
            }

            if (cutString.contains("&")) {
                value = cutString.substring(cutString.indexOf("=") + 1, cutString.indexOf("&") - 1);
            } else {
                value = cutString.substring(cutString.indexOf("=") + 1, cutString.length());
            }
            headers.put(key, value);
            cutString = cutString.substring(cutString.indexOf(value) + value.length(), cutString.length());
        }
        return headers;
    }

}
