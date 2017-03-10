package com.jcrew.runner.regression.highlevel;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by msayed3 on 11/27/2016.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression/jcrew/HeaderAndFooter"},
        tags = {"@Footer8","@HighLevel"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-Footer8.json",
                "pretty",
                "html:target/cucumber/Footer8"
        }
)
public class Footer8RunnerTest {
}
