package com.jcrew.runner.ci;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/jcrew/smoke",
        tags = {"@PDP"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-pdpcifeatures.json",
                "pretty",
                "html:target/cucumber/pdpcifeatures"
        }
)
public class PDPFeaturesRunnerTest {
}
