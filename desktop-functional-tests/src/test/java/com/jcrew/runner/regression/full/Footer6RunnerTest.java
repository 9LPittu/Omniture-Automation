package com.jcrew.runner.regression.full;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by msayed3 on 11/27/2016.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/regression/HeaderAndFooter"},
        tags = {"@Footer6"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-Footer6.json",
                "pretty",
                "html:target/cucumber/Footer6"
        }
)
public class Footer6RunnerTest {
}
