package com.jcrew.runner.ci;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/smoke"},
        tags = {"@Category"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-categorycifeatures.json",
                "pretty",
                "html:target/cucumber/categorycifeatures"
        }
)
public class CategoryFeaturesRunnerTest {
}
