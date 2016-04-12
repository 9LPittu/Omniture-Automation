package com.jcrew.steps;

import com.jcrew.page.Header;
import com.jcrew.page.SubcategoryPage;
import com.jcrew.util.DriverFactory;
import com.jcrew.util.StateHolder;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SubcategoryPageSteps extends DriverFactory {

    private final Logger logger = LoggerFactory.getLogger(SubcategoryPageSteps.class);

    private final SubcategoryPage subcategoryPage = new SubcategoryPage(getDriver());
    private final Header header = new Header(getDriver());
    private final StateHolder stateHolder = StateHolder.getInstance();

    @When("^Adds a product to shopping bag$")
    public void adds_a_product_to_shopping_bag() {

        subcategoryPage.adds_a_product_to_shopping_bag();

        assertFalse("Quick shop modal should no longer be present",
                subcategoryPage.quickShopModalIsPresent());

    }

    @When("^Clicks on shopping bag link$")
    public void clicks_on_shopping_bag_link() {

        subcategoryPage.clicks_on_shopping_bag_link();

    }

    @And("^User hovers a product$")
    public void user_hovers_a_product() {
        subcategoryPage.hover_first_product_in_grid();
    }

    @Then("^Product array should be displayed with correct values$")
    public void product_array_should_be_displayed_with_correct_values() {
        assertTrue("All products should contain correct values", subcategoryPage.isProductArrayValid());
    }

    @And("^Selects the first product from product grid list$")
    public void Selects_the_first_product_from_product_grid_list() {
        subcategoryPage.click_first_product_in_grid();
    }

    @And("^Selects any product from product grid list$")
    public void Selects_any_product_from_product_grid_list() {
        subcategoryPage.click_any_product_in_grid();
    }

    @Then("^User should be in ([^\"]*) page for women$")
    public void user_should_be_in_subcategory_page_for_women(String subcategory) {
        assertTrue("A Subcategory page should have a product grid",
                subcategoryPage.isProductGridPresent());

        assertTrue("User should be in " + subcategoryPage + " page for women",
                getDriver().getCurrentUrl().endsWith("/c/womens_category/" + subcategory));

    }

    @Then("^User should be in subcategory page$")
    public void user_should_be_in_subcategory_page() {
        String subcategory = stateHolder.get("subcategory").toString();
        String expectedSubcategory = subcategoryPage.getCategoryTitleBelowGlobalPromo();

        assertTrue("A Subcategory page should have a product grid",
                subcategoryPage.isProductGridPresent());

        assertTrue("Page title should be: " + subcategory + " and was: " + expectedSubcategory,
                subcategory.equalsIgnoreCase(expectedSubcategory));
    }

    @And("^Category title for ([^\"]*) should match below global promo$")
    public void category_title_for_subcategory_should_match_below_global_promo(String subcategory) {
        assertEquals(subcategory + " should have been displayed blow global promo",
                subcategory, subcategoryPage.getCategoryTitleBelowGlobalPromo());
    }

    @Then("^View All Section is present and collapsed$")
    public void view_all_section_is_present_and_collapsed() {
        //assertEquals("View All should be displayed", "VIEW ALL", subcategoryPage.getAccordianHeaderLabelText());
        assertTrue("See more icon should be displayed", subcategoryPage.isMoreIconDisplayed());
    }

    @Given("^User clicks on expand icon$")
    public void user_clicks_on_expand_icon() {
        subcategoryPage.click_expand_accordion_icon();
    }

    @Then("^Accordion should be expanded$")
    public void accordion_should_be_expanded() {
        assertTrue("Accordion menu should be expanded", subcategoryPage.isAccordionMenuVisible());
    }

    @And("^Collapse icon is displayed$")
    public void collapse_icon_is_displayed() {
        assertTrue("See less icon should be displayed", subcategoryPage.isLessIconDisplayed());
    }

    @Given("^Chooses a random filter$")
    public void selects_subcategory() {
        subcategoryPage.click_random_filter();
    }

    @Given("^Selects ([^\"]*) subcategory$")
    public void selects_subcategory(String subcategory) {
        subcategoryPage.click_subcategory(subcategory);
    }

    @Then("^([^\"]*) option becomes selected$")
    public void subcategory_option_becomes_bold(String option) {
        assertEquals(option + " should be bold", option, subcategoryPage.getAccordianHeaderLabelText());
    }

    @Then("filter becomes selected$")
    public void filter_option_becomes_bold() {
        String option = stateHolder.get("filter").toString();
        assertEquals(option + " should be bold", option, subcategoryPage.getAccordianHeaderLabelText());
    }

    @Then("^Refine modal autocloses$")
    public void refine_modal_autocloses() {
        assertTrue("Accordion menu should not be expanded", subcategoryPage.isAccordionMenuInvisible());
    }

    @Then("^Array page displays ([^\"]*)$")
    public void array_page_displays_subcategory(String subcategory) {
        assertEquals(subcategory + " array should be displayed", subcategory, subcategoryPage.getArrayLabel());
    }

    @Then("^Products displayed are ([^\"]*) from ([^\"]*) category$")
    public void products_displayed_are_from_subcategory(String subcategory, String category) {
        final List<String> productsDisplayedHrefs = subcategoryPage.getProductsDisplayedHrefs();

        for (String product : productsDisplayedHrefs) {
            assertTrue("product is not from " + subcategory + " href value is " + product,
                    product.contains("/p/womens_category/" + category + "/" + subcategory));
        }
    }

    @Then("^Products displayed match subcategory$")
    public void products_displayed_are_from_subcategory() {
        final String filter = stateHolder.get("filter").toString();

        assertEquals("Selected filter is correct", filter, subcategoryPage.getAccordianHeaderLabelText());
    }

    @Then("^Category header should not be present$")
    public void category_header_should_not_be_present() {
        assertTrue("Category header should not have been present but it is",
                !subcategoryPage.isCategoryHeaderPresent());
    }
    @Then("^Category header should be present$")
    public void category_header_should_be_present() {
        assertTrue("Category header should not have been present but it is",
                subcategoryPage.isCategoryHeaderPresent());
    }

    @And("^An image should be displayed for ([^\"]*)$")
    public void an_image_should_be_displayed_for_category(String category) {
        assertEquals("Image for category should have been displayed ", category,
                subcategoryPage.getCategoryImageHeaderAlt());
    }

    @And("^An image for subcategory should be displayed$")
    public void an_image_should_be_displayed_for_subcategory() {
        assertEquals("Image for category should have been displayed ", stateHolder.get("subcategory").toString(),
                subcategoryPage.getCategoryImageHeaderAlt());
    }

    @Then("^Verifies ([^\"]*) product is displayed$")
    public void verifies_product_is_displayed(String product) {
        assertTrue("Product should exist", subcategoryPage.productTileExistFor(product));
    }

    @Then("^Verifies product information is displayed$")
    public void verifies_product_information_is_displayed() {
        assertTrue("Product name and price are valid", subcategoryPage.isFirstProductNameAndPriceValid());
    }

    @And("^Verifies product image is displayed$")
    public void image_is_displayed_for_first_product() {
        assertTrue("Image should be displayed for product", subcategoryPage.isImageDisplayedForProduct());
    }

    @And("^Verifies ([^\"]*) product list price is ([^\"]*)$")
    public void verifies_product_list_price(String product, String price) {
        assertEquals("Price for product should be correct", price, subcategoryPage.getPriceFor(product));
    }

    @And("^An image is displayed for ([^\"]*) product$")
    public void image_is_displayed_for_product(String product) {
        assertTrue("Image should be displayed for product", subcategoryPage.isImageDisplayedFor(product));
    }

    @And("^Verifies ([^\"]*) product price (was [^\"]*)$")
    public void verifies_product_price_was(String product, String price) {
        assertEquals("Was price is not the expected",
                price, subcategoryPage.getWasPriceFor(product));
    }

    @And("^Verifies ([^\"]*) product sale price is ([^\"]*)$")
    public void verifies_product_sale_price(String product, String price) {
        assertEquals("Sale price is not what was expected",
                price, subcategoryPage.getSalePriceFor(product));
    }

    @And("^Verifies ([^\"]*) is (available in \\d+ colors)$")
    public void verifies_product_color_availability(String product, String colorAvailability) {
        assertEquals("Expected colors did not match", colorAvailability,
                subcategoryPage.getAvailableColorsMessageFor(product));
    }

    @And("^Verifies position of elements is the expected$")
    public void verifies_position_of_elements_is_the_expected() {
        final Point menuPosition = subcategoryPage.getMenuPosition();
        final Point logoPosition = header.getLogoPosition();
        final Point refinementPosition = subcategoryPage.getRefinementPosition();
        final Point categoryImagePosition = subcategoryPage.getCategoryImagePosition();
        final Point promoPosition;

        boolean isDesktop = subcategoryPage.isDesktopPromoDisplayed();

        if (isDesktop) {
            promoPosition = subcategoryPage.getDesktopPromoPosition();
        } else {
            promoPosition = subcategoryPage.getMobilePromoPosition();
        }

        boolean result;

        if (isDesktop) {
            result = (promoPosition.getY() < menuPosition.getY())
                    && (menuPosition.getY() < logoPosition.getY())
                    && (logoPosition.getY() < refinementPosition.getY())
                    && (refinementPosition.getY() < categoryImagePosition.getY());
        } else {
            result = (menuPosition.getY() < logoPosition.getY())
                    && (logoPosition.getY() < promoPosition.getY())
                    && (promoPosition.getY() < refinementPosition.getY())
                    && (refinementPosition.getY() < categoryImagePosition.getY());
        }

        assertTrue("An element is out of order, expected is menu " + menuPosition.getY() +
                ", logo " + logoPosition.getY() + ", promo " + promoPosition.getY() +
                ". refinement " + refinementPosition.getY() + " and category image " +
                categoryImagePosition.getY(), result);

    }

    @Then("^Verifies accordion menu contains same items as in sign post items, first item should not be present in post sign$")
    public void verifies_accordion_menu_contains_same_items_as_in_sign_post_items() {
        subcategoryPage.click_expand_accordion_icon();
        final List<String> postSignItems = subcategoryPage.getPostSignItems();
        final List<String> accordianItems = subcategoryPage.getAccordionItems();

        // first item from accordion menu is view all and is not present as post sign by design
        accordianItems.remove(0);
        // second item from accordion menu is not present as a post sign by design
        accordianItems.remove(0);

        assertEquals("Elements list should be the same", postSignItems, accordianItems);
    }

    @Then("^Verifies end cap navigation menu to say ([^\"]*)$")
    public void verifies_end_cap_navigation_menu_to_say(String header) {
        assertEquals("Header is different from expected", header,
                subcategoryPage.getEndCapNavigationMenuHeader());
    }

    @Then("^Verifies navigation draw options are ([^\"]*)$")
    public void verifies_navigation_draw_options_are(String options) {
        final String[] optionsAsArray = options.split(",");
        final List<String> optionList = new ArrayList<>();
        for (String option : optionsAsArray) {
            optionList.add(option.trim());
        }
        assertEquals("Menu Options are different than expected", optionList,
                subcategoryPage.getEndCapNavigationMenuOptions());
    }

    @Then("^Taps on ([^\"]*) drawer and opens and all other drawer options are closed$")
    public void taps_on_drawer_and_opens_other_drawer_options_are_closed(String option) {
        if (subcategoryPage.isEndCapMoreIconDisplayed()) {
            subcategoryPage.click_accordion_option(option);
            for (String menuOption : subcategoryPage.getEndCapNavigationMenuOptions()) {
                if (!menuOption.equalsIgnoreCase(option)) {
                    assertTrue(menuOption + " should have drawer closed",
                            subcategoryPage.isDrawerClosedForOption(menuOption));
                }
            }
        } else {
            logger.info("Skipping as this test is meant for mobile only");
        }
    }

    @And("^Verifies ([^\"]*) drawer is open$")
    public void verifies_drawer_is_open(String menuOption) {
        if (subcategoryPage.isEndCapMoreIconDisplayed()) {
            assertTrue("Drawer should be open for option " + menuOption,
                    subcategoryPage.isDrawerOpenForOption(menuOption));
        } else {
            logger.info("Skipping as this test is meant for mobile only");
        }
    }

    @Then("^Taps on collapse button for ([^\"]*)")
    public void taps_on_collapse_button_for_drawer(String menuOption) {
        if (subcategoryPage.isEndCapMoreIconDisplayed()) {
            subcategoryPage.click_accordion_option(menuOption);
        } else {
            logger.info("Skipping as this test is meant for mobile only");
        }
    }

    @And("^All drawers are closed$")
    public void all_drawers_are_closed() {
        if (subcategoryPage.isEndCapMoreIconDisplayed()) {
            for (String menuOption : subcategoryPage.getEndCapNavigationMenuOptions()) {
                assertTrue(menuOption + " should have drawer closed",
                        subcategoryPage.isDrawerClosedForOption(menuOption));
            }

        } else {
            logger.info("Skipping as this test is meant for mobile only");
        }
    }

    @Then("^Click on product ([^\"]*) to display PDP$")
    public void click_on_product_to_display_PDP(String product) {
        subcategoryPage.click_on_product(product);
    }

    @And("^Selects the first product with regular price from product grid list$")
    public void selectsTheFirstProductWithRegularPriceFromProductGridList() throws Throwable {
        subcategoryPage.click_first_product_with_xpath("//span[@class='tile__detail tile__detail--price--list']" +
                "/ancestor::div[@class='c-product-tile']");
    }

    @And("^Selects the first product with available colors from product grid list$")
    public void selectsTheFirstProductWithAvailableColorsFromProductGridList() throws Throwable {
        subcategoryPage.click_first_product_with_xpath("//span[@class='tile__detail " +
                "js-tile__detail--colors-count tile__detail--colors-count']/ancestor::div[@class='c-product-tile']");
    }

    @And("^Selects the first product with available colors and regular price from product grid list$")
    public void selectsTheFirstProductWithAvailableColorsAndRegularPriceFromProductGridList() throws Throwable {
        subcategoryPage.click_first_product_with_xpath("//span[@class='tile__detail js-tile__detail--colors-count " +
                "tile__detail--colors-count']/preceding-sibling::span[@class='tile__detail tile__detail--price--list']" +
                "/ancestor::div[@class='c-product-tile']");
    }

    @And("^user selects any item from array page, select any color and size$")
    public void user_selects_any_item_from_array_page_select_any_size_color(){
    	subcategoryPage.selectRandomItemAndSelectSizeColor();
    }

    @Then("^user should see \"([^\"]*)\" in search results$")
    public void verify_item_is_displayed_in_search_results_page(String propertyName){    	;
    	assertTrue("Product should exist", subcategoryPage.isItemDisplayedInSearchResultsPage(propertyName));
    }
    
    @And("^([^\"]*) price of \"([^\"]*)\" should match with expected \"([^\"]*)\"$")
    public void verify_item_price_in_search_results_page(String priceType, String saleItemPropertyName, String expectedPricePropertyName){
    	assertTrue(priceType + " price should match", subcategoryPage.isPriceMatchesForSaleItem(saleItemPropertyName, priceType, saleItemPropertyName));
    }
    
    @And("^Verify proper currency symbol is displayed on product grid list$")
    public void verify_currency_on_product_gridlist(){
    	assertTrue("Currency on product gridlist",subcategoryPage.isCorrectCurrencySymbolonProductGridList());
    }
    
    @And("^Verify proper currency symbol is displayed on PDP page$")
    public void verify_currency_on_product_PDP(){
    	assertTrue("Currency on product details page",subcategoryPage.isCorrectCurrencySymbolonPDP());
    }
    
    @And("^Verify proper currency symbol is displayed on item section on Checkout page$")
    public void verify_currency_on_checkout_itemsection(){
    	assertTrue("Currency on product details page",subcategoryPage.isCorrectCurrencySymbolShoppingBagItemSection());
    }
    
    @And("^Verify proper currency symbol is displayed on summary section on Checkout page$")
    public void verify_currency_on_checkout_summarysection(){
    	assertTrue("Currency on product details page",subcategoryPage.isCorrectCurrencySymbolShoppingBagSummarySection());
    }
    
    @And("^Verify proper currency symbol is displayed on shipping method section on Checkout page$")
    public void verify_currency_on_checkout_shippingmethodssection(){
    	assertTrue("Currency on product details page",subcategoryPage.isCorrectCurrencySymbolonShoppingBagMethodPrices());
    }
    
    @And("^Verify proper currency symbol is displayed on shipping section on Checkout page$")
    public void verify_currency_on_checkout_shippingsection(){
    	assertTrue("Currency on product details page",subcategoryPage.isCorrectCurrencySymbolonShoppingBagShippingPrices());
    }
}
