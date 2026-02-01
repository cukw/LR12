package com.lr12.test.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.lr12.test.base.TestBase;
import com.lr12.test.pages.DropdownPage;

public class DropdownTest extends TestBase {

    private DropdownPage page;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        page = new DropdownPage();
        page.navigateToPage();
    }

    /**
     * Тест 1: Получить все опции и проверить их количество
     */
    @Test(description = "Получить все опции dropdown'а и проверить количество")
    public void testDropdownContainsAllOptions() {
        List<String> optionTexts = page.getAllOptionTexts();

        
        Assert.assertTrue(optionTexts.size() > 0, "Dropdown должен содержать опции");
        Assert.assertTrue(optionTexts.contains("Option 1"), "Dropdown должен содержать Option 1");
        Assert.assertTrue(optionTexts.contains("Option 2"), "Dropdown должен содержать Option 2");
    }

    /**
     * Тест 2: Выбрать первую опцию и проверить
     */
    @Test(description = "Выбрать первую опцию и проверить, что она выбрана")
    public void testSelectFirstOption() {
        
        String expectedOption = "Option 1";

        
        page.selectFirstOption();

        
        String selectedOption = page.getSelectedOption();
        Assert.assertEquals(selectedOption, expectedOption, 
            "Должна быть выбрана опция: " + expectedOption);
    }

    /**
     * Тест 3: Выбрать вторую опцию и проверить
     */
    @Test(description = "Выбрать вторую опцию и проверить, что она выбрана")
    public void testSelectSecondOption() {
        
        String expectedOption = "Option 2";

        
        page.selectSecondOption();

        
        String selectedOption = page.getSelectedOption();
        Assert.assertEquals(selectedOption, expectedOption, 
            "Должна быть выбрана опция: " + expectedOption);
    }

    /**
     * Тест 4: Выбрать опцию и переключиться на другую
     */
    @Test(description = "Выбрать первую опцию, потом вторую и проверить")
    public void testSwitchBetweenOptions() {
        page.selectFirstOption();
        String firstSelection = page.getSelectedOption();
        Assert.assertEquals(firstSelection, "Option 1");

        page.selectSecondOption();
        String secondSelection = page.getSelectedOption();

        
        Assert.assertEquals(secondSelection, "Option 2", "Должна быть выбрана вторая опция");
    }

    /**
     * Тест 5: Проверить количество опций
     */
    @Test(description = "Проверить количество опций в dropdown'е")
    public void testDropdownOptionsCount() {
        int count = page.getOptionsCount();

        
        Assert.assertTrue(count >= 3, "Dropdown должен содержать минимум 3 опции");
    }

    /**
     * Тест 6: Проверить, что опция существует
     */
    @Test(description = "Проверить, что определённые опции существуют")
    public void testOptionExists() {
        Assert.assertTrue(page.optionExists("Option 1"), "Option 1 должна существовать");
        Assert.assertTrue(page.optionExists("Option 2"), "Option 2 должна существовать");
        Assert.assertFalse(page.optionExists("Option 999"), "Option 999 не должна существовать");
    }
}
