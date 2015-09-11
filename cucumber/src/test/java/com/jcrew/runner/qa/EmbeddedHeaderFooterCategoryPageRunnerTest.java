package com.jcrew.runner.qa;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/qa",
        tags = {"@EmbeddedHeaderFooterCategoryPage"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-embeddedheaderfooterqacategorypagefeatures.json",
                "pretty",
                "html:target/cucumber/embeddedheaderfooterqacategorypagefeatures"
        }
)
public class EmbeddedHeaderFooterCategoryPageRunnerTest {
}
