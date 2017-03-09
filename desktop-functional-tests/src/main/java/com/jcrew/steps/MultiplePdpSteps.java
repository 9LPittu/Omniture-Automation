package com.jcrew.steps;

import com.jcrew.page.MultiplePdpPage;
import com.jcrew.page.CheckoutShoppingBag;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.*;

public class MultiplePdpSteps extends DriverFactory{

    private final MultiplePdpPage multiplePDP = new MultiplePdpPage(getDriver());
    private final CheckoutShoppingBag shoppingBag = new CheckoutShoppingBag(getDriver());

    @Then("^Verifies multiple pdp page contains pagination$")
    public void verifies_multiple_pdp_page_contains_pagination(){
        assertTrue("Multiple PDP contains pagination", multiplePDP.containsNavigation());
    }

    @Then("^Verifies initial multiple pdp page state$")
    public void verifiesInitialMultiplePdpPageState() {
        assertTrue("URL compliance with country context", multiplePDP.checkURLCountryContext());
        assertTrue("Previous product is disabled", multiplePDP.isArrowDisabled("previous"));
        assertEquals("First product is selected", multiplePDP.getSelectedProductIndex(),0);
        assertTrue("Items number matches pictures in tray", multiplePDP.itemsNumberMatchesPicturesSize());
    }
    
   
    @And("^User clicks last product in multiple pdp page$")
    public void userClicksLastProductInMultiplePdpPage() {
        //-1 will go to the last product in the list
        multiplePDP.setSelectProductIndex(-1);
    }

    @And("^User clicks first product in multiple pdp page$")
    public void userClicksfirstProductInMultiplePdpPage() {
        //-1 will go to the last product in the list
        multiplePDP.setSelectProductIndex(0);
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
        assertEquals("Selected product is "+selectedProduct, selectedProduct-1, multiplePDP.getSelectedProductIndex());
    }

    @And("^User clicks previous product$")
    public void userClicksPreviousProduct(){
        multiplePDP.clickPrevious();
    }

    @Then("^Verifies product details have changed$")
    public void verifiesProductDetailsHaveChanged() {
        assertTrue(multiplePDP.hasProductChanged());
    }

    @Then("^Verify every product contains name, image, price, color and size$")
    public void verifyEveryProductContainsDetails() {
        assertTrue("Every product contains details", multiplePDP.checkEveryItemDetails());
       
    }

    //This step will check that the drawer exists, is closed when visiting the product and when opening, remains open
    @Then("^Verify every product contains size and fit and review drawers$")
    public void verifyEveryProductContainsProductDetails() {
        assertTrue("Every product contains product, size and fit and review drawers", multiplePDP.checkEveryItemDrawers());
    }

    @And("^Add all products to cart$")
    public void addAllProductsToCart(){
        multiplePDP.addAllProductsTo("cart");
    }

    @And("^Add random product to cart$")
    public void addRandomProductToCart(){
        multiplePDP.addRandomProductsTo("cart");
    }

    @Then("^Verify all products are in cart$")
    public void verifyAllProductsAreInCart() {
        assertTrue("Number of products in bag matches products in tray", shoppingBag.isBagItemsCountMatches(multiplePDP.getNumProducts()));
    }

    @Then("^Verifies header text is ([^\"]*)$")
    public void verifiesHeaderTextIsShopTheLook(String text){
        assertTrue("Shop the look header is 'Shop the look'", multiplePDP.headerText(text));
    }

    @And("^User adds all products to wish list$")
    public void userAddsAllProductsToWishList(){
        multiplePDP.addAllProductsTo("wish list");
    }

    @And("^Copy URL and use externalProductCodes in lower case to access tray$")
    public void copyURLAndUseExternalProductCodesInLowerCaseToAccessTray() {
        multiplePDP.visitTrayWithLowerCaseExternalProduct();
    }

    @Then("^Verify that all products match with original URL$")
    public void verifyThatAllProductsMatchWithOriginalURL() {
        assertTrue("The tray has same products than original URL",multiplePDP.productsMatchesOriginalURL());
    }

    @And("^Selects color for every item and visits URL$")
    public void selectsColorForEveryItemAndVisitsURL(){
        multiplePDP.visitTrayWithSelectedColors();
    }

    @Then("^Verify tray has selected colors as default$")
    public void verifyTrayHasSelectedColorsAsDefault() {
        assertTrue("New URL contains selected colors by default", multiplePDP.selectedColorsByDefault());
    }
    
   
	 
	 @When("^Verify the items counts matches with Carousel items$")
	 public void verifyItemsCount(){
		 assertEquals("Itmes count matches with Carousel items count",
			  multiplePDP.shoppableTrayItemcount(), multiplePDP.itemsCountInCarousel());
	 }
	 
	 @Then("^Verify every product contains SIZE & FIT$")
	    public void verifyEveryProductSizeandFitDetails() {
	        assertTrue("Every product contains details", multiplePDP.checkEveryItemSizeandFitDetails());
	       
	    }
}


