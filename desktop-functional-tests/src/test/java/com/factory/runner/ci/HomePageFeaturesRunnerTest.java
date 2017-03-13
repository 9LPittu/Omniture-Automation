package com.factory.runner.ci;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/factory/smoke",
        tags = {"@Home"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-homecifeatures.json",
                "pretty",
                "html:target/cucumber/homecifeatures"
        }
)
public class HomePageFeaturesRunnerTest {
}
