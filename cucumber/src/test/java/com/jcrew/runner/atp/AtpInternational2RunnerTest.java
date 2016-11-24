package com.jcrew.runner.atp;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/atp",
        tags = {"@ATPInternational2"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-atp-international2.json",
                "pretty",
                "html:target/cucumber/atp-international2"
        }
)
public class AtpInternational2RunnerTest {
}
