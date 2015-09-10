package com.jcrew.runner.qa;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/qa",
        tags = {"@EmbeddedHeaderFooterAccountMyDetailsPage"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-embeddedheaderfooterqaaccountmydetailspagefeatures.json",
                "pretty",
                "html:target/cucumber/embeddedheaderfooterqaaccountmydetailspagefeatures"
        }
)

public class EmbeddedHeaderFooterAccountMyDetailsPage {
}
