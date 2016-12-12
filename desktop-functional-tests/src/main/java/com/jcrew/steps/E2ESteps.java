package com.jcrew.steps;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcrew.page.ContextChooser;
import com.jcrew.page.Footer;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.ExcelUtils;
import com.jcrew.utils.PropertyReader;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.TestDataReader;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

import static org.junit.Assert.*;

public class E2ESteps extends DriverFactory {
	
	private final StateHolder stateHolder = StateHolder.getInstance();
	private final Logger logger = LoggerFactory.getLogger(E2ESteps.class);
	private PropertyReader propertyReader = PropertyReader.getPropertyReader();
	private String ftpPath = propertyReader.getProperty("jenkins.ftp.path");
	
	E2ESteps(){		
		String itemsMasterExcelFileName = "E2E_ITEMS_MASTER_TESTDATA.xls"; 
		ExcelUtils itemMasterReader = null;
		
		if(!stateHolder.hasKey("itemMasterTestdata")){
			try {
				if(System.getProperty("os.name").toLowerCase().contains("windows")){			
					itemMasterReader = new ExcelUtils(File.listRoots()[0].getAbsolutePath() + File.separator + "E2E_Testdata" + File.separator + itemsMasterExcelFileName , "E2E_ITEMS", "");			
				}
				else{
					itemMasterReader = new ExcelUtils(ftpPath + itemsMasterExcelFileName , "E2E_ITEMS", "");
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
			
			stateHolder.put("itemMasterTestdata", itemMasterReader);
		}
	}
	
	@Given("^test data is read from excel file \"([^\"]*)\"$")
	public void read_test_data_from_excel(String excelFileName) throws FileNotFoundException, IOException{
		
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
		logger.debug("Test data column name: {}", columnName);
		String columnValue = (String) testdataMap.get(columnName);
		return columnValue;
	}
	
	public String getE2ETestdataDelimiter(){
		TestDataReader testData = TestDataReader.getTestDataReader();
		String e2eDelimiter = testData.getData("e2e.testdata.delimiter");
		return e2eDelimiter;
	}
	
	@When("^User selects country as per testdata$")
	public void user_selects_country_as_per_testdata(){
		String countryName = getDataFromTestDataRowMap("Ship To Country");
		
		//click on change link from footer
		Footer footer = new Footer(getDriver());
		footer.clickChangeLinkInFooter();
		
		//Validate context chooser page is displayed
		ContextChooser contextChooser = new ContextChooser(getDriver());
		assertTrue("Is this context chooser page?", contextChooser.isInternationalContextChooserPageDisplayed());
		
		//Select country
		contextChooser.selectCountryOnContextChooserPage(countryName);
	}
	
	@And("^User enters login credentials$")
	public void user_enter_login_credentials(){
		String userType = getDataFromTestDataRowMap("User Type");
	}
	
	@When("^User adds the products to bag as per testdata$")
	public void user_adds_products_to_bag() throws Exception{
		String itemIdentifiers = getDataFromTestDataRowMap("Item Identifiers");
		String quantities = getDataFromTestDataRowMap("Quantities");
		
		String[] arrItemIdentifiers = itemIdentifiers.split(getE2ETestdataDelimiter());
		String[] arrQuantities = quantities.split(getE2ETestdataDelimiter());
		
		for(int i=0;i<arrItemIdentifiers.length;i++){
			int rowNumber = getRowNumberFromItemMaster(arrItemIdentifiers[i]);
			if(rowNumber>0){
				String productCode = getColumnValueFromItemMaster(rowNumber, "Product Code");
				String color = getColumnValueFromItemMaster(rowNumber, "Color");
				String size = getColumnValueFromItemMaster(rowNumber, "Size");
				String quantity = arrQuantities[i];
			}else{				
				  throw new Exception("Failed to find item identifier '" + arrItemIdentifiers[i] + "' in E2E item master test data sheet!!!");				
			}
		}
	}
	
	private int getRowNumberFromItemMaster(String itemIdentifier){		
		int rowNumber = -1;
		ExcelUtils itemMasterTestdata = stateHolder.get("itemMasterTestdata");
		
		for(int i = itemMasterTestdata.getSearchTextFirstRowNum();i<=itemMasterTestdata.getSearchTextLastRowNum();i++){
			try {
				Map<String, Object> itemMasterTestdataMap = itemMasterTestdata.getDataFromExcel(i);
				String itemIdentifierFromSheet = (String) itemMasterTestdataMap.get("Item Identifier");				
				if(itemIdentifierFromSheet.equalsIgnoreCase(itemIdentifier)){
					rowNumber = i;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return rowNumber;
	}
	
	private String getColumnValueFromItemMaster(int rowNumber, String columnName){
		ExcelUtils itemMasterTestdata = stateHolder.get("itemMasterTestdata");
		
		Map<String, Object> itemMasterTestdataMap = null;
		
		try {
			 itemMasterTestdataMap = itemMasterTestdata.getDataFromExcel(rowNumber);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return (String) itemMasterTestdataMap.get(columnName);
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