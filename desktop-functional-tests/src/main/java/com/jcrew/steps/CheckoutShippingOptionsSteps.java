package com.jcrew.steps;

import com.jcrew.page.CheckoutShippingOptions;
import com.jcrew.pojo.Country;
import com.jcrew.pojo.ShippingMethod;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.ShippingMethodCalculator;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.TestDataReader;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by nadiapaolagarcia on 5/6/16.
 */
public class CheckoutShippingOptionsSteps extends DriverFactory {

    private CheckoutShippingOptions shippingOptions = new CheckoutShippingOptions(getDriver());
    private final StateHolder stateHolder = StateHolder.getInstance();
    private TestDataReader testDataReader = TestDataReader.getTestDataReader();
    private ShippingMethodCalculator methodCalculator = new ShippingMethodCalculator();

    @Then("Verify Shipping And Gift Options page is displayed")
    public void is_shipping_options() {
    	
    	if(stateHolder.hasKey("isShippingDisabled") || stateHolder.hasKey("isSTS"))
    		return;
    	
        assertTrue("Is shipping and gift options page", shippingOptions.isDisplayed());
    }

    @Then("^Verify Shipping Options Page url is ([^\"]*)$")
    public void review_page_url(String url) {
        String current_url = getDriver().getCurrentUrl();
        assertTrue("Review page url is " + url, current_url.contains(url));
    }

    @Then("Verify that Shipping Options title is Checkout")
    public void verify_title() {
        String title = shippingOptions.getTitle();

        assertTrue("Title is Checkout", "Checkout".equalsIgnoreCase(title));
    }

    @Then("Verify checkout breadcrumb is SHIPPING & GIFT OPTIONS")
    public void verify_progress() {
        assertEquals("Breadcrumb is SHIPPING & GIFT OPTIONS", "SHIPPING & GIFT OPTIONS",
                shippingOptions.getBreadCrumb().toUpperCase());
    }

    @Then("^Verify that this shipping methods are available$")
    public void shipping_methods(List<ShippingMethod> methods) {
        List<ShippingMethod> pageMethods = shippingOptions.getShippingMethods();

        for (int i = 0; i < methods.size(); i++) {
            ShippingMethod page = pageMethods.get(i);
            ShippingMethod list = methods.get(i);

            assertTrue("Expected shipping method:" + list.toString() +
                    " Actual shipping method: " + page.toString(), page.equals(list));
        }
    }

    @Then("^Verify that this shipping methods are available including Thursday cut$")
    public void shipping_methods_include_thursday_cut(List<ShippingMethod> methods) {
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        List<ShippingMethod> pageMethods = shippingOptions.getShippingMethods();

        for (int i = 0; i < pageMethods.size(); i++) {
            ShippingMethod page = pageMethods.get(i);
            ShippingMethod list = methods.get(i);

            if (!list.isThursday()) {
                assertTrue("Expected standard shipping method", page.equals(list));
            } else if (list.isThursday() & (day == Calendar.THURSDAY || day == Calendar.FRIDAY)) {
                assertTrue("Expected thursday cut shipping method", page.equals(list));
            }
        }
    }
    
    
	@And("^Verify shipping methods are available$")
    public void verify_shipping_methods(List<String> expectedShippingMethods){
    	
    	List<ShippingMethod> expectedShippingMethodsList = new ArrayList<ShippingMethod>();
    	TestDataReader dataReader = TestDataReader.getTestDataReader();
    	String orderSubtotalThreshold = dataReader.getData("order.subtotal.threshold.free.shipping");
    	orderSubtotalThreshold = orderSubtotalThreshold.replaceAll("[^0-9]", "");
    	int threshold = Integer.parseInt(orderSubtotalThreshold);
    	
    	String orderSubTotal = stateHolder.get("subtotal");
    	orderSubTotal = orderSubTotal.replaceAll("[^0-9]", "");
    	int subTotal = Integer.parseInt(orderSubTotal);
    	
    	for (int i = 0; i < expectedShippingMethods.size(); i++) {
            String expectedShippingMethod = expectedShippingMethods.get(i);     
            
            String expectedShippingMethodName = dataReader.getData(expectedShippingMethod + ".shipping.method.name");            
            String expectedShippingMethodPrice= dataReader.getData(expectedShippingMethod + ".shipping.method.price1");
            
            if(expectedShippingMethod.equalsIgnoreCase("economy")){            	
            	if(subTotal>=threshold){
            		expectedShippingMethodPrice = dataReader.getData(expectedShippingMethod + ".shipping.method.price2");
            	}
            }
            
            boolean isShippingMethodSkipped = false;
            switch(expectedShippingMethod.toLowerCase()){
            	case "economy":
            		if(subTotal>=threshold){
                		expectedShippingMethodPrice = dataReader.getData(expectedShippingMethod + ".shipping.method.price2");
                	}
            		break;
            	case "overnight":
            		Calendar calendar = Calendar.getInstance();
            		if(calendar.get(Calendar.HOUR_OF_DAY)>11){
            			isShippingMethodSkipped = true;
            		}
            		break;
            }
            
            if(isShippingMethodSkipped){
            	continue;
            }
            
            String expectedShippingMethodText= dataReader.getData(expectedShippingMethod + ".shipping.method.text");
            boolean expectedShippingMethodThursdayCut = Boolean.getBoolean(dataReader.getData(expectedShippingMethod + ".shipping.method.thursday.cut"));
            
            ShippingMethod shippingMethod = new ShippingMethod(expectedShippingMethodName, expectedShippingMethodPrice, expectedShippingMethodText, expectedShippingMethodThursdayCut);
            expectedShippingMethodsList.add(shippingMethod);
    	}
    	
    	shipping_methods_include_thursday_cut(expectedShippingMethodsList);
    }

