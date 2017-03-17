package com.factory.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/factory/regression/Checkout/Merge"},
        tags = {"@MergeCheckout3"},
        glue = {"com.jcrew.steps","@HighLevel"},
        format = {
                "json:target/cucumber-regression-merge3.json",
                "pretty",
                "html:target/cucumber/regression-merge3"
        })
/**
 * Created by nadiapaolagarcia on 5/16/16.
 */
public class CheckoutMerge3RunnerTest {
}
