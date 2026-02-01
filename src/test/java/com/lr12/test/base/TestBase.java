package com.lr12.test.base;

import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.lr12.test.config.ConfigReader;
import com.lr12.test.config.DriverFactory;
import com.lr12.test.config.ThreadSafeDriver;

public class TestBase {
    protected WebDriver driver;
    private static final Logger logger = Logger.getLogger(TestBase.class.getName());

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        long threadId = Thread.currentThread().getId();
        logger.info("========== НАЧАЛО ТЕСТА (Поток: " + threadId + ") ==========");

        driver = DriverFactory.createDriver();
        
        // Сохранение в ThreadLocal для доступа из Page Objects
        ThreadSafeDriver.setDriver(driver);

        driver.manage().timeouts()
            .implicitlyWait(java.time.Duration.ofSeconds(ConfigReader.getImplicitWait()));
        
        // Максимизация окна (если не headless)
        if (!ConfigReader.isHeadless()) {
            try {
                driver.manage().window().maximize();
            } catch (Exception e) {
                logger.warning("Не удалось максимизировать окно (возможно headless режим)");
            }
        }
        
        logger.info("WebDriver инициализирован (Поток: " + threadId + ")");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        long threadId = Thread.currentThread().getId();
        logger.info("========== КОНЕЦ ТЕСТА (Поток: " + threadId + ") ==========");
        
        // Закрытие WebDrive
        ThreadSafeDriver.removeDriver();
    }
}
