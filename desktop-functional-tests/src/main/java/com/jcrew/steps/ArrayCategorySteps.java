package com.jcrew.steps;

import com.jcrew.page.Footer;
import com.jcrew.page.ArrayCategory;
import com.jcrew.pojo.Country;
import com.jcrew.utils.CurrencyChecker;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.TestDataReader;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by nadiapaolagarcia on 4/1/16.
 */
public class ArrayCategorySteps extends DriverFactory {
    private ArrayCategory productsArray = new ArrayCategory(getDriver());
    private StateHolder holder = StateHolder.getInstance();

    @When("User selects first product from product array")
    public void user_selects_first_product(){
        productsArray.selectFirstProduct();
    }
    
    @When("User selects random product from product array")
    public void user_selects_random_product(){
        productsArray.selectRandomProduct("any");
    }
    
    @When("User selects random variation product from product array")
    public void user_selects_random_variation_product(){
        productsArray.selectRandomProduct("variation");
    }

    @When("User selects random quick shop from product array")
    public void user_selects_random_qs_product(){
        productsArray.selectRandomQS("any");
    }
    
    @When("User selects random quick shop with variation product from product array")
    public void user_selects_random_variation_qs_product(){
        productsArray.selectRandomQS("variation");
    }

    @Then("Verify ([^\"]*) cookie path value is ([^\"]*)")
    public void verify_cookie_path_value(String cookieName,String expectedCookiePath){
        String cookiePath=productsArray.getCookiePath(cookieName);
        assertEquals("Cookie path value is ",expectedCookiePath,cookiePath);
    }

    @Then("Verify context in the array page")
    public void verify_context_in_url_and_footer_in_array_page() {
        Country c = holder.get("context");
        String countryName = c.getName();

        assertTrue("Category Array URL: <" + getDriver().getCurrentUrl() + "> does not contains expected" +
                "country code for " +  countryName, productsArray.verifyURL());

        Footer footer = new Footer(getDriver());
        assertEquals("Homepage footer matches expected country",
                countryName.toLowerCase(),
                footer.getCountry().toLowerCase());
    }

    @Then("^Verify proper currency symbol is displayed on product grid list$")
    public void verify_currency_on_product_gridlist(){
        List<String> listPrice = productsArray.getPrices();
        Country c = holder.get("context");
        String countryName = c.getName();

        for(String price : listPrice) {
            assertTrue("List price " + price + " matches country context "+countryName,
                    CurrencyChecker.isValid(price, c));
        }

        List<String> salePrice = productsArray.getSalePrices();
        for(String price : salePrice) {
            assertTrue("Sale price " + price + " matches country context "+countryName,
                    CurrencyChecker.isValid(price, c));
        }

        List<String> wasPrice = productsArray.getWasPrices();
        for(String price : wasPrice) {
            assertTrue("Was price " + price + " matches country context "+countryName,
                    CurrencyChecker.isValid(price, c));
        }
    }

    @Then("Verify refine dropdown displayed in array page")
    public void verify_refine_dropdown(){
        assertTrue("Refine dropdown should be displayed in category array page", productsArray.isRefineDropdownDisplayed());
    }

    @Then("Verify refine dropdown text is ([^\"]*)")
    public void verify_dropdown_text(String text) {
        text = text.toLowerCase();
        
        //Replace text by 'view all' if actual refinement filter is toggled off
        TestDataReader testDataReader = TestDataReader.getTestDataReader();
        boolean toggle = testDataReader.getBoolean("refinement.filter.text.toggle");
        if (!toggle)
        	text = "view all";
        
        String labelText = productsArray.getRefineText();
        assertEquals("Dropdown text", text, labelText);
    }

    @When("User expands refine dropdown")
    public void expand_refine_dropdown() {
        productsArray.openRefineAccordion();
    }

    @Then("Verify refine dropdown is ([^\"]*)")
    public void verify_refine_dropdown_state(String expectedState){
        String actualState = productsArray.retrieveDropdownState();
        assertEquals("Refine dropdown state", expectedState.toLowerCase(), actualState.toLowerCase());
    }

    @Then("Verify refine options matches available lists")
    public void verify_dropdown_text() {
        List<String> dropdown = productsArray.getRefineOptions();
        List<String> list = productsArray.getAvailableLists();

        //view all and first item in dropdown are not part of the list
        assertEquals("Same number of options", dropdown.size(), list.size() + 1);

        for (int i = 0; i < list.size(); i++) {
            String dropdownItem = dropdown.get(i+1);
            String listItem = list.get(i);

            assertEquals("Dropdown text", dropdownItem, listItem);
        }
    }

    @Then("Verify category contains items count")
    public void verify_items_count() {
        String count = productsArray.getItemsText();

        assertNotNull("Category contains item count", count);
    }

    @When("User selects a random refinement option")
    public void select_random_refinement_option() {
        productsArray.selectRefinement();
    }

    @Then("Verify refinement option was selected")
    public void verify_option_was_selected() {
        int itemsBefore = Integer.parseInt((String) holder.get("itemsBefore"));
        String refinement = (String) holder.get("selectedRefinement");

        assertEquals("Refine text is the previously selected", refinement, productsArray.getRefineText());
        assertTrue("Number of items were reduced", itemsBefore > Integer.parseInt(productsArray.getItemsText()));
        assertTrue("There is only one list in the grid", productsArray.getNumberOfLists() == 1);
    }

    @Then("Verify user is in ([^\"]*) category array page")
     public void is_category_array_page(String subCategoryName) {
        if("selected".equalsIgnoreCase(subCategoryName)){
            subCategoryName = (String) holder.get("subcategory");
        }
        assertTrue("Category array page for " + subCategoryName + " should be displayed", productsArray.isCategoryArrayPage(subCategoryName));
    }
    
    @Then("Verify user is in category array page")
    public void is_category_array_page() {
        assertTrue("Verify Category array page displayed", productsArray.isCategoryArray());
    }
    
    @When("^User selects random tray from available categories$")
	 public void select_random_item_for_shoppableTray() {
		
    	productsArray.selectTheRandomProductForShoppableTray();
	    	
	 }	 
}