package com.jcrew.runner.qa;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/qa",
        tags = {"@EmbeddedHeaderFooterShippingAndGiftOptionsPage"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-embeddedheaderfooterqashippingandgiftoptionspagefeatures.json",
                "pretty",
                "html:target/cucumber/embeddedheaderfooterqashippingandgiftoptionspagefeatures"
        }
)
public class EmbeddedHeaderFooterShippingAndGiftOptionsPageRunnerTest {
}
