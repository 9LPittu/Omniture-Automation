package com.jcrew.runner.qa;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/qa",
        tags = {"@EmbeddedHeaderFooterShippingAndHandlingPage"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-embeddedheaderfooterqashippingandhandlingpagefeatures.json",
                "pretty",
                "html:target/cucumber/embeddedheaderfooterqashippingandhandlingpagefeatures"
        }
)
public class EmbeddedHeaderFooterReturnsAndExchangesPageRunnerTest {
}
