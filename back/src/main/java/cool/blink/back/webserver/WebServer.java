package cool.blink.back.webserver;

import cool.blink.back.core.Blink;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsServer;
import cool.blink.back.core.Request;
import cool.blink.back.core.Response;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringEscapeUtils;

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

    public void startHttpServer() {
        try {
            final AsynchronousServerSocketChannel listener = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(this.httpPort));
            listener.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
                @Override
                public void completed(AsynchronousSocketChannel asynchronousSocketChannel, Void att) {
                    try {
                        listener.accept(null, this);

                        //New Request
                        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
                        asynchronousSocketChannel.read(byteBuffer).get(2000, TimeUnit.SECONDS);
                        byteBuffer.flip();
                        Request request = new Request(asynchronousSocketChannel, byteBuffer, StringEscapeUtils.escapeJava(Charset.defaultCharset().decode(byteBuffer).toString()));

                        //Async Response
                        Logger.getLogger(WebServer.class.getName()).log(Level.INFO, "New request to {0}", request.getUrl());
                        Blink.getNode().getRequestQueue().add(request);
                    } catch (InterruptedException | ExecutionException | TimeoutException ex) {
                        Logger.getLogger(HttpServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                @Override
                public void failed(Throwable exc, Void att) {
                }
            });
        } catch (IOException ex) {
        }
        while (true) {
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException ex) {
            }
        }
    }

    public void send(Request request, Response response) throws IOException, InterruptedException {
        if (response.getHeaders().get(Response.HeaderFieldName.Status.toString()) == null) {
            Logger.getLogger(WebServer.class.getName()).log(Level.SEVERE, "Response code was null when it probably should''ve been 200: Request: {0} response: {1}", new Object[]{request.toString(), response.toString()});
        }
        request.getAsynchronousSocketChannel().write(ByteBuffer.wrap(response.getBytes()));
        request.getByteBuffer().clear();
        request.getAsynchronousSocketChannel().close();
    }

}
