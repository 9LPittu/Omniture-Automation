package com.jcrew.runner.regression.full;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/regression/Checkout/Registered"},
        tags = {"@RegisteredCheckout4"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-regression-registered4.json",
                "pretty",
                "html:target/cucumber/regression-registered4"
        })
/**
 * Created by nadiapaolagarcia on 5/16/16.
 */
public class CheckoutRegistered4RunnerTest {
}
