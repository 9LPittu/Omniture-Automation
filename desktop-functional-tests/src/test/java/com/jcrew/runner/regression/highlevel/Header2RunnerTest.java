package com.jcrew.runner.regression.highlevel;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by 9msyed on 12/14/2016.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/regression/HeaderAndFooter"},
        tags = {"@Header2","@HighLevel"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-Header2.json",
                "pretty",
                "html:target/cucumber/Header2"
        }
)
public class Header2RunnerTest {
}
