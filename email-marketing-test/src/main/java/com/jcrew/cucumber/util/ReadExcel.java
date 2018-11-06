package com.jcrew.cucumber.util;

import com.cucumber.listener.Reporter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.*;

public class ReadExcel {
    private static String excelFilePath;
    static Workbook workbook;
    static Sheet sheet;
    FileInputStream inputStream;
    static int dataRowNum;
    static int dataColumnum;

    public ReadExcel(String excelFile) {
        excelFilePath = excelFile;
        try {
            inputStream = new FileInputStream(new File(excelFilePath));
            workbook = new XSSFWorkbook(inputStream);
            sheet = workbook.getSheetAt(0);
        } catch (Exception e) {
            Reporter.addStepLog("Excel file attachment is not available in Jira ticket, could not verify additional links");
            Assert.fail();
        }
    }

    public List<String> getAllLinksFromExcelSheet() {
        List<String> links = new ArrayList<>();
        getRowColNumberContainsValue("url");
        for (int i = dataRowNum + 1; i <= sheet.getLastRowNum(); i++) {
            String link = getData(0, i, dataColumnum);
            if (link != null)
                links.add(link.trim());
        }
        return links;
    }

    public Map<String, String> getPreheaderLinkFromExcelSheet() {
        Map<String, String> preheaders = new HashMap<>();
        getRowColNumberContainsValue("Image/Section");
        for (int i = dataRowNum + 1; i <= sheet.getLastRowNum(); i++) {
            String dataName = getData(0, i, dataColumnum);
            dataName = dataName.toUpperCase().trim();
            if (dataName.endsWith("PREHEADER")) {
                String dataValue = getData(0, i, dataColumnum + 2);
                preheaders.put(dataName, dataValue);
            }
        }
        return preheaders;
    }

    public static void main(String[] args) {
        ReadExcel r = new ReadExcel(System.getProperty("user.dir") + "/testdata/JCBEMAIL-3491/170912_updated_M_New_Arrivals_linking.xlsx");
        Map<String, String> ln = r.getPreheaderLinkFromExcelSheet();
        for (String data : ln.keySet()) {
            System.out.println(data);
        }
    }

    public void getRowColNumberContainsValue(String cellData) {
        for (int row = 0; row <= sheet.getLastRowNum(); row++) {
            for (int column = 0; column <= sheet.getRow(row).getLastCellNum(); column++) {
                Cell cell = sheet.getRow(row).getCell(column);

                if (cell != null && cell.getStringCellValue().equalsIgnoreCase(cellData)) {
                    //rowCol.put(row,column);
                    dataRowNum = row;
                    dataColumnum = column;
                }
            }
        }
    }


    public String getData(int sheetNo, int row, int column) {
        String cellData = "";
        try {
            Row rowData = sheet.getRow(row);
            Cell cell = rowData.getCell(column);

            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    cellData = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    cell.getBooleanCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    cell.getNumericCellValue();
                    break;
            }
        } catch (Exception e) {
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
            }
        }
        return cellData;
    }
}
