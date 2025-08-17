package basetest;

import io.appium.java_client.AppiumDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class ScreenshotUtils {

    public static String encodeImageToBase64(File imageFile) throws IOException {
        byte[] fileContent = Files.readAllBytes(imageFile.toPath());
        return Base64.getEncoder().encodeToString(fileContent);
    }

    public static String setScreenshotPath(String screenShotDir, String testCaseName){

        String reportDirPath =  screenShotDir+"/"+testCaseName+"/";
        File reportDir = new File(reportDirPath);
        if (!reportDir.exists()) {
            reportDir.mkdirs();
        }
        return reportDirPath;
    }

    public static String getInstance() {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String reportDirPath =  "extent-report/screenshots/"+timestamp+"_screenshots"+"/";
        File reportDir = new File(reportDirPath);
        if (!reportDir.exists()) {
            reportDir.mkdirs();
        }
        return reportDirPath;
    }
    public static String getScreenshotPath(String testCaseName, AppiumDriver driver, String reportDirPath) throws IOException {
        File source = driver.getScreenshotAs(OutputType.FILE);


        // Generate a timestamp
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        // Save the screenshot in the report directory
        String destinationFile = reportDirPath + testCaseName+"_" + timestamp + ".png";
        FileUtils.copyFile(source, new File(destinationFile));

        // Return Base64 string instead of file path
        return "data:image/png;base64," + encodeImageToBase64(new File(destinationFile));
    }
}
