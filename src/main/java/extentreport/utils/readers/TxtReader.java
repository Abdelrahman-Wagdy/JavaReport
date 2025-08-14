package utils.readers;

import core.custom_exceptions.UnSupportedYetException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static utils.reading_helper.FileHelper.getFileAbsolutePath;
import static utils.reading_helper.FileStatusValidator.isFileExist;
import static utils.reading_helper.FileStatusValidator.verifyFileStatus;


public class TxtReader {

    // This method to read TXT File and return it's content as List of string
    public static List<String> readTxtFile(String txtFile) {
        // Check if file exist and get file absolute path name
        if (!isFileExist(txtFile))
            // build full path for txtFile
            txtFile = getFileAbsolutePath(txtFile);
        // Verify txtFile Status
        verifyFileStatus(txtFile);
        // Get file content line by line and save them to List of string
        try (Stream<String> lines = new BufferedReader(new InputStreamReader(new FileInputStream(txtFile), StandardCharsets.UTF_8)).lines()) {
            // Return File content after removing null or empty lines
            return removeListNullEmptyValues(lines.collect(Collectors.toList()));
        } catch (Throwable throwable) {
            // In case something went wrong
            // Throw an error to Report and fail test case
            throw new UnSupportedYetException();
        }
    }

    // This method remove all null or empty lines from provided List
    public static List<String> removeListNullEmptyValues(List<String> listContent) {
        // Remove any line similar to "" or null (equals to empty and null)
        listContent.removeAll(Arrays.asList("", null));
        // Return cleaned List
        return listContent;
    }
}
