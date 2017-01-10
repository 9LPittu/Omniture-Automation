package com.jcrew.runner.regression.highlevel;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression/Checkout/Merge"},
        tags = {"@MergeCheckout4"},
        glue = {"com.jcrew.steps","@HighLevel"},
        format = {
                "json:target/cucumber-regression-merge4.json",
                "pretty",
                "html:target/cucumber/regression-merge4"
        })
/**
 * Created by nadiapaolagarcia on 5/16/16.
 */
public class CheckoutMerge4RunnerTest {
}
