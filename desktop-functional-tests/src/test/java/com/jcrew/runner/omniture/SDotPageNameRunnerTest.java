package com.jcrew.runner.omniture;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/omniture"},
        tags = {"@s.pageName"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-SDotPageName.json",
                "pretty",
                "html:target/cucumber/SDotPageName"
        }
)
public class SDotPageNameRunnerTest {

}
