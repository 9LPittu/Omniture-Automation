package com.jcrew.runner.qa;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/qa",
        tags = {"@EmbeddedHeaderFooterPrivacyPolicyPage"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-embeddedheaderfooterqaprivacypolicypagefeatures.json",
                "pretty",
                "html:target/cucumber/embeddedheaderfooterqaprivacypolicypagefeatures"
        }
)

public class EmbeddedHeaderFooterPrivacyPolicyPage {
}
