package cool.blink.site.htmltofront.read;

import cool.blink.back.webserver.Report;
import cool.blink.back.webserver.Request;
import cool.blink.back.webserver.Scenario;
import cool.blink.back.webserver.Url;
import cool.blink.back.utilities.LogUtilities.Priority;
import cool.blink.site.Site;
import cool.blink.site.home.read.Home;
import java.util.logging.Logger;

public class HtmlToFront extends Scenario {

    public HtmlToFront(Url... urls) {
        super(HtmlToFront.class, urls);
    }

    @Override
    public Boolean fit(Request request) {
        Logger.getLogger(Home.class.getName()).log(Priority.LOWEST, "Running fit: {0}", this.toString());
        return Url.hasMatchingAbsoluteUrls(request.getUrl(), this.getUrls());
    }

    @Override
    public void main(Request request) {
        Logger.getLogger(Home.class.getName()).log(Priority.LOWEST, "Running main: {0}", this.toString());
        Site.site.getWebServer().respond(request, cool.blink.site.htmltofront.create.HtmlToFront.htmlToFrontTemplate.getResponse());
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
        Logger.getLogger(Home.class.getName()).log(Priority.LOWEST, "Running test: {0}", this.toString());
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

}