    @Then("^Verify that this shipping method is selected by default$")
    public void default_method(List<ShippingMethod> method) {
        ShippingMethod pageMethod = shippingOptions.getSelectedShippingMethod();

        assertTrue("Expected standard shipping method", pageMethod.equals(method.get(0)));
    }
    
	@And("^Verify the below shipping method is selected by default$")
    public void verify_default_shipping_method(List<String> defaultShippingMethod){
    	
    	List<ShippingMethod> expectedShippingMethodsList = new ArrayList<ShippingMethod>();
    	TestDataReader dataReader = TestDataReader.getTestDataReader();
    	String orderSubtotalThreshold = dataReader.getData("order.subtotal.threshold.free.shipping");
    	orderSubtotalThreshold = orderSubtotalThreshold.replaceAll("[^0-9]", "");
    	int threshold = Integer.parseInt(orderSubtotalThreshold);
    	
    	String orderSubTotal = stateHolder.get("subtotal");
    	orderSubTotal = orderSubTotal.replaceAll("[^0-9]", "");
    	int subTotal = Integer.parseInt(orderSubTotal);
    	
    	String expectedShippingMethod = defaultShippingMethod.get(0);     
        
        String expectedShippingMethodName = dataReader.getData(expectedShippingMethod + ".shipping.method.name");            
        String expectedShippingMethodPrice= dataReader.getData(expectedShippingMethod + ".shipping.method.price1");
        
        if(expectedShippingMethod.equalsIgnoreCase("economy")){            	
        	if(subTotal>=threshold){
        		expectedShippingMethodPrice = dataReader.getData(expectedShippingMethod + ".shipping.method.price2");
        	}
        }
        
        String expectedShippingMethodText= dataReader.getData(expectedShippingMethod + ".shipping.method.text");
        
        ShippingMethod shippingMethod = new ShippingMethod(expectedShippingMethodName, expectedShippingMethodPrice, expectedShippingMethodText);
        expectedShippingMethodsList.add(shippingMethod);
        
        default_method(expectedShippingMethodsList);
    }

    @When("^User selects a random shipping method and continues$")
    public void select_random_shipping_method_and_continue() {
        shippingOptions.selectShippingMethod();
        shippingOptions.continueCheckout();
    }
    
    @When("^User selects a random shipping method$")
    public void select_random_shipping_method() {
        shippingOptions.selectShippingMethod();
    }

    @When("User continues to Payment Method page")
    public void continue_to_payment_method() {
    	if(stateHolder.hasKey("isShippingDisabled") || stateHolder.hasKey("isShippingMethodContinueClicked") || stateHolder.hasKey("isSTS"))
			return;
    	
        shippingOptions.continueCheckout();
    }

    @Then("^Verify Shipping Options Page contains gift option section$")
    public void gift_options_section() {
        assertTrue("Gift options section is displayed", shippingOptions.hasGiftOption());
    }

    @When("User selects gift option and adds message: ([^\"]*)")
    public void add_gift_option(String message) {
        shippingOptions.addGiftOption();
        shippingOptions.addGiftMessage(message);
    }
    
