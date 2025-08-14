package core.drivers_initializer.instance;

import core.drivers_initializer.drivers.AppiumMobileDriver;
import core.drivers_initializer.enums.AppiumMobilePlatforms;
import utils.PropertiesLoader;

import java.util.HashMap;
import java.util.Map;

import static core.drivers_initializer.drivers.DriverConstants.*;


public class MobileDriverInstance {

    private static final Map<String, AppiumMobilePlatforms> mobilePlatformsMap = new HashMap<>();

    static {
        mobilePlatformsMap.put(ANDROID, AppiumMobilePlatforms.ANDROID_DRIVER);
        mobilePlatformsMap.put(IOS, AppiumMobilePlatforms.IOS_DRIVER);
        mobilePlatformsMap.put(REACT_ANDROID, AppiumMobilePlatforms.REACT_ANDROID_DRIVER);
        mobilePlatformsMap.put(REACT_IOS,AppiumMobilePlatforms.REACT_IOS_DRIVER);
    }


    /**
     * EnumMap implementation of the Strategy design pattern. to get the MobileDriver instance.
     *
     * @return the mobile driver
     */
    public static AppiumMobileDriver getAppiumMobileDriver() {
        String driverType = System.getProperty("appium-mobile-driver", PropertiesLoader.readConfig(APPIUM_MOBILE_DRIVER));
        System.out.println("Platform type: < " + driverType + " > device");
        return mobilePlatformsMap.get(driverType).getAppiumMobileDriver();
    }
}