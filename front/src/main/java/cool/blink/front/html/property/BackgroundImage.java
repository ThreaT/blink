package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.BackgroundImageValue;
import cool.blink.front.template.value.ImageValue;

public class BackgroundImage extends Property {

//    public BackgroundImage(String image) {
//        super(new FrontContent("background-image"), new FrontContent(BackgroundImageValue.url.toString() + "(" + "\"" + image + "\"" + ")"));
//    }

    public BackgroundImage(ImageValue imageValue, String base64) {
        super(new FrontContent("background-image"), new FrontContent(BackgroundImageValue.url.toString() + "(" + "data:image/" + imageValue.toString() + ";base64," + base64 + ")"));
    }

}
