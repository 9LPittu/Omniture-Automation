package com.jcrew.runner.regression.highlevel;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression"},
        tags = {"@Email", "@HighLevel"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-emailcapturefeatures.json",
                "pretty",
                "html:target/cucumber/emailcapturefeatures"
        }
)
public class EmailCaptureFeaturesRunnerTest {
}
