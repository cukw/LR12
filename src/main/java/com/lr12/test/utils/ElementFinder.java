package com.lr12.test.utils;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ElementFinder {
    private static final Logger logger = Logger.getLogger(ElementFinder.class.getName());
    private WebDriver driver;

    public ElementFinder(WebDriver driver) {
        this.driver = driver;
    }

    public Optional<WebElement> findButtonByAnyAttribute(String searchTerm) {
        logger.info("Ищу кнопку: " + searchTerm);
        
        String[] xpathStrategies = {
            // 1. Точное совпадение текста
            "//button[text()='" + searchTerm + "']",
            
            // 2. Содержит текст
            "//button[contains(text(), '" + searchTerm + "')]",
            
            // 3. Нормализированный текст (убирает пробелы)
            "//button[normalize-space()='" + searchTerm + "']",
            
            // 4. Содержит в нормализированном
            "//button[contains(normalize-space(), '" + searchTerm + "')]",
            
            // 5. По атрибуту class
            "//button[contains(@class, '" + searchTerm + "')]",
            
            // 6. По атрибуту id
            "//button[@id='" + searchTerm + "']",
            
            // 7. По атрибуту onclick
            "//button[contains(@onclick, '" + searchTerm + "')]",
            
            // 8. Частичное совпадение в любом атрибуте
            "//button[contains(@*, '" + searchTerm + "')]"
        };
        
        for (String xpath : xpathStrategies) {
            try {
                List<WebElement> elements = driver.findElements(By.xpath(xpath));
                if (!elements.isEmpty()) {
                    WebElement element = elements.get(0);
                    if (element.isDisplayed()) {
                        logger.info("Найдена кнопка по стратегии: " + xpath);
                        logElementInfo(element);
                        return Optional.of(element);
                    }
                }
            } catch (Exception e) {
            }
        }
        
        logger.warning("Кнопка не найдена: " + searchTerm);
        return Optional.empty();
    }

    /**
     * Найти input по типу
     */
    public Optional<WebElement> findInputByType(String type) {
        logger.info("Ищу input type='" + type + "'");
        
        try {
            WebElement element = driver.findElement(By.xpath("//input[@type='" + type + "']"));
            logger.info("Найден input");
            logElementInfo(element);
            return Optional.of(element);
        } catch (Exception e) {
            logger.warning("Input не найден: " + type);
            return Optional.empty();
        }
    }

    /**
     * Найти все элементы и вернуть тот, который содержит текст
     */
    public Optional<WebElement> findElementByText(String tagName, String text) {
        logger.info("🔍 Ищу <" + tagName + "> с текстом: " + text);
        
        try {
            List<WebElement> elements = driver.findElements(By.tagName(tagName));
            
            for (WebElement element : elements) {
                if (element.getText().contains(text) && element.isDisplayed()) {
                    logger.info("Найден элемент");
                    logElementInfo(element);
                    return Optional.of(element);
                }
            }
        } catch (Exception e) {
            logger.warning("Элемент не найден");
        }
        
        return Optional.empty();
    }

    /**
     * Найти все кнопки и вернуть список с логированием
     */
    public List<WebElement> findAllButtons() {
        logger.info("Ищу ВСЕ кнопки...");
        
        try {
            List<WebElement> buttons = driver.findElements(By.tagName("button"));
            logger.info("Найдено кнопок: " + buttons.size());
            
            for (int i = 0; i < buttons.size(); i++) {
                WebElement btn = buttons.get(i);
                logger.info("  [" + i + "] Text: '" + btn.getText() + "', Class: '" + 
                           btn.getAttribute("class") + "', OnClick: '" + 
                           btn.getAttribute("onclick") + "'");
            }
            
            return buttons;
        } catch (Exception e) {
            logger.severe("Ошибка при поиске кнопок: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Найти все inputs и вернуть список с логированием
     */
    public List<WebElement> findAllInputs() {
        logger.info("🔍 Ищу ВСЕ inputs...");
        
        try {
            List<WebElement> inputs = driver.findElements(By.tagName("input"));
            logger.info("Найдено inputs: " + inputs.size());
            
            for (int i = 0; i < inputs.size(); i++) {
                WebElement input = inputs.get(i);
                logger.info("  [" + i + "] Type: '" + input.getAttribute("type") + 
                           "', Name: '" + input.getAttribute("name") + 
                           "', ID: '" + input.getAttribute("id") + "'");
            }
            
            return inputs;
        } catch (Exception e) {
            logger.severe("Ошибка при поиске inputs: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Найти все таблицы и вернуть список с логированием
     */
    public List<WebElement> findAllTables() {
        logger.info("Ищу ВСЕ таблицы...");
        
        try {
            List<WebElement> tables = driver.findElements(By.tagName("table"));
            logger.info("✅ Найдено таблиц: " + tables.size());
            
            for (int i = 0; i < tables.size(); i++) {
                WebElement table = tables.get(i);
                List<WebElement> rows = table.findElements(By.tagName("tr"));
                logger.info("  [" + i + "] Строк: " + rows.size() + ", ID: '" + 
                           table.getAttribute("id") + "'");
            }
            
            return tables;
        } catch (Exception e) {
            logger.severe("Ошибка при поиске таблиц: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Найти элементы с конкретным классом
     */
    public Optional<WebElement> findElementByClass(String className) {
        logger.info("Ищу элемент с классом: " + className);
        
        try {
            List<WebElement> elements = driver.findElements(By.className(className));
            if (!elements.isEmpty()) {
                WebElement element = elements.get(0);
                logger.info("Найден элемент");
                logElementInfo(element);
                return Optional.of(element);
            }
        } catch (Exception e) {
            logger.warning("Элемент не найден");
        }
        
        return Optional.empty();
    }

    /**
     * Найти элемент по ID
     */
    public Optional<WebElement> findElementById(String id) {
        logger.info("Ищу элемент по ID: " + id);
        
        try {
            WebElement element = driver.findElement(By.id(id));
            logger.info("Найден элемент");
            logElementInfo(element);
            return Optional.of(element);
        } catch (Exception e) {
            logger.warning("Элемент не найден");
        }
        
        return Optional.empty();
    }

    /**
     * Получить количество элементов по XPath
     */
    public int countElements(By locator) {
        int count = driver.findElements(locator).size();
        logger.info("Найдено элементов: " + count);
        return count;
    }

    /**
     * Вспомогательный метод для логирования информации об элементе
     */
    private void logElementInfo(WebElement element) {
        try {
            String tag = element.getTagName();
            String text = element.getText();
            String classAttr = element.getAttribute("class");
            String id = element.getAttribute("id");
            String type = element.getAttribute("type");
            String onclick = element.getAttribute("onclick");
            
            logger.info("  ├─ Tag: " + tag);
            if (!text.isEmpty()) logger.info("  ├─ Text: '" + text + "'");
            if (classAttr != null && !classAttr.isEmpty()) logger.info("  ├─ Class: '" + classAttr + "'");
            if (id != null && !id.isEmpty()) logger.info("  ├─ ID: '" + id + "'");
            if (type != null && !type.isEmpty()) logger.info("  ├─ Type: '" + type + "'");
            if (onclick != null && !onclick.isEmpty()) logger.info("  └─ OnClick: '" + onclick + "'");
        } catch (Exception e) {
            logger.warning("Ошибка при логировании элемента");
        }
    }
}
