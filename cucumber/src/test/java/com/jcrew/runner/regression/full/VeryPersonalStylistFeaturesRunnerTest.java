package com.jcrew.runner.regression.full;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/regression",
        tags = {"@VeryPersonalStylist"},
        glue = {"com.jcrew.steps"},
        format = {"json:target/cucumber-verypersonalstylistfeatures.json",
                "pretty",
                "html:target/cucumber/verypersonalstylistfeatures"}
)

public class VeryPersonalStylistFeaturesRunnerTest {
}
