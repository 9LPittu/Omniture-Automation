package com.jcrew.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression"},
        tags = {"@EmbeddedHeaderAndFooter"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-embeddedheaderregressionfeatures.json",
                "pretty",
                "html:target/cucumber/embeddedheaderregressionfeatures"
        }
)
public class EmbeddedHeaderAndFooterRunnerTest {
}
