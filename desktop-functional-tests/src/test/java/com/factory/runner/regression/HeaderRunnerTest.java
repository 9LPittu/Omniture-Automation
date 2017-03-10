package com.factory.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by msayed3 on 11/27/2016.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/factory/regression/HeaderAndFooter"},
        tags = {"@Header"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-Header.json",
                "pretty",
                "html:target/cucumber/Header"
        }
)
public class HeaderRunnerTest {
}
