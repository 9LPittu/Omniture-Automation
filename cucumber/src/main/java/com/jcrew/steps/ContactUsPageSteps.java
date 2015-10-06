package com.jcrew.steps;


import com.jcrew.page.ContactUsPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.Then;

import static org.junit.Assert.assertTrue;

public class ContactUsPageSteps extends DriverFactory {

    @Then("^Verify user is on contact us page$")
    public void Verify_user_is_on_contact_us_page() throws Throwable {
        ContactUsPage contactUsPage = new ContactUsPage(getDriver());
        assertTrue("User should have been in contact us page",
                contactUsPage.isContactUsPage());
    }

}
