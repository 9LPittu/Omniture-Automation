package com.jcrew.runner.regression.highlevel;

/**
 * Created by nadiapaolagarcia on 3/22/16.
 */

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/omniture"},
        tags = {"@Omniture1","@HighLevel"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-omnitureVerificationfeatures.json",
                "pretty",
                "html:target/cucumber/omnitureVerificationfeatures"
        }
)

public class OmnitureVerificationRunnerTest {
}
