package com.jcrew.steps;

import com.jcrew.page.HomePageAllBrands;
import com.jcrew.util.DriverFactory;
import com.jcrew.util.TestDataReader;
import cucumber.api.java.en.And;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class HomePageAllBrandSteps extends DriverFactory {

    private final HomePageAllBrands homePageAllBrands = new HomePageAllBrands(getDriver());
    private TestDataReader testDataReader = TestDataReader.getTestDataReader();

    @And("^Verify page source contains ([^\"]*)$")
    public void validate_page_source_contains_given_var(String var) {
        assertTrue("page source should contain "+var+"", homePageAllBrands.isGivenVarPresentInSourceCode(var));
    }

    @And("^Verify correct s_account value is displayed")
    public void validate_s_account_value() {
        String expectedSAccountValue= testDataReader.getData("s_account.value");
        if (expectedSAccountValue.equalsIgnoreCase("any")) {
            assertTrue("s_account value should not be blank", !expectedSAccountValue.isEmpty());
        } else {
            assertEquals("s_account value should match", expectedSAccountValue, homePageAllBrands.getSAccountValue());
        }
    }

}