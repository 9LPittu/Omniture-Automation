package com.jcrew.steps;


import com.jcrew.page.TermsOfUsePage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.Then;

import static org.junit.Assert.assertTrue;

public class TermsOfUsePageSteps extends DriverFactory {

    @Then("^Verify user is on terms of use page$")
    public void verify_user_is_on_terms_of_use_page() throws Throwable {
        TermsOfUsePage termsOfUsePage = new TermsOfUsePage(getDriver());
        assertTrue("User should be in terms of use page", termsOfUsePage.isTermsOfUsePage());
    }

}
