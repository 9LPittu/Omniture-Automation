package com.jcrew.steps;


import com.jcrew.page.PrivacyPolicyPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.Then;

import static org.junit.Assert.assertTrue;

public class PolicyPrivacyPageSteps extends DriverFactory {

    @Then("^Verify user is on privacy policy page$")
    public void Verify_user_is_on_privacy_policy_page() throws Throwable {
        PrivacyPolicyPage privacyPolicyPage = new PrivacyPolicyPage(getDriver());
        assertTrue("User should have been in privacy policy page", privacyPolicyPage.isPrivacyPolicyPage());
    }
}
