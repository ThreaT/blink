package cool.blink.site.htmltofront.read;

import cool.blink.back.core.Report;
import cool.blink.back.core.Request;
import cool.blink.back.core.Scenario;
import cool.blink.back.core.Url;
import cool.blink.back.utilities.Urls;
import cool.blink.site.Application;
import cool.blink.site.home.read.Home;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HtmlToFront extends Scenario {

    public HtmlToFront(Url... urls) {
        super(HtmlToFront.class, urls);
    }

    @Override
    public Boolean fit(Request request) {
        Logger.getLogger(Home.class.getName()).log(Level.INFO, "Running fit: {0}", this.toString());
        return Urls.hasMatchingAbsoluteUrls(request.getUrl(), this.getUrls());
    }

    @Override
    public void main(Request request) {
        Logger.getLogger(Home.class.getName()).log(Level.INFO, "Running main: {0}", this.toString());
        try {
            Application.getWebServer().send(request, cool.blink.site.htmltofront.create.HtmlToFront.htmlToFrontTemplate);
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(HtmlToFront.class.getName()).log(Level.SEVERE, null, ex);
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
        Logger.getLogger(Home.class.getName()).log(Level.INFO, "Running test: {0}", this.toString());
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
