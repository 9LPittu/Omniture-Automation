package com.jcrew.runner.qa;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/qa",
        tags = {"@EmbeddedHeaderFooterBillingPage"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-embeddedheaderfooterqabillingpagefeatures.json",
                "pretty",
                "html:target/cucumber/embeddedheaderfooterqabillingpagefeatures"
        }
)
public class EmbeddedHeaderFooterBillingPageRunnerTest {
}
