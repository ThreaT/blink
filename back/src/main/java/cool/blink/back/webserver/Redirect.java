package cool.blink.back.webserver;

import cool.blink.back.core.Container;
import cool.blink.back.webserver.Response.Status;
import cool.blink.back.utilities.LogUtilities.Priority;
import java.util.logging.Logger;

/**
 * Redirects from one url to another.
 */
public class Redirect extends Scenario {

    private Url from;
    private Url to;
    private final String applicationName;

    public Redirect(final String applicationName, Url from, Url to) {
        super(Redirect.class, from);
        this.from = from;
        this.to = to;
        this.applicationName = applicationName;
    }

    @Override
    public Boolean fit(Request request) {
        Logger.getLogger(Redirect.class.getName()).log(Priority.LOWEST, "Running fit: {0}", this.toString());
        return Url.hasMatchingAbsoluteUrls(this.from, request.getUrl());
    }

    @Override
    public void main(Request request) {
        Logger.getLogger(Redirect.class.getName()).log(Priority.LOWEST, "Running main: {0}", this.toString());
        Response response = new Response(Status.$302, "");
        response.getHeaders().put(Response.HeaderFieldName.Location, this.to.getAbsoluteUrl() + (request.getUrl().getQuery() == null ? "" : Url.mergeQueryStrings(this.to, request.getUrl())));
        WebServer webServer = Container.getWebServer(this.applicationName);
        webServer.respond(request, response);
    }

    /**
     * Acceptance requirements:
     *
     * <ol>
     * <li>
     * Ensure that webServerProcessing completes in under 10 ms.
     * </li>
     * </ol>
     *
     * @return Report
     */
    @Override
    public Report test(Request request) {
        Logger.getLogger(Redirect.class.getName()).log(Priority.LOWEST, "Running test: {0}", this.toString());
        Report report = new Report(1, 1, "100%", "");
        Long start = System.currentTimeMillis();
        main(request);
        Long end = System.currentTimeMillis();
        if (end - start > 10) {
            report.setSuccessful(report.getSuccessful() - 1);
        }
        report.setPercentage(report.calculatePercentage(report.getTotal(), report.getSuccessful()));
        return report;
    }

    public Url getFrom() {
        return from;
    }

    public void setFrom(Url from) {
        this.from = from;
    }

    public Url getTo() {
        return to;
    }

    public void setTo(Url to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "Redirect{" + "from=" + from + ", to=" + to + '}';
    }

}
