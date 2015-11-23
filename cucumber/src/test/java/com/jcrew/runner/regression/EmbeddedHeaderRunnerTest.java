package com.jcrew.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by 9hvenaga on 11/23/2015.
 */

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression"},
        tags = {"@EmbeddedHeader"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-embeddedheaderregressionfeatures.json",
                "pretty",
                "html:target/cucumber/embeddedheaderregressionfeatures"
        }
)
public class EmbeddedHeaderRunnerTest {
}
