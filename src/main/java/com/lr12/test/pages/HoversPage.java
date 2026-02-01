package com.lr12.test.pages;

import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.lr12.test.utils.ElementFinder;

public class HoversPage extends BasePage {
    private static final Logger logger = Logger.getLogger(HoversPage.class.getName());
    private ElementFinder finder;

    private static final By FIGURES = By.xpath("//div[@class='figure']");
    private static final By FIGURE_CAPTION = By.xpath(".//div[@class='figcaption']");

    public HoversPage() {
        super();
        this.finder = new ElementFinder(driver);
    }

    public void navigateToPage() {
        navigateToPath("/hovers");
        logger.info("Перейдено на страницу Hovers");
        sleep(1000);
    }

    /**
     * Получить все figure элементы
     */
    public List<WebElement> getAllFigures() {
        List<WebElement> figures = findElements(FIGURES);
        logger.info("Найдено figure элементов: " + figures.size());
        return figures;
    }

    /**
     * Hover над фигурой по индексу
     */
    public void hoverOverFigure(int index) {
        List<WebElement> figures = getAllFigures();
        
        if (index < 0 || index >= figures.size()) {
            logger.severe("Index " + index + " вне диапазона");
            throw new IndexOutOfBoundsException("Index " + index + " out of range");
        }
        
        WebElement figure = figures.get(index);
        
        Actions actions = new Actions(driver);
        actions.moveToElement(figure).perform();
        sleep(500);
        
        logger.info("Hover над фигурой #" + index);
    }

    /**
     * Получить текст figcaption после hover
     */
    public String getFigCaptionText(int figureIndex) {
        hoverOverFigure(figureIndex);
        
        try {
            List<WebElement> figures = getAllFigures();
            WebElement figure = figures.get(figureIndex);
            
            // Ищем figcaption внутри figure
            WebElement caption = figure.findElement(FIGURE_CAPTION);
            String text = caption.getText();
            
            logger.info("Текст caption: '" + text + "'");
            return text;
        } catch (Exception e) {
            logger.warning("Caption не найден: " + e.getMessage());
            return "";
        }
    }

    /**
     * Получить user ID из hover
     */
    public String getUserId(int figureIndex) {
        String captionText = getFigCaptionText(figureIndex);
        
        // Caption содержит что-то вроде "user ID: 1234"
        if (captionText.contains(":")) {
            String[] parts = captionText.split(":");
            String userId = parts[parts.length - 1].trim();
            logger.info("User ID: " + userId);
            return userId;
        }
        
        return captionText;
    }

    /**
     * Получить количество профилей
     */
    public int getUserProfilesCount() {
        List<WebElement> figures = getAllFigures();
        int count = figures.size();
        logger.info("Количество профилей: " + count);
        return count;
    }

    /**
     * Проверить видим ли профиль
     */
    public boolean isUserProfileDisplayed(int index) {
        try {
            List<WebElement> figures = getAllFigures();
            if (index < 0 || index >= figures.size()) {
                return false;
            }
            WebElement figure = figures.get(index);
            boolean displayed = figure.isDisplayed();
            logger.info("Профиль #" + index + " видим: " + displayed);
            return displayed;
        } catch (Exception e) {
            logger.warning("Ошибка при проверке профиля: " + e.getMessage());
            return false;
        }
    }

    /**
     * Получить имя пользователя
     */
    public String getUserName(int figureIndex) {
        String captionText = getFigCaptionText(figureIndex);
        logger.info("Имя пользователя: '" + captionText + "'");
        return captionText;
    }

    /**
     * Кликнуть на профиль и проверить загрузку
     */
    public void clickUserProfileAndVerify(int figureIndex) {
        try {
            List<WebElement> figures = getAllFigures();
            WebElement figure = figures.get(figureIndex);
            
            WebElement link = figure.findElement(By.xpath(".//a"));
            
            // Прокрутить к ссылке
            ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", link);
            sleep(300);
            
            // Кликнуть через JavaScript (более надёжно)
            ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", link);
            
            sleep(1000);
            
            logger.info("Профиль #" + figureIndex + " открыт");
        } catch (Exception e) {
            logger.severe("Ошибка при клике на профиль: " + e.getMessage());
            throw new RuntimeException("Ошибка при загрузке профиля", e);
        }
    }

}
