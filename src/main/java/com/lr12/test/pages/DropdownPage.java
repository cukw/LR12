package com.lr12.test.pages;

import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DropdownPage extends BasePage {
    private static final Logger logger = Logger.getLogger(DropdownPage.class.getName());

    private static final By DROPDOWN = By.id("dropdown");
    private static final By DROPDOWN_OPTIONS = By.cssSelector("#dropdown > option");

    public DropdownPage() {
        super();
    }

    public void navigateToPage() {
        navigateToPath("/dropdown");
        logger.info("Перейдено на страницу Dropdown");
    }

    /**
     * Получить все опции dropdown'а
     */
    public List<WebElement> getAllOptions() {
        List<WebElement> options = getAllOptions(DROPDOWN);
        logger.info("Найдено опций: " + options.size());
        return options;
    }

    /**
     * Получить текст опции по индексу
     */
    public String getOptionTextByIndex(int index) {
        List<WebElement> options = getAllOptions();
        
        if (index < 0 || index >= options.size()) {
            logger.severe("Индекс " + index + " вне диапазона");
            throw new IndexOutOfBoundsException("Индекс " + index + " вне диапазона");
        }
        
        String optionText = options.get(index).getText();
        logger.info("Текст опции #" + index + ": " + optionText);
        return optionText;
    }

    /**
     * Выбрать опцию по индексу
     */
    public void selectOptionByIndex(int index) {
        selectByIndex(DROPDOWN, index);
        String selectedOption = getSelectedOption(DROPDOWN);
        logger.info("Выбрана опция #" + index + ": " + selectedOption);
    }

    /**
     * Выбрать опцию по тексту
     */
    public void selectOptionByText(String text) {
        selectByVisibleText(DROPDOWN, text);
        logger.info("Выбрана опция: " + text);
    }

    /**
     * Выбрать опцию по value
     */
    public void selectOptionByValue(String value) {
        selectByValue(DROPDOWN, value);
        String selectedOption = getSelectedOption(DROPDOWN);
        logger.info("Выбрана опция с value: " + value + " (" + selectedOption + ")");
    }

    /**
     * Получить выбранную опцию
     */
    public String getSelectedOption() {
        String selected = getSelectedOption(DROPDOWN);
        logger.info("Выбранная опция: " + selected);
        return selected;
    }

    /**
     * Проверить, что опция выбрана
     */
    public boolean isOptionSelected(String optionText) {
        String selectedOption = getSelectedOption();
        boolean isSelected = selectedOption.equals(optionText);
        logger.info("Опция '" + optionText + "' выбрана: " + isSelected);
        return isSelected;
    }

    /**
     * Выбрать первую опцию (Option 1)
     */
    public void selectFirstOption() {
        selectOptionByIndex(1);
    }

    /**
     * Выбрать вторую опцию (Option 2)
     */
    public void selectSecondOption() {
        selectOptionByIndex(2);
    }

    /**
     * Получить количество опций
     */
    public int getOptionsCount() {
        int count = getAllOptions().size();
        logger.info("Всего опций: " + count);
        return count;
    }

    /**
     * Получить все тексты опций
     */
    public List<String> getAllOptionTexts() {
        List<WebElement> options = getAllOptions();
        List<String> optionTexts = options.stream()
            .map(WebElement::getText)
            .toList();
        logger.info("Все опции: " + optionTexts);
        return optionTexts;
    }

    /**
     * Проверить, что опция существует
     */
    public boolean optionExists(String optionText) {
        List<String> optionTexts = getAllOptionTexts();
        boolean exists = optionTexts.contains(optionText);
        logger.info("Опция '" + optionText + "' существует: " + exists);
        return exists;
    }
}
