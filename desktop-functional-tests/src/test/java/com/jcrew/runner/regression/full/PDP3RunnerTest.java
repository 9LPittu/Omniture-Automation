package com.jcrew.runner.regression.full;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/regression"},
        tags = {"@PDP3"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-pdp3.json",
                "pretty",
                "html:target/cucumber/pdp3"
        }
)
public class PDP3RunnerTest {

}
