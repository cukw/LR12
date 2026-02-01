package com.lr12.test.listeners;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.lr12.test.config.ThreadSafeDriver;

public class TestListener implements ITestListener {
    private static final Logger logger = Logger.getLogger(TestListener.class.getName());
    private static final String SCREENSHOT_PATH = "target/screenshots/";

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("НАЧАЛО ТЕСТА: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("УСПЕХ: " + result.getMethod().getMethodName() + 
            " (Время: " + (result.getEndMillis() - result.getStartMillis()) + "ms)");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.severe("ОШИБКА: " + result.getMethod().getMethodName());
        logger.severe("Причина: " + result.getThrowable().getMessage());
        
        // Скриншот при ошибке
        takeScreenshot(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warning("⏭ПРОПУЩЕН: " + result.getMethod().getMethodName());
    }

    /**
     * Делает скриншот при ошибке
     */
    private void takeScreenshot(String testName) {
        try {
            WebDriver driver = ThreadSafeDriver.getDriver();
            
            if (driver == null) {
                logger.warning("WebDriver не инициализирован, скриншот не сделан");
                return;
            }
            
            // Создание директории если её нет
            Files.createDirectories(Paths.get(SCREENSHOT_PATH));
            
            // Генерация имени файла
            String timestamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
            String filename = SCREENSHOT_PATH + testName + "_" + timestamp + ".png";
            
            // Получение скриншота
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), Paths.get(filename));
            
            logger.info("Скриншот сохранён: " + filename);
            
        } catch (IOException | RuntimeException e) {
            logger.warning("Не удалось сделать скриншот: " + e.getMessage());
        }
    }
}
