package com.jcrew.runner.qa;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/qa",
        tags = {"@EmbeddedHeaderFooterShoppingBagPage"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-embeddedheaderfooterqashoppingbagpagefeatures.json",
                "pretty",
                "html:target/cucumber/embeddedheaderfooterqashoppingbagpagefeatures"
        }
)
public class EmbeddedHeaderFooterShoppingBagPageRunnerTest {
}
