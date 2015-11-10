package com.jcrew.runner.qa;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/qa",
        tags = {"@EmbeddedHeaderFooterForgotPasswordPage"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-embeddedheaderfooterqaforgotpasswordpagefeatures.json",
                "pretty",
                "html:target/cucumber/embeddedheaderfooterqaforgotpasswordpagefeatures"
        }
)

public class EmbeddedHeaderFooterForgotPasswordPageRunnerTest {
}
