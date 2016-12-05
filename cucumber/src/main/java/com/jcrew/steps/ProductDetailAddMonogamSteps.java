package com.jcrew.steps;

import com.jcrew.page.ProductDetailAddMonogram;
import com.jcrew.util.DriverFactory;
import com.jcrew.util.TestDataReader;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by nadiapaolagarcia on 9/12/16.
 */
public class ProductDetailAddMonogamSteps extends DriverFactory {
    private ProductDetailAddMonogram monogram = new ProductDetailAddMonogram(getDriver());

    @When("User adds monogram to product")
    public void add_monogram() {
        monogram.addMonogram();
    }

    @Then("Verify product is able to add monogram")
    public void product_is_monograbable() {
        assertTrue("Product is able to add monogram", monogram.isMonogram());
    }

    @Then("Verify product is not able to add monogram")
    public void product_is_not_monograbable() {
        assertFalse("Product is not able to add monogram", monogram.isMonogram());
    }

    @When("User clicks on info for monogram")
    public void more_info() {
        monogram.infoMonogram();
    }

    @Then("Verify monogram info is displayed")
    public void monogram_info_is_displayed() {
        TestDataReader testData = TestDataReader.getTestDataReader();

        String expected = testData.getData("monogram.text.a");
        String actual = monogram.getInfoText(monogram.TEXT_A);
        assertEquals("Text A matches expected", expected, actual);

        expected = testData.getData("monogram.text.b");
        actual = monogram.getInfoText(monogram.TEXT_B);
        assertEquals("Text B matches expected", expected, actual);

        expected = testData.getData("monogram.text.c");
        actual = monogram.getInfoText(monogram.TEXT_C);
        assertEquals("Text C matches expected", expected, actual);
    }

    @Then("Verify monogram info is not displayed")
    public void monogram_info_is_not_displayed() {
        String actual = monogram.getInfoText(monogram.TEXT_A);
        assertEquals("Text A matches expected", "", actual);

        actual = monogram.getInfoText(monogram.TEXT_B);
        assertEquals("Text B matches expected", "", actual);

        actual = monogram.getInfoText(monogram.TEXT_C);
        assertEquals("Text C matches expected", "", actual);
    }
}
