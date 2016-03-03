package com.jcrew.runner.other;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/ProductionSpecific"},
        tags = {"@GoldSAccount"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-saccountgoldotherfeatures.json",
                "pretty",
                "html:target/cucumber/saccountgoldotherfeatures"
        }
)public class SAccountFeaturesRunnerTest {
}
