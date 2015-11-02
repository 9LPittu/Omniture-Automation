package com.jcrew.runner.qa;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/qa",
        tags = {"@EmbeddedHeaderFooterAccountRegistrationPage"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-embeddedheaderfooterqaaccountregistrationpagefeatures.json",
                "pretty",
                "html:target/cucumber/embeddedheaderfooterqaaccountregistrationpagefeatures"
        }
)

public class EmbeddedHeaderFooterAccountRegistrationPageRunnerTest {
}
