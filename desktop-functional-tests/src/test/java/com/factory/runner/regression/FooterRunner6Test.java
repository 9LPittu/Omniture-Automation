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
        tags = {"@Footer6"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-Footer6.json",
                "pretty",
                "html:target/cucumber/Footer6"
        }
)
public class FooterRunner6Test {
}
