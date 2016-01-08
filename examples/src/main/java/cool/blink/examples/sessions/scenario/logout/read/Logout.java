package cool.blink.examples.sessions.scenario.logout.read;

import cool.blink.back.core.Blink;
import cool.blink.back.core.Report;
import cool.blink.back.core.Request;
import cool.blink.back.core.Response;
import cool.blink.back.core.Response.Status;
import cool.blink.back.core.Scenario;
import cool.blink.back.core.Url;
import cool.blink.back.utilities.Logs.Priority;
import java.util.logging.Logger;

public class Logout extends Scenario {

    public Logout(Url... urls) {
        super(Logout.class, urls);
    }

    @Override
    public Boolean fit(Request request) {
        Logger.getLogger(Logout.class.getName()).log(Priority.LOWEST, "Running...");
        return Url.hasMatchingAbsoluteUrls(request.getUrl(), this.getUrls());
    }

    @Override
    public void main(Request request) {
        Logger.getLogger(Logout.class.getName()).log(Priority.LOWEST, "Running...");
        Blink.getSessions().remove("test_user");
        Response response = new Response(Status.$302, "");
        response.getHeaders().put(Response.HeaderFieldName.Location, "/examples/sessions/login");
        Blink.getWebServer().respond(request, response);
    }

    /**
     * Acceptance requirements:
     *
     * <ol>
     * <li>
     * Ensure that processing completes in under 10 ms
     * </li>
     * </ol>
     *
     * @return Report
     */
    @Override
    public Report test(Request request) {
        Logger.getLogger(Logout.class.getName()).log(Priority.LOWEST, "Starting");
        Report report = new Report(1, 1, "100%", "");
        Long start = System.currentTimeMillis();
        main(request);
        Long end = System.currentTimeMillis();
        if (end - start > 10) {
            report.setSuccessful(report.getSuccessful() - 1);
            report.appendLog(new String[][]{{"" + Thread.currentThread().getStackTrace()[1].getLineNumber(), "Processing took too long, it took " + (end - start) + " ms."}});
        }
        report.setPercentage(report.calculatePercentage(report.getTotal(), report.getSuccessful()));
        Logger.getLogger(Logout.class.getName()).log(Priority.LOWEST, "Finished");
        return report;
    }

}
