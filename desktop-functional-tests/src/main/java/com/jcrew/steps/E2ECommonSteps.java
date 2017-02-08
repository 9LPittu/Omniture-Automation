package com.jcrew.steps;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.E2EPropertyReader;
import com.jcrew.utils.ExcelUtils;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.TestDataReader;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;


public class E2ECommonSteps extends DriverFactory {

	protected final StateHolder stateHolder = StateHolder.getInstance();
	protected final Logger logger = LoggerFactory.getLogger(E2ECommonSteps.class);
	protected E2EPropertyReader e2ePropertyReader = E2EPropertyReader.getPropertyReader();
	protected TestDataReader testdataReader = TestDataReader.getTestDataReader();
	private String ftpPath = e2ePropertyReader.getProperty("jenkins.ftp.path");	
	
	@Before("@e2e")
	public void read_item_master_testdata() throws IOException{
		stateHolder.put("e2e_error_messages", "");
        getItemsMasterTestdata();
	}
	
	public void getItemsMasterTestdata() throws IOException{
    	String itemsMasterExcelFileName = "E2E_ITEMS_MASTER_TESTDATA.xls"; 
		ExcelUtils itemMasterReader = null;
		
		if(!stateHolder.hasKey("itemMasterTestdata")){
			try {
				if(System.getProperty("os.name").toLowerCase().contains("windows")){			
					itemMasterReader = new ExcelUtils(e2ePropertyReader.getProperty("windows.e2e.testdata.dir") + File.separator + itemsMasterExcelFileName , "E2E_ITEMS", "");			
				}
				else{
					itemMasterReader = new ExcelUtils(ftpPath + itemsMasterExcelFileName , "E2E_ITEMS", "");
				}
			}catch (IOException e) {
				throw new IOException();
			}
			
			stateHolder.put("itemMasterTestdata", itemMasterReader);
		}
    }

	public String getDataFromTestDataRowMap(String columnName) {
		Map<String, Object> testdataMap = stateHolder.get("testdataRowMap");
		String columnValue = null;
		if (testdataMap.containsKey(columnName)) {
			columnValue = ((String) testdataMap.get(columnName)).trim();
		}
		logger.debug("Testdata for '{}' = {}", columnName, columnValue);
		return columnValue;
	}

	public String getE2ETestdataDelimiter() {
		String e2eDelimiter = e2ePropertyReader.getProperty("e2e.testdata.delimiter");
		return e2eDelimiter;
	}
	
	@Given("^Test data is read from excel file \"([^\"]*)\"$")
	public void read_test_data_from_excel(String excelFileName) throws FileNotFoundException, IOException {

		ExcelUtils testDataReader;

		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
			testDataReader = new ExcelUtils(
					e2ePropertyReader.getProperty("windows.e2e.testdata.dir") + File.separator + excelFileName, "Testdata", "");
		} else {
			testDataReader = new ExcelUtils(ftpPath + excelFileName, "Testdata", "");
		}

		Map<String, Object> testdataRowMap = null;
		for (int j = testDataReader.getSearchTextFirstRowNum(); j <= testDataReader.getSearchTextLastRowNum(); j++) {
			testdataRowMap = testDataReader.getDataFromExcel(j);
			if (((String) testdataRowMap.get("Execute")).equalsIgnoreCase("YES") && !((String) testdataRowMap.get("Execution Completed")).equalsIgnoreCase("YES")) {
				stateHolder.put("excelObject", testDataReader);
				stateHolder.put("excelrowno", j);
				stateHolder.put("testdataRowMap", testdataRowMap);
				break;
			}
		}
	}
}