package utils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.*;
import java.util.Arrays;

public class XLUtilis {
    public static FileInputStream fis;
    public static FileOutputStream fos;
    public static XSSFWorkbook workbook;
    public static XSSFSheet sheet;
    public static XSSFRow row;
    public static XSSFCell cell;


    public static int getRowCount(String xlFile, String xlSheet) {
        int rowCount = 0;
        try {
            fis = new FileInputStream(new File(xlFile));
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(xlSheet);
            rowCount = sheet.getLastRowNum();
            workbook.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rowCount;
    }

    public static int getCellCount(String xlFile, String xlSheet, int rowNum) {
        int cellCount = 0;
        try {
            fis = new FileInputStream(new File(xlFile));
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(xlSheet);
            row = sheet.getRow(rowNum);
            cellCount = row.getLastCellNum();
            workbook.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cellCount;
    }

    public static String getCellData(String xlFile, String xlSheet, int rowNum, int colNum) {
        String cellData = "";
        try {
            fis = new FileInputStream(new File(xlFile));
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(xlSheet);
            row = sheet.getRow(rowNum);
            cell = row.getCell(colNum);

            DataFormatter formatter = new DataFormatter();
            cellData = formatter.formatCellValue(cell);
            workbook.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cellData;
    }

    public static void setCellData(String xlFile, String xlSheet, int rowNum, int colNum, String cellData) {
        try {
            fis = new FileInputStream(new File(xlFile));
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(xlSheet);
            row = sheet.getRow(rowNum);
            cell = row.getCell(colNum);
            cell.setCellValue(cellData);
            fos  = new FileOutputStream(xlFile);
            workbook.write(fos);
            workbook.close();
            fis.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object[][] getExcelData(String xlFile, String xlSheet) {
        String data[][] = null;
        try {
            int totalNoOfRows = getRowCount(xlFile, xlSheet);
            int totalNoOfCols = getCellCount(xlFile, xlSheet, totalNoOfRows);
            data = new String[totalNoOfRows][totalNoOfCols];

            for (int i = 0; i < totalNoOfCols; i++) {
                data[totalNoOfRows-1][i] = getCellData(xlFile, xlSheet, 1, i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
