package com.jcrew.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/smoke",
        tags = {"@Search"},
        glue = {"com.jcrew.steps"},
        format = {"json:target/cucumber-searchfeatures.json"}
)
public class SearchPageFeaturesRunnerTest {
}
