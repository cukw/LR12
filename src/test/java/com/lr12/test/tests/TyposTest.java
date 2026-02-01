package com.lr12.test.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.lr12.test.base.TestBase;
import com.lr12.test.pages.TyposPage;

public class TyposTest extends TestBase {

    private TyposPage page;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        page = new TyposPage();
        page.navigateToPage();
    }

    /**
     * Тест 1: Получить текст параграфа
     */
    @Test(description = "Получить текст параграфа со страницы")
    public void testGetParagraphText() {
        String paragraphText = page.getParagraphText();

        Assert.assertNotNull(paragraphText, "Текст параграфа не должен быть null");
        Assert.assertFalse(paragraphText.isEmpty(), "Текст параграфа не должен быть пустым");
    }

    /**
     * Тест 2: Проверить орфографию (содержит определённый текст)
     */
    @Test
    public void testParagraphContainsText() {
        page.navigateToPage();
        
        boolean contains = page.paragraphContains("typo");
        Assert.assertTrue(contains, "Параграф должен содержать: 'typo' или 'Typo'");
    }

    /**
     * Тест 3: Проверить количество слов
     */
    @Test(description = "Получить количество слов в параграфе")
    public void testWordCount() {
        int wordCount = page.getWordCount();

        Assert.assertTrue(wordCount > 0, "Параграф должен содержать слова");
        Assert.assertTrue(wordCount > 5, "Параграф должен содержать более 5 слов");
    }

    /**
     * Тест 4: Проверить длину текста
     */
    @Test(description = "Получить длину текста параграфа")
    public void testTextLength() {
        int textLength = page.getTextLength();

        Assert.assertTrue(textLength > 0, "Длина текста должна быть больше 0");
        Assert.assertTrue(textLength > 50, "Длина текста должна быть больше 50 символов");
    }

    /**
     * Тест 5: Проверить, что текст начинается с определённого слова
     */
    @Test(description = "Проверить, что параграф начинается с определённого слова")
    public void testParagraphStartsWith() {
        String paragraphText = page.getParagraphText();
        String firstWord = paragraphText.split("\\s+")[0];
        
        Assert.assertTrue(page.startsWithWord(firstWord), 
            "Параграф должен начинаться с первого слова");
    }

    /**
     * Тест 6: Проверить наличие опечаток (демонстрация)
     */
    @Test
    public void testTypoDetection() {
        page.navigateToPage();
        
        String text = page.getParagraphText();
        
        boolean hasTypo = text.toLowerCase().contains("typo");
        
        Assert.assertTrue(hasTypo, 
            "Параграф должен содержать 'typo', получено: '" + text + "'");
    }
}
