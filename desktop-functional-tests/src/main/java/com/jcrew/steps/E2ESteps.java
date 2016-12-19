package com.jcrew.steps;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcrew.page.ArraySearch;
import com.jcrew.page.Checkout;
import com.jcrew.page.CheckoutBilling;
import com.jcrew.page.CheckoutReview;
import com.jcrew.page.CheckoutShippingEdit;
import com.jcrew.page.CheckoutShippingOptions;
import com.jcrew.page.CheckoutShoppingBag;
import com.jcrew.page.ContextChooser;
import com.jcrew.page.Footer;
import com.jcrew.page.HeaderWrap;
import com.jcrew.page.ProductDetails;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.ExcelUtils;
import com.jcrew.utils.PropertyReader;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.TestDataReader;
import com.jcrew.utils.Util;

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
	
	@Given("^Test data is read from excel file \"([^\"]*)\"$")
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
		return columnValue.trim();
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
				
				//Adding monogram item is pending. Will be added later
				
				//search for item
				HeaderWrap headerWrap = new HeaderWrap(getDriver());
				headerWrap.searchForSpecificTerm(productCode);
				
				//select random item from search results
				String currentURL = getDriver().getCurrentUrl();
				if(currentURL.contains("/r/search")){					
					ArraySearch searchArray = new ArraySearch(getDriver());
					searchArray.selectRandomProduct();
				}
				
				//Select color
				ProductDetails pdp = new ProductDetails(getDriver());
				pdp.selectSpecifiedColor(color);
				
				//Select size
				pdp.selectSpecifiedSize(size);
				
				//Select quantity
				pdp.selectSpecifiedQuantity(quantity);
				
				//Add item to bag
				pdp.addToBag();
			}else{
				  Util.e2eErrorMessagesBuilder("Failed to find item identifier '" + arrItemIdentifiers[i] + "' in E2E item master test data sheet");
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
	
	@And("^Apply promos, if required. If applied, verify promos are applied successfully$")
	public void apply_and_verify_promo(){
		String promoCodes = getDataFromTestDataRowMap("Promo Codes");
		if(promoCodes.isEmpty()){
			return;
		}
		
		String promoApplyPage = getDataFromTestDataRowMap("Promo Apply Page").toLowerCase();		
		String currentPageTitle = getDriver().getTitle().toLowerCase();
		
		if(currentPageTitle.contains(promoApplyPage)){			
			
			String[] arrPromoCodes = promoCodes.split(getE2ETestdataDelimiter());
			int maxPromoCodesCount = 0;
			if(arrPromoCodes.length > 2){
				maxPromoCodesCount = 2;
				Util.e2eErrorMessagesBuilder("More than 2 promos cannot be applied on checkout pages. Only first 2 promos from test data will be applied!!");
			}
			else{
				maxPromoCodesCount = arrPromoCodes.length;
			}
			
			Checkout checkout = null;			
			switch(promoApplyPage.toLowerCase()){
				case "shopping bag":
					checkout = new CheckoutShoppingBag(getDriver());
					break;
				case "shipping address":
					checkout = new CheckoutShippingEdit(getDriver());
					break;
				case "shipping & gift options":
					checkout = new CheckoutShippingOptions(getDriver());
					break;
				case "billing":
					checkout = new CheckoutBilling(getDriver());
					break;
				case "review":
					checkout = new CheckoutReview(getDriver());
					break;
			}
			
			for(int i=0;i<=maxPromoCodesCount - 1;i++){
				String promoCode = arrPromoCodes[i];
				checkout.addPromoCode(promoCode);
				
				if(!checkout.isPromoCodeApplied(promoCode)){
					Util.e2eErrorMessagesBuilder("Failed to apply promo code: " + promoCode  + " in the order!!");
				}
				else{
					logger.debug("Successfully applied promo code: {}", promoCode);
				}
			}	
		}
	}
	
	@And("^Navigate to Shipping Address page, if user is on Review page$")
	public void navigate_to_shipping_address_page_is_user_on_review_page(){
		String currentPageTitle = getDriver().getTitle().toLowerCase();
		if(currentPageTitle.contains("review")){
			CheckoutReview review = new CheckoutReview(getDriver());
			 review.editDetails("shipping");
			 new CheckoutShippingEdit(getDriver());
		}
	}
	
	@When("^User selects Shipping Addresses as per testdata$")
	public void user_selects_shipping_addessses(){
		String multipleShippingAddressRequired = getDataFromTestDataRowMap("Multiple Shipping Address Required?");
		String shippingAddresses = getDataFromTestDataRowMap("Shipping Addresses");
		
		if(!multipleShippingAddressRequired.equalsIgnoreCase("YES")){
			//single shipping address selection
			if(shippingAddresses.isEmpty())
				return;
			
			CheckoutShippingEdit checkoutShipping = new CheckoutShippingEdit(getDriver());
			checkoutShipping.selectSpecificShippingAddress(shippingAddresses);
		}
		else{
			 //multiple shipping addresses selection			
		}
	}
	
	@When("^User selects Shipping Methods as per testdata$")
	public void user_selects_shipping_methods(){
		String multipleShippingMethodsRequired = getDataFromTestDataRowMap("Multiple Shipping Methods Required?");
		String shippingMethods = getDataFromTestDataRowMap("Shipping Methods");
		
		if(!multipleShippingMethodsRequired.equalsIgnoreCase("YES")){
			//single shipping method selection
			if(shippingMethods.isEmpty())
				return;
			
			CheckoutShippingOptions shippingOptions = new CheckoutShippingOptions(getDriver());
			shippingOptions.selectSpecificShippingMethod(shippingMethods);
		}
		else{
			 //multiple shipping methods selection			
		}
	}
}