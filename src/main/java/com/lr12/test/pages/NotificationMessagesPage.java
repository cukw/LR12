package com.lr12.test.pages;

import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

public class NotificationMessagesPage extends BasePage {
    private static final Logger logger = Logger.getLogger(NotificationMessagesPage.class.getName());

    private static final By NOTIFICATION_BUTTON = By.xpath("//a[@href='/notification_message']");
    private static final By NOTIFICATION_MESSAGE = By.id("flash");
    private static final By NOTIFICATION_CLOSE_BUTTON = By.xpath("//a[@class='close']");

    public NotificationMessagesPage() {
        super();
    }

    public void navigateToPage() {
        navigateToPath("/notification_message");
        logger.info("Перейдено на страницу Notification Messages");
    }

    /**
     * Кликнуть на кнопку, чтобы вызвать нотификацию
     */
    public void clickNotificationButton() {
        click(NOTIFICATION_BUTTON);
        logger.info("Нотификация кнопка нажата");
    }

    /**
     * Ожидание появления нотификации
     */
    public void waitForNotificationToAppear() {
        try {
            findVisibleElement(NOTIFICATION_MESSAGE);
            logger.info("Нотификация появилась");
        } catch (TimeoutException e) {
            logger.severe("Нотификация не появилась");
            throw e;
        }
    }

    /**
     * Ожидание исчезновения нотификации
     */
    public void waitForNotificationToDisappear() {
        try {
            waitForElementToDisappear(NOTIFICATION_MESSAGE);
            logger.info("Нотификация исчезла");
        } catch (TimeoutException e) {
            logger.severe("Нотификация не исчезла");
            throw e;
        }
    }

    /**
     * Проверить, что нотификация отображается
     */
    public boolean isNotificationDisplayed() {
        boolean displayed = isElementDisplayed(NOTIFICATION_MESSAGE);
        logger.info("Нотификация отображается: " + displayed);
        return displayed;
    }

    /**
     * Закрыть нотификацию
     */
    public void closeNotification() {
        try {
            click(NOTIFICATION_CLOSE_BUTTON);
            logger.info("Нотификация закрыта");
        } catch (Exception e) {
            logger.warning("Не удалось закрыть нотификацию: " + e.getMessage());
        }
    }

    /**
     * Полный сценарий: клик -> ожидание -> проверка текста
     */
    public String clickAndGetNotification() {
        clickNotificationButton();
        waitForNotificationToAppear();
        return getNotificationText();
    }

    /**
     * Проверить текст нотификации (точное совпадение)
     */
    public boolean verifyNotificationText(String expectedText) {
        String actualText = getNotificationText();
        
        boolean matches = actualText.contains(expectedText);
        
        if (matches) {
            logger.info("Текст нотификации совпадает: '" + expectedText + "'");
        } else {
            logger.warning("Текст нотификации не совпадает");
            logger.warning("Ожидалось содержание: '" + expectedText + "'");
            logger.warning("Получено: '" + actualText + "'");
        }
        
        return matches;
    }

    /**
     * Проверить один из возможных текстов
     */
    public boolean verifyNotificationTextAny(String... possibleTexts) {
        String actualText = getNotificationText();
        
        for (String expectedText : possibleTexts) {
            if (actualText.contains(expectedText)) {
                logger.info("Текст нотификации совпадает одному из вариантов: '" + expectedText + "'");
                return true;
            }
        }
        
        logger.warning("Текст нотификации не совпадает ни одному из вариантов");
        logger.warning("Получено: '" + actualText + "'");
        logger.warning("Ожидалось один из: ");
        for (String text : possibleTexts) {
            logger.warning("  - " + text);
        }
        
        return false;
    }

    /**
     * Несколько кликов подряд с проверкой нотификаций
     */
    public void multipleNotificationsTest(int clickCount) {
        for (int i = 0; i < clickCount; i++) {
            logger.info("\n--- Нотификация " + (i + 1) + " ---");
            clickNotificationButton();
            waitForNotificationToAppear();
            String notificationText = getNotificationText();
            logger.info("Текст: " + notificationText);
            sleep(500);
        }
        logger.info("\nВсе " + clickCount + " нотификаций успешно протестированы");
    }

    /**
     * Проверить видима ли нотификация
     */
    public boolean isNotificationVisible() {
        try {
            WebElement notification = driver.findElement(By.id("flash"));
            
            String display = notification.getCssValue("display");
            String visibility = notification.getCssValue("visibility");
            
            boolean isVisible = !display.equals("none") && !visibility.equals("hidden");
            
            logger.info("Notification display: " + display + ", visibility: " + visibility);
            
            return isVisible;
        } catch (Exception e) {
            logger.warning("Нотификация не найдена: " + e.getMessage());
            return false;
        }
    }

    /**
     * Проверить что нотификация существует на странице
     */
    public boolean doesNotificationExist() {
        try {
            WebElement notification = driver.findElement(By.id("flash"));
            logger.info("Нотификация существует на странице");
            return true;
        } catch (Exception e) {
            logger.warning("Нотификация не найдена на странице");
            return false;
        }
    }

    /**
     * Получить текст нотификации
     */
    public String getNotificationText() {
        try {
            WebElement notification = driver.findElement(By.id("flash"));
            String text = notification.getText();
            logger.info("Текст нотификации: '" + text + "'");
            return text;
        } catch (Exception e) {
            logger.warning("Нотификация не найдена");
            return "";
        }
    }

}
