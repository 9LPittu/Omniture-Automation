package com.jcrew.steps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.jcrew.page.*;
import com.jcrew.page.checkout.*;
import com.jcrew.page.header.HeaderSearch;
import com.jcrew.page.product.*;
import com.jcrew.steps.checkout.CheckoutShoppingBagSteps;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.jcrew.pojo.Address;
import com.jcrew.pojo.GiftCard;
import com.jcrew.utils.ExcelUtils;
import com.jcrew.utils.Util;

import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.*;
@SuppressWarnings("unused")
public class E2E1Steps extends E2ECommon {
	E2ECommon e2e = new E2ECommon();
	private final Properties properties = new Properties();
	private boolean isItemDataExist = true;
	String forgotPasswordText;
	String updatePasswordText;
	@FindBy(xpath = "(//A[@href='//www.jcrew.com/index.jsp?srcCode=EMOP00015&utm_source=email&utm_medium=email&utm_campaign=email-password-update'])[2]")
	private WebElement updatePassword;
	Map<String, Object> testdataRowMap = null;
	String itemCode;
	String shippingAddresses;
	String ord;
	@Before("@e2e")
	public void read_item_master_testdata() throws IOException{
		stateHolder.put("e2e_error_messages", "");
        getItemsMasterTestdata();
	}
	
	@Given("^Test data is read from excel file \"([^\"]*)\"$")
	public void read_test_data_from_excel(String excelFileName) throws FileNotFoundException, IOException {

		ExcelUtils testDataReader;

		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
			testDataReader = new ExcelUtils(
					System.getProperty("user.dir")+"\\properties\\test_data\\"+excelFileName,
					"Testdata", "");
		} else {
			testDataReader = new ExcelUtils(ftpPath + excelFileName, "Testdata", "");
		}

