package com.factory.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/factory/regression/Checkout/Registered"},
        tags = {"@RegisteredCheckout4"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-regression-registered4.json",
                "pretty",
                "html:target/cucumber/regression-registered4"
        })
/**
 * Created by ravi kumar bokka on 3/24/17
 */
public class CheckoutRegistered4RunnerTest {
}
