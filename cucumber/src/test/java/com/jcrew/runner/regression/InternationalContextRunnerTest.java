package com.jcrew.runner.regression;

/**
 * Created by 9hvenaga on 4/21/2016.
 */
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression"},
        tags = {"@Context"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-internationalcontextregressionfeatures.json",
                "pretty",
                "html:target/cucumber/internationalcontextregressionfeatures"
        }
)

public class InternationalContextRunnerTest {
}
