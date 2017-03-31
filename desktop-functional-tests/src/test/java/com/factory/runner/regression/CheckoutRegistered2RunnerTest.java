package com.factory.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/factory/regression/Checkout/Registered"},
        tags = {"@RegisteredCheckout2"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-regression-registered2.json",
                "pretty",
                "html:target/cucumber/regression-registered2"
        })
/**
 * Created by ravi kumar bokka on 3/24/17
 */
public class CheckoutRegistered2RunnerTest {
}
