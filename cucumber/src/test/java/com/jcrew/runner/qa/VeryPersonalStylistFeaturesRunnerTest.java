package com.jcrew.runner.qa;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/qa",
        tags = {"@VeryPersonalStylist"},
        glue = {"com.jcrew.steps"},
        format = {"json:target/cucumber-verypersonalstylistfeatures.json",
                "pretty",
                "html:target/cucumber/verypersonalstylisteatures"}
)


public class VeryPersonalStylistFeaturesRunnerTest {

}
