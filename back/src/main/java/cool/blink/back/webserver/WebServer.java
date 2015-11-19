package cool.blink.back.webserver;

import cool.blink.back.core.Blink;
import cool.blink.back.core.Request;
import cool.blink.back.core.Response;
import cool.blink.back.exception.CorruptHeadersException;
import cool.blink.back.exception.CorruptMethodException;
import cool.blink.back.exception.CorruptProtocolException;
import cool.blink.back.utilities.Logs.CustomLevel;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
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

public class WebServer extends Thread {

    private Boolean running;
    private AsynchronousServerSocketChannel asynchronousServerSocketChannel;
    private Integer httpPort;
    private Integer httpsPort;
    private File keystore;
    private String keystorePassword;

    public WebServer() {
        this.running = true;
        this.httpPort = -1;
        this.httpsPort = -1;
        this.keystore = null;
        this.keystorePassword = null;
    }

    public WebServer(Integer httpPort) throws IOException {
        this.running = true;
        this.httpPort = httpPort;
        this.httpsPort = -1;
        this.keystore = null;
        this.keystorePassword = null;
    }

    public WebServer(Integer httpPort, Integer httpsPort, File keystore, String keystorePassword) throws IOException {
        this.running = true;
        this.httpPort = httpPort;
        this.httpsPort = httpsPort;
        this.keystore = keystore;
        this.keystorePassword = keystorePassword;
    }

    public AsynchronousServerSocketChannel getAsynchronousServerSocketChannel() {
        return asynchronousServerSocketChannel;
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

    @Override
    public void run() {
        try {
            this.asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(this.httpPort));
            asynchronousServerSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
                @Override
                public void completed(AsynchronousSocketChannel asynchronousSocketChannel, Void att) {
                    try {
                        asynchronousServerSocketChannel.accept(null, this);

                        //New Request
                        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
                        asynchronousSocketChannel.read(byteBuffer).get(2000, TimeUnit.SECONDS);
                        byteBuffer.flip();
                        Request request = new Request(asynchronousSocketChannel, byteBuffer, StringEscapeUtils.escapeJava(Charset.defaultCharset().decode(byteBuffer).toString()));

                        //Async Response
                        Logger.getLogger(WebServer.class.getName()).log(CustomLevel.LOW, "New request to {0}", request.getUrl());
                        Blink.getNode().getRequestQueue().add(request);
                    } catch (CorruptHeadersException | CorruptProtocolException | CorruptMethodException | MalformedURLException ex) {
                        Logger.getLogger(WebServer.class.getName()).log(CustomLevel.HIGH, null, ex);
                    } catch (InterruptedException | ExecutionException | TimeoutException ex) {
                        Logger.getLogger(WebServer.class.getName()).log(CustomLevel.HIGHEST, null, ex);
                    }
                }

                @Override
                public void failed(Throwable exc, Void att) {
                }
            });
        } catch (IOException ex) {
        }
        while (this.running) {
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException ex) {
            }
        }
    }

    public void end() {
        this.running = false;
        try {
            this.asynchronousServerSocketChannel.close();
        } catch (IOException ex) {
            Logger.getLogger(WebServer.class.getName()).log(CustomLevel.HIGHEST, null, ex);
        }
        this.interrupt();
    }

    public void respond(Request request, Response response) {
        request.getAsynchronousSocketChannel().write(ByteBuffer.wrap(response.getData().getBytes()));
        request.getByteBuffer().clear();
        try {
            request.getAsynchronousSocketChannel().close();
        } catch (IOException ex) {
            Logger.getLogger(WebServer.class.getName()).log(CustomLevel.HIGHEST, null, ex);
        }
    }

}
