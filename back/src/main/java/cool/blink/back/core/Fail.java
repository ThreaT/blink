package cool.blink.back.core;

import cool.blink.back.utilities.Urls;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Used when:
 *
 * <ul>
 * <li>An error occurs preventing a request from being completed</li>
 * <li>There is no response for the given URL</li>
 * </ul>
 *
 */
public final class Fail extends Scenario {

    public static final FailTemplate failTemplate = new FailTemplate();

    public Fail(Url... urls) {
        super(Fail.class, urls);
    }

    @Override
    public final Boolean fit(Request request) {
        return Urls.hasMatchingAbsoluteUrls(request.getUrl(), this.getUrls());
    }

    @Override
    public void main(Request request) {
        Logger.getLogger(Fail.class.getName()).log(Level.INFO, "Running Fail: main(Request request)");
        try {
            Blink.getWebServer().send(request, failTemplate);
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Fail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Report test(Request request) {
        Report report = new Report(0, 0, "100%", "");
        report.setPercentage(report.calculatePercentage(report.getTotal(), report.getSuccessful()));
        return report;
    }

    public static final class FailTemplate extends Response {

        public FailTemplate() {
            super.setCode(500);
            super.setPayload("There was a problem processing your request.");
        }

    }
}
