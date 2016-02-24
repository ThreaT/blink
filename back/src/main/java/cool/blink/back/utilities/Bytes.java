package cool.blink.back.utilities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Bytes {

    public static final byte[] join(final byte[] array1, final byte[] array2) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(array1);
        outputStream.write(array2);
        byte[] array3 = outputStream.toByteArray();
        return array3;
    }

}
