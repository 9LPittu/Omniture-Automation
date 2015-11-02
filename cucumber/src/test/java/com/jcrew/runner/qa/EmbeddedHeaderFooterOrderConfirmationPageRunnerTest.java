package com.jcrew.runner.qa;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/qa",
        tags = {"@EmbeddedHeaderFooterOrderConfirmationPage"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-embeddedheaderfooterqaorderconfirmationpagefeatures.json",
                "pretty",
                "html:target/cucumber/embeddedheaderfooterqaorderconfirmationpagefeatures"
        }
)
public class EmbeddedHeaderFooterOrderConfirmationPageRunnerTest {
}
