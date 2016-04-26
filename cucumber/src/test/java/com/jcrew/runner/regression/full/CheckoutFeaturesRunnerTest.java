package com.jcrew.runner.regression.full;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression"},
        tags = {"@RegressionCheckout"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-regressioncheckoutfeatures.json",
                "pretty",
                "html:target/cucumber/regressioncheckoutfeatures"
        }
)
public class CheckoutFeaturesRunnerTest {
}