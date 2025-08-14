package core.drivers_initializer.drivers;

import io.appium.java_client.ios.IOSDriver;
import utils.PropertiesLoader;

import java.net.MalformedURLException;
import java.net.URL;

import static core.drivers_initializer.drivers.DriverConstants.APPIUM_URL;
import static core.drivers_initializer.drivers.DriverConstants.REACT_IOS_CAPABILITIES;
import static core.error_handlers.CustomErrorMessages.INVALID_APPIUM_URL_MESSAGE;

public class BaseReactIOSDriver extends AppiumMobileDriver<IOSDriver> {

    @Override
    public IOSDriver createDriver() {
        try {
            return new IOSDriver(new URL(PropertiesLoader.readConfig(APPIUM_URL)),
                    init(PropertiesLoader.readConfig(REACT_IOS_CAPABILITIES)));
        } catch (MalformedURLException e) {
            throw new RuntimeException(INVALID_APPIUM_URL_MESSAGE, e);
        }
    }
}
