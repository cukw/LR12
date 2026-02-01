package com.lr12.test.pages;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.lr12.test.utils.ElementFinder;

public class InputsPage extends BasePage {
    private static final Logger logger = Logger.getLogger(InputsPage.class.getName());
    private ElementFinder finder;

    public InputsPage() {
        super();
        this.finder = new ElementFinder(driver);
    }

    public void navigateToPage() {
        navigateToPath("/inputs");
        logger.info("Перейдено на страницу Inputs");
        
        // DEBUG: Вывести все inputs на странице
        sleep(1000);
        finder.findAllInputs();
    }

    /**
     * Получить input field (с автоматическим поиском)
     */
    public WebElement getInputField() {
        logger.info("Ищу input field");
        
        Optional<WebElement> input = finder.findInputByType("number");
        if (input.isPresent()) {
            return input.get();
        }
        
        List<WebElement> allInputs = finder.findAllInputs();
        for (WebElement in : allInputs) {
            try {
                if (in.isDisplayed()) {
                    logger.info("Найден input");
                    return in;
                }
            } catch (Exception e) {
            }
        }
        
        logger.severe("Input field не найден");
        throw new RuntimeException("Input field не найден!");
    }

    /**
     * Ввести число
     */
    public void enterNumber(String number) {
        logger.info("Ввожу число: " + number);
        
        WebElement input = getInputField();
        input.clear();
        input.sendKeys(number);
        sleep(300);
        logger.info("Число введено");
    }

    /**
     * Ввести текст (для number input будут только цифры)
     */
    public void enterText(String text) {
        enterNumber(text);
    }

    /**
     * Ввести буквы и цифры
     */
    public void enterAlphanumeric(String value) {
        logger.info("Ввожу буквы и цифры: " + value);
        
        WebElement input = getInputField();
        input.clear();
        input.sendKeys(value);
        sleep(300);
        logger.info("Буквы и цифры введены");
    }

    /**
     * Ввести специальные символы
     */
    public void enterSpecialCharacters(String value) {
        logger.info("Ввожу спецсимволы: " + value);
        
        WebElement input = getInputField();
        input.clear();
        input.sendKeys(value);
        sleep(300);
        logger.info("Спецсимволы введены");
    }

    /**
     * Ввести значение
     */
    public void enterValue(String value) {
        enterNumber(value);
    }

    /**
     * Получить текущее значение input
     */
    public String getInputValue() {
        String value = getInputField().getAttribute("value");
        logger.info("Текущее значение: '" + value + "'");
        return value;
    }

    /**
     * Очистить input
     */
    public void clearInput() {
        logger.info("Очищаю input");
        
        WebElement input = getInputField();
        input.clear();
        sleep(300);
        logger.info("Input очищен");
    }

    /**
     * Отправить стрелку вверх
     */
    public void sendArrowUp() {
        getInputField().sendKeys(Keys.ARROW_UP);
        sleep(300);
        logger.info("Стрелка вверх отправлена");
    }

    /**
     * Отправить стрелку вниз
     */
    public void sendArrowDown() {
        getInputField().sendKeys(Keys.ARROW_DOWN);
        sleep(300);
        logger.info("Стрелка вниз отправлена");
    }

    /**
     * Ввести число и отправить стрелку вверх
     */
    public void enterNumberAndArrowUp(int number) {
        clearInput();
        enterNumber(String.valueOf(number));
        sendArrowUp();
        logger.info("Число " + number + " введено и стрелка вверх отправлена");
    }

    /**
     * Ввести число и отправить стрелку вниз
     */
    public void enterNumberAndArrowDown(int number) {
        clearInput();
        enterNumber(String.valueOf(number));
        sendArrowDown();
        logger.info("Число " + number + " введено и стрелка вниз отправлена");
    }

    /**
     * Отправить несколько стрелок вверх
     */
    public void sendMultipleArrowUp(int count) {
        WebElement input = getInputField();
        for (int i = 0; i < count; i++) {
            input.sendKeys(Keys.ARROW_UP);
            sleep(100);
        }
        logger.info(count + " стрелок вверх отправлено");
    }

    /**
     * Отправить несколько стрелок вниз
     */
    public void sendMultipleArrowDown(int count) {
        WebElement input = getInputField();
        for (int i = 0; i < count; i++) {
            input.sendKeys(Keys.ARROW_DOWN);
            sleep(100);
        }
        logger.info(count + " стрелок вниз отправлено");
    }

    /**
     * Проверить значение
     */
    public boolean verifyValue(String expectedValue) {
        String actualValue = getInputValue();
        boolean matches = actualValue.equals(expectedValue);
        
        if (matches) {
            logger.info("Значение совпадает: " + expectedValue);
        } else {
            logger.warning("Значение не совпадает. Ожидалось: '" + expectedValue + 
                          "', получено: '" + actualValue + "'");
        }
        
        return matches;
    }

    /**
     * Проверить, что значение содержит текст
     */
    public boolean valueContains(String text) {
        String value = getInputValue();
        boolean contains = value.contains(text);
        
        if (contains) {
            logger.info("Значение содержит: '" + text + "'");
        } else {
            logger.warning("Значение не содержит: '" + text + "'");
        }
        
        return contains;
    }
}