		//Map<String, Object> testdataRowMap = null;
		for (int j = testDataReader.getSearchTextFirstRowNum(); j <= testDataReader.getSearchTextLastRowNum(); j++) {
			testdataRowMap = testDataReader.getDataFromExcel(j);
			if (((String) testdataRowMap.get("Execute")).equalsIgnoreCase("YES")
					&& !((String) testdataRowMap.get("Execution Completed")).equalsIgnoreCase("YES")) {
				stateHolder.put("excelObject", testDataReader);
				stateHolder.put("excelrowno", j);
				stateHolder.put("testdataRowMap", testdataRowMap);
				break;
			}
		}
	}
	
	@When("^User selects country as per testdata$")
	public void user_selects_country_as_per_testdata() throws Exception {
		Util.wait(3000);
		String countryName = getDataFromTestDataRowMap("Ship To Country");

		if (countryName.equalsIgnoreCase("US"))
			return;
		
		// click on change link from footer
		Footer footer = new Footer(getDriver());
		footer.clickChangeLinkInFooter();

		// Validate context chooser page is displayed
		ContextChooser contextChooser = new ContextChooser(getDriver());
		assertTrue("Is this context chooser page?", contextChooser.isInternationalContextChooserPageDisplayed());

		// Select country
		contextChooser.selectCountryOnContextChooserPage(countryName);
	}

	@And("^User enters login credentials$")
	public void user_enter_login_credentials() throws Exception {
		// Thread.sleep(20000);
		/*
		 * String userType = getDataFromTestDataRowMap("User Type"); String countryName
		 * = getDataFromTestDataRowMap("Ship To Country");
		 * 
		 * String emailAddress = ""; String password = ""; User user = null;
		 * 
		 * try { if (!stateHolder.hasKey("e2eUserObject")) { UsersHub userHub =
		 * UsersHub.getInstance(); user = userHub.getE2EUser(userType.toLowerCase(),
		 * countryName.toLowerCase()); stateHolder.put("e2eUserObject", user); } else {
		 * user = stateHolder.get("e2eUserObject"); }
		 * 
		 * emailAddress = user.getEmail(); password = user.getPassword(); } catch
		 * (SQLException e) { String errorMsg = "Failed to retrieve '" + userType +
		 * "' type username and password from DB!!!";
		 * Util.e2eErrorMessagesBuilder(errorMsg); throw new
		 * WebDriverException(errorMsg); }
		 */
		LogIn logIn = new LogIn(getDriver());
		E2ECommon e2e = new E2ECommon();
		logIn.submitUserCredentials(e2e.getDataFromTestDataRowMap("Username"),e2e.getDataFromTestDataRowMap("Password"));
		Util.wait(10000);
	}
	
	@And("^User creates new account$")
	public void user_creates_newAccount() {
		Util.wait(1000);
		LogIn logIn = new LogIn(getDriver());
		logIn.createNewAccount();
	}
	
	@And("User creates new account")
	public void createNewAccount() {
		
	}

	@When("^User adds the products to bag as per testdata$")
	public void user_adds_products_to_bag() throws Exception {
		String itemIdentifiers = getDataFromTestDataRowMap("Item Identifiers");
		String quantities = getDataFromTestDataRowMap("Quantities");
		if (itemIdentifiers.isEmpty()) {
			isItemDataExist = false;
			return;
		}

		String[] arrItemIdentifiers = itemIdentifiers.split(getE2ETestdataDelimiter());
		String[] arrQuantities = quantities.split(getE2ETestdataDelimiter());
		stateHolder.put("itemsCount", arrItemIdentifiers.length);

		for (int i = 0; i < arrItemIdentifiers.length; i++) {
			int rowNumber = getRowNumberFromItemMaster(arrItemIdentifiers[i]);
			if (rowNumber > 0) {
				 itemCode = getColumnValueFromItemMaster(rowNumber, "Item Code");
				String color = getColumnValueFromItemMaster(rowNumber, "Color");
				String size = getColumnValueFromItemMaster(rowNumber, "Size");
				String quantity = arrQuantities[i];
				String isMonogramRequired = getColumnValueFromItemMaster(rowNumber, "isMonogramRequired?");
				// search for item
				HeaderSearch headerWrap = new HeaderSearch(getDriver());
				headerWrap.searchForSpecificTerm(itemCode);

				// select random item from search results
				String currentURL = getDriver().getCurrentUrl();
				if (currentURL.contains("/r/search")) {
					ArraySearch searchArray = new ArraySearch(getDriver());
					searchArray.click_first_product_in_grid();
				}

				// Select color
				ProductDetailColors colors = new ProductDetailColors(getDriver());
				colors.selectSpecifiedColor(color);

				// Select size
				ProductDetailsSizes sizes = new ProductDetailsSizes(getDriver());
				sizes.selectSize(size);

				// Select quantity
				ProductDetailsQuantity pdp = new ProductDetailsQuantity(getDriver());
				pdp.selectSpecifiedQuantity(quantity);

				// Add monogramming
				if (isMonogramRequired.equalsIgnoreCase("YES")) {
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
				Util.wait(2000);
				// Add item to bag
				ProductDetailsActions pdpAction = new ProductDetailsActions(getDriver());
				pdpAction.addToBag();
			} else {
				String message = "Failed to find item identifier '" + arrItemIdentifiers[i]
						+ "' in E2E item master test data sheet";
				Util.e2eErrorMessagesBuilder(message);
				throw new WebDriverException(message);
			}
		}
	}

	@And("^User adds gift cards to bag as per testdata$")
	public void user_adds_gift_card_to_bag() throws Exception {
		String giftCardTypes = getDataFromTestDataRowMap("Gift Card Type");
		String giftCardAmounts = getDataFromTestDataRowMap("Gift Card Amount");

		if (!isItemDataExist && giftCardTypes.isEmpty()) {
			String message = "No test data is provided for items/gift cards!!!";
			Util.e2eErrorMessagesBuilder(message);
			throw new WebDriverException(message);
		}

		if (giftCardTypes.isEmpty())
			return;

		if (!isItemDataExist && !giftCardTypes.toLowerCase().contains("classic"))
			stateHolder.put("isShippingDisabled", true);

		String[] arrGiftCardTypes = giftCardTypes.split(getE2ETestdataDelimiter());
		String[] arrGiftCardAmounts = giftCardAmounts.split(getE2ETestdataDelimiter());

		for (int i = 0; i < arrGiftCardTypes.length; i++) {
			Footer footer = new Footer(getDriver());
			footer.goToGiftCardPage();
			// footer.clickFooterLinkFromDrawer("The J.Crew Gift Card", "Let Us Help You");
			GiftCards giftCards = new GiftCards(getDriver());

			arrGiftCardTypes[i] = arrGiftCardTypes[i].toLowerCase();
			if (arrGiftCardTypes[i].contains("classic")) {
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
														 (String) stateHolder.get("giftCardSenderName"),
														 (String) stateHolder.get("giftCardRecipientName"),
														 (String) stateHolder.get("giftCardRecipientEmail"), lineMessage1, lineMessage2);

				stateHolder.addToList("giftCardsToBag", classicGiftCard);

			} else if (arrGiftCardTypes[i].contains("e-gift")) {
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
												  (String) stateHolder.get("giftCardSenderName"),
												  (String) stateHolder.get("giftCardRecipientName"),
												  (String) stateHolder.get("giftCardRecipientEmail"), date, giftMessage);

				stateHolder.addToList("giftCardsToBag", eGiftCard);
			} else {
				String message = arrGiftCardTypes[i] + " is not recognized gift card!!!";
				Util.e2eErrorMessagesBuilder(message);
				throw new WebDriverException(message);
			}
		}
	}

	@When("^User clicks on CHECK OUT NOW button or Express Paypal button$")
	public void user_clicks_checkout_express_paypal() {
		String paymentMethod = getDataFromTestDataRowMap("Payment Method");

		switch (paymentMethod.toUpperCase()) {
			case "EXPRESS PAYPAL":
				CheckoutSummary checkoutSummary = new CheckoutSummary(getDriver());
				checkoutSummary.clickPaypalElement();
				break;
			default:
				CheckoutShoppingBagSteps checkoutShoppingBagSteps = new CheckoutShoppingBagSteps();
				checkoutShoppingBagSteps.check_out_now();
		}
	}
	
	@And("^Apply promos, if required. If applied, verify promos are applied successfully$")
	public void apply_and_verify_promo() {
		String userType = getDataFromTestDataRowMap("User Type");
		String promoCodes = getDataFromTestDataRowMap("Promo Codes");
		
		if (promoCodes.isEmpty() || stateHolder.hasKey("isPromoAppliedFail")) {
			return;
		}

		String promoApplyPage = getDataFromTestDataRowMap("Promo Apply Page").toLowerCase();
		String currentPageTitle = getDriver().getTitle().toLowerCase();

		if (currentPageTitle.contains(promoApplyPage)) {

			String[] arrPromoCodes = promoCodes.split(getE2ETestdataDelimiter());
			int maxPromoCodesCount = 0;
			if (arrPromoCodes.length > 2) {
				maxPromoCodesCount = 2;
				Util.e2eErrorMessagesBuilder(
						"More than 2 promos cannot be applied on checkout pages. Only first 2 promos from test data will be applied!!");
			} else {
				maxPromoCodesCount = arrPromoCodes.length;
			}

			CheckoutPromoCode checkout = new CheckoutPromoCode(getDriver());
			
			//Verify promo codes are already applied
			int promoAppliedCount = checkout.getAppliedPromoCodesCount(); 
			if(promoAppliedCount == 2 || promoAppliedCount == arrPromoCodes.length){
				logger.debug("Required promo codes are already applied as per test data...");
				return;
			}

			for (int i = 0; i <= maxPromoCodesCount - 1; i++) {
				String promoCode = arrPromoCodes[i];
				checkout.addPromoCode(promoCode);

				if (!checkout.isPromoCodeApplied(promoCode)) {
					stateHolder.put("isPromoAppliedFail", true);
					Util.e2eErrorMessagesBuilder("Failed to apply promo code: " + promoCode + " in the order!!");
				} else {
					logger.debug("Successfully applied promo code: {}", promoCode);
				}
			}
		}
	}

	@And("^Navigate to Shipping Address page, if user is on Review page$")
	public void navigate_to_shipping_address_page_is_user_on_review_page() {
		/*if(getDataFromTestDataRowMap("E2E Scenario Description").contains("Express paypal")) {
			return;
		}*/
		if (stateHolder.hasKey("isShippingDisabled"))
			return;

		String currentPageTitle = getDriver().getTitle().toLowerCase();
		if (currentPageTitle.contains("review")) {
			CheckoutReview review = new CheckoutReview(getDriver());
			review.editDetails("shipping");
			new CheckoutShippingEdit(getDriver());
		}
	}

	@When("^User selects Shipping Addresses as per testdata$")
	public void user_selects_shipping_addessses() {
		/*if(getDataFromTestDataRowMap("E2E Scenario Description").contains("Express paypal")) {
			return;
		}*/
		CheckoutShippingEdit checkoutShipping = new CheckoutShippingEdit(getDriver());
		if (stateHolder.hasKey("isShippingDisabled"))
			return;
		if (getDataFromTestDataRowMap("OrderType").equalsIgnoreCase("STS")) {
			checkoutShipping.selectSTS();
			// checkoutShipping.continueCheckout();
		} else {
			String multipleShippingAddressRequired = getDataFromTestDataRowMap("Multiple Shipping Address Required?");
			 shippingAddresses = getDataFromTestDataRowMap("Shipping Addresses");

			if (!multipleShippingAddressRequired.equalsIgnoreCase("YES")) {
				// single shipping address selection
				if (shippingAddresses.isEmpty())
					return;

				checkoutShipping.selectSpecificShippingAddress(shippingAddresses);
			} else {
				// multiple shipping addresses selection
				String[] arrShippingAddresses = shippingAddresses.split(getE2ETestdataDelimiter());

				int itemsCount = stateHolder.get("itemsCount");
				if (itemsCount < 2) {
					String message = "Multiple shipping addresses are selected. But only 1 item is added to bag.";
					Util.e2eErrorMessagesBuilder(message);
					throw new WebDriverException(message);
				}

				for (int i = 0; i < arrShippingAddresses.length; i++) {
					stateHolder.addToList("shippingAddresses", arrShippingAddresses[i]);
				}

				checkoutShipping.selectMultipleShippingAddressRadioButton();
				checkoutShipping.continueCheckout();

				CheckoutMultipleShippingAddresses multiShipping = new CheckoutMultipleShippingAddresses(getDriver());
				List<String> shippingAddressesList = stateHolder.getList("shippingAddresses");
				multiShipping.multiShippingAddressSelection(shippingAddressesList);

				multiShipping.continueCheckout();

				stateHolder.put("isShippingAddressContinueClicked", true);
			}
		}
	}
	
	/*@And("^I validate Order Number In Email$")
	public void validate_Order_Number_In_Gmail()throws Exception{
		Thread.sleep(5000);
		String currentUrl=getDriver().getCurrentUrl();
		if(currentUrl.contains("https://mail.google.com")) {
			Thread.sleep(5000);
		WebElement search= getDriver().findElement(By.xpath("//*[@id='aso_search_form_anchor']/div/input"));
		Thread.sleep(5000);
		ord=CheckoutConfirmation.getOrderNum();
        search.sendKeys(ord);
        Thread.sleep(500);
        search.sendKeys(Keys.ENTER);
        Thread.sleep(5000);
		}
		else {
			WebElement search= getDriver().findElement(By.xpath("//INPUT[@id='txtS']"));
			Thread.sleep(5000);
			ord=CheckoutConfirmation.getOrderNum();
	        search.sendKeys(ord);
	        Thread.sleep(5000);
	        search.sendKeys(Keys.ENTER);
	        Thread.sleep(5000);
	        String orderConfirmTxt=   getDriver().findElement(By.xpath("//*[@id='divSubject']")).getText();
	        assertEquals(orderConfirmTxt,"Thanks for your J.Crew order");
		}
	}*/
	@Then("Verify_user_is_in_UpdatePassword_mail_Confirmation_Page")
    public void verify_user_is_in_UpdatePassword_mail_Confirmation_Page() throws IOException, InterruptedException { 
		 FileInputStream inputFile = new FileInputStream("properties/e2e.properties");
	       properties.load(inputFile);
          
	     //WebMail
	       String webUrl=  properties.getProperty("webmailURL");
	       if(getDriver().getCurrentUrl().contains(webUrl)) {
	           updatePasswordText=   getDriver().findElement(By.xpath("(//span[contains(text(),'Your password has been updated')])[2]")).getText();
	           assertEquals(updatePasswordText,"Your password has been updated");
	       }
	       else {
	    	   List<WebElement>  updateUserIDText=  getDriver().findElements(By.xpath("//table/tbody/tr[1]/td[5]/div/span/span/following::span[contains(text(),'Your password has been updated')]/parent::span[1]"));
		  		 int size=updateUserIDText.size();
		  		 System.out.println(size);
		  		 for(WebElement userIDText:updateUserIDText) {
		  			 String updatedText=userIDText.getText();
		  			 if(updatedText.equalsIgnoreCase("Your password has been updated")) {
		  				userIDText.click();
		  				 break;
		  			 }
		  			 else {
		  				 System.out.println("Your password updated Text is not match in Gmail");
		  			 }
		  			
		  	
		       }
		  		WebElement	updatedUserIDText=   getDriver().findElement(By.xpath("//h2[contains(text(),'Your password has been updated')]"));
		  		Assert.assertTrue("updated password  Text is displayed", updatedUserIDText.isDisplayed());
        }
	}
	@Then("Verify_user_is_in_ForgotPassword_mail_Confirmation_Page")
    public void verify_user_is_in_ForgotPassword_mail_Confirmation_Page() throws IOException, InterruptedException { 
		 FileInputStream inputFile = new FileInputStream("properties/e2e.properties");
	       properties.load(inputFile);
	       Thread.sleep(5000);
	     //WebMail
	       String webUrl=  properties.getProperty("webmailURL");
	       if(getDriver().getCurrentUrl().contains(webUrl)) {
	    	   forgotPasswordText=   getDriver().findElement(By.xpath("(//span[contains(text(),'Your password has been updated')])[2]")).getText();
	           assertEquals(forgotPasswordText,"Your password has been updated");
	       }
	       else {
	    	   List<WebElement>  passwordText=  getDriver().findElements(By.xpath("//table/tbody/tr[1]/td[5]/div/span/span/following::span[contains(text(),'Your jcrew.com password...')]/parent::span[1]"));
		  		 int size=passwordText.size();
		  		 System.out.println(size);
		  		 for(WebElement ForgotpasswordText:passwordText) {
		  			 String updatedText=ForgotpasswordText.getText();
		  			 if(updatedText.equalsIgnoreCase("Your jcrew.com password...")) {
		  				ForgotpasswordText.click();
		  				 break;
		  			 }
		  			 else {
		  				 System.out.println("Your jcrew.com password Text is not match in Gmail");
		  			 }
		  			
		  	
		       }
		  		Thread.sleep(5000);
		  		WebElement	ForgotPasswordText=   getDriver().findElement(By.xpath("(//span[contains(text(),'Forgot your password?')])[3]"));
		  		Assert.assertTrue("Forgot Password Text is displayed", ForgotPasswordText.isDisplayed());
        }
	}
	
	@Then("Verify_user_is_in_Update UserID_mail_Confirmation_Page")
    public void verify_user_is_in_Update_UserID_mail_Confirmation_Page() throws IOException, InterruptedException { 
		 FileInputStream inputFile = new FileInputStream("properties/e2e.properties");
	       properties.load(inputFile);
          
	     //WebMail
	       String webUrl=  properties.getProperty("webmailURL");
	       if(getDriver().getCurrentUrl().contains(webUrl)) {
	    	String   updateUseridText=   getDriver().findElement(By.xpath("//span[contains(text(),'Your user ID has been updated.')]")).getText();
	           assertEquals(updateUseridText,"Your user ID has been updated.");
	     
	    	 }
	       List<WebElement>  updateUserIDText=  getDriver().findElements(By.xpath("//table/tbody/tr[1]/td[5]/div/span/span/following::span[contains(text(),'Your user ID has been updated')]/parent::span[1]"));
	  		 int size=updateUserIDText.size();
	  		 System.out.println(size);
	  		 for(WebElement userIDText:updateUserIDText) {
	  			 String updatedText=userIDText.getText();
	  			 if(updatedText.equalsIgnoreCase("Your user ID has been updated...")) {
	  				userIDText.click();
	  				 break;
	  			 }
	  			 else {
	  				 System.out.println("Your Updated user ID Text is not match in Gmail");
	  			 }
	  			
	  	
	       }
	  		Thread.sleep(5000);
	  		WebElement	updatedUserIDText=   getDriver().findElement(By.xpath("//h2[contains(text(),'Your user ID has been updated...')]"));
	  		Assert.assertTrue("updated UserID Text is displayed", updatedUserIDText.isDisplayed());
	  		/*WebElement	forwardText=   getDriver().findElement(By.xpath("//table/tbody/tr/td/div/div/span[contains(text(),'Forward')]"));
	  		//WebElement scrollw = getDriver().findElement(By.xpath("//table/tbody/tr/td/div/div/span[contains(text(),'Forward')]"));
	  		//scroll.sendKeys(Keys.PAGE_DOWN); 
	  		((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView();", forwardText); 
	  		forwardText.sendKeys(Keys.PAGE_DOWN);*/
	}
	@Then("Verify user is in mail confirmation page")
    public void verify_user_is_in_mail_confirmation_page() {
		
		Date date = new Date();
		 
		Address add1=E2E2Steps.getFirstAddress();
		String addQas=E2E2Steps.getIsFirstAddressQAS();
		String addLine1=E2E2Steps.getFirstAddress_AddressLine1();
		String addLine2=E2E2Steps.getFirstAddress_AddressLine2();
		String addCity=E2E2Steps.getFirstAddress_City();
		String addState=E2E2Steps.getFirstAddress_State();
		String addZipe=E2E2Steps.getFirstAddress_ZipCode();
		 System.out.println(testdataRowMap);
	     System.out.println(itemCode);
	     System.out.println(shippingAddresses);
	     
	     WebElement element =	getDriver().findElement(By.xpath("//*[@class='Cp']/div/table/tbody/tr"));
	     element.click();
	     
	    
        assertTrue("Order Confirmation message is displayed", getDriver().findElement(By.xpath("//H1[text()='Thank you for shopping at J.Crew']")).isDisplayed());
        String orderTxt=  getDriver().findElement(By.xpath("(//*[contains(text(),'Order Number')])[2]")).getText();
        String billingAdd=  getDriver().findElement(By.xpath("//*[@id=\":gh\"]/div[1]/table/tbody/tr/td/center/table/tbody/tr/td/table[5]/tbody/tr/th[1]")).getText();
        assertTrue("Order displayed in email confirmation page..", orderTxt.contains(ord));
        
        /*String dateTxt=  getDriver().findElement(By.xpath("//FONT[@face='Soleil,Helvetica,Arial,sans-serif'][contains(text(),'Placed on')]")).getText();
       String output = String.format("%1$s %2$tB %2$td, %2$tY", "", date).toString();
       System.out.println(output);*/
       //assertTrue("Verify Order Placed On Date",dateTxt.contains(output));
    }
}