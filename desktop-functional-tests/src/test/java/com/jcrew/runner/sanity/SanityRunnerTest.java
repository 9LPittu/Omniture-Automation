package com.jcrew.runner.sanity;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/regression"},
        tags = {"@Sanity"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-Sanity.json",
                "pretty",
                "html:target/cucumber/Sanity"
        }
)

public class SanityRunnerTest {

}
