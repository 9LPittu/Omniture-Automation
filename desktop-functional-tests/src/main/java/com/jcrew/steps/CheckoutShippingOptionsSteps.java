package com.jcrew.steps;

import com.jcrew.page.CheckoutShippingOptions;
import com.jcrew.pojo.ShippingMethod;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.TestDataReader;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.ArrayList;
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

    @Then("Verify Shipping And Gift Options page is displayed")
    public void is_shipping_options() {
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
    	
    	String orderSubTotal = shippingOptions.stateHolder.get("ordersubtotal");
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
    	
    	String orderSubTotal = shippingOptions.stateHolder.get("ordersubtotal");
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
}