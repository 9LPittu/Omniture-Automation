package com.jcrew.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/smoke", "features/regression"},
        tags = {"@Category"},
        glue = {"com.jcrew.steps"}
)
public class CategoryFeaturesRunnerTest {
}
