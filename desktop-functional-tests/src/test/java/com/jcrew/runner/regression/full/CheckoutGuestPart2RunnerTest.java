package com.jcrew.runner.regression.full;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression/Checkout/Guest"},
        tags = {"@GuestCheckout-Part2"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-regression-guest-part2.json",
                "pretty",
                "html:target/cucumber/regression-guest-part2"
        })
/**
 * Created by nadiapaolagarcia on 5/16/16.
 */
public class CheckoutGuestPart2RunnerTest {
}
