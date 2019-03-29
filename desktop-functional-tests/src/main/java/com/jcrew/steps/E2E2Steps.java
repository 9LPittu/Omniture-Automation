package com.jcrew.steps;

import java.util.List;

import org.openqa.selenium.WebDriverException;
import com.github.javafaker.Faker;
import com.jcrew.page.checkout.CheckoutBilling;
import com.jcrew.page.checkout.CheckoutBillingPayment;
import com.jcrew.page.checkout.CheckoutGiftOptions;
import com.jcrew.page.checkout.CheckoutMultipleShippingAddresses;
import com.jcrew.page.checkout.CheckoutReview;
import com.jcrew.page.checkout.CheckoutShippingAdd;
import com.jcrew.page.checkout.CheckoutShippingOptions;
import com.jcrew.page.PaypalLogin;
import com.jcrew.page.PaypalReview;
import com.jcrew.pojo.Address;
import com.jcrew.utils.Util;

import cucumber.api.java.en.And;
import cucumber.api.java.en.When;

public class E2E2Steps extends E2ECommon {
	static Address billingAddress=null;
	static Address firstAddress =null;
	static String isFirstAddressQAS =null;
	static String firstAddress_AddressLine1 =null;
    static String firstAddress_AddressLine2 =null;
	public static String getIsFirstAddressQAS() {
		return isFirstAddressQAS;
	}

	public static void setIsFirstAddressQAS(String isFirstAddressQAS) {
		E2E2Steps.isFirstAddressQAS = isFirstAddressQAS;
	}

	public static String getFirstAddress_AddressLine1() {
		return firstAddress_AddressLine1;
	}

	public static void setFirstAddress_AddressLine1(String firstAddress_AddressLine1) {
		E2E2Steps.firstAddress_AddressLine1 = firstAddress_AddressLine1;
	}

	public static String getFirstAddress_AddressLine2() {
		return firstAddress_AddressLine2;
	}

	public static void setFirstAddress_AddressLine2(String firstAddress_AddressLine2) {
		E2E2Steps.firstAddress_AddressLine2 = firstAddress_AddressLine2;
	}

	public static String getFirstAddress_City() {
		return firstAddress_City;
	}

	public static void setFirstAddress_City(String firstAddress_City) {
		E2E2Steps.firstAddress_City = firstAddress_City;
	}

	public static String getFirstAddress_State() {
		return firstAddress_State;
	}

	public static void setFirstAddress_State(String firstAddress_State) {
		E2E2Steps.firstAddress_State = firstAddress_State;
	}

	public static String getFirstAddress_ZipCode() {
		return firstAddress_ZipCode;
	}

	public static void setFirstAddress_ZipCode(String firstAddress_ZipCode) {
		E2E2Steps.firstAddress_ZipCode = firstAddress_ZipCode;
	}

	static String firstAddress_City =null;
	static String firstAddress_State =null;
	static String firstAddress_ZipCode =null;
	public static Address getFirstAddress() {
		return firstAddress;
	}

	public static void setFirstAddress(Address firstAddress) {
		E2E2Steps.firstAddress = firstAddress;
	}

	public static Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	@When("^User selects Shipping Methods as per testdata$")
	public void user_selects_shipping_methods() throws InterruptedException {
		/*if(getDataFromTestDataRowMap("E2E Scenario Description").contains("Express paypal")) {
			return;
		}*/
		
		if (stateHolder.hasKey("isShippingDisabled"))
			return;

		String multipleShippingAddressRequired = getDataFromTestDataRowMap("Multiple Shipping Address Required?");
		String multipleShippingMethodsRequired = getDataFromTestDataRowMap("Multiple Shipping Methods Required?");
		String shippingMethods = getDataFromTestDataRowMap("Shipping Methods");

		/*
		 * 1. If scenario specifies single shipping address, then obviously
		 * multiple shipping methods are not possible 
		 * 
		 * 2. If scenario specifies multiple shipping addresses and no data is provided for shipping
		 * methods, then default selections will be used
		 * 
		 * 3. If scenario specifies multiple shipping addresses and shipping methods are
		 * provided in test data, then shipping methods will be selected as per
		 * test data
		 */

		CheckoutShippingOptions shippingOptions = new CheckoutShippingOptions(getDriver());

		if (multipleShippingAddressRequired.equalsIgnoreCase("NO")) {
			// single shipping method selection
			if (shippingMethods.isEmpty())
				return;

			shippingOptions.selectSpecificShippingMethod(shippingMethods);
		} else {
			if (multipleShippingMethodsRequired.equalsIgnoreCase("YES")) {
				String[] arrShippingMethods = shippingMethods.split(getE2ETestdataDelimiter());
				shippingOptions.selectMultipleShippingMethods(arrShippingMethods);
			}
		}
	}

