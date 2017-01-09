package com.jcrew.steps;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcrew.page.ArraySearch;
import com.jcrew.page.Checkout;
import com.jcrew.page.CheckoutBilling;
import com.jcrew.page.CheckoutBillingPayment;
import com.jcrew.page.CheckoutReview;
import com.jcrew.page.CheckoutShippingEdit;
import com.jcrew.page.CheckoutShippingOptions;
import com.jcrew.page.CheckoutShoppingBag;
import com.jcrew.page.ContextChooser;
import com.jcrew.page.Footer;
import com.jcrew.page.GiftCards;
import com.jcrew.page.HeaderWrap;
import com.jcrew.page.LogIn;
import com.jcrew.page.Monogram;
import com.jcrew.page.PaypalLogin;
import com.jcrew.page.PaypalReview;
import com.jcrew.page.ProductDetails;
import com.jcrew.pojo.GiftCard;
import com.jcrew.pojo.User;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.ExcelUtils;
import com.jcrew.utils.PropertyReader;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.TestDataReader;
import com.jcrew.utils.UsersHub;
import com.jcrew.utils.Util;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

import static org.junit.Assert.*;

public class E2ESteps extends DriverFactory {
	
	private final StateHolder stateHolder = StateHolder.getInstance();
	private final Logger logger = LoggerFactory.getLogger(E2ESteps.class);
	private PropertyReader propertyReader = PropertyReader.getPropertyReader();
	private TestDataReader testdataReader = TestDataReader.getTestDataReader();
	private String ftpPath = propertyReader.getProperty("jenkins.ftp.path");
	private boolean isItemDataExist = true;
	
