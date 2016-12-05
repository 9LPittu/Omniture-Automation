package com.jcrew.runner.regression.full;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by nadiapaolagarcia on 4/18/16.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression/International"},
        tags = {"@International-Part1"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-international1.json",
                "pretty",
                "html:target/cucumber/international1"
        }
)
public class InternationalPart1RunnerTest {
}
