package cool.blink.back.webserver;

import cool.blink.back.core.Container;
import cool.blink.back.utilities.HttpRequestUtilities;
import cool.blink.back.utilities.HttpUtilities;
import cool.blink.back.utilities.LogUtilities;
import cool.blink.back.utilities.LogUtilities.Priority;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class HttpRequestHandler extends Thread {

    private final Long executeInterval;
    private final Integer abandonServerTimeout;
    private WebServerChain webServerChain;

    public HttpRequestHandler(final Long executeInterval, final Integer abandonServerTimeout) {
        this.executeInterval = executeInterval;
        this.abandonServerTimeout = abandonServerTimeout;
    }

    public Long getExecuteInterval() {
        return executeInterval;
    }

    public Integer getAbandonServerTimeout() {
        return abandonServerTimeout;
    }

    public WebServerChain getWebServerChain() {
        return webServerChain;
    }

    public void setWebServerChain(WebServerChain webServerChain) {
        this.webServerChain = webServerChain;
    }

    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        WebServer webServer = Container.getWebServer(this.webServerChain.getApplicationName());
        while (true) {
            if (!webServer.getRequestQueue().isEmpty()) {
                Request request = null;
                if (webServer.getWebServerDetails().getRunning()) {
                    try {
                        //1. Get request from queue
                        request = (Request) webServer.getRequestQueue().take();

                        //2. Find the best node to execute the request
                        //This method has been temporarily deactivated due to preference over physical load balancing as opposed to software-based load balancing
//                        WebServerDetails bestWebServerDetails = findBestWebServerDetails(request, webServer);
//                        if ((bestWebServerDetails != null) && (!bestWebServerDetails.equals(webServer.getWebServerDetails()))) {
//                            sendProxyHttpRequest(bestWebServerDetails, request, webServer);
//                            continue;
//                        }
                        //3. If this node is the best node then find out which scenario should execute the request and execute it
                        //if ((bestWebServerDetails == null) || (bestWebServerDetails.equals(webServer.getWebServerDetails()))) {
                        Scenario execute = find(request, webServer);
                        if (execute != null) {
                            run(execute, request, webServer);
                            continue;
                        }
                        //}

                        //4. If no node supports the request then send a redirect to the fail scenario
                        Response response = new Response(Response.Status.$302, "");
                        response.getHeaders().put(Response.HeaderFieldName.Location, "/fail");
                        webServer.respond(request, response);
                    } catch (NullPointerException | IllegalAccessException | InstantiationException | InvocationTargetException | CloneNotSupportedException | IllegalArgumentException | NoSuchMethodException | SecurityException | IOException | InterruptedException ex) {
                        Logger.getLogger(HttpRequestHandler.class.getName()).log(Priority.HIGHEST, null, ex);
                    }
                } else {
                    Response response = new Response(Response.Status.$302, "");
                    response.getHeaders().put(Response.HeaderFieldName.Location, "/fail");
                    webServer.respond(request, response);
                }
            } else {
                try {
                    Thread.sleep(this.executeInterval);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SessionManager.class.getName()).log(LogUtilities.Priority.HIGHEST, null, ex);
                }
            }
        }
    }