	@And("^User select Gift Options as per testdata, if required$")
	public void select_gift_options() {
		/*if(getDataFromTestDataRowMap("E2E Scenario Description").contains("Express paypal")) {
			return;
		}*/
		if (stateHolder.hasKey("isShippingDisabled"))
			return;

		String giftOptionSelection = getDataFromTestDataRowMap("Gift Option Selection");
		String giftWrappingService = getDataFromTestDataRowMap("Gift Wrapping Service");

		CheckoutShippingOptions shippingOptions = new CheckoutShippingOptions(getDriver());

		if (giftOptionSelection.equalsIgnoreCase("NONE") || giftOptionSelection == null) {
			return;
		} else {
			// Select gift option as 'yes'
			shippingOptions.selectGiftOptionRadioButtons();

			if (giftOptionSelection.equalsIgnoreCase("GIFT RECEIPT")) {
				shippingOptions.enterGiftReceiptMessages();
			} else {
				if (giftWrappingService.isEmpty() || giftWrappingService == null
						|| giftWrappingService.equalsIgnoreCase("NONE")) {
					String message = "Gift Wrapping Service value is provided as NONE/empty. Please select valid value for Gift wrapping service.";
					Util.e2eErrorMessagesBuilder(message);
					throw new WebDriverException(message);
				}
				shippingOptions.selectGiftWrappingServiceRadioButtons();
			}

			// click on 'continue' button on shipping methods page
			shippingOptions.continueCheckout();
			stateHolder.put("isShippingMethodContinueClicked", true);

			// gift boxes selection
			if (giftOptionSelection.toUpperCase().contains("GIFT WRAPPING")) {
				CheckoutGiftOptions giftOptions = new CheckoutGiftOptions(getDriver());
				giftOptions.selectGiftBoxesForItems(giftWrappingService);

				if (giftOptionSelection.toUpperCase().contains("GIFT MESSAGE")) {
					giftOptions.enterGiftBoxMessages();
				}

				giftOptions.continueCheckout();
			}
		}
	}

	@And("^Navigate to Billing page, if user is on review page and only e-gift card is added to bag$")
	public void navigate_billing_page_when_only_egift_card_is_added() {
		/*if(getDataFromTestDataRowMap("E2E Scenario Description").contains("Express paypal")) {
			return;
		}*/
		String currentPageTitle = getDriver().getTitle().toLowerCase();
		if (currentPageTitle.contains("review") && stateHolder.hasKey("isShippingDisabled")) {
			CheckoutReview review = new CheckoutReview(getDriver());
			review.editDetails("billing");
			new CheckoutBilling(getDriver());
		}
	}

