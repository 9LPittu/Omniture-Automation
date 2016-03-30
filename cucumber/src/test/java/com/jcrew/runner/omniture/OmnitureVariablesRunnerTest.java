package com.jcrew.runner.omniture;

/**
 * Created by nadiapaolagarcia on 3/22/16.
 */

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/omniture"},
        tags = {"@Omniture"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-omniturefeatures.json",
                "pretty",
                "html:target/cucumber/omniturefeatures"
        }
)

public class OmnitureVariablesRunnerTest {
}
