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
                "json:target/cucumber-sdotpagename.json",
                "pretty",
                "html:target/cucumber/sdotpagenamefeatures"
        }
)

public class SDotPageNameRunnerTest {
}
