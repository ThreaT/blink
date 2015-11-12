package cool.blink.front.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Files {

    public static final synchronized String read(final File file) throws IOException {
        String output = "";
        String absoluteFilePath = file.getAbsolutePath();
        BufferedReader br;
        String sCurrentLine;
        br = new BufferedReader(new FileReader(absoluteFilePath));
        while ((sCurrentLine = br.readLine()) != null) {
            output += sCurrentLine;
        }
        br.close();
        return output;
    }

    public static final synchronized void write(final File file, final String content) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        try (BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(content);
        }
    }

    /**
     *
     * @param fileLocationSource
     * @param fileLocationDestination
     * @param numberOfFilesToCopy
     *
     * @throws java.io.IOException
     *
     * Copies all files from source location to a destination location.
     */
    public static final synchronized void copy(final String fileLocationSource, final String fileLocationDestination, Integer numberOfFilesToCopy) throws IOException {
        File inputLocation = new File(fileLocationSource);
        if (inputLocation.isDirectory()) {
            File[] attachmentFiles = inputLocation.listFiles();
            for (File aFile : attachmentFiles) {
                if (!aFile.isDirectory()) {
                    String fileName = aFile.getName();
                    String sourceFileName = aFile.getAbsolutePath();
                    String destinationFileName = fileLocationDestination + fileName;
                    copy(sourceFileName, destinationFileName);
                }
                if (numberOfFilesToCopy >= 0) {
                    if (--numberOfFilesToCopy == 0) {
                        break;
                    }
                }
            }
        }
    }

    /**
     *
     * @param sourceFileName
     * @param destionFileName
     *
     * @throws java.io.IOException
     *
     * Copies a single file from source location to a destination location.
     */
    public static final synchronized void copy(final String sourceFileName, final String destionFileName) throws IOException {
        File sourceFile = new File(sourceFileName);
        File destinationFile = new File(destionFileName);
        OutputStream out;
        try (InputStream in = new FileInputStream(sourceFile)) {
            out = new FileOutputStream(destinationFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
        out.close();
    }

}
