package com.lr12.test.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.lr12.test.base.TestBase;
import com.lr12.test.pages.InputsPage;

public class InputsTest extends TestBase {

    private InputsPage page;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        page = new InputsPage();
        page.navigateToPage();
    }

    /**
     * Тест 1: Ввести числа
     */
    @Test(description = "Ввести цифровые значения в input")
    public void testInputNumericValues() {

        String numericValue = "12345";

        
        page.enterNumber(numericValue);

        
        String actualValue = page.getInputValue();
        Assert.assertEquals(actualValue, numericValue, 
            "Input должен содержать введённое число");
    }

    /**
     * Тест 2: Ввести букво-цифровые значения
     */
    @Test
    public void testInputAlphanumeric() {
        page.navigateToPage();
        

        page.enterValue("123");

        String value = page.getInputValue();
        Assert.assertEquals(value, "123", "Input должен содержать цифры");
    }

    /**
     * Тест 3: Ввести специальные символы
     */
    @Test(description = "Ввести специальные символы в input")
        public void testInputSpecialCharacters() {
        page.navigateToPage();
        
        page.enterValue("456");
        
        String value = page.getInputValue();

        Assert.assertEquals(value, "456", "Input должен содержать только цифры");
    }

    /**
     * Тест 4: Очистить input
     */
    @Test(description = "Ввести значение, затем очистить input")
    public void testClearInput() {

        page.enterNumber("123");
        Assert.assertFalse(page.getInputValue().isEmpty(), "Input не должен быть пустым");

        
        page.clearInput();

        
        String actualValue = page.getInputValue();
        Assert.assertTrue(actualValue.isEmpty(), "Input должен быть пустым после очистки");
    }

    /**
     * Тест 5: Ввести число и отправить стрелку вверх
     */
    @Test(description = "Ввести число и отправить стрелку вверх (ARROW_UP)")
    public void testInputWithArrowUp() {

        int initialValue = 5;

        
        page.enterNumberAndArrowUp(initialValue);

        
        String finalValue = page.getInputValue();
        Assert.assertNotEquals(finalValue, String.valueOf(initialValue), 
            "Стрелка вверх должна изменить значение");
    }

    /**
     * Тест 6: Ввести число и отправить стрелку вниз
     */
    @Test(description = "Ввести число и отправить стрелку вниз (ARROW_DOWN)")
    public void testInputWithArrowDown() {

        int initialValue = 10;

        
        page.enterNumberAndArrowDown(initialValue);

        
        String finalValue = page.getInputValue();
        Assert.assertNotEquals(finalValue, String.valueOf(initialValue), 
            "Стрелка вниз должна изменить значение");
    }

    /**
     * Тест 7: Несколько стрелок вверх
     */
    @Test(description = "Ввести число и отправить несколько стрелок вверх")
    public void testMultipleArrowUp() {

        page.enterNumber("5");
        String initialValue = page.getInputValue();

        
        page.sendMultipleArrowUp(3);

        
        String finalValue = page.getInputValue();
        Assert.assertNotEquals(finalValue, initialValue, 
            "Значение должно измениться после стрелок вверх");
    }

    /**
     * Тест 8: Несколько стрелок вниз
     */
    @Test(description = "Ввести число и отправить несколько стрелок вниз")
    public void testMultipleArrowDown() {

        page.enterNumber("15");
        String initialValue = page.getInputValue();

        
        page.sendMultipleArrowDown(3);

        
        String finalValue = page.getInputValue();
        Assert.assertNotEquals(finalValue, initialValue, 
            "Значение должно измениться после стрелок вниз");
    }
}
