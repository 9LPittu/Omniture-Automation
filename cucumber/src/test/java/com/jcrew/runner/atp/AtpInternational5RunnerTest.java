package com.jcrew.runner.atp;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/atp",
        tags = {"@ATPInternational5"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-atp-international5.json",
                "pretty",
                "html:target/cucumber/atp-international5"
        }
)
public class AtpInternational5RunnerTest {
}
