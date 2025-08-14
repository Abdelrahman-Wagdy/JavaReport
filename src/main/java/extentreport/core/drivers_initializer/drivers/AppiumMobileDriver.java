package core.drivers_initializer.drivers;

import io.appium.java_client.AppiumDriver;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.remote.DesiredCapabilities;
import utils.PropertiesLoader;

import java.io.File;
import java.util.List;

import static core.drivers_initializer.drivers.DriverConstants.APPIUM_MOBILE_DRIVER;
import static org.junit.jupiter.api.Assertions.fail;
import static utils.readers.TxtReader.readTxtFile;
import static utils.reading_helper.FileHelper.getFileAbsolutePath;


/**
 * The type Mobile driver.
 */
public abstract class AppiumMobileDriver<D extends AppiumDriver> {

    private final ThreadLocal<AppiumDriver> driverThreadLocal = new ThreadLocal<>();

    /**
     * This method initialize capabilities values and return Capabilities object
     * <p>
     * Instantiates a new Mobile driver.
     */

    @NotNull
    static DesiredCapabilities init(String capabilitiesFile) {
        if (isNotValid(capabilitiesFile))
            fail("Please provide a capabilitiesFile for mobile.");
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        List<String> lines = readTxtFile(capabilitiesFile);
        if (isNullLines(lines))
            fail("Please provide capabilities file for mobile");

        for (String line : lines) {
            System.out.println("lines"+ line);
            @NonNls String key = line.split("=").length > 0 ? line.split("=")[0] : "";
            String value = "";
            if(line.split("=").length>1){
                value = line.substring(line.indexOf("=")+1);
            }
            // Check if app provided then return absolute path
            if (isApp(key) && !value.contains("cloud")) {
                value = getFileAbsolutePath(value, true);
                System.out.println("App Absolute Path: " + value);
                if (isNullValue(value) || value.isEmpty())
                    if (PropertiesLoader.readConfig(APPIUM_MOBILE_DRIVER).equalsIgnoreCase("iOS")) { //Remove if not using .app
                        value = System.getProperty("user.dir") + "/src/main/resources/mobile/builds/" + PropertiesLoader.readiOS("app");
                    }else {
                        fail("Please provide the mobile app name or check if the app exists in your project resource folder");
                    }
            }

            if (!key.isEmpty() && !value.isEmpty())
                desiredCapabilities.setCapability(key, value);
        }
        return desiredCapabilities;
    }


    private static boolean isNullValue(String value) {
        return value == null;
    }

    private static boolean isApp(String key) {
        return key.equalsIgnoreCase("app");
    }

    private static boolean isNullLines(List<String> lines) {
        return lines == null;
    }

    private static boolean isNotFound(String capabilitiesFile) {
        File file = new File(capabilitiesFile);
        return !file.exists();
    }

    private static boolean isNotValid(String capabilitiesFile) {
        return isNull(capabilitiesFile) || capabilitiesFile.isEmpty() || isNotFound(capabilitiesFile);
    }

    private static boolean isNull(String capabilitiesFile) {
        return capabilitiesFile == null;
    }

    abstract D createDriver();

    public AppiumDriver getDriver() {
        return driverThreadLocal.get();
    }


    public void setup() {
        driverThreadLocal.set(createDriver());
    }
}
