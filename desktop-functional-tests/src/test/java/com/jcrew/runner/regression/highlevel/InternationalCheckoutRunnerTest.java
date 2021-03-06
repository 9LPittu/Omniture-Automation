package com.jcrew.runner.regression.highlevel;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by nadiapaolagarcia on 4/18/16.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/regression/International"},
        tags = {"@InternationalCheckout","@HighLevel"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-international-checkout.json",
                "pretty",
                "html:target/cucumber/international-checkout"
        }
)
public class InternationalCheckoutRunnerTest {

}
