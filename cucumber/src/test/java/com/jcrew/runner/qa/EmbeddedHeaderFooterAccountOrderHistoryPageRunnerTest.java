package com.jcrew.runner.qa;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/qa",
        tags = {"@EmbeddedHeaderFooterAccountOrderHistoryPage"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-embeddedheaderfooterqaaccountorderhistorypagefeatures.json",
                "pretty",
                "html:target/cucumber/embeddedheaderfooterqaaccountorderhistorypagefeatures"
        }
)

public class EmbeddedHeaderFooterAccountOrderHistoryPageRunnerTest {
}