    @Then("^Verify gift receipt info message is '([^\"]*)'$")
    public void verify_gift_receipt_info_message(String expectedMessage){
    	String actualMessage = shippingOptions.getGiftReceiptInfoMessage().toLowerCase();
    	assertEquals("Gift receipt info message should be displayed as " + expectedMessage, expectedMessage.toLowerCase(), actualMessage);    	
    }
    
    @And("^Verify default value for shipping method$")
    public void uses_default_value_for_shipping_method() throws Throwable {
        Country country = (Country) stateHolder.get("context");
        String countryName = country.getCountryName().toLowerCase().trim();
        String countryCode = country.getCountry();

        if (countryCode.equalsIgnoreCase("us")) {

            String addressType = (String) stateHolder.get("atpAddressType");
            String expectedDefaultShipMethod = testDataReader.getData(addressType + ".default.shipping.method");

            String actualShippingMethodSelected = shippingOptions.getSelectedShippingMethodName().toLowerCase();
            actualShippingMethodSelected = actualShippingMethodSelected.split("\\(")[0].trim();


            List<ShippingMethod> expectedMethods = methodCalculator.getExpectedList();
            for (int i = 0; i < expectedMethods.size(); i++) {
                ShippingMethod method = expectedMethods.get(i);
                if (method.getPrice().equalsIgnoreCase("free")) {
                    expectedDefaultShipMethod = expectedDefaultShipMethod.split("\\(")[0].trim();
                    break;
                }
            }

            assertEquals("Default shipping method selected should be ", expectedDefaultShipMethod, actualShippingMethodSelected);

        } else {

            assertTrue("First shipping method should be selected by default for the country " + countryName, shippingOptions.isFirstShippingMethod());
        }
    }
    
    @And("^validate correct shipping methods displayed on the page$")
    public void validate_shipping_methods() {
        Country country = (Country) stateHolder.get("context");
        String countryCode = country.getCountry();

        List<ShippingMethod> pageMethods = shippingOptions.getShippingMethods();
        stateHolder.put("actualShippingMethods", pageMethods);

        if (countryCode.equalsIgnoreCase("us")) {
            List<ShippingMethod> expectedMethods = methodCalculator.getExpectedList();
            stateHolder.put("expectedShippingMethods", expectedMethods);

            for (int i = 0; i < expectedMethods.size(); i++) {
                ShippingMethod actual = pageMethods.get(i);
                ShippingMethod expected = expectedMethods.get(i);

                assertEquals("Expected: " + expected.toString() + " actual: " + actual.toString() + " should be same", expected, actual);
                verify_ATP_date(actual, expected);
            }
        } else {
            String shipMethods[] = testDataReader.getDataArray(countryCode + ".shippingMethods");
            List<String> expectedMethods = Arrays.asList(shipMethods);

            for (int i = 0; i < expectedMethods.size(); i++) {
                String actualShipMethod = pageMethods.get(i).getMethod().toLowerCase();
                String expectedShipMethod = expectedMethods.get(i).toLowerCase();
                assertEquals("Expected shipping method", expectedShipMethod, actualShipMethod);
            }

        }

    }
    
    public void verify_ATP_date(ShippingMethod actual, ShippingMethod expected) {
        //Verifies if ATP date is falling in between expected date range
        String actualName = actual.getMethod().replaceAll("[^a-zA-Z0-9]", "");
        String expectedName = expected.getMethod().replaceAll("[^a-zA-Z0-9]", "");

        String actualDate = actualName.replaceFirst(expectedName, "").trim();
        actualDate = actualDate.replace("–", "").trim();
        if (!actualDate.isEmpty()) {
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("EEEE, MMMM dd");
            try {
                Date date = dateFormat1.parse(actualDate);
                Calendar actualShipDay = Calendar.getInstance();
                actualShipDay.setTime(date);

                Calendar today = Calendar.getInstance();

                int actualMonth = actualShipDay.get(Calendar.MONTH);
                int currentMonth = today.get(Calendar.MONTH);
                int currentYear = today.get(Calendar.YEAR);

                if (actualMonth < currentMonth) {
                    actualShipDay.set(Calendar.YEAR, currentYear + 1);
                } else {
                    actualShipDay.set(Calendar.YEAR, currentYear);
                }
                Date actualShipDate = actualShipDay.getTime();

                Date startDate = expected.getStartDate();
                Date endDate = expected.getEndDate();

                assertTrue("ATP shipping date for the method " + expectedName + " should be between " + startDate.toString() + " and " + endDate.toString() + ". But, the actual ship date is " + actualShipDate.toString(),(!actualShipDate.before(startDate)) && (!actualShipDate.after(endDate)));


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}