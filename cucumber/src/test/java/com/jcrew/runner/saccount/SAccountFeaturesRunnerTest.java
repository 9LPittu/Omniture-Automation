package com.jcrew.runner.saccount;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/saccount"},
        tags = {"@GoldSAccount"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-saccountgoldotherfeatures.json",
                "pretty",
                "html:target/cucumber/saccountgoldotherfeatures"
        }
)public class SAccountFeaturesRunnerTest {
}
