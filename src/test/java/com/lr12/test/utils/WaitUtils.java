package com.lr12.test.utils;

import java.time.Duration;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.lr12.test.config.ConfigReader;
import com.lr12.test.config.ThreadSafeDriver;

public class WaitUtils {
    private static final Logger logger = Logger.getLogger(WaitUtils.class.getName());

    /**
     * Ожидание элемента с дефолтным таймаутом
     */
    public static WebElement waitForElement(By locator) {
        return waitForElement(locator, ConfigReader.getExplicitWait());
    }

    /**
     * Ожидание элемента с кастомным таймаутом
     */
    public static WebElement waitForElement(By locator, int timeoutInSeconds) {
        WebDriver driver = ThreadSafeDriver.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Exception e) {
            logger.severe("Элемент не найден: " + locator);
            throw e;
        }
    }

    /**
     * Ожидание видимости элемента
     */
    public static WebElement waitForElementVisible(By locator) {
        return waitForElementVisible(locator, ConfigReader.getExplicitWait());
    }

    /**
     * Ожидание видимости элемента с кастомным таймаутом
     */
    public static WebElement waitForElementVisible(By locator, int timeoutInSeconds) {
        WebDriver driver = ThreadSafeDriver.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            logger.severe("Элемент не видим: " + locator);
            throw e;
        }
    }

    /**
     * Ожидание кликабельности элемента
     */
    public static void waitForElementClickable(By locator) {
        WebDriver driver = ThreadSafeDriver.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Ожидание исчезновения элемента
     */
    public static void waitForElementToDisappear(By locator) {
        WebDriver driver = ThreadSafeDriver.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Ожидание изменения значения атрибута
     */
    public static void waitForAttributeChange(By locator, String attribute, String value) {
        WebDriver driver = ThreadSafeDriver.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
        wait.until(d -> {
            WebElement element = d.findElement(locator);
            return element.getAttribute(attribute).equals(value);
        });
    }
}
