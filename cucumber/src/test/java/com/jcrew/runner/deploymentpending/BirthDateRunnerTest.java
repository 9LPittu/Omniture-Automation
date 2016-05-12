package com.jcrew.runner.deploymentpending;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression"},
        tags = {"@deploymentpending"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-birthdatefeatures.json",
                "pretty",
                "html:target/cucumber/birthdatefeatures"
        }
)

public class BirthDateRunnerTest {
}
