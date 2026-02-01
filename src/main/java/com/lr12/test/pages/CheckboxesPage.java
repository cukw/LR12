package com.lr12.test.pages;

import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CheckboxesPage extends BasePage {
    private static final Logger logger = Logger.getLogger(CheckboxesPage.class.getName());

    private static final By CHECKBOXES = By.cssSelector("[type=checkbox]");

    public CheckboxesPage() {
        super();
    }

    public void navigateToPage() {
        navigateToPath("/checkboxes");
        logger.info("Перейдено на страницу Checkboxes");
    }

    /**
     * Получить все чекбоксы
     */
    public List<WebElement> getAllCheckboxes() {
        List<WebElement> checkboxes = findElements(CHECKBOXES);
        logger.info("Найдено чекбоксов: " + checkboxes.size());
        return checkboxes;
    }

    /**
     * Получить чекбокс по индексу
     */
    public WebElement getCheckboxByIndex(int index) {
        List<WebElement> checkboxes = getAllCheckboxes();
        
        if (index < 0 || index >= checkboxes.size()) {
            logger.severe("Индекс " + index + " вне диапазона");
            throw new IndexOutOfBoundsException("Индекс " + index + " вне диапазона");
        }
        
        return checkboxes.get(index);
    }

    /**
     * Проверить, отмечен ли чекбокс
     */
    public boolean isCheckboxChecked(int index) {
        WebElement checkbox = getCheckboxByIndex(index);
        boolean isChecked = checkbox.isSelected();
        logger.info("Чекбокс #" + index + " отмечен: " + isChecked);
        return isChecked;
    }

    /**
     * Отметить чекбокс
     */
    public void checkCheckbox(int index) {
        WebElement checkbox = getCheckboxByIndex(index);
        
        if (!checkbox.isSelected()) {
            checkbox.click();
            logger.info("Чекбокс #" + index + " отмечен");
        } else {
            logger.info("ℹЧекбокс #" + index + " уже отмечен");
        }
    }

    /**
     * Снять отметку с чекбокса
     */
    public void uncheckCheckbox(int index) {
        WebElement checkbox = getCheckboxByIndex(index);
        
        if (checkbox.isSelected()) {
            checkbox.click();
            logger.info("Чекбокс #" + index + " снят");
        } else {
            logger.info("ℹЧекбокс #" + index + " уже снят");
        }
    }

    /**
     * Переключить чекбокс (toggle)
     */
    public void toggleCheckbox(int index) {
        WebElement checkbox = getCheckboxByIndex(index);
        boolean wasCheсked = checkbox.isSelected();
        checkbox.click();
        logger.info("Чекбокс #" + index + " переключен с " + wasCheсked + " на " + !wasCheсked);
    }

    /**
     * Отметить первый чекбокс
     */
    public void checkFirstCheckbox() {
        checkCheckbox(0);
    }

    /**
     * Отметить второй чекбокс
     */
    public void checkSecondCheckbox() {
        checkCheckbox(1);
    }

    /**
     * Снять отметку со второго чекбокса
     */
    public void uncheckSecondCheckbox() {
        uncheckCheckbox(1);
    }

    /**
     * Проверить состояние первого чекбокса
     */
    public boolean isFirstCheckboxChecked() {
        return isCheckboxChecked(0);
    }

    /**
     * Проверить состояние второго чекбокса
     */
    public boolean isSecondCheckboxChecked() {
        return isCheckboxChecked(1);
    }
}
