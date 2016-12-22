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
                "json:target/JC_E2E_RegisteredUser_Testdata_Sheet.json",
                "pretty",
                "html:target/cucumber/JC_E2E_RegisteredUser_Testdata_Sheet"
        }
)
public class E2ERegisteredUserRunnerTest {
}