package cool.blink.back.core;

import cool.blink.back.utilities.HttpExchanges;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Request {

    private HttpExchange httpExchange;
    private Url url;
    private HashMap<String, String> parameters;
    private Http.Method method;

    public Request(HttpExchange httpExchange, Url url, HashMap<String, String> parameters, Http.Method method) {
        this.httpExchange = httpExchange;
        this.url = url;
        this.parameters = parameters;
        this.method = method;
    }

    public Request(HttpExchange httpExchange) throws MalformedURLException {
        try {
            this.httpExchange = httpExchange;
            this.url = new Url(HttpExchanges.getAbsoluteUrl(httpExchange));
            this.parameters = HttpExchanges.getHttpExchangeParameters(httpExchange);
            this.method = HttpExchanges.getHttpExchangeMethod(httpExchange);
        } catch (IOException ex) {
            //TODO Replace HttpExchanges.getAbsoluteUrl(httpExchange) with null after figuring out what's causing this
            Logger.getLogger(Request.class.getName()).log(Level.SEVERE, HttpExchanges.getAbsoluteUrl(httpExchange), ex);
        }
    }

    public HttpExchange getHttpExchange() {
        return httpExchange;
    }

    public void setHttpExchange(HttpExchange httpExchange) {
        this.httpExchange = httpExchange;
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(HashMap<String, String> parameters) {
        this.parameters = parameters;
    }

    public Http.Method getMethod() {
        return method;
    }

    public void setMethod(Http.Method method) {
        this.method = method;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.httpExchange);
        hash = 37 * hash + Objects.hashCode(this.url);
        hash = 37 * hash + Objects.hashCode(this.parameters);
        hash = 37 * hash + Objects.hashCode(this.method);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Request other = (Request) obj;
        if (!Objects.equals(this.httpExchange, other.httpExchange)) {
            return false;
        }
        if (!Objects.equals(this.url, other.url)) {
            return false;
        }
        if (!Objects.equals(this.parameters, other.parameters)) {
            return false;
        }
        return this.method == other.method;
    }

    @Override
    public String toString() {
        return "Request{" + "httpExchange=" + httpExchange + ", url=" + url + ", parameters=" + parameters + ", method=" + method + '}';
    }

}
