package com.factory.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/regression/Checkout/Registered"},
        tags = {"@RegisteredCheckout"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-regression-registered.json",
                "pretty",
                "html:target/cucumber/regression-registered"
        })
/**
 * Created by ravi kumar bokka on 3/24/17
 */
public class CheckoutRegisteredRunnerTest {
}
