package cool.blink.back.webserver;

import java.io.Serializable;

public class WebServerProcess implements Serializable {

    private WebServerDetails networkDetails;
    private Object object;
    private WebServerProcessType webServerProcessType;

    public WebServerProcess() {

    }

    public WebServerProcess(final WebServerDetails networkDetails, final Object object, final WebServerProcessType webServerProcessType) {
        this.networkDetails = networkDetails;
        this.object = object;
        this.webServerProcessType = webServerProcessType;
    }

    public WebServerDetails getWebServerDetails() {
        return networkDetails;
    }

    public void setWebServerDetails(WebServerDetails networkDetails) {
        this.networkDetails = networkDetails;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public WebServerProcessType getWebServerProcessType() {
        return webServerProcessType;
    }

    public void setWebServerProcessType(WebServerProcessType webServerProcessType) {
        this.webServerProcessType = webServerProcessType;
    }

    @Override
    public String toString() {
        return "WebServerProcess{" + "networkDetails=" + networkDetails + ", object=" + object + ", webServerProcessType=" + webServerProcessType + '}';
    }

}
