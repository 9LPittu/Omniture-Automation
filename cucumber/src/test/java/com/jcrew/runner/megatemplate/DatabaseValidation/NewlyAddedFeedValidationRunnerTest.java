package com.jcrew.runner.megatemplate.DatabaseValidation;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/megatemplate"},
        tags = {"@NA"},
        glue = {"com.jcrew.util",
        		"com.jcrew.steps"
        		
        },
        format = {
                "json:target/cucumber-megatemplateNewlyAddedModules.json",
                "pretty",
                "html:target/cucumber/megatemplateNewlyAddedModules"
        }
)
public class NewlyAddedFeedValidationRunnerTest {
}