package com.jcrew.runner.other;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/ProductionSpecific"},
        tags = {"@sourcecode"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-saccountotherfeatures.json",
                "pretty",
                "html:target/cucumber/saccountotherfeatures"
        }
)public class SAccountFeaturesRunnerTest {
}
