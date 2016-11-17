package com.jcrew.runner.regression.highlevel;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression/Omniture"},
        tags = {"@omniture","@HighLevel"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-omniture.json",
                "pretty",
                "html:target/cucumber/omniture"
        }
)
public class OmnitureRunnerTest {

}
