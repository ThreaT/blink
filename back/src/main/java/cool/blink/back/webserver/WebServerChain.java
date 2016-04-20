package cool.blink.back.webserver;

public final class WebServerChain {

    private final String applicationName;

    public WebServerChain(final String applicationName) {
        this.applicationName = applicationName;
    }

    public final String getApplicationName() {
        return applicationName;
    }

}
