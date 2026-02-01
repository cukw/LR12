package com.lr12.test.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class SortableDataTablesPage extends BasePage 
{
    private static final Logger logger = Logger.getLogger(SortableDataTablesPage.class.getName());

    private static final By TABLES = By.tagName("table");

    public SortableDataTablesPage() {
        super();
    }

    public void navigateToPage() {
        navigateToPath("/tables");
        logger.info("Перейдено на страницу Sortable Data Tables");
    }

    /**
     * Получить все таблицы на странице
     */
    public List<WebElement> getAllTables() {
        List<WebElement> tables = findElements(TABLES);
        logger.info("Найдено таблиц: " + tables.size());
        return tables;
    }

    /**
     * Получить таблицу по индексу
     */
    public WebElement getTableByIndex(int tableIndex) {
        List<WebElement> tables = getAllTables();
        
        if (tableIndex < 0 || tableIndex >= tables.size()) {
            logger.severe("Индекс таблицы " + tableIndex + " вне диапазона");
            throw new IndexOutOfBoundsException("Индекс таблицы " + tableIndex + " вне диапазона");
        }
        
        return tables.get(tableIndex);
    }

    /**
     * Получить значение ячейки таблицы
     */
    public String getCellValue(int tableIndex, int row, int col) {
        String xpathExpression = String.format(
            "(//table)[%d]//tr[%d]//td[%d]",
            tableIndex + 1,
            row + 1,
            col + 1
        );
        
        By cellLocator = By.xpath(xpathExpression);
        String cellValue = getText(cellLocator);
        logger.info("Ячейка [" + tableIndex + "][" + row + "][" + col + "]: " + cellValue);
        return cellValue;
    }

    public int getTableRowCount() {
        try {
            String xpath = "(//table)[1]//tr";
            java.util.List<WebElement> rows = findElements(By.xpath(xpath));
            int count = rows.size();
            logger.info("Количество строк в таблице: " + count);
            return count;
        } catch (Exception e) {
            logger.warning("Ошибка при подсчёте строк: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Получить данные конкретной строки
     */
    public java.util.List<String> getRowValuesSafe(int rowIndex) {
        java.util.List<String> rowData = new java.util.ArrayList<>();
        
        try {
            int totalRows = getTableRowCount();
            
            if (rowIndex < 1 || rowIndex > totalRows) {
                logger.warning("Строка " + rowIndex + " вне диапазона (всего: " + totalRows + ")");
                return rowData;
            }
            
            String xpath = "(//table)[1]//tr[" + rowIndex + "]//td";
            java.util.List<WebElement> cells = findElements(By.xpath(xpath));
            
            for (WebElement cell : cells) {
                rowData.add(cell.getText());
            }
            
            logger.info("Данные строки " + rowIndex + ": " + rowData);
        } catch (Exception e) {
            logger.warning("Ошибка при получении данных строки: " + e.getMessage());
        }
        
        return rowData;
    }

    /**
     * Получить все ячейки из строки таблицы
     */
    public List<String> getRowValues(int tableIndex, int row) {
        String xpathExpression = String.format(
            "(//table)[%d]//tr[%d]//td",
            tableIndex + 1,
            row + 1
        );
        
        By rowLocator = By.xpath(xpathExpression);
        List<WebElement> cells = findElements(rowLocator);
        
        List<String> rowValues = new ArrayList<>();
        for (WebElement cell : cells) {
            rowValues.add(cell.getText());
        }
        
        logger.info("Строка [" + tableIndex + "][" + row + "]: " + rowValues);
        return rowValues;
    }

    /**
     * Получить все ячейки из колонки таблицы
     */
    public List<String> getColumnValues(int tableIndex, int col) {
        String xpathExpression = String.format(
            "(//table)[%d]//tr//td[%d]",
            tableIndex + 1,
            col + 1
        );
        
        By colLocator = By.xpath(xpathExpression);
        List<WebElement> cells = findElements(colLocator);
        
        List<String> colValues = new ArrayList<>();
        for (WebElement cell : cells) {
            colValues.add(cell.getText());
        }
        
        logger.info("Колонка [" + tableIndex + "][" + col + "]: " + colValues);
        return colValues;
    }

    /**
     * Получить все данные таблицы
     */
    public List<List<String>> getTableData(int tableIndex) {
        String xpathExpression = String.format(
            "(//table)[%d]//tr",
            tableIndex + 1
        );
        
        By rowsLocator = By.xpath(xpathExpression);
        List<WebElement> rows = findElements(rowsLocator);
        
        List<List<String>> tableData = new ArrayList<>();
        for (int i = 0; i < rows.size(); i++) {
            tableData.add(getRowValues(tableIndex, i));
        }
        
        logger.info("Таблица [" + tableIndex + "] загружена (" + tableData.size() + " строк)");
        return tableData;
    }

    /**
     * Получить количество строк в таблице
     */
    public int getRowCount(int tableIndex) {
        String xpathExpression = String.format(
            "(//table)[%d]//tr",
            tableIndex + 1
        );
        
        List<WebElement> rows = findElements(By.xpath(xpathExpression));
        int count = rows.size();
        logger.info("Количество строк в таблице [" + tableIndex + "]: " + count);
        return count;
    }

    /**
     * Получить количество колонок в таблице
     */
    public int getColumnCount(int tableIndex) {
        String xpathExpression = String.format(
            "(//table)[%d]//tr[1]//td",
            tableIndex + 1
        );
        
        List<WebElement> cols = findElements(By.xpath(xpathExpression));
        int count = cols.size();
        logger.info("Количество колонок в таблице [" + tableIndex + "]: " + count);
        return count;
    }

    /**
     * Проверить значение ячейки
     */
    public boolean verifyCellValue(int tableIndex, int row, int col, String expectedValue) {
        String actualValue = getCellValue(tableIndex, row, col);
        boolean matches = actualValue.equals(expectedValue);
        
        if (matches) {
            logger.info("Ячейка [" + tableIndex + "][" + row + "][" + col + "] содержит: '" + expectedValue + "'");
        } else {
            logger.warning("Ячейка [" + tableIndex + "][" + row + "][" + col + "] не совпадает");
            logger.warning("Ожидалось: '" + expectedValue + "', получено: '" + actualValue + "'");
        }
        
        return matches;
    }

    /**
     * Проверить значения в строке
     */
    public boolean verifyRowValues(int tableIndex, int row, List<String> expectedValues) {
        List<String> actualValues = getRowValues(tableIndex, row);
        boolean matches = actualValues.equals(expectedValues);
        
        if (matches) {
            logger.info("Строка [" + tableIndex + "][" + row + "] совпадает");
        } else {
            logger.warning("Строка [" + tableIndex + "][" + row + "] не совпадает");
        }
        
        return matches;
    }

    /**
     * Найти ячейку с текстом
     */
    public boolean cellContainsText(int tableIndex, int row, int col, String text) {
        String cellValue = getCellValue(tableIndex, row, col);
        boolean contains = cellValue.contains(text);
        
        if (contains) {
            logger.info("Ячейка [" + tableIndex + "][" + row + "][" + col + "] содержит: '" + text + "'");
        } else {
            logger.warning("Ячейка [" + tableIndex + "][" + row + "][" + col + "] не содержит: '" + text + "'");
        }
        
        return contains;
    }

    /**
     * Кликнуть на ячейку
     */
    public void clickCell(int tableIndex, int row, int col) {
        String xpathExpression = String.format(
            "(//table)[%d]//tr[%d]//td[%d]",
            tableIndex + 1,
            row + 1,
            col + 1
        );
        
        By cellLocator = By.xpath(xpathExpression);
        click(cellLocator);
        logger.info("Клик по ячейке [" + tableIndex + "][" + row + "][" + col + "]");
    }

    /**
     * Получить все значения из первой таблицы для удобства
     */
    public List<List<String>> getFirstTableData() {
        return getTableData(0);
    }

    /**
     * Получить значение ячейки из первой таблицы для удобства
     */
    public String getFirstTableCellValue(int row, int col) {
        return getCellValue(0, row, col);
    }
}
