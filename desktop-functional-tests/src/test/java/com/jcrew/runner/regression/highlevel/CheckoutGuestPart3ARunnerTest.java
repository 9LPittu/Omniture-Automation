package com.jcrew.runner.regression.highlevel;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/regression/Checkout/Guest"},
        tags = {"@GuestCheckout-Part3A","@HighLevel"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-regression-guest-part3A.json",
                "pretty",
                "html:target/cucumber/regression-guest-part3A"
        })

public class CheckoutGuestPart3ARunnerTest {
}
