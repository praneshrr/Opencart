package Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {
    public FileInputStream fi;
    public FileOutputStream fo;
    public XSSFWorkbook workbook;
    public XSSFSheet sheet;
    public XSSFRow row;
    public XSSFCell cell;
    public CellStyle style;
    String path;

    // Constructor to initialize the Excel file path
    public ExcelUtility(String path) {
        this.path = path;
    }

    // Get total row count from a specific sheet
    public int getRowCount(String sheetName) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        int rowcount = sheet.getLastRowNum(); // Get the last row number
        workbook.close();
        fi.close();
        return rowcount; // Return total row count
    }

    // Get total cell count (number of columns) in a specific row
    public int getCellCount(String sheetName, int rownum) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rownum);
        int cellcount = row.getLastCellNum(); // Get total number of columns in the row
        workbook.close();
        fi.close();
        return cellcount; // Return total cell count
    }

    // Retrieve cell data from a specific row and column
    public String getCellData(String sheetName, int rownum, int colnum) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rownum);
        cell = row.getCell(colnum);
        DataFormatter formatter = new DataFormatter(); // Format cell value to string
        String data;
        try {
            data = formatter.formatCellValue(cell); // Get cell data
        } catch (Exception e) {
            data = ""; // Return empty string if an error occurs
        }
        workbook.close();
        fi.close();
        return data; // Return the retrieved cell data
    }

    // Set or update cell data at a specific row and column
    public void setCellData(String sheetName, int rownum, int colnum, String data) throws IOException {
        File xlfile = new File(path);

        // If the Excel file does not exist, create a new workbook
        if (!xlfile.exists()) {
            workbook = new XSSFWorkbook();
        } else {
            fi = new FileInputStream(path);
            workbook = new XSSFWorkbook(fi); // Load the existing workbook
        }

        // Check if the sheet exists; if not, create it
        if (workbook.getSheetIndex(sheetName) == -1) {
            sheet = workbook.createSheet(sheetName); // Create a new sheet
        } else {
            sheet = workbook.getSheet(sheetName); // Get the existing sheet
        }

        // Check if the row exists; if not, create it
        if (sheet.getRow(rownum) == null) {
            row = sheet.createRow(rownum); // Create a new row
        } else {
            row = sheet.getRow(rownum); // Get the existing row
        }

        cell = row.createCell(colnum); // Create or get the cell
        cell.setCellValue(data); // Set the data in the cell

        fo = new FileOutputStream(path); // Write data to the file
        workbook.write(fo); // Save the changes
        workbook.close();
        if (fi != null) {
            fi.close(); // Close file input stream if open
        }
        fo.close(); // Close file output stream
    }
}
