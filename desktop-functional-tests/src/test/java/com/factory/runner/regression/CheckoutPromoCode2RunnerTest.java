package com.factory.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/factory/regression/checkout"},
        tags = {"@PromoCode2"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-fa-regression-promocode2.json",
                "pretty",
                "html:target/cucumber/fa-regression-promocode2"
        })

public class CheckoutPromoCode2RunnerTest {
}