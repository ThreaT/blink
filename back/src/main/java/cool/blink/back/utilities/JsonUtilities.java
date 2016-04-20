package cool.blink.back.utilities;

import com.google.gson.Gson;
import java.io.IOException;
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
}
