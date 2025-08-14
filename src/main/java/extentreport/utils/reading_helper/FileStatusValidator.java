package utils.reading_helper;

import java.io.File;


public class FileStatusValidator {

    /*************** Class Methods Section ***************/
    // This method to validate if file exist then return true
    public static Boolean isFileExist(String filePath) {
        return new File(filePath).exists();
    }

    // This method validate if Provided File is file or directory
    public static Boolean verifyFileIsFolder(String filePath) {
        return new File(filePath).isDirectory();
    }

    // This method validate if Provided File contains Data or Empty
    public static Boolean verifyFileContainsData(String filePath) {
        return new File(filePath).length() != 0;
    }

    // This method validate File status overall, If File Path exist, not a directory and contains data
    public static void verifyFileStatus(String filePath) {
        if (!isFileExist(filePath)) {
            System.out.println("File isn't exist, Please check File Path: " + filePath);
        }

        if (verifyFileIsFolder(filePath)) {
            System.out.println("The Provided a folder not a file, Please check Folder Path: " + filePath);
        }

        if (!verifyFileContainsData(filePath)) {
            System.out.println("The Provided an Empty File, Please check file Path: " + filePath);
        }
    }
}
