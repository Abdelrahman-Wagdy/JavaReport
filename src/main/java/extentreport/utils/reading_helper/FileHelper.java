package utils.reading_helper;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import java.io.File;
import java.util.Iterator;

public class FileHelper {
    // This method search in Test Resources Folders/sub to retrieve the file
    public static String getFileAbsolutePath(String fileName) {
        // Save file partial path if user provides fileName with path, Like: test/admin/ismail.csv
        String filePath = fileName.replace(new File(fileName).getName(), "");
        fileName = new File(fileName).getName();
        // Initialize Iterator and get all sub-Directories and files
        Iterator<File> iterator = FileUtils.iterateFiles(new File("src/"), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        // Iterate all Files content and search for File name
        while (iterator.hasNext()) {
            // Get File Path as File
            File file = iterator.next();
            // Check if File name is equal to Provided one
            if (file.getName().equalsIgnoreCase(fileName)) {
                // Check fileName contains partial path
                if (file.getPath().contains(filePath))
                    return file.getAbsolutePath();
            }
        }
        // If file not exists
        return null;
    }

    // This method search in All Project Folders/sub to retrieve the file
    public static String getFileAbsolutePath(String fileName, boolean searchAllProject) {
        // If searchAllProject is false then search inside resources in main module
        if (!searchAllProject)
            return getFileAbsolutePath(fileName);
        else {
            // Save file partial path if user provides fileName with path, Like: test/admin/test_case.csv
            String filePath = fileName.replace(new File(fileName).getName(), "");
            fileName = new File(fileName).getName();
            // Initialize Iterator and get all sub-Directories and files
            Iterator<File> iterator = FileUtils.iterateFiles(new File(System.getProperty("user.dir")),
                    TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
            // Iterate all Files content and search for File name
            while (iterator.hasNext()) {
                // Get File Path as File
                File file = iterator.next();
                // Check if File name is equal to Provided one
                if (file.getName().equalsIgnoreCase(fileName)) {
                    // Check fileName contains partial path
                    if (file.getPath().contains(filePath))
                        return file.getAbsolutePath();
                }
            }
            // If file not exists
            return null;
        }
    }
}
