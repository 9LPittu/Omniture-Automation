package com.jcrew.runner.megatemplate.PDPValidation;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/megatemplate"},
        tags = {"@megaExtendedSize"},
        glue = {"com.jcrew.util",
        		"com.jcrew.steps"
        		
        },
        format = {
                "json:target/cucumber-megatemplateExtendedSizefeatures.json",
                "pretty",
                "html:target/cucumber/megatemplateExtendedSizefeatures"
        }
)
public class ExtendedSizeValidationRunnerTest {
}