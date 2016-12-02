package com.jcrew.runner.atp;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/atp",
        tags = {"@ATPRestricted4"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-atp-restricted4.json",
                "pretty",
                "html:target/cucumber/atp-restricted4"
        }
)
public class AtpRestricted4RunnerTest {
}
