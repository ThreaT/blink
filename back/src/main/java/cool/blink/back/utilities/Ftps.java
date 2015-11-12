package cool.blink.back.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class Ftps {

    public static final synchronized void copy(final File from, final String to, final String hostname, final String username, final String password) throws IOException {
        String newTo = to.replaceAll("ftp://", "");
        int BUFFER_SIZE = 4096;
        String ftpUrl = "ftp://%s:%s@%s/%s;type=i";
        ftpUrl = String.format(ftpUrl, username, password, hostname, newTo);
        URL url = new URL(ftpUrl);
        URLConnection urlConnection = url.openConnection();
        try (OutputStream outputStream = urlConnection.getOutputStream(); FileInputStream fileInputStream = new FileInputStream(from.getAbsolutePath())) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }

}
