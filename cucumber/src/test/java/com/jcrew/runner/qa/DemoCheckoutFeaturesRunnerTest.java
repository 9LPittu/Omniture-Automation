package com.jcrew.runner.qa;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/smoke",
        tags = {"@CheckoutSignedinUser2"},
        glue = {"com.jcrew.steps"},
        format = {"json:target/cucumber-checkoutfeatures.json","html:target/HTMLReports"}
)
public class DemoCheckoutFeaturesRunnerTest {
}
