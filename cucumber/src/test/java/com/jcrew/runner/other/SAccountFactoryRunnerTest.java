package com.jcrew.runner.other;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;



@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/ProductionSpecific"},
        tags = {"@Factory"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-saccountfactoryotherfeatures.json",
                "pretty",
                "html:target/cucumber/saccountfactoryotherfeatures"
        }
)public class SAccountFactoryRunnerTest {
}
