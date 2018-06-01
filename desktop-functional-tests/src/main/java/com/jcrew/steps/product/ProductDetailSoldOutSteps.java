package com.jcrew.steps.product;

import com.jcrew.page.product.ProductDetailSoldOut;
import com.jcrew.pojo.Country;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.TestDataReader;
import cucumber.api.java.en.Then;

import static org.junit.Assert.assertEquals;

/**
 * Created by ngarcia on 3/22/17.
 */
@SuppressWarnings("static-access")
public class ProductDetailSoldOutSteps extends DriverFactory {
    private ProductDetailSoldOut soldOut = new ProductDetailSoldOut(getDriver());

    @Then("^Verify sold out message is displayed on PDP$")
    public void user_should_see_pdp_page_soldout_message_which_includes_phone_number(){
        Country c = soldOut.stateHolder.get("context");
        String countryCode = c.getCountry();

        TestDataReader testDataReader = TestDataReader.getTestDataReader();
        String expected;

        String actual = soldOut.getSoldOutMessage();

        if ("jp".equalsIgnoreCase(countryCode)) {
            expected = testDataReader.getData("pdp.soldout.item.message") + " " +
                    testDataReader.getData("email");

        } else {
            expected = testDataReader.getData("pdp.soldout.item.message") + " " +
                    testDataReader.getData("phone");
        }

        assertEquals("user should see PDP page with soldout message which includes phone number",
                expected.toLowerCase().trim(), actual.toLowerCase());
    }

}
