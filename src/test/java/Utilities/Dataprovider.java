package Utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class Dataprovider {
		
	@DataProvider(name="LoginData")
	public String[][] getData() throws IOException {
	    String path = (System.getProperty("user.dir")+"//testData//namee.xlsx"); // Taking Excel file from testData
	    ExcelUtility xlutil = new ExcelUtility(path); // Creating an object for ExcelUtility

	    int totalrows = xlutil.getRowCount("Sheet1"); // Get the total row count
	    int totalcols = xlutil.getCellCount("Sheet1", 1); // Get the total column count for the first row

	    // Creating a two-dimensional array to store the data
	    String[][] logindata = new String[totalrows][totalcols];
	     

	    // Reading the data from Excel and storing it in the two-dimensional array
	    for (int i = 1; i <= totalrows; i++) { // Start from 1 (rows)
	        for (int j = 0; j < totalcols; j++) { // Start from 0 (columns)
	            logindata[i - 1][j] = xlutil.getCellData("Sheet1", i, j); // Storing in the array
	        }
	    }
	    
	    return logindata; // Returning the 2D array with login data
	}


}