	@When("^User selects/enters Payment Methods as per testdata$")
	public void user_selects_payment_methods() {
		CheckoutBilling checkoutBilling = new CheckoutBilling(getDriver());
		if(getDataFromTestDataRowMap("E2E Scenario Description").contains("Express paypal")) {
			checkoutBilling.clickOnPaypalGoldLogo();
			return;
		}
		String userType = getDataFromTestDataRowMap("User Type");
		String splitPaymentsRequired = getDataFromTestDataRowMap("Split Payments Required?");
		String paymentMethod1 = getDataFromTestDataRowMap("Payment Method 1");
		String paymentMethod2 = getDataFromTestDataRowMap("Payment Method 2");

		if (!splitPaymentsRequired.equalsIgnoreCase("YES")) {

			// single payment method selection
			if (paymentMethod1.isEmpty())
				return;

			singlePaymentMethod(userType, paymentMethod1);

			enter_billing_address();

		} else {
			// split payment methods selection

			// first payment method selection
			singlePaymentMethod(userType, paymentMethod1);

			enter_billing_address();

			if (paymentMethod2.isEmpty())
				return;

			
			CheckoutBillingPayment checkoutBillingPayment = new CheckoutBillingPayment(getDriver());

			if (userType.equalsIgnoreCase("GUEST")) {
				checkoutBilling.continueCheckout();
				E2ECommon e2e = new E2ECommon();
				if (e2e.getDataFromTestDataRowMap("OrderType").equalsIgnoreCase("STS")) {
					checkoutBilling.clickOnSelectedAddress();
				}
				checkoutBilling.clickOnChangeButton();
				checkoutBilling.addNewCard();
				checkoutBillingPayment.addNewCreditDebitCard(paymentMethod2);
				checkoutBilling.clickOnChangeButton();
			}
			if(paymentMethod1.equalsIgnoreCase("Gift Card")) {
				singlePaymentMethod(userType, paymentMethod2);
				checkoutBilling.continueCheckout();
			}else {
			// second payment selection
			checkoutBilling.clickTwoCardsPayment();
			checkoutBilling.continueCheckout();
			checkoutBilling.splitPayment(paymentMethod1, paymentMethod2);
			stateHolder.put("isBillingContinueClicked", true);
			}
		}
	}

	public void singlePaymentMethod(String userType, String paymentMethodName) {
		CheckoutBilling checkoutBilling = new CheckoutBilling(getDriver());
		switch (paymentMethodName.toUpperCase()) {
		case "PAYPAL":
			checkoutBilling.selectPaypalRadioButton();

			if (userType.equalsIgnoreCase("GUEST")) {
				checkoutBilling.enterEmailAddress();
			}
			checkoutBilling.clickOnPayPalButton();
			checkoutBilling.switchToPayPalWindow();
			// checkoutBilling.continueCheckout();
			// enterPaypalDetails();
			break;
		default:
			if (/*!userType.equalsIgnoreCase("NONEXPRESS") &&*/ userType.equalsIgnoreCase("EXPRESS")) {
				checkoutBilling.selectSpecificPaymentMethod(paymentMethodName);
			} else if (userType.equalsIgnoreCase("GUEST")) {
				checkoutBilling.fillPaymentCardDetails(paymentMethodName);
			}
		}
	}

	@And("^User completes Paypal transaction, if required$")
	public void user_completes_paypal_transaction() {

		String paymentMethod = getDataFromTestDataRowMap("Payment Method");
		if (!paymentMethod.equalsIgnoreCase("EXPRESS PAYPAL"))
			return;

		enterPaypalDetails();
	}

	public void enterPaypalDetails() {
		PaypalLogin paypalLogin = new PaypalLogin(getDriver());
		String paypalEmail = testdataReader.getData("paypal.email");
		String paypalPassword = testdataReader.getData("paypal.password");
		paypalLogin.submitPaypalCredentials(paypalEmail, paypalPassword);

		PaypalReview paypalReview = new PaypalReview(getDriver());
		paypalReview.clickContinue();

		stateHolder.put("isBillingContinueClicked", true);
	}

	@And("^User enters security code as per payment method, if required$")
	public void user_enters_security_code() {
		if(getDataFromTestDataRowMap("E2E Scenario Description").contains("Express paypal")) {
			return;
		}
		String userType = getDataFromTestDataRowMap("User Type");

		switch (userType.toUpperCase()) {
		case "NONEXPRESS":
			enterSecurityCodeForNonExpressUser();
			break;
		case "EXPRESS":
			enterSecurityCodeForExpressUser();
			break;
		}
	}

	public void enterSecurityCodeForNonExpressUser() {
		
		String splitPaymentsRequired = getDataFromTestDataRowMap("Split Payments Required?");
		String paymentMethod1 = getDataFromTestDataRowMap("Payment Method 1");
		String paymentMethod2 = getDataFromTestDataRowMap("Payment Method 2");
		CheckoutReview checkoutReview = new CheckoutReview(getDriver());
		
		if (!splitPaymentsRequired.equalsIgnoreCase("YES")) {
			switch (paymentMethod1.toUpperCase()) {
			case "PAYPAL":
			case "JCC":
			case "Gift Card":
				return;
			default:
				// entering security code when single payment method is used
				checkoutReview.enterSecurityCode(paymentMethod1);
			}
		} else {
			// entering security code when split payment is done
			checkoutReview.enterSecurityCode(paymentMethod1);
			checkoutReview.enterSecurityCode(paymentMethod2);
		}
	}

