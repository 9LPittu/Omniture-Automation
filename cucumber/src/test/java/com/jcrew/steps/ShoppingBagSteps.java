package com.jcrew.steps;

import com.jcrew.page.ShoppingBagPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ShoppingBagSteps extends DriverFactory {


    private final ShoppingBagPage shoppingBagPage = new ShoppingBagPage(getDriver());

    @When("^Clicks on checkout$")
    public void clicks_on_checkout() throws Throwable {
        shoppingBagPage.click_checkout_button();
    }

    @Then("^User should be in shopping bag page$")
    public void user_should_be_in_shopping_bag_page() throws Throwable {
        assertTrue("Article checkout should have been present",
                shoppingBagPage.isArticleCheckoutPresent());
    }

    @And("^Verifies edit button is present$")
    public void verifies_edit_button_is_present() throws Throwable {
        assertTrue(shoppingBagPage.isEditButtonPresent());
    }

    @And("^Verifies remove button is present$")
    public void verifies_remove_button_is_present() throws Throwable {
        assertTrue(shoppingBagPage.isRemoveButtonPresent());
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
}
