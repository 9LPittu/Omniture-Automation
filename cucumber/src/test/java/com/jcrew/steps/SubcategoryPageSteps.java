package com.jcrew.steps;


import com.jcrew.page.SubcategoryPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SubcategoryPageSteps extends DriverFactory {

    private SubcategoryPage subcategoryPage = new SubcategoryPage(getDriver());

    @When("^Adds a product to shopping bag$")
    public void adds_a_product_to_shopping_bag() throws Throwable {

        subcategoryPage.adds_a_product_to_shopping_bag();

        assertFalse("Quick shop modal should no longer be present",
                subcategoryPage.quickShopModalIsPresent());

    }

    @When("^Clicks on shopping bag link$")
    public void clicks_on_shopping_bag_link() throws Throwable {

        subcategoryPage.clicks_on_shopping_bag_link();

    }

    @Then("^User is in shirts and tops for women page$")
    public void user_is_in_shirts_and_tops_for_women_page() throws Throwable {

        assertTrue("A Subcategory page should have a product grid", subcategoryPage.isProductGridPresent());

        assertTrue("User should be in shirts and top page for women",
                getDriver().getCurrentUrl().endsWith("/c/womens_category/shirtsandtops"));

    }

    @And("^User hovers a product$")
    public void user_hovers_a_product() throws Throwable {
        subcategoryPage.hover_first_product_in_grid();
    }

    @Then("^Proper details are shown for the hovered product$")
    public void proper_details_are_shown_for_the_hovered_product() throws Throwable {
        assertTrue("All products should contain a name and a price", subcategoryPage.isFirstProductNameAndPriceValid());
        assertTrue("All product variations should be valid", subcategoryPage.areFirstProductColorVariationsValid());
    }

    @Then("^Product array should be displayed with correct values$")
    public void product_array_should_be_displayed_with_correct_values() throws Throwable {
        assertTrue("All products should contain correct values", subcategoryPage.isProductArrayValid());
    }

    @And("^Selects the first product from product grid list$")
    public void Selects_the_first_product_from_product_grid_list() throws Throwable {
        subcategoryPage.click_first_product_in_grid();
    }

    @Then("^User should be in sweaters page for women$")
    public void user_should_be_in_sweaters_page_for_women() throws Throwable {
        assertTrue("A Subcategory page should have a product grid", subcategoryPage.isProductGridPresent());

        assertTrue("User should be in shirts and top page for women",
                getDriver().getCurrentUrl().endsWith("/c/womens_category/sweaters"));

    }

    @And("^Category title for Sweaters should match below global promo$")
    public void category_title_for_sweaters_should_match_below_global_promo() throws Throwable {
        assertEquals("Sweaters should have been displayed blow global promo",
                "SWEATERS", subcategoryPage.getCategoryTitleBelowGlobalPromo());
    }

    @Then("^View All Section is present and collapsed$")
    public void view_all_section_is_present_and_collapsed() throws Throwable {
        assertEquals("View All should be displayed", "VIEW ALL", subcategoryPage.getAccordianHeaderLabelText());
        assertTrue("See more icon should be displayed", subcategoryPage.isMoreIconDisplayed());
    }

    @Given("^User clicks on expand icon$")
    public void user_clicks_on_expand_icon() throws Throwable {
        subcategoryPage.click_expand_accordion_icon();
    }

    @Then("^Accordion should be expanded$")
    public void accordion_should_be_expanded() throws Throwable {
        assertTrue("Accordion menu should be expanded", subcategoryPage.isAccordionMenuVisible());
    }

    @And("^Collapse icon is displayed$")
    public void collapse_icon_is_displayed() throws Throwable {
        assertTrue("See less icon should be displayed", subcategoryPage.isLessIconDisplayed());
    }
}
