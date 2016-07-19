package com.jcrew.steps;

import com.jcrew.page.EmailCapture;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.When;

/**
 * Created by nadiapaolagarcia on 7/19/16.
 */
public class EmailCaptureSteps extends DriverFactory{
    EmailCapture emailCapture = new EmailCapture(getDriver());

    @When("User closes email capture")
    public void user_closes_email_capture() {
        emailCapture.closeEmailCapture();
    }
}
