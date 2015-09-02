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
                "json:target/cucumber-embeddedheaderfooterqacategoryfeatures.json",
                "pretty",
                "html:target/cucumber/embeddedheaderfooterqacategoryfeatures"
        }
)
public class EmbeddedHeaderFooterCategoryPageRunner {
}
