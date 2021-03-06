package com.jcrew.runner.regression.highlevel;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by msayed3 on 11/27/2016.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/regression/HeaderAndFooter"},
        tags = {"@Footer3","@HighLevel"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-Footer3.json",
                "pretty",
                "html:target/cucumber/Footer3"
        }
)
public class Footer3RunnerTest {
}
