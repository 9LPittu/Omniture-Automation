package com.jcrew.runner.qa;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/qa",
        tags = {"@EmbeddedHeaderFooterReviewPage"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-embeddedheaderfooterqareviewpagefeatures.json",
                "pretty",
                "html:target/cucumber/embeddedheaderfooterqareviewpagefeatures"
        }
)
public class EmbeddedHeaderFooterReviewPageRunnerTest {
}
