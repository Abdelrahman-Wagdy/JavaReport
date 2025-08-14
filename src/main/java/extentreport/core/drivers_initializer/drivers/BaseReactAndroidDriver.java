package core.drivers_initializer.drivers;

import io.appium.java_client.android.AndroidDriver;
import utils.PropertiesLoader;

import java.net.MalformedURLException;
import java.net.URL;

import static core.drivers_initializer.drivers.DriverConstants.APPIUM_URL;
import static core.drivers_initializer.drivers.DriverConstants.REACT_ANDROID_CAPABILITIES;
import static core.error_handlers.CustomErrorMessages.INVALID_APPIUM_URL_MESSAGE;

public class BaseReactAndroidDriver extends AppiumMobileDriver<AndroidDriver> {

    @Override
    public AndroidDriver createDriver() {
        try {
            return new AndroidDriver(new URL(PropertiesLoader.readConfig(APPIUM_URL)), init(PropertiesLoader.readConfig(REACT_ANDROID_CAPABILITIES)));
        } catch (MalformedURLException e) {
            throw new RuntimeException(INVALID_APPIUM_URL_MESSAGE, e);
        }
    }
}
