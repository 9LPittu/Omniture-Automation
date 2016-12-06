package com.jcrew.runner.atp;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/atp",
        tags = {"@ATPRestricted3"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-atp-restricted3.json",
                "pretty",
                "html:target/cucumber/atp-restricted3"
        }
)
public class AtpRestricted3RunnerTest {
}
