package com.jcrew.steps;

import com.jcrew.page.ForgotPassword;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.Then;

import static org.junit.Assert.assertTrue;

public class ForgotPasswordSteps extends DriverFactory {
    private final ForgotPassword forgotPasswordPage = new ForgotPassword(getDriver());

    @Then("^Verify user is in forgot password page$")
    public void verify_user_is_in_forgot_password_page() throws Throwable {
        assertTrue("User should have been in forgot password page", forgotPasswordPage.isForgotPasswordPage());
    }

}
