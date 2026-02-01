package com.lr12.test.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.lr12.test.base.TestBase;
import com.lr12.test.pages.CheckboxesPage;

public class CheckboxesTest extends TestBase {

    private CheckboxesPage page;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        page = new CheckboxesPage();
        page.navigateToPage();
    }

    /**
     * Тест 1: Проверить, что первый чекбокс изначально не отмечен
     */
    @Test(description = "Проверить, что первый чекбокс unchecked")
    public void testFirstCheckboxInitialStateUnchecked() {

        boolean isChecked = page.isFirstCheckboxChecked();

        
        Assert.assertFalse(isChecked, "Первый чекбокс должен быть unchecked изначально");
    }

    /**
     * Тест 2: Отметить первый чекбокс и проверить
     */
    @Test(description = "Отметить первый чекбокс и проверить, что он checked")
    public void testCheckFirstCheckbox() {
        
        Assert.assertFalse(page.isFirstCheckboxChecked());

        
        page.checkFirstCheckbox();

        
        Assert.assertTrue(page.isFirstCheckboxChecked(), 
            "Первый чекбокс должен быть checked после клика");
    }

    /**
     * Тест 3: Проверить начальное состояние второго чекбокса
     */
    @Test(description = "Проверить, что второй чекбокс checked")
    public void testSecondCheckboxInitialStateChecked() {
        boolean isChecked = page.isSecondCheckboxChecked();

        
        Assert.assertTrue(isChecked, "Второй чекбокс должен быть checked изначально");
    }

    /**
     * Тест 4: Снять отметку со второго чекбокса
     */
    @Test(description = "Снять отметку со второго чекбокса и проверить, что он unchecked")
    public void testUncheckSecondCheckbox() {
        
        Assert.assertTrue(page.isSecondCheckboxChecked());

        
        page.uncheckSecondCheckbox();

        
        Assert.assertFalse(page.isSecondCheckboxChecked(), 
            "Второй чекбокс должен быть unchecked после клика");
    }

    /**
     * Тест 5: Переключить оба чекбокса несколько раз
     */
    @Test(description = "Переключить оба чекбокса и проверить их состояния")
    public void testToggleBothCheckboxes() {
        
        boolean initialState0 = page.isFirstCheckboxChecked();
        boolean initialState1 = page.isSecondCheckboxChecked();

        
        page.toggleCheckbox(0);
        page.toggleCheckbox(1);

        
        Assert.assertNotEquals(page.isFirstCheckboxChecked(), initialState0, 
            "Состояние первого чекбокса должно измениться");
        Assert.assertNotEquals(page.isSecondCheckboxChecked(), initialState1, 
            "Состояние второго чекбокса должно измениться");
    }

    /**
     * Тест 6: Отметить и снять отметку (toggle) несколько раз
     */
    @Test(description = "Несколько раз переключить чекбокс и вернуть в исходное состояние")
    public void testToggleCheckboxMultipleTimes() {
        
        boolean initialState = page.isFirstCheckboxChecked();

        
        page.toggleCheckbox(0);
        page.toggleCheckbox(0);

        
        boolean finalState = page.isFirstCheckboxChecked();
        Assert.assertEquals(finalState, initialState, 
            "После двух переключений чекбокс должен вернуться в исходное состояние");
    }
}
