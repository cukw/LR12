package com.lr12.test.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.lr12.test.base.TestBase;
import com.lr12.test.pages.AddRemoveElementsPage;

public class AddRemoveElementsTest extends TestBase {

    private AddRemoveElementsPage page;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        page = new AddRemoveElementsPage();
        page.navigateToPage();
    }

    /**
     * Тест 1: Добавить одиночный элемент и проверить количество
     */
    @Test(description = "Добавить 1 элемент и проверить количество DELETE кнопок")
    public void testAddSingleElement() {
        int initialCount = page.getDeleteButtonsCount();
        Assert.assertEquals(initialCount, 0, "Изначально должно быть 0 элементов");

        page.addElement();

        int actualCount = page.getDeleteButtonsCount();
        Assert.assertEquals(actualCount, 1, "После добавления должно быть 1 элемент");
    }

    /**
     * Тест 2: Добавить несколько элементов
     */
    @Test(description = "Добавить 2 элемента и проверить количество")
    public void testAddMultipleElements() {
        
        int countToAdd = 2;
        int initialCount = page.getDeleteButtonsCount();

        
        page.addMultipleElements(countToAdd);

        
        int actualCount = page.getDeleteButtonsCount();
        Assert.assertEquals(actualCount, countToAdd, 
            "После добавления " + countToAdd + " элементов должно быть " + countToAdd);
    }

    /**
     * Тест 3: Добавить элемент и удалить его
     */
    @Test(description = "Добавить элемент, удалить его и проверить количество")
    public void testAddAndDeleteElement() {
        
        page.addElement();
        int countAfterAdd = page.getDeleteButtonsCount();
        Assert.assertEquals(countAfterAdd, 1, "После добавления должно быть 1 элемент");

        
        page.deleteFirstElement();

        
        int countAfterDelete = page.getDeleteButtonsCount();
        Assert.assertEquals(countAfterDelete, 0, "После удаления должно быть 0 элементов");
    }

    /**
     * Тест 4: Добавить несколько и удалить один
     */
    @Test(description = "Добавить 3 элемента, удалить 1 и проверить количество")
    public void testAddMultipleAndDeleteOne() {
        
        int countToAdd = 3;
        page.addMultipleElements(countToAdd);
        Assert.assertEquals(page.getDeleteButtonsCount(), countToAdd);

        
        page.deleteElement(1); // Удаляем второй элемент

        
        int expectedCount = countToAdd - 1;
        Assert.assertEquals(page.getDeleteButtonsCount(), expectedCount, 
            "После удаления одного должно быть " + expectedCount + " элементов");
    }

    /**
     * Тест 5: Удалить последний элемент из нескольких
     */
    @Test(description = "Добавить элементы, удалить последний и проверить")
    public void testDeleteLastElement() {
        
        page.addMultipleElements(3);
        int countBefore = page.getDeleteButtonsCount();

        
        page.deleteLastElement();

        
        int countAfter = page.getDeleteButtonsCount();
        Assert.assertEquals(countAfter, countBefore - 1);
    }

    /**
     * Тест 6: Очистить все элементы
     */
    @Test(description = "Добавить элементы, удалить все и проверить")
    public void testDeleteAllElements() {
        
        page.addMultipleElements(5);
        Assert.assertEquals(page.getDeleteButtonsCount(), 5);

        
        page.deleteAllElements();

        
        Assert.assertEquals(page.getDeleteButtonsCount(), 0, "Все элементы должны быть удалены");
    }
}
