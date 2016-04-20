package cool.blink.back.utilities;

import java.io.IOException;
import java.net.Socket;

public class SocketUtilities {

    public static final synchronized Boolean isPortInUse(final String host, final Integer port) {
        Boolean result = false;
        try {
            (new Socket(host, port)).close();
            result = true;
        } catch (IOException ex) {
        }
        return result;
    }

}
