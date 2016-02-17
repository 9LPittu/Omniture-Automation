package com.jcrew.steps;

import com.jcrew.page.MultiplePdpPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

import static org.junit.Assert.*;

public class MultiplePdpSteps extends DriverFactory{

    private final MultiplePdpPage multiplePDP = new MultiplePdpPage(getDriver());

    @Then("^Verifies multiple pdp page contains pagination$")
    public void verifies_multiple_pdp_page_contains_pagination(){
        assertTrue("Multiple PDP contains pagination", multiplePDP.containsNavigation());
    }

    @Then("^Verifies initial multiple pdp page state$")
    public void verifiesInitialMultiplePdpPageState() {
        assertTrue("Previous product is disabled", multiplePDP.isArrowDisabled("previous"));
        assertEquals("First product is selected", multiplePDP.getSelectedProductIndex(),0);
        assertTrue("Items number matches pictures in tray", multiplePDP.itemsNumberMatchesPicturesSize());
    }

    @And("^User clicks last product in multiple pdp page$")
    public void userClicksLastProductInMultiplePdpPage() {
        //-1 will go to the last product in the list
        multiplePDP.setSelectProductIndex(-1);
    }

    @Then("^Verifies ([^\"]*) item arrow is disabled$")
    public void verifiesNextItemArrowIsDisabled(String arrow){
        assertTrue(arrow +" product is disabled", multiplePDP.isArrowDisabled(arrow));
    }

    @Then("^Verifies ([^\"]*) item arrow is enabled$")
    public void verifiesLastItemArrowIsEnabled(String arrow) {
        assertFalse(arrow + " product is enabled", multiplePDP.isArrowDisabled(arrow));
    }

    @And("^User clicks next product$")
    public void userClicksNextProduct() {
        multiplePDP.clickNext();
    }

    @Then("^Verifies selected product is product (\\d+)$")
    public void verifiesSelectedProductIsProduct(int selectedProduct) {
        assertEquals("Selected product is "+selectedProduct, selectedProduct, multiplePDP.getSelectedProductIndex());
    }

    @And("^User clicks previous product$")
    public void userClicksPreviousProduct(){
        multiplePDP.clickPrevious();
    }

    @Then("^Verifies product details have changed$")
    public void verifiesProductDetailsHaveChanged() {
        assertTrue(multiplePDP.hasProductChanged());
    }

    @Then("^Verify every product contains details$")
    public void verifyEveryProductContainsDetails() {
        assertTrue("Every product contains details", multiplePDP.checkEveryItemDetails());
    }
}
