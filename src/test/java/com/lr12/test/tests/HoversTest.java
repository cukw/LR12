package com.lr12.test.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.lr12.test.base.TestBase;
import com.lr12.test.pages.HoversPage;

public class HoversTest extends TestBase {

    private HoversPage page;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        page = new HoversPage();
        page.navigateToPage();
    }

    /**
     * Тест 1: Получить количество профилей на странице
     */
    @Test(description = "Получить количество профилей (avatars) на странице")
    public void testGetUserProfilesCount() {
        
        int profileCount = page.getUserProfilesCount();


        Assert.assertTrue(profileCount > 0, "На странице должны быть профили");
        Assert.assertTrue(profileCount >= 3, "Должно быть минимум 3 профиля");
    }

    /**
     * Тест 2: Проверить, что первый профиль отображается
     */
    @Test(description = "Проверить, что первый профиль отображается на странице")
    public void testFirstUserProfileDisplayed() {
        
        boolean isDisplayed = page.isUserProfileDisplayed(0);


        Assert.assertTrue(isDisplayed, "Первый профиль должен быть виден");
    }

    /**
     * Тест 3: Наведение на первый профиль и получение имени
     */
    @Test(description = "Навести на первый профиль и получить его имя")
    public void testHoverFirstProfileAndGetName() {

        int profileIndex = 0;

        
        String userName = page.getUserName(profileIndex);


        Assert.assertNotNull(userName, "Имя пользователя не должно быть null");
        Assert.assertFalse(userName.isEmpty(), "Имя пользователя не должно быть пустым");
        Assert.assertTrue(userName.contains("user"), 
            "Имя должно содержать 'user' или похожий паттерн");
    }

    /**
     * Тест 4: Получить ID пользователя
     */
    @Test(description = "Получить ID первого пользователя")
    public void testGetUserIdHover() {

        int profileIndex = 0;

        
        String userId = page.getUserId(profileIndex);


        Assert.assertNotNull(userId, "ID пользователя не должен быть null");
        Assert.assertFalse(userId.isEmpty(), "ID пользователя не должен быть пустым");
    }

    /**
     * Тест 5: Кликнуть на ссылку первого профиля
     */
    @Test(description = "Кликнуть на ссылку первого профиля и проверить загрузку")
    public void testClickFirstUserProfile() {

        int profileIndex = 0;
        try {
            page.clickUserProfileAndVerify(profileIndex);
            // Если мы здесь, значит не было 404 ошибки
            Assert.assertTrue(true, "Профиль загружен успешно");
        } catch (RuntimeException e) {
            if (e.getMessage().contains("404")) {
                Assert.fail("Получена 404 ошибка при загрузке профиля");
            }
            throw e;
        }
    }

    /**
     * Тест 6: Наведение и клик для каждого профиля (работает, но может быть долгим)
     */
    @Test(description = "Для первого профиля: наведение, проверка имени, клик, проверка загрузки")
    public void testHoverVerifyNameAndClick() {

        int profileIndex = 0;

        
        String userName = page.getUserName(profileIndex);
        page.clickUserProfileAndVerify(profileIndex);


        Assert.assertFalse(userName.isEmpty(), "Имя должно быть получено");
        // Если мы здесь, значит страница загружена без 404
    }

    /**
     * Тест 7: Проверить второй профиль
     */
    @Test(description = "Получить имя второго пользователя и проверить")
    public void testSecondUserProfile() {

        int profileIndex = 1;

        
        String userName = page.getUserName(profileIndex);


        Assert.assertNotNull(userName, "Имя второго пользователя не должно быть null");
        Assert.assertFalse(userName.isEmpty());
    }

    /**
     * Тест 8: Проверить третий профиль
     */
    @Test(description = "Получить имя третьего пользователя и проверить")
    public void testThirdUserProfile() {

        int profileIndex = 2;

        
        String userName = page.getUserName(profileIndex);


        Assert.assertNotNull(userName, "Имя третьего пользователя не должно быть null");
        Assert.assertFalse(userName.isEmpty());
    }

    /**
     * Тест 9: Наведение на все профили и проверка имён
     */
    @Test(description = "Наведение на все видимые профили и проверка, что имена получены")
    public void testAllUserProfilesHover() {

        int profileCount = page.getUserProfilesCount();

        for (int i = 0; i < profileCount; i++) {
            String userName = page.getUserName(i);
            Assert.assertFalse(userName.isEmpty(), 
                "Имя профиля #" + i + " не должно быть пустым");
        }
    }

    /**
     * Тест 10: Кликнуть и вернуться для каждого профиля (комплексный тест)
     */
    @Test(description = "Для каждого профиля: клик, проверка, возврат")
    public void testClickAllUserProfiles() {

        int profileCount = page.getUserProfilesCount();

        for (int i = 0; i < profileCount; i++) {
            try {
                String userName = page.getUserName(i);
                page.clickUserProfileAndVerify(i);
                
                // Возвращаемся на страницу hovers
                page.navigateToPage();
                
                Assert.assertTrue(true, "Профиль #" + i + " успешно протестирован");
            } catch (Exception e) {
                Assert.fail("Ошибка при тестировании профиля #" + i + ": " + e.getMessage());
            }
        }
    }
}
