package com.jcrew.runner.regression.highlevel;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/regression"},
        tags = {"@PDP","@HighLevel"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-pdp.json",
                "pretty",
                "html:target/cucumber/pdp"
        }
)
public class PDPRunnerTest {

}
