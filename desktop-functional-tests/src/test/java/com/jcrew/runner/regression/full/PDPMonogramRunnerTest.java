package com.jcrew.runner.regression.full;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/regression"},
        tags = {"@PDPMonogram"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-pdpmonogram.json",
                "pretty",
                "html:target/cucumber/pdpmonogram"
        }
)
public class PDPMonogramRunnerTest {

}
