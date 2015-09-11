package com.jcrew.runner.qa;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/qa",
        tags = {"@EmbeddedHeaderFooterVeryPersonalStylistPage"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-embeddedheaderfooterqaverypersonalstylistpagefeatures.json",
                "pretty",
                "html:target/cucumber/embeddedheaderfooterqaverypersonalstylistpagefeatures"
        }
)
public class EmbeddedHeaderFooterVeryPersonalStylistPageRunnerTest {
}
