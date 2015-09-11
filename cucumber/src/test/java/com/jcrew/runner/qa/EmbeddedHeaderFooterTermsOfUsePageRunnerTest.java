package com.jcrew.runner.qa;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/qa",
        tags = {"@EmbeddedHeaderFooterTermsOfUsePage"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-embeddedheaderfooterqatermsofusepagefeatures.json",
                "pretty",
                "html:target/cucumber/embeddedheaderfooterqatermsofusepagefeatures"
        }
)

public class EmbeddedHeaderFooterTermsOfUsePageRunnerTest {
}
