package com.jcrew.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by nadiapaolagarcia on 4/18/16.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression"},
        tags = {"@International-Part2"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-international2.json",
                "pretty",
                "html:target/cucumber/international2"
        }
)
public class InternationalPart2RunnerTest {

}
