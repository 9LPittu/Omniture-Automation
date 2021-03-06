package com.jcrew.runner.regression.full;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression/Checkout/Merge"},
        tags = {"@MergeCheckout"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-regression-merge.json",
                "pretty",
                "html:target/cucumber/regression-merge"
        })

public class CheckoutMergeRunnerTest {
}
