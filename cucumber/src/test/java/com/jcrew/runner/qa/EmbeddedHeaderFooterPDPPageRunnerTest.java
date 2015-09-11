package com.jcrew.runner.qa;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/qa",
        tags = {"@EmbeddedHeaderFooterPDPPage"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-embeddedheaderfooterqapdppagefeatures.json",
                "pretty",
                "html:target/cucumber/embeddedheaderfooterqapdppagefeatures"
        }
)
public class EmbeddedHeaderFooterPDPPageRunnerTest {
}
