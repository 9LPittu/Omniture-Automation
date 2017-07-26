package com.jcrew.runner.regression.full;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/regression/Checkout/PromoCode"},
        tags = {"@promocode3"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-regression-promocode3.json",
                "pretty",
                "html:target/cucumber/regression-promocode3"
        })

public class CheckoutPromoCode3RunnerTest {
}
