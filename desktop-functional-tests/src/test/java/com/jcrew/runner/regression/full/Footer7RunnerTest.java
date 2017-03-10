package com.jcrew.runner.regression.full;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by msayed3 on 11/27/2016.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression/jcrew/HeaderAndFooter"},
        tags = {"@Footer7"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-Footer7.json",
                "pretty",
                "html:target/cucumber/Footer7"
        }
)
public class Footer7RunnerTest {
}
