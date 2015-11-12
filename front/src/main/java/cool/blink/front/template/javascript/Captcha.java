package cool.blink.front.template.javascript;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public final class Captcha extends JPanel {

    private final Integer captchaWidth;
    private final Integer captchaHeight;
    private final Color backgroundColorStart;
    private final Color backgroundColorEnd;
    private Font font;
    private final Color fontColor;
    private final Integer length;
    public final double PI = 3.1415926535897932384626433832795;
    private File file;
    private String base64;
    private String captchaText = "";

    public Captcha(Integer captchaWidth, Integer captchaHeight, Color backgroundColorStart, Color backgroundColorEnd, Color fontColor, Font font, Integer length) {
        this.captchaWidth = captchaWidth;
        this.captchaHeight = captchaHeight;
        this.backgroundColorStart = backgroundColorStart;
        this.backgroundColorEnd = backgroundColorEnd;
        this.fontColor = fontColor;
        this.font = font;
        this.length = length;
        this.captchaText = generateCaptchaText(this.length);
        try {
            draw(captchaText, backgroundColorStart, backgroundColorEnd);
        } catch (IOException ex) {
            Logger.getLogger(Captcha.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Captcha(Integer captchaWidth, Integer captchaHeight, Color backgroundColorStart, Color backgroundColorEnd, Color fontColor, Font font, Integer length, File file) {
        this.captchaWidth = captchaWidth;
        this.captchaHeight = captchaHeight;
        this.backgroundColorStart = backgroundColorStart;
        this.backgroundColorEnd = backgroundColorEnd;
        this.fontColor = fontColor;
        this.font = font;
        this.length = length;
        this.file = file;
        this.captchaText = generateCaptchaText(this.length);
        try {
            draw(captchaText, backgroundColorStart, backgroundColorEnd);
        } catch (IOException ex) {
            Logger.getLogger(Captcha.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String generateCaptchaText(Integer length) {
        Character[] allCharacters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        for (int i = 0; i < length; i++) {
            Character character = allCharacters[(new Random().nextInt(25 - 1 + 1) + 1)];
            if (Math.random() < 0.5) {
                character = character.toString().toUpperCase().charAt(0);
            }
            captchaText += character;
        }
        return captchaText;
    }

    public void draw(String captchaText, Color backgroundColorStart, Color backgroundColorEnd) throws IOException {
        BufferedImage bufferedImage;
        Graphics2D graphics;
        bufferedImage = new BufferedImage(captchaWidth, captchaHeight, BufferedImage.TYPE_BYTE_INDEXED);
        graphics = bufferedImage.createGraphics();
        GradientPaint gradientPaint = new GradientPaint(225, 45, backgroundColorStart, captchaWidth, captchaHeight, backgroundColorEnd);
        graphics.setPaint(gradientPaint);
        graphics.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        graphics.setColor(fontColor);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        TextLayout textLayout;
        for (int i = 0; i < captchaText.length(); i++) {
            Integer initialFontSize = font.getSize();
            Integer randomFontSize = font.getSize() + (new Random().nextInt((30 - 0) + 1) + 0);
            font = new Font(font.getName(), font.getStyle(), randomFontSize);
            textLayout = new TextLayout(("" + captchaText.charAt(i)), font, graphics.getFontRenderContext());
            Integer randomX = (new Random().nextInt((30 - 20) + 1) + 20) + 20;
            Integer randomY = (new Random().nextInt((80 - 20) + 1) + 20) + 20;
            textLayout.draw(graphics, 30 + (i * randomX), randomY);
            font = new Font(font.getName(), font.getStyle(), initialFontSize);
        }
        drawPath(graphics);
        drawPath(graphics);
        if (file != null) {
            if (file.exists()) {
                file.delete();
            }
            ImageIO.write((RenderedImage) bufferedImage, "png", file);
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write((RenderedImage) bufferedImage, "png", out);
        byte[] bytes = out.toByteArray();
        String base64bytes = Base64.encode(bytes);
        base64 = "data:image/png;base64," + base64bytes;
    }

    private void drawPath(Graphics2D graphics) {
        int y1;
        int y2;
        int y3;
        int y4;
        int y5;
        Random random = new Random();
        y1 = random.nextInt(captchaHeight);
        y2 = random.nextInt(captchaHeight);
        y3 = captchaHeight / 2;
        y4 = random.nextInt(captchaHeight);
        y5 = random.nextInt(captchaHeight);
        drawLine(graphics, 0, y1, captchaWidth / 4, y2);
        drawLine(graphics, captchaWidth / 4, y2, captchaWidth / 2, y3);
        drawLine(graphics, captchaWidth / 2, y3, 3 * captchaWidth / 4, y4);
        drawLine(graphics, 3 * captchaWidth / 4, y4, captchaWidth, y5);
    }

    private void drawLine(Graphics graphics, int x1, int y1, int x2, int y2) {
        int dX = x2 - x1;
        int dY = y2 - y1;
        int[] x = new int[4];
        int[] y = new int[4];
        int thickness = 2;
        double lineLength = Math.sqrt(dX * dX + dY * dY);
        double scale = (double) (thickness) / (2 * lineLength);
        double ddx = -scale * (double) dY;
        double ddy = scale * (double) dX;
        graphics.setColor(fontColor);
        ddx += (ddx > 0) ? 0.5 : -0.5;
        ddy += (ddy > 0) ? 0.5 : -0.5;
        dX = (int) ddx;
        dY = (int) ddy;
        x[0] = x1 + dX;
        y[0] = y1 + dY;
        x[1] = x1 - dX;
        y[1] = y1 - dY;
        x[2] = x2 - dX;
        y[2] = y2 - dY;
        x[3] = x2 + dX;
        y[3] = y2 + dY;
        graphics.fillPolygon(x, y, 4);
    }

    public String getCaptchaText() {
        return captchaText;
    }

    public void setCaptchaText(String captchaText) {
        this.captchaText = captchaText;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public Integer getCaptchaWidth() {
        return captchaWidth;
    }

    public Integer getCaptchaHeight() {
        return captchaHeight;
    }

    public Color getBackgroundColorStart() {
        return backgroundColorStart;
    }

    public Color getBackgroundColorEnd() {
        return backgroundColorEnd;
    }

    public Color getFontColor() {
        return fontColor;
    }

    public Integer getLength() {
        return length;
    }

}
