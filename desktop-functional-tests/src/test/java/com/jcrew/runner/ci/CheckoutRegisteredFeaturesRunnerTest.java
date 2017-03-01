package com.jcrew.runner.ci;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/smoke",
        tags = {"@CheckoutRegistered"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-checkoutregisteredcifeatures.json",
                "pretty",
                "html:target/cucumber/checkoutregisteredcifeatures"
        }
)
public class CheckoutRegisteredFeaturesRunnerTest {
}
