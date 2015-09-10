package com.jcrew.runner.qa;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/qa",
        tags = {"@EmbeddedHeaderFooterOrderStatusPage"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-embeddedheaderfooterqaorderstatuspagefeatures.json",
                "pretty",
                "html:target/cucumber/embeddedheaderfooterqaorderstatuspagefeatures"
        }
)

public class EmbeddedHeaderFooterOrderStatusPage {
}
