package cool.blink.back.utilities;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;

public class HttpClientUtilities {

    public static final synchronized String get(final String url) throws IOException {
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);
        client.executeMethod(method);
        InputStream inputStream = method.getResponseBodyAsStream();
        return IOUtils.toString(inputStream, "UTF-8");
    }
}
