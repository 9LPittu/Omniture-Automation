package com.jcrew.runner.ci;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/smoke",
        tags = {"@Checkout"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-checkoutcifeatures.json",
                "pretty",
                "html:target/cucumber/checkoutcifeatures"
        }
)
public class CheckoutFeaturesRunnerTest {
}
