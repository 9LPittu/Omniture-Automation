package com.jcrew.runner.ci;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/smoke",
        tags = {"@Home"},
        glue = {"com.jcrew.steps"},
        format = {"json:target/cucumber-homefeatures.json"}
)
public class HomePageFeaturesRunnerTest {
}
