package cool.blink.back.utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.io.FileUtils;

public class FileUtilities {

    public static final synchronized String read(final File file) throws IOException {
        return FileUtils.readFileToString(file);
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
     * @param fileLocationSource fileLocationSource
     * @param fileLocationDestination fileLocationDestination
     * @param numberOfFilesToCopy numberOfFilesToCopy
     *
     * @throws java.io.IOException IOException
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
     * @param source source
     * @param destination destination
     * @throws IOException IOException
     *
     * Copy directory and all sub-directories and files to destination directory
     */
    @SuppressWarnings("ConvertToTryWithResources")
    public static void copy(final File source, final File destination) throws IOException {
        if (source.isDirectory()) {
            if (!destination.exists()) {
                destination.mkdirs();
            }
            String files[] = source.list();
            for (String file : files) {
                File sourceFile = new File(source, file);
                File destinationFile = new File(destination, file);
                copy(sourceFile, destinationFile);
            }
        } else {
            InputStream inputStream = new FileInputStream(source);
            OutputStream outputStream = new FileOutputStream(destination);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            inputStream.close();
            outputStream.close();
        }
    }

    /**
     *
     * @param sourceFileName sourceFileName
     * @param destionFileName destionFileName
     *
     * @throws java.io.IOException IOException
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

    /**
     *
     * @param directory directory to be created
     * @return Boolean true if directory or sub-directory was created
     * successfully
     */
    public static final synchronized Boolean createDirectory(final File directory) {
        if (directory.exists()) {
            return true;
        }
        return directory.mkdirs();
    }

    /**
     *
     * @param file file to be created
     * @return Boolean true if file was created
     * @throws IOException if file cannot be created
     */
    public static final synchronized Boolean createFile(final File file) throws IOException {
        if (file.exists()) {
            return false;
        }
        return file.createNewFile();
    }

    /**
     *
     * @param directory directory to scan
     * @return all files found in the directory
     */
    public static final synchronized List<File> listFiles(final File directory) {
        List<File> listOfFiles = new ArrayList<>();
        listOfFiles.addAll(Arrays.asList(directory.listFiles()));
        for (int i = 0; i < listOfFiles.size(); i++) {
            if (listOfFiles.get(i).isFile()) {
                listOfFiles.add(listOfFiles.get(i));
            }
        }
        return listOfFiles;
    }

    /**
     *
     * @param directory directory to scan
     * @return all folders found in the directory
     */
    public static final synchronized List<File> listFolders(final File directory) {
        List<File> listOfFolders = new ArrayList<>();
        listOfFolders.addAll(Arrays.asList(directory.listFiles()));
        for (int i = 0; i < listOfFolders.size(); i++) {
            if (listOfFolders.get(i).isDirectory()) {
                listOfFolders.add(listOfFolders.get(i));
            }
        }
        return listOfFolders;
    }

}
