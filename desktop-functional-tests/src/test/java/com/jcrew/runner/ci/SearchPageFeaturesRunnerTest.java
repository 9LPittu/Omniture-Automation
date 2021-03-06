package com.jcrew.runner.ci;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/jcrew/smoke",
        tags = {"@Search"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-searchcifeatures.json",
                "pretty",
                "html:target/cucumber/searchcifeatures"
        }
)
public class SearchPageFeaturesRunnerTest {
}
