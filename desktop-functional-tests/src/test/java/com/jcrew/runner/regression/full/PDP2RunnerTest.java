package com.jcrew.runner.regression.full;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/regression"},
        tags = {"@PDP2"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-pdp2.json",
                "pretty",
                "html:target/cucumber/pdp2"
        }
)
public class PDP2RunnerTest {

}
