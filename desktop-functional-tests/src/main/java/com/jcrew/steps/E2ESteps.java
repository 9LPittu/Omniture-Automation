package com.jcrew.steps;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import com.jcrew.utils.ExcelUtils;
import com.jcrew.utils.PropertyReader;
import com.jcrew.utils.StateHolder;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class E2ESteps {
	
	public final StateHolder stateHolder = StateHolder.getInstance();
	
	@Given("^test data is read from excel file \"([^\"]*)\"$")
	public void read_test_data_from_excel(String excelFileName) throws FileNotFoundException, IOException{
		
		PropertyReader propertyReader = PropertyReader.getPropertyReader();
		String ftpPath = propertyReader.getProperty("jenkins.ftp.path");		
		ExcelUtils testDataReader;
		
		if(System.getProperty("os.name").toLowerCase().contains("windows")){
			testDataReader = new ExcelUtils(File.listRoots()[0].getAbsolutePath() + File.separator + "E2E_Testdata" + File.separator + excelFileName, "Testdata", "");
		}
		else{
			testDataReader = new ExcelUtils(ftpPath + excelFileName, "Testdata", "");
		}
		
		Map<String, Object> testdataRowMap = null;
		for(int j=testDataReader.getSearchTextFirstRowNum();j<=testDataReader.getSearchTextLastRowNum();j++){
			testdataRowMap = testDataReader.getDataFromExcel(j);
			if(((String)testdataRowMap.get("Execute")).equalsIgnoreCase("YES") && !((String)testdataRowMap.get("Execution Completed")).equalsIgnoreCase("YES")){
				stateHolder.put("excelObject", testDataReader);
				stateHolder.put("excelrowno", j);
				stateHolder.put("testdataRowMap", testdataRowMap);
				break;
			}
		}
	}
	
	public String getDataFromTestDataRowMap(String columnName){
		Map<String, Object> testdataMap = stateHolder.get("testdataRowMap");
		System.out.println("Test data column name: " + columnName);
		String columnValue = (String) testdataMap.get(columnName);
		return columnValue;
	}
	
	@When("^User searches items and add to bag$")
	public void e2e_item_search(){
		String itemCodes = getDataFromTestDataRowMap("Item Codes");
		System.out.println(itemCodes);
	}
	
	@And("^User select shipping address$")
	public void e2e_select_shipping_address(){
		String shippingAddress = getDataFromTestDataRowMap("Shipping Address");
		System.out.println(shippingAddress);
	}
	
	@And("^User select shipping method$")
	public void e2e_select_shipping_method(){
		String shippingMethod = getDataFromTestDataRowMap("Shipping Method");
		System.out.println(shippingMethod);
		
		if(shippingMethod.equalsIgnoreCase("Invalid")){
			stateHolder.put("orderNumber", "Order number is not generated");
			try {
				org.junit.Assert.fail("Invalid shipping method");
				throw new Exception("Invalid shipping method");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		try {
			Thread.sleep(5 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	
}
