package com.jcrew.steps;

import com.jcrew.page.ForgotPasswordPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;

import static org.junit.Assert.assertTrue;

public class ForgotPasswordPageSteps extends DriverFactory {
    private final ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(getDriver());

    @And("^Verify user is in forgot password page$")
    public void verify_user_is_in_forgot_password_page() throws Throwable {
        assertTrue("User should have been in forgot password page", forgotPasswordPage.isForgotPasswordPage());
    }

}
