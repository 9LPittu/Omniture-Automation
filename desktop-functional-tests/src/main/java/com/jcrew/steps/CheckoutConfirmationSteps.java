package com.jcrew.steps;

import com.jcrew.page.CheckoutConfirmation;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.PropertyReader;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.openqa.selenium.WebDriverException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by nadiapaolagarcia on 5/3/16.
 */
public class CheckoutConfirmationSteps extends DriverFactory {
    private CheckoutConfirmation confirmation;
    private boolean isProduction = false;

    public CheckoutConfirmationSteps() {
        PropertyReader properties = PropertyReader.getPropertyReader();
        String environment = properties.getProperty("environment");

        if("production".equalsIgnoreCase(environment)) {
            isProduction = true;
        } else {
            confirmation = new CheckoutConfirmation(getDriver());
        }
    }

    @Then("Verify user gets a confirmation number")
    public void get_confirmation_number() {
        if(!isProduction) {            
            if (confirmation.isOrderConfirmationPage()) {
            	assertTrue("User is in confirmation page", confirmation.isDisplayed());
                assertFalse("Get confirmation number", confirmation.getConfirmationCode().isEmpty());
                confirmation.stateHolder.put("orderNumber", confirmation.getConfirmationCode());
            } else {
                float total = confirmation.getOrderTotal();
                assertTrue("Order total is " + total + " than threshold and there is no error", confirmation.hasErrors());
            }
        }
    }

    @Then("Verify Confirmation Page url is ([^\"]*)")
    public void review_page_url(String url) {
        if(!isProduction) {
            String current_url = getDriver().getCurrentUrl();
            assertTrue("Confirmation page url is " + url, current_url.contains(url));
        }
    }

    @Then("Verify that title is Order Complete")
    public void verify_title() {
        if(!isProduction) {
            String title = confirmation.getTitle().toLowerCase().replaceAll("print", "").trim();

            assertEquals("Title of order confirmation page is ", "order complete",title);
        }
    }

    @Then("Verify Gift message is ([^\"]*)")
    public void gift_message(String message) {
        if(!isProduction) {
            String messageInPage = confirmation.getGitfMessage();

            assertEquals("Confirmation page has the expected gift message", message.toLowerCase(), messageInPage.toLowerCase());
        }
    }

    @Then("Verify that confirmation message is visible")
    public void verify_subtitle() {
        if(!isProduction) {
            String title = confirmation.getSubtitle().replace("\n", " ");
            String expected = "THANK YOU FOR SHOPPING AT ";

            PropertyReader propertyReader = PropertyReader.getPropertyReader();
            String brandName = propertyReader.getProperty("brand");

            if(brandName.equalsIgnoreCase("factory")) {
                expected += "JCREWFACTORY.COM";
            } else if(brandName.equalsIgnoreCase("jcrew")) {
                expected += "JCREW.COM";
            } else {
                throw new WebDriverException("Unable to find subtitle for brand " + brandName);
            }

            assertTrue("Confirmation message is displayed", title.equalsIgnoreCase(expected));
        }
    }
    
    @Then("Verify user is in order confirmation page")
    public void verify_user_is_in_order_confirmation_page() {
        assertTrue("User should be in order confirmation page", confirmation.isOrderConfirmationPage());
    }
    
    @And("User closes the Bizrate Popup")
    public void closeBizratePopup() {
        if(!isProduction) {
         confirmation.handleBizratePopup();                    
        }
    }
}
