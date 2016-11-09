package com.jcrew.steps;

import com.jcrew.page.QuickShop;
import com.jcrew.pojo.Product;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.StateHolder;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by 9msyed on 10/21/2016.
 */
public class QuickShopSteps extends DriverFactory{
    QuickShop quickShop = new QuickShop(getDriver());
    StateHolder stateHolder = StateHolder.getInstance();

    @Then("Verify quick shop modal is displayed")
    public void verify_quickshop_is_displayed(){
        assertEquals("Verify quick shop modal is displayed",true,quickShop.isQuickShop());

    }

    @Then("Verify quick shop modal is not displayed")
    public void verify_quickshop_is_not_displayed(){
        assertEquals("Verify quick shop modal is not displayed",false,quickShop.isQuickShop());
    }

    @And("Verify product name on QS matches with category array")
    public void verify_product_name_in_quickshop(){
        Product product = (Product) stateHolder.get("fromArray");
       assertEquals("Verify product name in quickshop matches with quickshop",product.getName(),
               quickShop.getProductName());
    }
    @And("Verify product details drawer is closed by default")
    public void verify_product_details_drawer_in_qs(){
        assertEquals("Verify product details drawer is closed by default",true,quickShop.isDetailsDrawerClosed());
    }

    @And("User clicks on view full details link")
    public void click_full_detail_on_qs(){
        quickShop.clickFullOnDetails();
    }
    @And("User clicks on Wishlist button")
    public void click_wishlist(){
        quickShop.clickWishlist();
    }

    @And("User clicks on X icon")
    public void click_on_X_icon(){
        quickShop.clickClose();
    }

    @And("User clicks on Add to Bag button")
    public void click_bag(){
        quickShop.clickBag();
    }
    @And("^Selects random color$")
    public void user_selects_random_color() {
        quickShop.selectRandomColor();
    }

    @When("^Selects random size$")
    public void user_selects_random_size() {
        quickShop.selectRandomSize();
    }
    @When("^Selects random quantity from Quickshop$")
    public void user_selects_random_quantity() {
        quickShop.selectRandomQty();
    }


    @And("^Verify ([^\"]*) is displayed in QS modal$")
    public void verify_element_is_displayed(String element){
       assertTrue("Verify "+element+" is displayed in quick shop",quickShop.isElementDisplayed(element));
    }
    @And("Verify user able to change variations")
    public void verify_user_can_change_variations(){
        assertTrue("Verify user able to change variations in quick shop",quickShop.canChangeVariation());
    }
    @And("Verify ([^\"]*) message displayed")
    public void verify_error_message(String errorMessage){
        assertEquals("Verify error message on Quick shop",errorMessage,quickShop.getMessage());
    }
    @And("Verify ([^\"]*) button is in disabled state")
    public void verify_button_state(String element){
        assertEquals("Verify error message on Quick shop",false,quickShop.isEnabled(element));
    }

}
