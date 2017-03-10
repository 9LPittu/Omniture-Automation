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
        tags = {"@Footer4"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-Footer4.json",
                "pretty",
                "html:target/cucumber/Footer4"
        }
)
public class FooterRunner4Test {
}
