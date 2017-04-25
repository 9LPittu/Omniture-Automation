package com.factory.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/factory/regression/checkout"},
        tags = {"@PromoCode"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-fa-regression-promocode.json",
                "pretty",
                "html:target/cucumber/fa-regression-promocode"
        })

public class CheckoutPromoCodeRunnerTest {
}