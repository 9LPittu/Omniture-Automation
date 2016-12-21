package com.e2e.runner.jcrew;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/e2e/jcrew"},
        tags = {"@e2e-registereduser"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/e2e-jcrew-registereduser.json",
                "pretty",
                "html:target/cucumber/e2e-jcrew-registereduser"
        }
)
public class E2ERegisteredUserRunnerTest {
}