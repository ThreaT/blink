
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

public class HttpServer {

    private static final Integer port = 1502;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        try {
            final AsynchronousServerSocketChannel listener = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(port));
            listener.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
                @Override
                public void completed(AsynchronousSocketChannel asynchronousSocketChannel, Void att) {
                    try {
                        listener.accept(null, this);

                        //Request - Print request to console
                        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
                        asynchronousSocketChannel.read(byteBuffer).get(2000, TimeUnit.SECONDS);
                        byteBuffer.flip();
                        String request = Charset.defaultCharset().decode(byteBuffer).toString();
                        System.out.print(StringEscapeUtils.escapeJava(request));
                        //System.out.println(request);

                        //Response - Print response to client (echo request to client)
                        byte[] response = request.getBytes();
                        asynchronousSocketChannel.write(ByteBuffer.wrap(response));
                        byteBuffer.clear();
                        asynchronousSocketChannel.close();
                    } catch (InterruptedException | ExecutionException | TimeoutException | IOException ex) {
                        Logger.getLogger(HttpServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                @Override
                public void failed(Throwable exc, Void att) {
                }
            });
        } catch (IOException ex) {
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Http Server started in " + (endTime - startTime) + "ms.");
        while (true) {
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException ex) {
            }
        }
    }

}
