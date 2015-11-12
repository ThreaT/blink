package cool.blink.back.webserver;

import cool.blink.back.core.Blink;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsServer;
import cool.blink.back.core.Http.Method;
import cool.blink.back.core.Request;
import cool.blink.back.core.Response;
import cool.blink.back.utilities.HttpExchanges;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.security.cert.CertificateException;

public class WebServer {

    private HttpServer httpServer;
    private HttpsServer httpsServer;
    private Integer httpPort;
    private Integer httpsPort;
    private File keystore;
    private String keystorePassword;

    public WebServer() {
        this.httpServer = null;
        this.httpsServer = null;
        this.httpPort = -1;
        this.httpsPort = -1;
        this.keystore = null;
        this.keystorePassword = null;
    }

    public WebServer(Integer httpPort) throws IOException {
        this.httpServer = HttpServer.create(new InetSocketAddress(httpPort), 0);
        this.httpsServer = null;
        this.httpPort = httpPort;
        this.httpsPort = -1;
        this.keystore = null;
        this.keystorePassword = null;
    }

    public WebServer(Integer httpPort, Integer httpsPort, File keystore, String keystorePassword) throws IOException {
        this.httpServer = HttpServer.create(new InetSocketAddress(httpPort), 0);
        this.httpsServer = HttpsServer.create(new InetSocketAddress(httpsPort), 0);
        this.httpPort = httpPort;
        this.httpsPort = httpsPort;
        this.keystore = keystore;
        this.keystorePassword = keystorePassword;
    }

    public HttpServer getHttpServer() {
        return httpServer;
    }

    public void setHttpServer(HttpServer httpServer) {
        this.httpServer = httpServer;
    }

    public HttpsServer getHttpsServer() {
        return httpsServer;
    }

    public void setHttpsServer(HttpsServer httpsServer) {
        this.httpsServer = httpsServer;
    }

    public Integer getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(Integer httpPort) {
        this.httpPort = httpPort;
    }

    public Integer getHttpsPort() {
        return httpsPort;
    }

    public void setHttpsPort(Integer httpsPort) {
        this.httpsPort = httpsPort;
    }

    public File getKeystore() {
        return keystore;
    }

    public void setKeystore(File keystore) {
        this.keystore = keystore;
    }

    public String getKeystorePassword() {
        return keystorePassword;
    }

    public void setKeystorePassword(String keystorePassword) {
        this.keystorePassword = keystorePassword;
    }

    public void startHttpServer() throws IOException {
        this.httpServer.createContext("/", new DestinationHandler());
        this.httpServer.setExecutor(null);
        this.httpServer.start();
    }

    public void startHttpsServer() throws CertificateException, IOException, KeyManagementException, KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException, java.security.cert.CertificateException {
        char[] keystorePassword2 = this.keystorePassword.toCharArray();
        SSLContext sslContext = SSLContext.getInstance("TLS");
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream(this.keystore), keystorePassword2);
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(keyStore, keystorePassword2);
        sslContext.init(keyManagerFactory.getKeyManagers(), null, null);
        HttpsConfigurator configurator = new HttpsConfigurator(sslContext);
        this.httpsServer.createContext("/", new DestinationHandler());
        this.httpsServer.setHttpsConfigurator(configurator);
        this.httpsServer.setExecutor(null);
        this.httpsServer.start();
    }

    public class DestinationHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) {
            Request request;
            try {
                request = new Request(httpExchange);
            } catch (MalformedURLException ex) {
                request = new Request(httpExchange, Blink.getFail().getUrls().get(0), null, Method.GET);
            }
            Blink.getNode().getRequestQueue().add(request);
            Logger.getLogger(WebServer.class.getName()).log(Level.INFO, "New request to {0}", HttpExchanges.getAbsoluteUrl(request.getHttpExchange()));
        }
    }

    public void send(Request request, Response response) throws IOException, InterruptedException {
        if (response.getCode() == 0) {
            Logger.getLogger(WebServer.class.getName()).log(Level.SEVERE, "Response code was 0 when it probably should''ve been 200: Request: {0} response: {1}", new Object[]{request.toString(), response.toString()});
        }
        if ((response.getContentType() != null) && (!response.getContentType().isEmpty())) {
            request.getHttpExchange().getResponseHeaders().set("Content-Type", response.getContentType());
        }
        request.getHttpExchange().sendResponseHeaders(response.getCode(), response.getBytes().length);
        try (OutputStream outputStream = request.getHttpExchange().getResponseBody()) {
            outputStream.write(response.getBytes());
        } catch (IOException ex) {

        }
    }

}
