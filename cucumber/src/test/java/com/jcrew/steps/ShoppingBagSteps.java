package com.jcrew.steps;

import com.jcrew.page.ShoppingBagPage;
import com.jcrew.util.DriverFactory;
import com.jcrew.util.StateHolder;
import com.jcrew.util.Reporting;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ShoppingBagSteps extends DriverFactory {


	private final ShoppingBagPage shoppingBagPage = new ShoppingBagPage(getDriver());
	private StateHolder stateHolder = StateHolder.getInstance();
	private Scenario scenario;
	private Reporting reporting = new Reporting();

	@Before
	public void getScenarioObject(Scenario s){
		this.scenario = s;
	}

	@When("^Clicks on checkout$")
	public void clicks_on_checkout() throws Throwable {
		shoppingBagPage.click_checkout_button();

		reporting.takeScreenshot(scenario);
	}

	@Then("^User should be in shopping bag page$")
	public void user_should_be_in_shopping_bag_page() throws Throwable {
		assertTrue("Article checkout should have been present",
				shoppingBagPage.isArticleCheckoutPresent());
	}

	@And("^Verifies edit button is present$")
	public void verifies_edit_button_is_present() throws Throwable {
		assertTrue(shoppingBagPage.isEditButtonPresent());

		reporting.takeScreenshot(scenario);
	}

	@And("^Verifies remove button is present$")
	public void verifies_remove_button_is_present() throws Throwable {
		assertTrue(shoppingBagPage.isRemoveButtonPresent());

		reporting.takeScreenshot(scenario);
	}

	@And("^Verifies that total amount and subtotal values are similar$")
	public void verifies_that_total_amount_and_subtotal_values_are_similar() throws Throwable {
		String totalAmount = shoppingBagPage.getTotalAmountPage();
		String subtotalValue = shoppingBagPage.getSubtotalValue();
		assertEquals("Total Amount and subtotal should be the same", totalAmount, subtotalValue);
	}

	@Given("^Verifies that total amount and subtotal values are numbers$")
	public void Verifies_that_total_amount_and_subtotal_values_are_numbers() throws Throwable {
		String totalAmount = shoppingBagPage.getTotalAmountPage();
		String subtotalValue = shoppingBagPage.getSubtotalValue();
		Pattern totalAmountPattern = Pattern.compile("^\\$\\d(\\d)?\\.\\d\\d$");
		Pattern subtotalAmountPattern = Pattern.compile("^\\$\\d{1,3}(,\\d{3}|\\d)?\\.\\d\\d$");

		Matcher totalAmountMatch = totalAmountPattern.matcher(totalAmount);
		Matcher subtotalAmountMatch = subtotalAmountPattern.matcher(subtotalValue);

		assertTrue("Total amount should be a price value", totalAmountMatch.matches());
		assertTrue("Subtotal amount should be a price value", subtotalAmountMatch.matches());

	}

	@And("^Clicks edit button on item bag page$")
	public void clicks_edit_button_on_item_bag_page() throws Throwable {
		shoppingBagPage.click_edit_button();
	}

	@Then("^Verify color ([^\"]*) is displayed in shopping bag$")
	public void verify_color_is_displayed_in_shopping_bag(String productColor) throws Throwable {
		assertTrue(productColor + " color should have been displayed",
				shoppingBagPage.isProductColorDisplayed(productColor));
	}

	@And("^Verify size ([^\"]*) is displayed in shopping bag$")
	public void verify_size_is_displayed_in_shopping_bag(String productSize) throws Throwable {
		assertTrue(productSize + " color should have been displayed",
				shoppingBagPage.isProductSizeDisplayed(productSize));
	}

	@And("^Verify ([^\"]*) items are specified as quantity in shopping bag$")
	public void verify_items_are_specified_as_quantity(String productQuantity) throws Throwable {
		assertEquals("Product Quantity should be the same", productQuantity,
				shoppingBagPage.getItemQuantity());
	}

	@And("^Verify product name is the same as the one displayed in pdp page$")
	public void verify_product_name_is_the_same_as_the_one_displayed_in_pdp_page() throws Throwable {
		String productName = stateHolder.get("productName").toUpperCase();
		assertTrue(productName + " product is not the one displayed",
				shoppingBagPage.getProductName().endsWith(productName));
	}

	@And("^Verify color is the one selected from pdp page$")
	public void verify_color_is_the_one_selected_from_pdp_page() throws Throwable {
		String expectedColor = stateHolder.get("color");
		assertTrue(expectedColor + " color is not displayed", shoppingBagPage.isProductColorDisplayed(expectedColor));
	}

	@And("^Verify size is the one selected from pdp page$")
	public void verify_size_is_the_one_selected_from_pdp_page() throws Throwable {
		String expectedSize = stateHolder.get("size");
		assertTrue(expectedSize + " size is not displayed", shoppingBagPage.isProductSizeDisplayed(expectedSize));
	}

	@And("^Verify price is the same as the one displayed in pdp page$")
	public void verify_price_is_the_same_as_the_one_displayed_in_pdp_page() throws Throwable {
		String price = stateHolder.get("priceList") == null ? stateHolder.get("priceWas") : stateHolder.get("priceList");
		assertEquals("Price should be the same", price, shoppingBagPage.getSubtotalValue());
	}

	@And("^Verify variation is the one selected from pdp page$")
	public void verify_variation_is_the_one_selected_from_pdp_page() throws Throwable {
		String variation = stateHolder.get("variation");
		if (variation != null) {
			String prefix = variation.toUpperCase();
			if (!"REGULAR".equalsIgnoreCase(prefix)) {
				assertTrue(prefix + " variation should have been displayed as part of the product name:",
						shoppingBagPage.getProductName().startsWith(prefix));
			}
		}
	}


	@Then("change URL to mobile URL")
	public void change_URL_To_Mobile_URL(){

		//Get the current URL and append the ?isMobile=true
		String currentURL = getDriver().getCurrentUrl().trim();
		String browserURL = currentURL.replaceAll("sidecar=true", "");
		browserURL = browserURL + "isMobile=true";

		//navigate to the mobile URL
		getDriver().navigate().to(browserURL);
		getDriver().manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		reporting.takeScreenshot(scenario);
	}

	@Then("verify mobile page is displayed")
	public void verify_mobile_page_displayed(){
		shoppingBagPage.verifyMobilePageDisplayed();

		reporting.takeScreenshot(scenario);
	}
}
