package com.lr12.test.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.lr12.test.base.TestBase;
import com.lr12.test.pages.SortableDataTablesPage;

public class SortableDataTablesTest extends TestBase {

    private SortableDataTablesPage page;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        page = new SortableDataTablesPage();
        page.navigateToPage();
    }

    /**
     * Тест 1: Получить все таблицы со страницы
     */
    @Test(description = "Получить все таблицы и проверить их количество")
    public void testGetAllTables() {
        int tableCount = page.getAllTables().size();

        Assert.assertTrue(tableCount > 0, "На странице должны быть таблицы");
        Assert.assertTrue(tableCount >= 2, "Должно быть минимум 2 таблицы");
    }

    /**
     * Тест 2: Получить первую ячейку первой таблицы
     */
    @Test(description = "Получить значение первой ячейки из первой таблицы")
    public void testGetFirstTableFirstCell() {
        int tableIndex = 0;
        int row = 0;
        int col = 0;

        String cellValue = page.getCellValue(tableIndex, row, col);

        Assert.assertNotNull(cellValue, "Значение ячейки не должно быть null");
        Assert.assertFalse(cellValue.isEmpty(), "Значение ячейки не должно быть пустым");
    }

    /**
     * Тест 3: Проверить значение конкретной ячейки
     */
    @Test(description = "Проверить значение ячейки в первой таблице")
    public void testVerifyCellValue() {
        int tableIndex = 0;
        int row = 0;
        int col = 0;

        String cellValue = page.getCellValue(tableIndex, row, col);

        Assert.assertNotNull(cellValue, "Ячейка должна содержать значение");
        Assert.assertTrue(cellValue.length() > 0, "Ячейка не должна быть пустой");
    }

    /**
     * Тест 4: Получить количество строк в таблице
     */
    @Test(description = "Получить количество строк в первой таблице")
    public void testGetRowCount() {
        int tableIndex = 0;

        int rowCount = page.getRowCount(tableIndex);

        Assert.assertTrue(rowCount > 0, "Таблица должна содержать строки");
        Assert.assertTrue(rowCount > 1, "Таблица должна содержать минимум 2 строки (заголовок + данные)");
    }

    /**
     * Тест 5: Получить количество колонок в таблице
     */
    @Test(description = "Получить количество колонок в первой таблице")
    public void testGetColumnCount() {
        int tableIndex = 0;

        int colCount = page.getColumnCount(tableIndex);

        Assert.assertTrue(colCount > 0, "Таблица должна содержать колонки");
        Assert.assertTrue(colCount >= 3, "Таблица должна содержать минимум 3 колонки");
    }

    /**
     * Тест 6: Получить все значения из строки
     */
    @Test(description = "Получить все значения из первой строки первой таблицы")
    public void testGetRowValues() {
        int tableIndex = 0;
        int row = 0;

        List<String> rowValues = page.getRowValues(tableIndex, row);

        Assert.assertNotNull(rowValues, "Список значений не должен быть null");
        Assert.assertTrue(rowValues.size() > 0, "Строка должна содержать значения");
    }

    /**
     * Тест 7: Получить все значения из колонки
     */
    @Test(description = "Получить все значения из первой колонки первой таблицы")
    public void testGetColumnValues() {
        int tableIndex = 0;
        int col = 0;

        List<String> colValues = page.getColumnValues(tableIndex, col);

        Assert.assertNotNull(colValues, "Список значений не должен быть null");
        Assert.assertTrue(colValues.size() > 0, "Колонка должна содержать значения");
    }

    /**
     * Тест 8: Проверить несколько ячеек в таблице
     */
    @Test(description = "Проверить содержимое нескольких ячеек в таблице")
    public void testVerifyMultipleCells() {
        int tableIndex = 0;

        // Получаем значения несколько ячеек (строка 1, разные колонки)
        String cell1 = page.getCellValue(tableIndex, 1, 0);
        String cell2 = page.getCellValue(tableIndex, 1, 1);
        String cell3 = page.getCellValue(tableIndex, 1, 2);

        Assert.assertNotNull(cell1, "Ячейка [1][0] не должна быть null");
        Assert.assertNotNull(cell2, "Ячейка [1][1] не должна быть null");
        Assert.assertNotNull(cell3, "Ячейка [1][2] не должна быть null");
    }

    /**
     * Тест 9: Получить всю таблицу
     */
    @Test
    public void testGetFullTableData() {
        page.navigateToPage();

        int rowCount = page.getTableRowCount();
        
        Assert.assertTrue(rowCount > 0, "Таблица должна содержать строки");

        int validRowsCount = 0;
        for (int i = 1; i <= rowCount; i++) {
            java.util.List<String> rowData = page.getRowValuesSafe(i);

            if (!rowData.isEmpty()) {
                validRowsCount++;
            }
        }
        
        Assert.assertTrue(validRowsCount > 0, "Таблица должна содержать строки с данными");
    }

    /**
     * Тест 10: Проверить, что ячейка содержит текст
     */
    @Test(description = "Проверить, что ячейка содержит определённый текст")
    public void testCellContainsText() {
        int tableIndex = 0;
        int row = 1;
        int col = 0;
        String cellValue = page.getCellValue(tableIndex, row, col);

        Assert.assertTrue(page.cellContainsText(tableIndex, row, col, cellValue), 
            "Ячейка должна содержать свой же текст");
    }

    /**
     * Тест 11: Проверить вторую таблицу
     */
    @Test(description = "Проверить данные из второй таблицы")
    public void testSecondTableData() {
        int tableIndex = 1;

        int rowCount = page.getRowCount(tableIndex);
        int colCount = page.getColumnCount(tableIndex);

        Assert.assertTrue(rowCount > 0, "Вторая таблица должна содержать строки");
        Assert.assertTrue(colCount > 0, "Вторая таблица должна содержать колонки");
    }

    /**
     * Тест 12: Получить несколько значений ячеек для проверки содержимого
     */
    @Test(description = "Проверить содержимое 3-5 ячеек из первой таблицы")
    public void testVerifyTableContent() {
        int tableIndex = 0;

        String cell00 = page.getCellValue(tableIndex, 0, 0);
        String cell01 = page.getCellValue(tableIndex, 0, 1);
        String cell10 = page.getCellValue(tableIndex, 1, 0);
        String cell11 = page.getCellValue(tableIndex, 1, 1);
        String cell12 = page.getCellValue(tableIndex, 1, 2);

        Assert.assertFalse(cell00.isEmpty());
        Assert.assertFalse(cell01.isEmpty());
        Assert.assertFalse(cell10.isEmpty());
        Assert.assertFalse(cell11.isEmpty());
        Assert.assertFalse(cell12.isEmpty());
    }
}
