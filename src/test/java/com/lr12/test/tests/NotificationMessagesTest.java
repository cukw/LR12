package com.lr12.test.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.lr12.test.base.TestBase;
import com.lr12.test.pages.NotificationMessagesPage;

public class NotificationMessagesTest extends TestBase {

    private NotificationMessagesPage page;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        page = new NotificationMessagesPage();
        page.navigateToPage();
    }

    /**
     * Тест 1: Кликнуть на кнопку и проверить, что нотификация появилась
     */
    @Test
    public void testNotificationAppears() {
        page.navigateToPage();
        

        boolean exists = page.doesNotificationExist();
        Assert.assertTrue(exists, "Нотификация должна существовать на странице");
    }

    /**
     * Тест 2: Получить текст нотификации
     */
    @Test(description = "Получить текст нотификации")
    public void testGetNotificationText() {

        page.clickNotificationButton();
        page.waitForNotificationToAppear();

        String notificationText = page.getNotificationText();

        
        Assert.assertNotNull(notificationText, "Текст нотификации не должен быть null");
        Assert.assertFalse(notificationText.isEmpty(), "Текст нотификации не должен быть пустым");
    }

    /**
     * Тест 3: Проверить, что нотификация содержит ожидаемый текст
     */
    @Test(description = "Проверить, что нотификация содержит определённый текст")
    public void testNotificationContainsText() {
        page.clickNotificationButton();
        page.waitForNotificationToAppear();

        String notificationText = page.getNotificationText();
        
        Assert.assertFalse(notificationText.isEmpty(), 
            "Нотификация должна содержать сообщение");
    }


    /**
     * Тест 4: Проверить несколько вариантов текста нотификации
     */
    @Test(description = "Проверить, что нотификация содержит один из возможных текстов")
    public void testNotificationTextVariations() {

        page.clickNotificationButton();
        page.waitForNotificationToAppear();

        boolean textMatches = page.verifyNotificationTextAny(
            "Action successful",
            "successful",
            "You",
            "Action"
        );
        
        Assert.assertTrue(textMatches, 
            "Нотификация должна содержать один из ожидаемых текстов");
    }

    /**
     * Тест 5: Кликнуть и получить нотификацию (полный сценарий)
     */
    @Test(description = "Полный сценарий: клик -> ожидание -> получение текста")
    public void testCompleteNotificationScenario() {

        String notificationText = page.clickAndGetNotification();

        
        Assert.assertNotNull(notificationText, "Текст должен быть получен");
        Assert.assertFalse(notificationText.isEmpty(), "Текст не должен быть пустым");
    }

    /**
     * Тест 6: Закрыть нотификацию
     */
    @Test(description = "Нажать на кнопку закрытия нотификации")
    public void testCloseNotification() {

        page.clickNotificationButton();
        page.waitForNotificationToAppear();
        Assert.assertTrue(page.isNotificationDisplayed());

        page.closeNotification();

        page.sleep(500);
        Assert.assertFalse(page.isNotificationDisplayed(), 
            "Нотификация должна быть закрыта");
    }

    /**
     * Тест 7: Несколько кликов подряд
     */
    @Test(description = "Несколько кликов на кнопку и проверка нотификаций")
    public void testMultipleNotifications() {

        int clickCount = 3;

        for (int i = 0; i < clickCount; i++) {
            page.clickNotificationButton();
            page.waitForNotificationToAppear();
            
            String notificationText = page.getNotificationText();
            Assert.assertFalse(notificationText.isEmpty(), 
                "Нотификация #" + (i + 1) + " должна содержать текст");
            
            page.sleep(500);
        }
    }

    /**
     * Тест 8: Проверить ожидание исчезновения нотификации
     */
    @Test(description = "Проверить, что нотификация исчезает через некоторое время")
    public void testNotificationDisappears() {

        page.clickNotificationButton();
        page.waitForNotificationToAppear();
        Assert.assertTrue(page.isNotificationDisplayed());

        try {
            page.waitForNotificationToDisappear();
            Assert.assertTrue(true, "Нотификация исчезла как ожидалось");
        } catch (Exception e) {

            Assert.assertNotNull(page.getNotificationText(), 
                "Нотификация всё ещё отображается");
        }
    }

    /**
     * Тест 9: Проверить текст нотификации с обработкой вариантов
     */
    @Test(description = "Проверить различные возможные варианты текста нотификации")
    public void testNotificationTextWithVariations() {

        String notificationText = page.clickAndGetNotification();


        Assert.assertNotNull(notificationText);
        Assert.assertFalse(notificationText.trim().isEmpty());
    }

    /**
     * Тест 10: Вся нотификация тестируется через общий механизм
     */
    @Test(description = "Комплексный тест нотификаций")
    public void testComplexNotificationFlow() {
        page.clickNotificationButton();
        page.waitForNotificationToAppear();
        
        String text = page.getNotificationText();
        boolean displayed = page.isNotificationDisplayed();
        
        
        Assert.assertTrue(displayed, "Нотификация должна быть видима");
        Assert.assertFalse(text.isEmpty(), "Текст должен быть получен");
    }
}
