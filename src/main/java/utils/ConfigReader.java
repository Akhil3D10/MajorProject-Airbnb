package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final Properties props = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("config/config.properties")) {
            props.load(fis);
        } catch (IOException e) {
            System.err.println("Failed to load config/config.properties: " + e.getMessage());
        }
    }

    public static String get(String key) {
        String sys = System.getProperty(key);
        if (sys != null && !sys.isEmpty()) return sys;
        return props.getProperty(key);
    }

    public static String get(String key, String defaultVal) {
        String v = get(key);
        return v != null ? v : defaultVal;
    }
}
