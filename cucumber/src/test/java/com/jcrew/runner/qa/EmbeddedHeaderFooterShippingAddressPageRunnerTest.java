package com.jcrew.runner.qa;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/qa",
        tags = {"@EmbeddedHeaderFooterShippingAddressPage"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-embeddedheaderfooterqashippingaddresspagefeatures.json",
                "pretty",
                "html:target/cucumber/embeddedheaderfooterqashippingaddresspagefeatures"
        }
)
public class EmbeddedHeaderFooterShippingAddressPageRunnerTest {
}