	public void enterSecurityCodeForExpressUser() {
		/*String paymentMethod = getDataFromTestDataRowMap("Payment Method");

		if (paymentMethod.equalsIgnoreCase("EXPRESS PAYPAL"))
			return;

		CheckoutReview checkoutReview = new CheckoutReview(getDriver());
		checkoutReview.enterSecurityCode();*/
		
		String splitPaymentsRequired = getDataFromTestDataRowMap("Split Payments Required?");
		String paymentMethod1 = getDataFromTestDataRowMap("Payment Method 1");
		String paymentMethod2 = getDataFromTestDataRowMap("Payment Method 2");
		CheckoutReview checkoutReview = new CheckoutReview(getDriver());
		
		if (!splitPaymentsRequired.equalsIgnoreCase("YES")) {
			switch (paymentMethod1.toUpperCase()) {
			case "PAYPAL":
			case "JCC":
			case "Gift Card":
				return;
			default:
				// entering security code when single payment method is used
				checkoutReview.enterSecurityCode(paymentMethod1);
			}
		} else {
			// entering security code when split payment is done
			checkoutReview.enterSecurityCode(paymentMethod1);
			checkoutReview.enterSecurityCode(paymentMethod2);
		}
	}

	@When("^User enters Shipping Addresses as per testdata$")
	public void user_enters_shipping_addresses() throws Exception {
		CheckoutShippingAdd checkoutShippingAdd = new CheckoutShippingAdd(getDriver());
		if (stateHolder.hasKey("isShippingDisabled"))
			return;
		if (getDataFromTestDataRowMap("OrderType").equalsIgnoreCase("STS")) {
			checkoutShippingAdd.selectSTS();
			// checkoutShipping.continueCheckout();
		} else {
			String multipleShippingAddressRequired = getDataFromTestDataRowMap("Multiple Shipping Address Required?");

			if (multipleShippingAddressRequired.equalsIgnoreCase("YES")) {

				int itemsCount = stateHolder.get("itemsCount");
				if (itemsCount < 2) {
					String message = "Multiple shipping addresses are selected in the testdata. But only 1 item is added to bag.";
					Util.e2eErrorMessagesBuilder(message);
					throw new WebDriverException(message);
				}

				checkoutShippingAdd.selectMultipleAddressesRadioButton();
			}
			
			 isFirstAddressQAS = getDataFromTestDataRowMap("is First Address QAS?");
			 firstAddress_AddressLine1 = getDataFromTestDataRowMap("FirstAddress_AddressLine1");
			 firstAddress_AddressLine2 = getDataFromTestDataRowMap("FirstAddress_AddressLine2");
			 firstAddress_City = getDataFromTestDataRowMap("FirstAddress_City");
			 firstAddress_State = getDataFromTestDataRowMap("FirstAddress_State");
			 firstAddress_ZipCode = getDataFromTestDataRowMap("FirstAddress_ZipCode");

			/*Address firstAddress = new Address(firstAddress_AddressLine1, firstAddress_AddressLine2, firstAddress_City,
					firstAddress_State, firstAddress_ZipCode, new Faker().phoneNumber().phoneNumber());
			checkoutShippingAdd.fillShippingData(firstAddress);*/
			 firstAddress = new Address(firstAddress_AddressLine1, firstAddress_AddressLine2, firstAddress_City,
					firstAddress_State, firstAddress_ZipCode, new Faker().phoneNumber().phoneNumber());
			checkoutShippingAdd.fillShippingData(firstAddress);

			stateHolder.addToList("shippingAddresses", firstAddress_AddressLine1);

			checkoutShippingAdd.continueCheckout();
			checkoutShippingAdd.useSameAddressLink();
			stateHolder.put("isShippingAddressContinueClicked", true);

			if (isFirstAddressQAS.equalsIgnoreCase("YES")) {
				checkoutShippingAdd.handleQAS();
			}

			if (multipleShippingAddressRequired.equalsIgnoreCase("YES")) {
				checkoutShippingAdd.clickAddNewShippingAddress();

				String isSecondAddressQAS = getDataFromTestDataRowMap("is Second Address QAS?");
				String secondAddress_AddressLine1 = getDataFromTestDataRowMap("SecondAddress_AddressLine1");
				String secondAddress_AddressLine2 = getDataFromTestDataRowMap("SecondAddress_AddressLine2");
				String secondAddress_City = getDataFromTestDataRowMap("SecondAddress_City");
				String secondAddress_State = getDataFromTestDataRowMap("SecondAddress_State");
				String secondAddress_ZipCode = getDataFromTestDataRowMap("SecondAddress_ZipCode");
				Address secondAddress = new Address(secondAddress_AddressLine1, secondAddress_AddressLine2,
						secondAddress_City, secondAddress_State, secondAddress_ZipCode,
						new Faker().phoneNumber().phoneNumber());
				checkoutShippingAdd.addNewShippingAddress(isSecondAddressQAS, secondAddress);
				stateHolder.addToList("shippingAddresses", secondAddress_AddressLine1);
				checkoutShippingAdd.useAddressAsEntered();
				checkoutShippingAdd.continueCheckoutForMulti();

				CheckoutMultipleShippingAddresses multiShipping = new CheckoutMultipleShippingAddresses(getDriver());
				List<String> shippingAddressesList = stateHolder.getList("shippingAddresses");
				multiShipping.multiShippingAddressSelection(shippingAddressesList);

				multiShipping.continueCheckout();

				stateHolder.put("isShippingAddressContinueClicked", true);
			}
		}
	}

