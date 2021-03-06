package com.jcrew.runner.regression.full;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/regression/Checkout/Merge"},
        tags = {"@MergeCheckout2"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-regression-merge2.json",
                "pretty",
                "html:target/cucumber/regression-merge2"
        })
/**
 * Created by nadiapaolagarcia on 5/16/16.
 */
public class CheckoutMerge2RunnerTest {
}
