package cool.blink.back.utilities;

import com.google.gson.Gson;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.xml.bind.JAXBException;

public class JsonUtilities {

    public static final synchronized String marshal(final Object object) throws JAXBException, IOException {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static final synchronized Object unmarshal(final Class clazz, String json) throws JAXBException, IOException {
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }

    public static final String serialize(final Object object) throws JAXBException, IOException {
        OutputStream outputStream = new ByteArrayOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outputStream, object);
        return outputStream.toString();
    }

    public static final Object deserialize(final Class clazz, final String json) throws JAXBException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        Object object = mapper.readValue(json, clazz);
        return object;
    }
}
