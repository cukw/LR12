package com.lr12.test.pages;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.lr12.test.utils.ElementFinder;

public class AddRemoveElementsPage extends BasePage {
    private static final Logger logger = Logger.getLogger(AddRemoveElementsPage.class.getName());
    private ElementFinder finder;

    private static final By ADD_BUTTON = By.xpath("//button[contains(text(), 'Add')]");
    private static final By DELETE_BUTTONS = By.xpath("//button[contains(., 'Delete')]");
    private static final By ELEMENTS_CONTAINER = By.id("elements");

    public AddRemoveElementsPage() {
        super();
        this.finder = new ElementFinder(driver);
    }

    public void navigateToPage() {
        navigateToPath("/add_remove_elements/");
        logger.info("Перейдено на страницу Add/Remove Elements");

        finder.findAllButtons();
    }

    /**
     * Добавить один элемент (с автоматическим поиском)
     */
    public void addElement() {
        logger.info("Попытка добавить элемент");

        try {
            List<WebElement> buttons = findElements(ADD_BUTTON);
            if (!buttons.isEmpty()) {
                buttons.get(0).click();
                sleep(500);
                logger.info("Элемент добавлен (XPath)");
                return;
            }
        } catch (Exception e) {
            logger.warning("XPath не сработал: " + e.getMessage());
        }

        Optional<WebElement> addBtn = finder.findButtonByAnyAttribute("Add");
        if (addBtn.isPresent()) {
            addBtn.get().click();
            sleep(500);
            logger.info("Элемент добавлен (ElementFinder)");
            return;
        }

        List<WebElement> allButtons = finder.findAllButtons();
        for (WebElement btn : allButtons) {
            if (btn.getText().contains("Add") || btn.getText().contains("add")) {
                btn.click();
                sleep(500);
                logger.info("Элемент добавлен (перебор всех кнопок)");
                return;
            }
        }
        
        logger.severe("Не удалось найти кнопку Add");
        throw new RuntimeException("Кнопка Add Element не найдена!");
    }

    /**
     * Добавить несколько элементов
     */
    public void addMultipleElements(int count) {
        for (int i = 0; i < count; i++) {
            addElement();
        }
        logger.info("Добавлено " + count + " элементов");
    }

    /**
     * Получить количество DELETE кнопок
     */
    public int getDeleteButtonsCount() {
        logger.info("Ищу DELETE кнопки");

        try {
            List<WebElement> deleteButtons = findElements(DELETE_BUTTONS);
            if (!deleteButtons.isEmpty()) {
                logger.info("Найдено DELETE кнопок (XPath): " + deleteButtons.size());
                return deleteButtons.size();
            }
        } catch (Exception e) {
            logger.warning("XPath не сработал");
        }

        List<WebElement> allButtons = finder.findAllButtons();
        int deleteCount = 0;
        for (WebElement btn : allButtons) {
            String text = btn.getText();
            String className = btn.getAttribute("class");
            String onclick = btn.getAttribute("onclick");
            
            if (text.contains("Delete") || text.contains("delete") ||
                className.contains("delete") || 
                (onclick != null && onclick.contains("delete"))) {
                deleteCount++;
            }
        }
        
        logger.info("Найдено DELETE кнопок: " + deleteCount);
        return deleteCount;
    }

    /**
     * Получить все DELETE кнопки (с фильтрацией)
     */
    private List<WebElement> getDeleteButtons() {
        List<WebElement> allButtons = finder.findAllButtons();
        
        return allButtons.stream()
            .filter(btn -> {
                String text = btn.getText();
                String className = btn.getAttribute("class");
                String onclick = btn.getAttribute("onclick");
                
                return text.contains("Delete") || 
                       className.contains("delete") || 
                       (onclick != null && onclick.contains("delete"));
            })
            .toList();
    }

    /**
     * Удалить элемент по индексу
     */
    public void deleteElement(int index) {
        logger.info("Удаляю элемент #" + index);
        
        List<WebElement> deleteButtons = getDeleteButtons();
        
        if (index < 0 || index >= deleteButtons.size()) {
            logger.severe("Индекс " + index + " вне диапазона (всего: " + deleteButtons.size() + ")");
            throw new IndexOutOfBoundsException("Индекс " + index + " вне диапазона");
        }
        
        deleteButtons.get(index).click();
        sleep(500);
        logger.info("Элемент #" + index + " удалён");
    }

    /**
     * Удалить первый элемент
     */
    public void deleteFirstElement() {
        deleteElement(0);
    }

    /**
     * Удалить все элементы
     */
    public void deleteAllElements() {
        while (getDeleteButtonsCount() > 0) {
            deleteFirstElement();
        }
        logger.info("Все элементы удалены");
    }

    /**
     * Проверить, что элементы присутствуют
     */
    public boolean areElementsPresent() {
        return getDeleteButtonsCount() > 0;
    }

    public void deleteLastElement() {
        List<WebElement> deleteButtons = getDeleteButtons();
        
        if (deleteButtons.isEmpty()) {
            logger.severe("Нет элементов для удаления");
            throw new RuntimeException("Нет DELETE кнопок");
        }
        
        deleteButtons.get(deleteButtons.size() - 1).click();
        sleep(500);
        logger.info("Последний элемент удалён");
    }

    /**
     * Получить количество добавленных элементов
     */
    public int getAddedElementsCount() {
        return getDeleteButtonsCount();
    }
}
