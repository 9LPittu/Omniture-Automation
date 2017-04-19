package com.jcrew.runner.regression.full;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/regression/Checkout/PromoCode"},
        tags = {"@PromoCode"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-regression-promocode.json",
                "pretty",
                "html:target/cucumber/regression-promocode"
        })
/**
 * Created by nadiapaolagarcia on 5/16/16.
 */
public class CheckoutPromoCodeRunnerTest {
}
