package com.jcrew.runner.prodmonitoring;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/prodmonitoring"},
        tags = {"@ProdCheckout"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-prodmonitoring-guest.json",
                "pretty",
                "html:target/cucumber/prodmonitoring-guest"
        })

public class CheckoutGuestRunnerTest {
}
