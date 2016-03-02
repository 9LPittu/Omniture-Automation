package com.jcrew.runner.other;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;



@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/ProductionSpecific"},
        tags = {"@Madewell"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-saccountmadewellotherfeatures.json",
                "pretty",
                "html:target/cucumber/saccountmadewellotherfeatures"
        }
)public class SAccountMadewellRunnerTest {
}
