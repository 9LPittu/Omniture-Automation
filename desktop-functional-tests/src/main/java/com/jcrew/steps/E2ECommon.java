package com.jcrew.steps;

import java.io.File;
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
}