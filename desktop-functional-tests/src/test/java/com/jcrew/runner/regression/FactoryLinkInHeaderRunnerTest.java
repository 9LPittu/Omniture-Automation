package com.jcrew.runner.regression;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression"},
        tags = {"@FactoryLinkInHeader"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-factorylinkinheader.json",
                "pretty",
                "html:target/cucumber/factorylinkinheader"
        }
)
public class FactoryLinkInHeaderRunnerTest {

}