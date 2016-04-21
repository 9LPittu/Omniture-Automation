package com.jcrew.runner.regression.highlevel;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by nadiapaolagarcia on 4/20/16.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression"},
        tags = {"@HighLevel"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-breadcrumbregressionfeatures.json",
                "pretty",
                "html:target/cucumber/breadcrumbregressionfeatures"
        }
)
public class HighLevelRunnerTest {
}
