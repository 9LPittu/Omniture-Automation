package com.jcrew.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression"},
        tags = {"@EmbeddedFooter"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-embeddedfooterregressionfeatures.json",
                "pretty",
                "html:target/cucumber/embeddedfooterregressionfeatures"
        }
)
public class EmbeddedFooterRunnerTest {
}
