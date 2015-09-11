package com.jcrew.runner.qa;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/qa",
        tags = {"@EmbeddedHeaderFooterJcrewGiftCardPage"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-embeddedheaderfooterqajcrewgiftcardpagefeatures.json",
                "pretty",
                "html:target/cucumber/embeddedheaderfooterqajcrewgiftcardpagefeatures"
        }
)
public class EmbeddedHeaderFooterJcrewGiftCardPageRunnerTest {
}
