package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static final Properties prop = new Properties();

    static {
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
            prop.load(fis);
            System.out.println("Config loaded successfully");
        } catch (IOException e) {
            System.err.println("Failed to load config.properties: " + e.getMessage());
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String get(String key) {
        String value = prop.getProperty(key);
        if (value == null) {
            System.err.println("Property not found: " + key);
        }
        return value;
    }

    public static String get(String key, String defaultValue) {
        return prop.getProperty(key, defaultValue);
    }
}
