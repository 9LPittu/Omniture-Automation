package com.factory.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by msayed3 on 11/27/2016.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression/factory/HeaderAndFooter"},
        tags = {"@Footer8"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-Footer8.json",
                "pretty",
                "html:target/cucumber/Footer8"
        }
)
public class FooterRunner8Test {
}
