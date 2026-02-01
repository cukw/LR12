package com.lr12.test.config;

import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {
    private static final Logger logger = Logger.getLogger(DriverFactory.class.getName());

    public static WebDriver createDriver() {
        String browser = ConfigReader.getBrowser().toLowerCase();
        
        return switch (browser) {
            case "chrome" -> createChromeDriver();
            case "firefox" -> createFirefoxDriver();
            default -> {
                logger.warning("Неизвестный браузер: " + browser + ". Используем Chrome.");
                yield createChromeDriver();
            }
        };
    }

    private static WebDriver createChromeDriver() {
        logger.info("Инициализация Chrome WebDriver...");
        
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        if (ConfigReader.isHeadless()) {
            options.addArguments("--headless=new");
            logger.info("Headless режим: ВКЛЮЧЕН");
        }

        options.addArguments(
            "--no-sandbox",
            "--disable-dev-shm-usage",
            "--disable-gpu",
            "--window-size=1920,1080",
            "--disable-blink-features=AutomationControlled",
            "--disable-extensions"
        );

        options.setCapability("goog:loggingPrefs", 
            java.util.Map.of("browser", "OFF", "driver", "OFF"));
        
        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver() {
        logger.info("Инициализация Firefox WebDriver...");
        
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();

        if (ConfigReader.isHeadless()) {
            options.addArguments("--headless");
            logger.info("Headless режим: ВКЛЮЧЕН");
        }
        
        options.addArguments(
            "--width=1920",
            "--height=1080"
        );
        
        return new FirefoxDriver(options);
    }
}
