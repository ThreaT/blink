package cool.blink.front;

import cool.blink.front.utilities.Files;
import cool.blink.front.utilities.Logs.CustomLevel;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

public class Autorunner extends Thread {

    private Class clazz;
    private String htmlFileName;

    public Autorunner(Class clazz, String htmlFileName) {
        this.clazz = clazz;
        this.htmlFileName = htmlFileName;
    }

    @Override
    public void run() {
        try {
            Class runtimeClass = getClassAtRuntime();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(byteArrayOutputStream);
            PrintStream old = System.out;
            System.setOut(ps);
            Method method = runtimeClass.getMethod("main", String[].class);
            method.invoke(null, (Object) null);
            System.out.flush();
            System.setOut(old);
            String consoleOutput = byteArrayOutputStream.toString();
            File htmlFile = new File(this.htmlFileName);
            Files.write(htmlFile, consoleOutput);
            Logger.getLogger(Autorunner.class.getName()).log(CustomLevel.LOWEST, "Output written to: {0}", htmlFile.getAbsolutePath());
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException | InstantiationException | IOException ex) {
            Logger.getLogger(Autorunner.class.getName()).log(CustomLevel.HIGHEST, null, ex);
        }
    }

    public Class getClassAtRuntime() throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException {
        return ClassLoader.getSystemClassLoader().loadClass(clazz.getName());
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getHtmlFileName() {
        return htmlFileName;
    }

    public void setHtmlFileName(String htmlFileName) {
        this.htmlFileName = htmlFileName;
    }

}
