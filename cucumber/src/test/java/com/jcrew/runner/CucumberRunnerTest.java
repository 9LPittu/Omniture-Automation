package com.jcrew.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features"
        ,glue={"com.jcrew.steps"}
)
public class CucumberRunnerTest {
}
