package com.jcrew.runner.ci;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/smoke"},
        tags = {"@Account"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-accountsmokefeatures.json",
                "pretty",
                "html:target/cucumber/accountsmokefeatures"
        }
)

public class AccountFeaturesRunnerTest {
}