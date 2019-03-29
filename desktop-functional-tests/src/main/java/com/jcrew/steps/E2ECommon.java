package com.jcrew.steps;

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

public class E2ECommon extends DriverFactory {

	protected final StateHolder stateHolder = StateHolder.getInstance();
	protected final Logger logger = LoggerFactory.getLogger(E2ECommon.class);
	protected E2EPropertyReader e2ePropertyReader = E2EPropertyReader.getPropertyReader();
	protected TestDataReader testdataReader = TestDataReader.getTestDataReader();
	protected String ftpPath = e2ePropertyReader.getProperty("jenkins.ftp.path");	
	
	protected void getItemsMasterTestdata() throws IOException{
    	String itemsMasterExcelFileName = "E2E_ITEMS_MASTER_TESTDATA.xls"; 
		ExcelUtils itemMasterReader = null;
		
		if(!stateHolder.hasKey("itemMasterTestdata")){
			try {
				if(System.getProperty("os.name").toLowerCase().contains("windows")){			
					itemMasterReader = new ExcelUtils(System.getProperty("user.dir")+"\\properties\\test_data\\"+itemsMasterExcelFileName , "E2E_ITEMS", "");			
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

	protected int getRowNumberFromItemMaster(String itemIdentifier) throws IOException {
		int rowNumber = -1;

		if (itemIdentifier.isEmpty())
			return rowNumber;

		ExcelUtils itemMasterTestdata = /*stateHolder.get("itemMasterTestdata");*/new ExcelUtils(System.getProperty("user.dir")+"\\properties\\test_data\\E2E_ITEMS_MASTER_TESTDATA.xls", "E2E_ITEMS", "");
		for (int i = itemMasterTestdata.getSearchTextFirstRowNum(); i <= itemMasterTestdata.getSearchTextLastRowNum(); i++) {
			try {
				System.out.println(i);
				Map<String, Object> itemMasterTestdataMap = itemMasterTestdata.getDataFromExcel(i);
				
				String itemIdentifierFromSheet = (String) itemMasterTestdataMap.get("Item Identifier");
				if (itemIdentifierFromSheet.equalsIgnoreCase(itemIdentifier)) {
					rowNumber = i;
					break;
				}
			} catch (FileNotFoundException e) {
				throw new FileNotFoundException();
			} catch (IOException e) {
				throw new IOException();
			}
		}

		return rowNumber;
	}

	protected String getColumnValueFromItemMaster(int rowNumber, String columnName) throws IOException {
		ExcelUtils itemMasterTestdata = /*stateHolder.get("itemMasterTestdata");*/ new ExcelUtils(System.getProperty("user.dir")+"\\properties\\test_data\\E2E_ITEMS_MASTER_TESTDATA.xls", "E2E_ITEMS", "");

		Map<String, Object> itemMasterTestdataMap = null;

		try {
			itemMasterTestdataMap = itemMasterTestdata.getDataFromExcel(rowNumber);
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		} catch (IOException e) {
			throw new IOException();
		}

		if (itemMasterTestdataMap.containsKey(columnName)) {
			return (String) itemMasterTestdataMap.get(columnName);
		} else {
			return null;
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

	protected String getE2ETestdataDelimiter() {
		String e2eDelimiter = e2ePropertyReader.getProperty("e2e.testdata.delimiter");
		return e2eDelimiter;
	}
}