package cool.blink.back.utilities;

import cool.blink.back.database.Column.CLOB;
import static cool.blink.back.webserver.Response.HeaderFieldName.Connection;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.Clob;
import java.sql.Connection;
import static java.sql.JDBCType.CLOB;
import java.sql.SQLException;

public class ByteUtilities {

    public static final synchronized byte[] join(final byte[] array1, final byte[] array2) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(array1);
        outputStream.write(array2);
        byte[] array3 = outputStream.toByteArray();
        return array3;
    }

    public static final synchronized byte[] toPrimitive(final Byte[] bytes) {
        byte[] primitiveBytes = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            primitiveBytes[i] = bytes[i];
        }
        return primitiveBytes;
    }

    public static final synchronized Byte[] toComposite(final byte[] bytes) {
        Byte[] compositeBytes = new Byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            compositeBytes[i] = bytes[i];
        }
        return compositeBytes;
    }

    public static final synchronized Byte[] serialize(final Object object) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(object);
        return toComposite(byteArrayOutputStream.toByteArray());
    }

    public static final synchronized Object deserialize(final byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(byteArrayInputStream);
        return is.readObject();
    }

    public static final synchronized Object deserialize(final Byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(toPrimitive(data));
        ObjectInputStream is = new ObjectInputStream(byteArrayInputStream);
        return is.readObject();
    }

}
