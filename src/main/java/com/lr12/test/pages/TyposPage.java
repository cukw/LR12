package com.lr12.test.pages;

import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.lr12.test.utils.ElementFinder;

public class TyposPage extends BasePage {
    private static final Logger logger = Logger.getLogger(TyposPage.class.getName());
    private ElementFinder finder;

    // ==================== ЛОКАТОРЫ ====================
    private static final By PARAGRAPH = By.xpath("//p");

    // ==================== КОНСТРУКТОР ====================
    public TyposPage() {
        super();
        this.finder = new ElementFinder(driver);
    }

    // ==================== НАВИГАЦИЯ ====================
    public void navigateToPage() {
        navigateToPath("/typos");
        logger.info("Перейдено на страницу Typos");
        sleep(1000);
    }

    // ==================== МЕТОДЫ ====================

    /**
     * Получить текст первого параграфа
     */
    public String getParagraphText() {
        try {
            WebElement paragraph = findVisibleElement(PARAGRAPH);
            String text = paragraph.getText();
            logger.info("Текст параграфа: '" + text + "'");
            return text;
        } catch (Exception e) {
            logger.warning("Параграф не найден: " + e.getMessage());
            return "";
        }
    }

    /**
     * Проверить что параграф содержит текст (case-insensitive)
     */
    public boolean paragraphContains(String searchText) {
        String text = getParagraphText().toLowerCase();
        String search = searchText.toLowerCase();
        
        boolean contains = text.contains(search);
        logger.info("Параграф содержит '" + searchText + "' (case-insensitive): " + contains);
        
        return contains;
    }

    /**
     * Проверить точное совпадение текста
     */
    public boolean paragraphEqualsExact(String expectedText) {
        String actualText = getParagraphText();
        boolean equals = actualText.equals(expectedText);
        
        if (!equals) {
            logger.warning("Текст не совпадает!");
            logger.warning("  Ожидалось: '" + expectedText + "'");
            logger.warning("  Получено: '" + actualText + "'");
        } else {
            logger.info("Текст совпадает точно");
        }
        
        return equals;
    }

    /**
     * Получить все параграфы на странице
     */
    public List<WebElement> getAllParagraphs() {
        return findElements(PARAGRAPH);
    }

    /**
     * Получить количество слов в параграфе
     */
    public int getWordCount() {
        String text = getParagraphText();
        if (text.isEmpty()) return 0;
        String[] words = text.split("\\s+");
        int count = words.length;
        logger.info("Количество слов: " + count);
        return count;
    }

    /**
     * Получить длину текста параграфа
     */
    public int getTextLength() {
        String text = getParagraphText();
        int length = text.length();
        logger.info("Длина текста: " + length);
        return length;
    }

    /**
     * Проверить что параграф начинается со слова
     */
    public boolean startsWithWord(String word) {
        String text = getParagraphText();
        boolean starts = text.toLowerCase().startsWith(word.toLowerCase());
        logger.info("Параграф начинается с '" + word + "': " + starts);
        return starts;
    }
}
