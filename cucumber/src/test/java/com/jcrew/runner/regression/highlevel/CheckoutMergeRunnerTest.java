package com.jcrew.runner.regression.highlevel;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression/Checkout/Merge"},
        tags = {"@MergeCheckout", "@HighLevel"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-regression-merge.json",
                "pretty",
                "html:target/cucumber/regression-merge"
        })

public class CheckoutMergeRunnerTest {
}
