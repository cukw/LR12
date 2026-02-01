package com.lr12.test.config;

import org.openqa.selenium.WebDriver;
import java.util.logging.Logger;

public class ThreadSafeDriver {
    private static final Logger logger = Logger.getLogger(ThreadSafeDriver.class.getName());
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static void setDriver(WebDriver driver) {
        if (driver != null) {
            driverThreadLocal.set(driver);
            logger.info("WebDriver инициализирован для потока: " + Thread.currentThread().getId());
        }
    }

    public static WebDriver getDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver == null) {
            logger.warning("WebDriver не инициализирован для потока: " + Thread.currentThread().getId());
        }
        return driver;
    }

    public static void removeDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
            logger.info("WebDriver закрыт для потока: " + Thread.currentThread().getId());
        }
    }

    public static void quitDriver() {
        removeDriver();
    }
}
