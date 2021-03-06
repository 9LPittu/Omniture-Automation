package com.jcrew.runner.regression.highlevel;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by nadiapaolagarcia on 4/18/16.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/regression/Array"},
        tags = {"@Array","@HighLevel"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-array.json",
                "pretty",
                "html:target/cucumber/array"
        }
)
public class ArrayRunnerTest {

}
