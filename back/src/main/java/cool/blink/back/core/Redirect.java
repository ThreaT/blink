package cool.blink.back.core;

import cool.blink.back.utilities.Urls;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Redirects from one url to another.
 */
public class Redirect extends Scenario {

    private Url from;
    private Url to;

    public Redirect(Url from, Url to) {
        super(Redirect.class, from);
        this.from = from;
        this.to = to;
    }

    @Override
    public Boolean fit(Request request) {
        Logger.getLogger(Redirect.class.getName()).log(Level.INFO, "Running fit: {0}", this.toString());
        return Urls.hasMatchingAbsoluteUrls(this.from, request.getUrl());
    }

    @Override
    public void main(Request request) {
        Logger.getLogger(Redirect.class.getName()).log(Level.INFO, "Running main: {0}", this.toString());
        try {
            request.getHttpExchange().getResponseHeaders().add("Location", this.to.getAbsoluteUrl() + (request.getUrl().getQuery() == null ? "" : Urls.mergeQueryStrings(this.to, request.getUrl())));
            Blink.getWebServer().send(request, new Response(302, ""));
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Redirect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Acceptance requirements:
     *
     * <ol>
     * <li>
     * Ensure that processing completes in under 10 ms.
     * </li>
     * </ol>
     *
     * @return Report
     */
    @Override
    public Report test(Request request) {
        Logger.getLogger(Redirect.class.getName()).log(Level.INFO, "Running test: {0}", this.toString());
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