	@Given("^Test data is read from excel file \"([^\"]*)\"$")
	public void read_test_data_from_excel(String excelFileName) throws FileNotFoundException, IOException{
		
		ExcelUtils testDataReader;
		
		if(System.getProperty("os.name").toLowerCase().contains("windows")){
			testDataReader = new ExcelUtils(propertyReader.getProperty("windows.e2e.testdata.dir") + File.separator + excelFileName, "Testdata", "");
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
		String columnValue = null;
		if(testdataMap.containsKey(columnName)){
			columnValue = ((String) testdataMap.get(columnName)).trim();
		}
		logger.debug("Data for {} = {}", columnName, columnValue);
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
	public void user_enter_login_credentials() {
		String userType = getDataFromTestDataRowMap("User Type");
		String countryName = getDataFromTestDataRowMap("Ship To Country");
		
		String emailAddress = "";
		String password = "";		
		User user = null;
		
		try {
			  if(!stateHolder.hasKey("e2eUserObject")){
				  UsersHub userHub = UsersHub.getInstance();
				  user = userHub.getE2EUser(userType.toLowerCase(), countryName.toLowerCase());
				  stateHolder.put("e2eUserObject", user);
			  }else{
				  user = stateHolder.get("e2eUserObject");
			  }
			  
			  emailAddress = user.getEmail();
		      password = user.getPassword();		      
		}
		catch (SQLException e) {
			String errorMsg = "Failed to retrieve '" +  userType + "' type username and password from DB!!!";
			Util.e2eErrorMessagesBuilder(errorMsg);
			throw new WebDriverException(errorMsg);
		}
		
		LogIn logIn = new LogIn(getDriver());
		logIn.submitUserCredentials(emailAddress, password);
	}
	
	@When("^User adds the products to bag as per testdata$")
	public void user_adds_products_to_bag() throws Exception{
		String itemIdentifiers = getDataFromTestDataRowMap("Item Identifiers");
		String quantities = getDataFromTestDataRowMap("Quantities");
		
		if(itemIdentifiers.isEmpty()){
			isItemDataExist=false;
			return;
		}
		
		String[] arrItemIdentifiers = itemIdentifiers.split(getE2ETestdataDelimiter());
		String[] arrQuantities = quantities.split(getE2ETestdataDelimiter());
		
		for(int i=0;i<arrItemIdentifiers.length;i++){
			int rowNumber = getRowNumberFromItemMaster(arrItemIdentifiers[i]);
			if(rowNumber>0){
				String itemCode = getColumnValueFromItemMaster(rowNumber, "Item Code");
				String color = getColumnValueFromItemMaster(rowNumber, "Color");
				String size = getColumnValueFromItemMaster(rowNumber, "Size");
				String quantity = arrQuantities[i];				
				String isMonogramRequired = getColumnValueFromItemMaster(rowNumber, "isMonogramRequired?");
				
				//search for item
				HeaderWrap headerWrap = new HeaderWrap(getDriver());
				headerWrap.searchForSpecificTerm(itemCode);
				
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
				
				//Add monogramming
				if(isMonogramRequired.equalsIgnoreCase("YES")){
					String placement = getColumnValueFromItemMaster(rowNumber, "Placement");
					String style = getColumnValueFromItemMaster(rowNumber, "Style");
					String firstInitial = getColumnValueFromItemMaster(rowNumber, "First Initial");
					String middleInitial = getColumnValueFromItemMaster(rowNumber, "Middle Initial");
					String lastInitial = getColumnValueFromItemMaster(rowNumber, "Last Initial");
					String threadColor = getColumnValueFromItemMaster(rowNumber, "Thread Color");
					
					pdp.addMonogram();
					
					Monogram monogram = new Monogram(getDriver());
					
					monogram.selectPlacement(placement);
					monogram.selectStyle(style);
					monogram.selectInitial("First", firstInitial);
					monogram.selectInitial("Middle", middleInitial);
					monogram.selectInitial("Last", lastInitial);
					monogram.selectThreadColor(threadColor);
					monogram.saveMonogram();
				}
				
				//Add item to bag
				pdp.addToBag();
			}else{
				  Util.e2eErrorMessagesBuilder("Failed to find item identifier '" + arrItemIdentifiers[i] + "' in E2E item master test data sheet");
				  throw new WebDriverException("Failed to find item identifier '" + arrItemIdentifiers[i] + "' in E2E item master test data sheet!!!");				
			}
		}
	}
	
	@And("^User adds gift cards to bag as per testdata$")
	public void user_adds_gift_card_to_bag() throws Exception{
		String giftCardTypes = getDataFromTestDataRowMap("Gift Card Type");
		String giftCardAmounts = getDataFromTestDataRowMap("Gift Card Amount");
		
		if(!isItemDataExist && giftCardTypes.isEmpty()){
			String message = "No test data is provided for items/gift cards!!!";
			Util.e2eErrorMessagesBuilder(message);
			throw new WebDriverException(message);
		}
		
		if(giftCardTypes.isEmpty())
			return;
		
		if(!isItemDataExist && !giftCardTypes.toLowerCase().contains("classic"))
			stateHolder.put("isShippingDisabled", true);
		
		String[] arrGiftCardTypes = giftCardTypes.split(getE2ETestdataDelimiter());
		String[] arrGiftCardAmounts = giftCardAmounts.split(getE2ETestdataDelimiter());
		
		for(int i =0;i<arrGiftCardTypes.length;i++){
			Footer footer = new Footer(getDriver());
		    footer.clickFooterLinkFromDrawer("The J.Crew Gift Card", "Let Us Help You");
		    
		    GiftCards giftCards = new GiftCards(getDriver());
		    
		    arrGiftCardTypes[i] = arrGiftCardTypes[i].toLowerCase();
		    if(arrGiftCardTypes[i].contains("classic")){
		    	giftCards.selectCardType("classic card");
		    	giftCards.selectGiftAmount(arrGiftCardAmounts[i]);
		    	giftCards.enterSenderName("any ");
		    	giftCards.enterRecipientName("any ");
		    	giftCards.enterRecipientEmailAddress("any ");
		    	
		    	String lineMessage1 = "Automated message for line 1";
		    	giftCards.enterGiftMessage(lineMessage1, "Line 1");
		    	
		    	
		    	String lineMessage2 = "Automated message for line 2";
		    	giftCards.enterGiftMessage(lineMessage2, "Line 2");
		    	
		    	giftCards.clickAddtoBag();
		    	
		    	GiftCard classicGiftCard = new GiftCard("J.CREW GIFT CARD", arrGiftCardAmounts[i],
		    			                                (String)stateHolder.get("giftCardSenderName"), (String)stateHolder.get("giftCardRecipientName"),
		    			                                (String)stateHolder.get("giftCardRecipientEmail"), lineMessage1, lineMessage2);
		    	
		    	stateHolder.addToList("giftCardsToBag", classicGiftCard);
		    	
		    }else if(arrGiftCardTypes[i].contains("e-gift")){
		    	giftCards.selectCardType("e-gift card");
		    	giftCards.selectGiftAmount(arrGiftCardAmounts[i]);
		    	giftCards.enterSenderName("any ");
		    	giftCards.enterRecipientName("any ");
		    	giftCards.enterRecipientEmailAddress("any ");
		    	giftCards.enterDateToBeSent();
		    	
		    	String dateString = stateHolder.get("giftCardDateSent");		    	
		    	DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");				
				Date date = null;
				try {
					date = dateFormat.parse(dateString);
				} catch (ParseException e) {
					throw new Exception("Failed to parse date");
				}
		    	
		    	String giftMessage = "This is the automated Gift Message";
		    	giftCards.enterGiftMessage(giftMessage, "Gift Message");
		    	
		    	giftCards.clickAddtoBag();
		    	
		    	GiftCard eGiftCard = new GiftCard("J.CREW E-GIFT CARD", arrGiftCardAmounts[i],
                                                  (String)stateHolder.get("giftCardSenderName"), (String)stateHolder.get("giftCardRecipientName"),
                                                  (String)stateHolder.get("giftCardRecipientEmail"), date, giftMessage);
		    	
		    	stateHolder.addToList("giftCardsToBag", eGiftCard);
		    }else{
		    	String message = arrGiftCardTypes[i] + " is not recognized gift card!!!";
				Util.e2eErrorMessagesBuilder(message);
				throw new WebDriverException(message);
		    }
		}
	}
	
	private int getRowNumberFromItemMaster(String itemIdentifier){		
		int rowNumber = -1;
		
		if(itemIdentifier.isEmpty())
			return rowNumber;
		
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
		
		if(itemMasterTestdataMap.containsKey(columnName)){
			return (String) itemMasterTestdataMap.get(columnName);
		}else{
			return null;
		}
	}
	
	@When("^User clicks on CHECK OUT NOW button or Express Paypal button$")
	public void user_clicks_checkout_express_paypal(){
		String paymentMethod = getDataFromTestDataRowMap("Payment Method");
		
		switch(paymentMethod.toUpperCase()){
			case "EXPRESS PAYPAL":
				CheckoutShoppingBag checkoutShoppingBag = new CheckoutShoppingBag(getDriver());
				checkoutShoppingBag.clickPaypalElement();
				break;
			default:
				CheckoutShoppingBagSteps checkoutShoppingBagSteps = new CheckoutShoppingBagSteps();
				checkoutShoppingBagSteps.check_out_now();
		}
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
		
		if(stateHolder.hasKey("isShippingDisabled"))
			return;
		
		String currentPageTitle = getDriver().getTitle().toLowerCase();
		if(currentPageTitle.contains("review")){
			CheckoutReview review = new CheckoutReview(getDriver());
			 review.editDetails("shipping");
			 new CheckoutShippingEdit(getDriver());
		}
	}
	
	@When("^User selects Shipping Addresses as per testdata$")
	public void user_selects_shipping_addessses(){
		
		if(stateHolder.hasKey("isShippingDisabled"))
			return;
		
		String multipleShippingAddressRequired = getDataFromTestDataRowMap("Multiple Shipping Address Required?");
		String shippingAddresses = getDataFromTestDataRowMap("Shipping Addresses");
		
		CheckoutShippingEdit checkoutShipping = new CheckoutShippingEdit(getDriver());
		
		if(!multipleShippingAddressRequired.equalsIgnoreCase("YES")){
			//single shipping address selection
			if(shippingAddresses.isEmpty())
				return;			
			
			checkoutShipping.selectSpecificShippingAddress(shippingAddresses);
		}
		else{
			//multiple shipping addresses selection
			String[] arrShippingAddresses = shippingAddresses.split(getE2ETestdataDelimiter());
			checkoutShipping.selectMultipleShippingAddresses(arrShippingAddresses);
			checkoutShipping.continueCheckout();
			stateHolder.put("isShippingAddressContinueClicked", true);
		}
	}
	
	@When("^User selects Shipping Methods as per testdata$")
	public void user_selects_shipping_methods(){
		
		if(stateHolder.hasKey("isShippingDisabled"))
			return;
		
		String multipleShippingAddressRequired = getDataFromTestDataRowMap("Multiple Shipping Address Required?");
		String multipleShippingMethodsRequired = getDataFromTestDataRowMap("Multiple Shipping Methods Required?");
		String shippingMethods = getDataFromTestDataRowMap("Shipping Methods");
		
		/*
		 	1. If scenario specifies single shipping address, then obviously multiple shipping methods are not possible
		 	2. If scenario specifies multiple shipping addresses and no data is provided for shipping methods, then default selections
		 	   will be used
		 	3. If scenario specifies multiple shipping addresses and shipping methods are provided in test data, then shipping methods
		 	   will be selected as per test data
		*/
		
		CheckoutShippingOptions shippingOptions = new CheckoutShippingOptions(getDriver());
		
		if(multipleShippingAddressRequired.equalsIgnoreCase("NO")){
			//single shipping method selection
			if(shippingMethods.isEmpty())
				return;
			
			shippingOptions.selectSpecificShippingMethod(shippingMethods);
		}
		else{
			if(multipleShippingMethodsRequired.equalsIgnoreCase("YES")){
				String[] arrShippingMethods = shippingMethods.split(getE2ETestdataDelimiter());
				shippingOptions.selectMultipleShippingMethods(arrShippingMethods);
			}
		}
	}
	
	@And("^Select Gift Receipt as per testdata, if required$")
	public void select_gift_receipt(){
		if(stateHolder.hasKey("isShippingDisabled"))
			return;
		
		throw new WebDriverException("Gift receipt implementation is pending");
	}
	
	@And("^Select Gift Wrapping as per testdata, if required$")
	public void select_gift_wrapping(){
		if(stateHolder.hasKey("isShippingDisabled"))
			return;
		
		throw new WebDriverException("Gift wrapping implementation is pending");
	}
	
	@And("^Navigate to Billing page, if user is on review page and only e-gift card is added to bag$")
	public void navigate_billing_page_when_only_egift_card_is_added(){
		
		String currentPageTitle = getDriver().getTitle().toLowerCase();
		if(currentPageTitle.contains("review")){
			CheckoutReview review = new CheckoutReview(getDriver());
			 review.editDetails("billing");
			 new CheckoutBilling(getDriver());
		}
	}
	
	@When("^User selects Payment Methods as per testdata$")
	public void user_selects_payment_methods(){
		String userType = getDataFromTestDataRowMap("User Type");
		String splitPaymentsRequired = getDataFromTestDataRowMap("Split Payments Required?");
		String paymentMethod1 = getDataFromTestDataRowMap("Payment Method 1");
		String paymentMethod2 = getDataFromTestDataRowMap("Payment Method 2");
		
		if(!splitPaymentsRequired.equalsIgnoreCase("YES")){

			//single payment method selection			
			if(paymentMethod1.isEmpty())
				return;	
			
			singlePaymentMethod(userType, paymentMethod1);
		}
		else{
			//split payment methods selection
			
			//first payment method selection
			singlePaymentMethod(userType, paymentMethod1);
			
			CheckoutBilling checkoutBilling = new CheckoutBilling(getDriver());
			CheckoutBillingPayment checkoutBillingPayment = new CheckoutBillingPayment(getDriver());
			
			//second payment selection
			if(userType.equalsIgnoreCase("GUEST")){
				checkoutBilling.addNewCard();
				checkoutBillingPayment.addNewCreditDebitCard(paymentMethod2);
			}				
		    
			checkoutBilling.clickTwoCardsPayment();			
			checkoutBilling.continueCheckout();			
			checkoutBilling.splitPayment(paymentMethod1, paymentMethod2);
			
			stateHolder.put("isBillingContinueClicked", true);
		}
	}
	
	public void singlePaymentMethod(String userType, String paymentMethodName){
		CheckoutBilling checkoutBilling = new CheckoutBilling(getDriver());
		if(userType.equalsIgnoreCase("REGISTERED") && !userType.equalsIgnoreCase("EXPRESS")){				
			switch(paymentMethodName.toUpperCase()){
				case "PAYPAL":
					checkoutBilling.selectPaypalRadioButton();
					checkoutBilling.continueCheckout();
					
					enterPaypalDetails();
					break;
				default:						
					checkoutBilling.selectSpecificPaymentMethod(paymentMethodName);
			}
		}else if(userType.equalsIgnoreCase("GUEST")){
			checkoutBilling.fillPaymentCardDetails(paymentMethodName);
		}
	}
	
	@And("^User completes Paypal transaction, if required$")
	public void user_completes_paypal_transaction(){
		
		String paymentMethod = getDataFromTestDataRowMap("Payment Method");
		if(!paymentMethod.equalsIgnoreCase("EXPRESS PAYPAL"))
			return;
		
		enterPaypalDetails();
	}
	
	public void enterPaypalDetails(){
		PaypalLogin paypalLogin = new PaypalLogin(getDriver());
		String paypalEmail = testdataReader.getData("paypal.email");
		String paypalPassword = testdataReader.getData("paypal.password");
		paypalLogin.submitPaypalCredentials(paypalEmail, paypalPassword);
		
		PaypalReview paypalReview = new PaypalReview(getDriver());
		paypalReview.clickContinue();
		
		stateHolder.put("isBillingContinueClicked", true);
	}
	
	@And("^User enters security code as per payment method, if required$")
	public void user_enters_security_code(){
		
		String userType = getDataFromTestDataRowMap("User Type");
		
		switch(userType.toUpperCase()){
			case "REGISTERED":
				enterSecurityCodeForRegisteredUser();
				break;
			case "EXPRESS":
				enterSecurityCodeForExpressUser();
				break;
		}
	}
	
	public void enterSecurityCodeForRegisteredUser(){
		String splitPaymentsRequired = getDataFromTestDataRowMap("Split Payments Required?");
		String paymentMethod1 = getDataFromTestDataRowMap("Payment Method 1");
		String paymentMethod2 = getDataFromTestDataRowMap("Payment Method 2");
		
		CheckoutReview checkoutReview = new CheckoutReview(getDriver());
		
		if(!splitPaymentsRequired.equalsIgnoreCase("YES")){
			switch(paymentMethod1.toUpperCase()){
				case "PAYPAL":
				case "JCC":
					return;
				default:
					//entering security code when single payment method is used
					checkoutReview.enterSecurityCode(paymentMethod1);
			}
		}
		else{
			//entering security code when split payment is done
			checkoutReview.enterSecurityCode(paymentMethod1);
			checkoutReview.enterSecurityCode(paymentMethod2);
		}
	}
	
	public void enterSecurityCodeForExpressUser(){
		String paymentMethod = getDataFromTestDataRowMap("Payment Method");
		
		if(paymentMethod.equalsIgnoreCase("EXPRESS PAYPAL"))
			return;
		
		CheckoutReview checkoutReview = new CheckoutReview(getDriver());
		checkoutReview.enterSecurityCode();
	}
}