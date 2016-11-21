package com.jcrew.runner.atp;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/atp",
        tags = {"@ATPInternational1"},
        glue = {"com.jcrew.steps"},
        format = {
        		"json:target/cucumber-atp.json",
                "pretty",
                "html:target/cucumber/atp-international"
        }
)
public class AtpInternational1RunnerTest {
}
