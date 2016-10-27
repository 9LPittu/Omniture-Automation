package com.jcrew.runner.regression.highlevel;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression"},
        tags = {"@RegressionCheckout1", "@HighLevel"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-regressioncheckoutfeatures.json",
                "pretty",
                "html:target/cucumber/regressioncheckoutfeatures"
        }
)
public class CheckoutFeaturesRunnerTest {
}