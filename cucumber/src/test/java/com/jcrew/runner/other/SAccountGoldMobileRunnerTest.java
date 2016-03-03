package com.jcrew.runner.other;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;



@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/ProductionSpecific"},
        tags = {"@GoldSAccountMobile"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-saccountgoldmobiletherfeatures.json",
                "pretty",
                "html:target/cucumber/saccountgoldmobileotherfeatures"
        }
)public class SAccountGoldMobileRunnerTest {
}