	public void enter_billing_address() {

		String userType = getDataFromTestDataRowMap("User Type");

		if (!userType.equalsIgnoreCase("GUEST"))
			return;

		String differentBillingAddressRequired = getDataFromTestDataRowMap("Different Billing Address Required?");

		if (!differentBillingAddressRequired.equalsIgnoreCase("YES") && !stateHolder.hasKey("isShippingDisabled"))
			return;

		String isBillingAddressQAS = getDataFromTestDataRowMap("is Billing Address QAS?");
		String billingAddress_Country = getDataFromTestDataRowMap("BillingAddress_Country");
		String billingAddress_FirstName = getDataFromTestDataRowMap("BillingAddress_FirstName");
		String billingAddress_LastName = getDataFromTestDataRowMap("BillingAddress_LastName");
		String billingAddress_AddressLine1 = getDataFromTestDataRowMap("BillingAddress_AddressLine1");
		String billingAddress_AddressLine2 = getDataFromTestDataRowMap("BillingAddress_AddressLine2");
		String billingAddress_City = getDataFromTestDataRowMap("BillingAddress_City");
		String billingAddress_State = getDataFromTestDataRowMap("BillingAddress_State");
		String billingAddress_ZipCode = getDataFromTestDataRowMap("BillingAddress_ZipCode");

		/*Address billingAddress = new Address(billingAddress_FirstName, billingAddress_LastName,
				billingAddress_AddressLine1, billingAddress_AddressLine2, billingAddress_City, billingAddress_State,
				billingAddress_ZipCode, new Faker().phoneNumber().phoneNumber(), billingAddress_Country);*/
		  billingAddress = new Address(billingAddress_FirstName, billingAddress_LastName,
				billingAddress_AddressLine1, billingAddress_AddressLine2, billingAddress_City, billingAddress_State,
				billingAddress_ZipCode, new Faker().phoneNumber().phoneNumber(), billingAddress_Country);

		if (stateHolder.hasKey("isShippingDisabled")) {
			CheckoutBilling checkoutBilling = new CheckoutBilling(getDriver());
			checkoutBilling.enterBillingAddress(billingAddress);

			if (isBillingAddressQAS.equalsIgnoreCase("YES")) {
				checkoutBilling.continueCheckout();
				stateHolder.put("isBillingContinueClicked", true);
				checkoutBilling.handleQAS();
			}

		} else {
			CheckoutBillingPayment checkoutBillingPayment = new CheckoutBillingPayment(getDriver());
			checkoutBillingPayment.addNewBillingAdrress(isBillingAddressQAS, billingAddress);
		}
	}
}