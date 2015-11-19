package cool.blink.back.core;

import cool.blink.back.core.Response.Status;
import cool.blink.back.utilities.Logs.Priority;
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
        Logger.getLogger(Fail.class.getName()).log(Priority.LOWEST, "Running fit: {0}", this.toString());
        return Url.hasMatchingAbsoluteUrls(request.getUrl(), this.getUrls());
    }

    @Override
    public void main(Request request) {
        Logger.getLogger(Fail.class.getName()).log(Priority.LOWEST, "Running main: {0}", this.toString());
        Blink.getWebServer().respond(request, failTemplate.getResponse());
    }

    @Override
    public Report test(Request request) {
        Logger.getLogger(Fail.class.getName()).log(Priority.LOWEST, "Running test: {0}", this.toString());
        Report report = new Report(0, 0, "100%", "");
        report.setPercentage(report.calculatePercentage(report.getTotal(), report.getSuccessful()));
        return report;
    }

    public static final class FailTemplate {

        private Response response;

        public FailTemplate() {
            this.response = new Response(Status.$500, "There was a problem processing your request.");
        }

        public final Response getResponse() {
            return response;
        }

        public final void setResponse(final Response response) {
            this.response = response;
        }

    }
}
