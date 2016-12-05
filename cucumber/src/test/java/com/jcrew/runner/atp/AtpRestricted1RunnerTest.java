package com.jcrew.runner.atp;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/atp",
        tags = {"@ATPRestricted1"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-atp-restricted1.json",
                "pretty",
                "html:target/cucumber/atp-restricted1"
        }
)
public class AtpRestricted1RunnerTest {
}
