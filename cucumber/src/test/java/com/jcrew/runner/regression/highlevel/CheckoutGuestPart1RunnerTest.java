package com.jcrew.runner.regression.highlevel;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression/Checkout/Guest"},
        tags = {"@GuestCheckout-Part1", "@HighLevel"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-regression-guest-part1.json",
                "pretty",
                "html:target/cucumber/regression-guest-part1"
        })

public class CheckoutGuestPart1RunnerTest {
}
