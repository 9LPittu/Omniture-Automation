package Sap_login;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class FileRead {
	
	public static E2EPropertyReader e2ePropertyReader = E2EPropertyReader.getPropertyReader();
	public static File targetDirectory;
	public static File reportsDirectory = null;
	
	public static void main(String[] args) {	
		

	targetDirectory = new File(System.getProperty("user.dir") + File.separator + "target");
		
		//new logic here :
		isTestDataRowAvailableForExecution("JC_E2E_ExpressUser_Testdata_Sheet.xls");
		
	}
	
		
	public static String isTestDataRowAvailableForExecution(String testdataFile){
		
		ExcelUtils testDataReader = null;
		try {
				testDataReader = new ExcelUtils("c:\\JC_E2E_ExpressUser_Testdata_Sheet.xls", "Testdata", "");
		
		} catch (IOException e) {
			e.printStackTrace();
		}				
	
		Map<String, Object> testdataRowMap = null;
		int maxRowCount = 500;
		
		int j = testDataReader.getSearchTextFirstRowNum();
		
		while(j<=maxRowCount){
			try{
				testdataRowMap = testDataReader.getDataFromExcel(j);
				if (((String) testdataRowMap.get("Execute")).equalsIgnoreCase("YES") && !((String) testdataRowMap.get("Execution Completed")).equalsIgnoreCase("YES")) {
			//		isRowAvailable = true;
					
					System.out.println(">>>>>>>>>qty " + testdataRowMap.get("Qty"));
					System.out.println(">>>>>>>>>order Number  " + testdataRowMap.get("Order Number"));
				}
				j++;
			}
			catch(Exception e){
				j++;
			}
		}
		return "";
	}
}