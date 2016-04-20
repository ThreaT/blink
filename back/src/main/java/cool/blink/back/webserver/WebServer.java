package cool.blink.back.webserver;

import cool.blink.back.core.Container;
import cool.blink.back.exception.CorruptHeadersException;
import cool.blink.back.exception.CorruptMethodException;
import cool.blink.back.exception.CorruptProtocolException;
import cool.blink.back.utilities.LogUtilities.Priority;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

public class WebServer extends Thread {

    private final WebServerChain webServerChain;
    private final WebServerDetails webServerDetails;
    private final Integer httpPort;
    private final Integer httpsPort;
    private final File keystore;
    private final String keystorePassword;
    private final LinkedTransferQueue requestQueue;
    private final WebServerSocketManager webServerSocketManager;
    private final WebServerPortScanner webServerPortScanner;
    private final HttpRequestHandler httpRequestHandler;
    private final SessionManager sessionManager;
    private AsynchronousServerSocketChannel asynchronousServerSocketChannel;
    private final Scenario fail;
    private final List<Scenario> scenarios;

    public WebServer(final WebServerChain webServerChain, final WebServerDetails webServerDetails, final Integer httpPort, final Integer httpsPort, final File keystore, final String keystorePassword, final WebServerSocketManager webServerSocketManager, final WebServerPortScanner webServerPortScanner, final HttpRequestHandler httpRequestHandler, final SessionManager sessionManager, final Scenario fail, final Scenario... scenarios) throws IOException {
        this.webServerChain = webServerChain;
        this.fail = fail;
        this.scenarios = asList(scenarios);
        this.webServerDetails = webServerDetails;
        this.webServerDetails.setSupportedUrls(getSupportedUrls(this.scenarios));
        this.webServerDetails.setRunning(true);
        this.httpPort = httpPort;
        this.httpsPort = httpsPort;
        this.keystore = keystore;
        this.keystorePassword = keystorePassword;
        this.requestQueue = new LinkedTransferQueue();

        this.webServerSocketManager = webServerSocketManager;
        this.webServerSocketManager.setWebServerChain(webServerChain);

        this.webServerPortScanner = webServerPortScanner;
        this.webServerPortScanner.setWebServerChain(webServerChain);

        this.httpRequestHandler = httpRequestHandler;
        this.httpRequestHandler.setWebServerChain(webServerChain);

        this.sessionManager = sessionManager;
        this.sessionManager.setWebServerChain(webServerChain);

        this.asynchronousServerSocketChannel = null;
    }

    public WebServerChain getWebServerChain() {
        return webServerChain;
    }

    public WebServerDetails getWebServerDetails() {
        return webServerDetails;
    }

    public Integer getHttpPort() {
        return httpPort;
    }

    public Integer getHttpsPort() {
        return httpsPort;
    }

    public File getKeystore() {
        return keystore;
    }

    public String getKeystorePassword() {
        return keystorePassword;
    }

    public LinkedTransferQueue getRequestQueue() {
        return requestQueue;
    }

    public WebServerSocketManager getWebServerSocketManager() {
        return webServerSocketManager;
    }

    public WebServerPortScanner getWebServerPortScanner() {
        return webServerPortScanner;
    }

    public HttpRequestHandler getHttpRequestHandler() {
        return httpRequestHandler;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public AsynchronousServerSocketChannel getAsynchronousServerSocketChannel() {
        return asynchronousServerSocketChannel;
    }

    public void setAsynchronousServerSocketChannel(AsynchronousServerSocketChannel asynchronousServerSocketChannel) {
        this.asynchronousServerSocketChannel = asynchronousServerSocketChannel;
    }

    public Scenario getFail() {
        return fail;
    }

    public List<Scenario> getScenarios() {
        return scenarios;
    }

    public final List<Url> getSupportedUrls(final List<Scenario> scenarios) {
        List<Url> tempSupportedUrls = new ArrayList<>();
        for (Scenario scenario : scenarios) {
            for (Url url : scenario.getUrls()) {
                tempSupportedUrls.add(url);
            }
        }
        return tempSupportedUrls;
    }

    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        WebServer webServer = Container.getWebServer(this.getWebServerChain().getApplicationName());
        try {
            this.asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(this.httpPort));
            this.asynchronousServerSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
                @Override
                public void completed(AsynchronousSocketChannel asynchronousSocketChannel, Void att) {
                    try {
                        asynchronousServerSocketChannel.accept(null, this);

                        //New Request
                        ByteBuffer byteBuffer = ByteBuffer.allocate(10485760);
                        asynchronousSocketChannel.read(byteBuffer).get(120000, TimeUnit.SECONDS);
                        byteBuffer.flip();
                        Request request = new Request(webServer, asynchronousSocketChannel, byteBuffer, Charset.defaultCharset().decode(byteBuffer).toString());

                        //Async Response
                        Logger.getLogger(WebServer.class.getName()).log(Priority.LOW, "New request to {0}", request.getUrl());
                        requestQueue.add(request);
                        webServerDetails.setRequestQueueSize(requestQueue.size());
                    } catch (CorruptHeadersException | CorruptProtocolException | CorruptMethodException | MalformedURLException ex) {
                        Logger.getLogger(WebServer.class.getName()).log(Priority.HIGH, null, ex);
                    } catch (InterruptedException | ExecutionException | TimeoutException ex) {
                        Logger.getLogger(WebServer.class.getName()).log(Priority.HIGHEST, null, ex);
                    }
                }

                @Override
                public void failed(Throwable exc, Void att) {
                }
            });
        } catch (IOException ex) {
        }
        while (this.webServerDetails.getRunning()) {
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException ex) {
            }
        }
    }

    public void begin() {
        this.start();
        Logger.getLogger(WebServer.class.getName()).log(Priority.MEDIUM, "WebServer Started.");
        this.webServerSocketManager.start();
        Logger.getLogger(WebServer.class.getName()).log(Priority.MEDIUM, "Socket Handler Started.");
        this.webServerPortScanner.start();
        Logger.getLogger(WebServer.class.getName()).log(Priority.MEDIUM, "Port Scanner Started.");
        this.httpRequestHandler.start();
        Logger.getLogger(WebServer.class.getName()).log(Priority.MEDIUM, "Http Request Handler Started.");
        this.sessionManager.start();
        Logger.getLogger(WebServer.class.getName()).log(Priority.MEDIUM, "Session Manager Started.");
    }

    public void end() {
        this.webServerDetails.setRunning(false);
        try {
            this.asynchronousServerSocketChannel.close();
        } catch (IOException ex) {
            Logger.getLogger(WebServer.class.getName()).log(Priority.HIGHEST, null, ex);
        }
        this.interrupt();
    }

    public void respond(Request request, Response response) {
        request.getAsynchronousSocketChannel().write(ByteBuffer.wrap(response.getData().getBytes()));
        request.getAsynchronousSocketChannel().write(ByteBuffer.wrap(response.getPayload()));
        request.getByteBuffer().clear();
        try {
            request.getAsynchronousSocketChannel().close();
        } catch (IOException ex) {
            Logger.getLogger(WebServer.class.getName()).log(Priority.HIGHEST, null, ex);
        }
    }

}
