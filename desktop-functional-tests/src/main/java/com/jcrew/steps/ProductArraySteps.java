package com.jcrew.steps;

import com.jcrew.page.Footer;
import com.jcrew.page.ProductsArray;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.Util;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by nadiapaolagarcia on 4/1/16.
 */
public class ProductArraySteps extends DriverFactory {
    ProductsArray productsArray = new ProductsArray(getDriver());
    StateHolder holder = StateHolder.getInstance();

    @When("User selects random product from product array")
    public void user_selects_random_product(){
        productsArray.selectRandomProduct();
    }

    @Then("Verify context in the array page")
    public void verify_that_all_products_in_first_page_contains_expected_cucrrency() {
        String countryName = productsArray.country.getName();

        assertTrue("Category Array url contains expected country code", productsArray.verifyURL());

        Footer footer = new Footer(getDriver());
        assertEquals("Homepage footer matches expected country",
                countryName.toLowerCase(),
                footer.getCountry().toLowerCase());
    }

    @Then("^Verify proper currency symbol is displayed on product grid list$")
    public void verify_currency_on_product_gridlist(){
        assertTrue("Currency on product gridlist", productsArray.isCorrectCurrencySymbolonProductGridList());
    }

    @Then("Verify refine dropdown text is ([^\"]*)")
    public void verify_dropdown_text(String text) {
        text = text.toLowerCase();
        String labelText = productsArray.getRefineText();

        assertEquals("Dropdown text", text, labelText);
    }

    @Then("Verify refine options matches available lists")
    public void verify_dropdown_text() {
        List<String> dropdown = productsArray.getRefineOptions();
        List<String> list = productsArray.getAvailableLists();

        //view all and first item in dropdown are not part of the list
        assertEquals("Same number of options", dropdown.size(), list.size() + 2);

        for (int i = 0, j = 2; i < list.size(); i++, j++) {
            String dropdownItem = dropdown.get(j);
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
}
