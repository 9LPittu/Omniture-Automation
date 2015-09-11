package com.jcrew.runner.qa;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/qa",
        tags = {"@EmbeddedHeaderFooterReturnsAndExchangesPage"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-embeddedheaderfooterqareturnsandexchangespagefeatures.json",
                "pretty",
                "html:target/cucumber/embeddedheaderfooterqareturnsandexchangespagefeatures"
        }
)
public class EmbeddedHeaderFooterReturnsAndExchangesPageRunnerTest {
}
