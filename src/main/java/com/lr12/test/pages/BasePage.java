package com.lr12.test.pages;

import java.time.Duration;
import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.lr12.test.config.ConfigReader;
import com.lr12.test.config.ThreadSafeDriver;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    private static final Logger logger = Logger.getLogger(BasePage.class.getName());

    public BasePage() {
        this.driver = ThreadSafeDriver.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
    }

    /**
     * Перейти на страницу
     */
    public void navigateTo(String url) {
        logger.info("Переход на URL: " + url);
        driver.navigate().to(url);
        waitForPageLoad();
    }

    /**
     * Перейти на страницу с путём
     */
    public void navigateToPath(String path) {
        String fullUrl = ConfigReader.getBaseUrl() + path;
        navigateTo(fullUrl);
    }

    /**
     * Ожидание загрузки страницы
     */
    public void waitForPageLoad() {
        wait.until(d -> ((JavascriptExecutor) d).executeScript(
            "return document.readyState").equals("complete"));
    }

    /**
     * Найти элемент (с явным ожиданием)
     */
    public WebElement findElement(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            logger.info("Элемент найден: " + locator);
            return element;
        } catch (TimeoutException e) {
            logger.severe("Элемент не найден за отведённое время: " + locator);
            throw e;
        }
    }

    /**
     * Найти элемент (виден на экране)
     */
    public WebElement findVisibleElement(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            logger.info("Видимый элемент найден: " + locator);
            return element;
        } catch (TimeoutException e) {
            logger.severe("Видимый элемент не найден: " + locator);
            throw e;
        }
    }

    /**
     * Найти все элементы
     */
    public List<WebElement> findElements(By locator) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        return driver.findElements(locator);
    }

    /**
     * Клик по элементу
     */
    public void click(By locator) {
        WebElement element = findVisibleElement(locator);
        try {
            element.click();
            logger.info("Клик выполнен: " + locator);
        } catch (ElementClickInterceptedException e) {
            logger.warning("Элемент перекрыт, используем JavaScript клик");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    /**
     * Ввод текста
     */
    public void sendKeys(By locator, String text) {
        WebElement element = findVisibleElement(locator);
        element.clear();
        element.sendKeys(text);
        logger.info("Текст введён: '" + text + "' в " + locator);
    }

    /**
     * Ввод текста с задержкой между символами
     */
    public void sendKeysSlowly(By locator, String text) {
        WebElement element = findVisibleElement(locator);
        element.clear();
        for (char c : text.toCharArray()) {
            element.sendKeys(String.valueOf(c));
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                logger.warning("Сон прерван: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        logger.info("Текст введён медленно: '" + text + "'");
    }
    /**
     * Отправить клавишу
     */
    public void sendKey(By locator, Keys key) {
        WebElement element = findVisibleElement(locator);
        element.sendKeys(key);
        logger.info("Клавиша отправлена: " + key);
    }

    /**
     * Очистить инпут
     */
    public void clearInput(By locator) {
        WebElement element = findVisibleElement(locator);
        element.clear();
        logger.info("Инпут очищен: " + locator);
    }

    /**
     * Получить текст элемента
     */
    public String getText(By locator) {
        WebElement element = findVisibleElement(locator);
        String text = element.getText();
        logger.info("Текст элемента получен: '" + text + "'");
        return text;
    }

    /**
     * Получить атрибут элемента
     */
    public String getAttribute(By locator, String attributeName) {
        WebElement element = findElement(locator);
        String attributeValue = element.getAttribute(attributeName);
        logger.info("Атрибут '" + attributeName + "' = '" + attributeValue + "'");
        return attributeValue;
    }

    /**
     * Получить значение инпута
     */
    public String getInputValue(By locator) {
        return getAttribute(locator, "value");
    }

    /**
     * Проверить, что элемент отображается
     */
    public boolean isElementDisplayed(By locator) {
        try {
            return findVisibleElement(locator).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Проверить, что элемент существует
     */
    public boolean isElementPresent(By locator) {
        try {
            findElement(locator);
            return true;
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Проверить, что чекбокс отмечен
     */
    public boolean isCheckboxChecked(By locator) {
        WebElement element = findElement(locator);
        boolean isChecked = element.isSelected();
        logger.info("Чекбокс отмечен: " + isChecked);
        return isChecked;
    }

    /**
     * Проверить, что элемент включен
     */
    public boolean isElementEnabled(By locator) {
        return findElement(locator).isEnabled();
    }

    /**
     * Выбрать опцию по значению
     */
    public void selectByValue(By locator, String value) {
        WebElement element = findVisibleElement(locator);
        Select select = new Select(element);
        select.selectByValue(value);
        logger.info("Опция выбрана по value: " + value);
    }

    /**
     * Выбрать опцию по тексту
     */
    public void selectByVisibleText(By locator, String text) {
        WebElement element = findVisibleElement(locator);
        Select select = new Select(element);
        select.selectByVisibleText(text);
        logger.info("Опция выбрана по тексту: " + text);
    }

    /**
     * Выбрать опцию по индексу
     */
    public void selectByIndex(By locator, int index) {
        WebElement element = findVisibleElement(locator);
        Select select = new Select(element);
        select.selectByIndex(index);
        logger.info("Опция выбрана по индексу: " + index);
    }

    /**
     * Получить все опции
     */
    public List<WebElement> getAllOptions(By locator) {
        WebElement element = findVisibleElement(locator);
        Select select = new Select(element);
        return select.getOptions();
    }

    /**
     * Получить выбранную опцию
     */
    public String getSelectedOption(By locator) {
        WebElement element = findVisibleElement(locator);
        Select select = new Select(element);
        return select.getFirstSelectedOption().getText();
    }

    /**
     * Наведение на элемент
     */
    public void hoverOverElement(By locator) {
        WebElement element = findVisibleElement(locator);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        logger.info("Наведение выполнено на: " + locator);
    }

    /**
     * Drag and Drop
     */
    public void dragAndDrop(By sourceLocator, By targetLocator) {
        WebElement source = findVisibleElement(sourceLocator);
        WebElement target = findVisibleElement(targetLocator);
        Actions actions = new Actions(driver);
        actions.dragAndDrop(source, target).perform();
        logger.info("Drag and Drop выполнен");
    }

    /**
     * Двойной клик
     */
    public void doubleClick(By locator) {
        WebElement element = findVisibleElement(locator);
        Actions actions = new Actions(driver);
        actions.doubleClick(element).perform();
        logger.info("Двойной клик выполнен: " + locator);
    }

    /**
     * Ожидание исчезновения элемента
     */
    public void waitForElementToDisappear(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        logger.info("Элемент исчез: " + locator);
    }

    /**
     * Ожидание появления элемента
     */
    public void waitForElementToAppear(By locator) {
        findVisibleElement(locator);
        logger.info("Элемент появился: " + locator);
    }

    /**
     * Ожидание, пока элемент станет кликабельным
     */
    public void waitForElementToBeClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        logger.info("Элемент кликабелен: " + locator);
    }

    /**
     * Кастомное ожидание
     */
    public void waitForCondition(long timeoutSeconds, String condition) {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        customWait.until(d -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return true;
        });
    }


    /**
     * Выполнить JavaScript
     */
    public Object executeScript(String script, Object... args) {
        return ((JavascriptExecutor) driver).executeScript(script, args);
    }

    /**
     * Скроллить к элементу
     */
    public void scrollToElement(By locator) {
        WebElement element = findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        logger.info("Скроллинг к элементу: " + locator);
    }

    /**
     * Скроллить вниз
     */
    public void scrollDown(int pixels) {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, " + pixels + ");");
        logger.info("Скроллинг вниз на " + pixels + " пикселей");
    }

    /**
     * Получить текущий URL
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Получить заголовок страницы
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Сделать паузу (не рекомендуется, но иногда нужно)
     */
    public void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Переключиться на другой tab
     */
    public void switchToNewTab() {
        driver.switchTo().window(driver.getWindowHandles().stream()
            .filter(handle -> !handle.equals(driver.getWindowHandle()))
            .findFirst()
            .orElseThrow());
    }

    /**
     * Закрыть текущий tab
     */
    public void closeCurrentTab() {
        driver.close();
        driver.switchTo().window(driver.getWindowHandles().iterator().next());
    }

    /**
     * Перезагрузить страницу
     */
    public void refreshPage() {
        driver.navigate().refresh();
        logger.info("Страница перезагружена");
    }
}
