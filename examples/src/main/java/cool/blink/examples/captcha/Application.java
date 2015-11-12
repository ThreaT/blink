package cool.blink.examples.captcha;

import cool.blink.front.template.javascript.Captcha;
import java.awt.Color;
import java.awt.Font;
import java.io.File;

public class Application {

    public static void main(String args[]) {
        //Base64 Captcha
        Captcha base64Captcha = new Captcha(280, 120, new Color(255, 255, 255, 255), new Color(255, 255, 255, 255), new Color(0, 0, 0, 255), new Font("TimesRoman", Font.PLAIN, 18), 5);
        System.out.println(base64Captcha.getBase64());

        //File image captcha
        Captcha fileCaptcha = new Captcha(280, 120, new Color(255, 255, 255, 255), new Color(255, 255, 255, 255), new Color(0, 0, 0, 255), new Font("TimesRoman", Font.PLAIN, 18), 5, new File("example.png"));
        System.out.println(fileCaptcha.getFile().getAbsolutePath());
    }

}
