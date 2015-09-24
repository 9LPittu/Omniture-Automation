package com.jcrew.runner.qa;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/qa",
        tags = {"@Preorder"},
        glue = {"com.jcrew.steps"},
        format = {"json:target/cucumber-preorderfeatures.json",
                "pretty",
                "html:target/cucumber/preorderfeatures"}
)


public class PreorderFeaturesRunnerTest1 {

}
