package utils;

import config.ConfigReader;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.io.File;

public class CapabilityBuilder {
    public static DesiredCapabilities getCapabilities(String platform) {
        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("platformName", platform);
        caps.setCapability("newCommandTimeout", ConfigReader.get("newCommandTimeout"));
        caps.setCapability("udid", ConfigReader.get("udid"));

        // Fix app path - convert relative path to absolute path
        String appPath = ConfigReader.get("app");
        if (appPath != null && !appPath.startsWith("/") && !appPath.contains("://")) {
            // Convert relative path to absolute path
            File appFile = new File(appPath);
            if (appFile.exists()) {
                appPath = appFile.getAbsolutePath();
                System.out.println("App absolute path: " + appPath);
            } else {
                System.err.println("App file not found at: " + appPath);
                throw new RuntimeException("App file not found at: " + appPath);
            }
        }
        caps.setCapability("app", appPath);

        // Set automationName and additional capabilities based on platform
        switch (platform.toLowerCase()) {
            case "android":
                caps.setCapability("automationName", "UiAutomator2");
                caps.setCapability("deviceName", ConfigReader.get("deviceName", "Android Emulator"));
                caps.setCapability("platformVersion", ConfigReader.get("platformVersion", "11.0"));

                // Additional Android capabilities for stability
                caps.setCapability("newCommandTimeout", 300);
                caps.setCapability("noReset", false);
                caps.setCapability("fullReset", false);
                caps.setCapability("autoGrantPermissions", true);
                caps.setCapability("ignoreHiddenApiPolicyError", true);
                caps.setCapability("disableIdLocatorAutocompletion", true);

                // App-specific capabilities
                caps.setCapability("appWaitActivity", "com.swaglabsmobileapp.MainActivity");
                caps.setCapability("appWaitDuration", 30000);
                break;

            case "ios":
                caps.setCapability("automationName", "XCUITest");
                caps.setCapability("platformVersion", ConfigReader.get("platformVersion"));
                caps.setCapability("deviceName", ConfigReader.get("deviceName", "iPhone Simulator"));
//                caps.setCapability("newCommandTimeout", 300);
                break;

            default:
                throw new IllegalArgumentException("Unsupported platform: " + platform);
        }

        return caps;
    }
}
