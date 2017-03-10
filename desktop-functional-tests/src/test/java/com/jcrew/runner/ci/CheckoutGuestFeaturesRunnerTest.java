package com.jcrew.runner.ci;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/jcrew/smoke",
        tags = {"@CheckoutGuest"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-checkoutguestcifeatures.json",
                "pretty",
                "html:target/cucumber/checkoutguestcifeatures"
        }
)
public class CheckoutGuestFeaturesRunnerTest {
}
