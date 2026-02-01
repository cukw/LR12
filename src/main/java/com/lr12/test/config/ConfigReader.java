package com.lr12.test.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;
    private static final String CONFIG_FILE = "src/test/resources/config.properties";

    static {
        loadProperties();
    }

    private static void loadProperties() {
        properties = new Properties();
        try (FileInputStream file = new FileInputStream(CONFIG_FILE)) {
            properties.load(file);
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке config.properties: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static String getBaseUrl() {
        return getProperty("base.url", "http://the-internet.herokuapp.com");
    }

    public static String getBrowser() {
        return getProperty("browser", "chrome");
    }

    public static boolean isHeadless() {
        String headless = getProperty("headless", "true");
        return Boolean.parseBoolean(headless);
    }

    public static int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait", "10"));
    }

    public static int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait", "15"));
    }

    public static int getPageLoadTimeout() {
        return Integer.parseInt(getProperty("page.load.timeout", "20"));
    }

    public static int getThreadCount() {
        return Integer.parseInt(getProperty("thread.count", "4"));
    }

    public static boolean isScreenshotOnFailure() {
        String screenshot = getProperty("screenshot.on.failure", "true");
        return Boolean.parseBoolean(screenshot);
    }

    public static String getScreenshotPath() {
        return getProperty("screenshot.path", "target/screenshots/");
    }

    public static String getLogLevel() {
        return getProperty("log.level", "INFO");
    }

    public static String getLogPath() {
        return getProperty("log.path", "target/logs/");
    }

    private static String getProperty(String key, String defaultValue) {
        String value = System.getProperty(key);
        if (value != null) {
            return value;
        }
        value = properties.getProperty(key);
        return value != null ? value : defaultValue;
    }

    private static String getProperty(String key) {
        String value = System.getProperty(key);
        if (value != null) {
            return value;
        }
        return properties.getProperty(key);
    }
}