//    This method has been temporarily deactivated due to preference over physical load balancing as opposed to software-based load balancing
//    public final synchronized WebServerDetails findBestWebServerDetails(final Request request, final WebServer webServer) {
//        List<WebServerDetails> suspects = webServer.getWebServerPortScanner().getFoundWebServers();
//        List<WebServerDetails> candidates = new ArrayList<>();
//
//        //1. Check if found nodes supports a url to fulfill the request (it should find itself as well)
//        for (WebServerDetails webServerDetails_ : suspects) {
//            for (Url url : webServerDetails_.getSupportedUrls()) {
//                if (Url.hasMatchingAbsoluteUrls(url, request.getUrl())) {
//                    candidates.add(webServerDetails_);
//                }
//            }
//        }
//
//        //2. Find least busy node that is currently handling requests
//        WebServerDetails bestWebServerDetails = null;
//        for (WebServerDetails webServerDetails_ : candidates) {
//            if (((bestWebServerDetails == null) || (bestWebServerDetails.getRequestQueueSize() > webServerDetails_.getRequestQueueSize())) && (webServerDetails_.getRunning())) {
//                bestWebServerDetails = webServerDetails_;
//            }
//        }
//        if (bestWebServerDetails == null) {
//            bestWebServerDetails = webServer.getWebServerDetails();
//        }
//        return bestWebServerDetails;
//    }
    /**
     * Loop through all existing application scenarios and return the one that
     * has intentions that match that of the request
     *
     * @param request request
     * @param webServer
     * @return Scenario Scenario
     * @throws IllegalAccessException When there is a problem invoking Fit
     * @throws IllegalArgumentException When there is a problem creating a
     * scenario clone
     * @throws InstantiationException When there is a problem invoking Fit
     * @throws InvocationTargetException When there is a problem invoking Fit
     * @throws java.lang.CloneNotSupportedException When there is a problem
     * cloning the request scenario
     * @throws java.lang.NoSuchMethodException When a scenario cannot be
     * instantiated due to an invalid Class type or Intention
     */
    public Scenario find(final Request request, final WebServer webServer) throws IllegalAccessException, InstantiationException, InvocationTargetException, CloneNotSupportedException, IllegalArgumentException, NoSuchMethodException, SecurityException {
        for (Scenario scenario : webServer.getScenarios()) {
            if (isFit(scenario, request, webServer)) {
                return scenario.clone();
            }
        }
        return null;
    }

    /**
     * @param scenario scenario
     * @param request request
     * @param webServer webServer
     * @return Boolean true if the request matches the given scenario
     * @throws IllegalAccessException When there is a problem invoking Fit
     * @throws IllegalArgumentException When there is a problem creating a
     * scenario clone
     * @throws InstantiationException When there is a problem invoking Fit
     * @throws InvocationTargetException When there is a problem invoking Fit
     * @throws java.lang.NoSuchMethodException When a scenario cannot be
     * instantiated due to an invalid Class type or Intention
     */
    public Boolean isFit(final Scenario scenario, final Request request, final WebServer webServer) throws IllegalAccessException, IllegalArgumentException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException {
        Boolean fit;
        if (scenario instanceof Fail) {
            fit = (Boolean) scenario.getFit().getMethod().invoke(webServer.getFail(), new Object[]{request});
        } else {
            fit = (Boolean) scenario.getFit().getMethod().invoke(scenario, new Object[]{request});
        }
        return fit;
    }

    /**
     * If test mode is requested then run test method instead of main method
     *
     * @param scenario Scenario
     * @param request request
     * @param webServer
     * @throws IllegalAccessException When there is a problem invoking Test or
     * Main
     * @throws InstantiationException When there is a problem invoking Test or
     * Main
     * @throws InvocationTargetException When there is a problem invoking Test
     * or Main
     * @throws java.io.IOException When there is a problem completing the filter
     * @throws java.lang.NoSuchMethodException When a scenario cannot be
     * instantiated due to an invalid Class type or Intention
     */
    public void run(final Scenario scenario, final Request request, final WebServer webServer) throws IllegalAccessException, InstantiationException, InvocationTargetException, IOException, IllegalArgumentException, NoSuchMethodException, SecurityException {
        Boolean testMode = false;
        if (request.getParameters().get("testMode") != null) {
            testMode = true;
        }
        if (testMode) {
            scenario.getTest().getMethod().invoke((scenario instanceof Fail ? webServer.getFail() : scenario), new Object[]{request});
        } else {
            scenario.getMain().getMethod().invoke((scenario instanceof Fail ? webServer.getFail() : scenario), new Object[]{request});
        }
    }

    /**
     * Use http client tools to forward the request somewhere else and then pass
     * on the reply to the request
     *
     * @param bestWebServerDetails best node to be used to handle the request
     * @param request request
     */
    public final synchronized void sendProxyHttpRequest(final WebServerDetails bestWebServerDetails, final Request request, final WebServer webServer) {
        String redirectUrl = "";
        for (Url url : (List<Url>) bestWebServerDetails.getSupportedUrls()) {
            if (url.getPath().equalsIgnoreCase(request.getUrl().getPath())) {
                redirectUrl = url.getAbsoluteUrl();
                break;
            }
        }
        Response response;
        try {
            if (request.getMethod().equals(HttpUtilities.Method.POST)) {
                response = HttpRequestUtilities.sendPost(redirectUrl, this.abandonServerTimeout);
            } else {
                response = HttpRequestUtilities.sendGet(redirectUrl, this.abandonServerTimeout);
            }
            webServer.respond(request, response);
        } catch (IOException ex) {
            response = new Response(Response.Status.$404, "404 Not Found");
            webServer.respond(request, response);
            Logger.getLogger(HttpRequestHandler.class.getName()).log(Priority.HIGHEST, null, ex);
        }
    }

}
