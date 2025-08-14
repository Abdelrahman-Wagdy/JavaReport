package utils;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Properties;


public class PropertiesLoader {
    /**
     * method to read the config file from automation modules.
     *
     * @param key the key param is being passed to determine the key on the config file
     * @return the value of the config key.
     */
    public static String readConfig(String key) {
        File file = new File(System.getProperty("projectProfile", "src/main/resources/mobile/config.properties"));
        return getPropValue(key, file);
    }

    /**
     * method to read the test data file from automation modules.
     *
     * @param key the key param is being passed to determine the key on the test data file
     * @return the value of the test data key.
     */
    public static String readTestdata(String key) {
        File file = new File(System.getProperty("projectProfile", "src/main/resources/mobile/testdata.properties"));
        return getPropValue(key, file);
    }

    public static String readiOS(String key) {
        File file = new File("src/main/resources/mobile/capabilities/iOS.properties");
        return getPropValue(key, file);
    }

    @NotNull
    public static String getPropValue(String key, File file) {

        Properties properties = new Properties();
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            properties.load(bufferedReader);

        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return properties.getProperty(key);
    }
}
