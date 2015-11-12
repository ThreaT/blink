package cool.blink.front.utilities;

import cool.blink.front.html.Property;
import java.util.Arrays;
import java.util.List;

public class Properties {

    public static final synchronized String generatePropertyValues(Property... property) {
        List<Property> allProperties = Arrays.asList(property);
        String properties = "";
        for (Property property_ : allProperties) {
            properties += property_.getProperty().getPublished();
        }
        return properties;
    }

}
