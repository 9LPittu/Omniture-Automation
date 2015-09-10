package com.jcrew.runner.qa;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/qa",
        tags = {"@EmbeddedHeaderFooterContactUsPage"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-embeddedheaderfooterqacontactuspagefeatures.json",
                "pretty",
                "html:target/cucumber/embeddedheaderfooterqacontactuspagefeatures"
        }
)

public class EmbeddedHeaderFooterContactUsPage {
}
