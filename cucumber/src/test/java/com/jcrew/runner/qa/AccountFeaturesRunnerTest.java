package com.jcrew.runner.qa;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/qa",
        tags = {"@Account"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-accountqafeatures.json",
                "pretty",
                "html:target/cucumber/accountqafeatures"
        }
)

public class AccountFeaturesRunnerTest {

}
