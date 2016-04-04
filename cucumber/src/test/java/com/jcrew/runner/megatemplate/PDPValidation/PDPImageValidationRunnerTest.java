package com.jcrew.runner.megatemplate.PDPValidation;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/megatemplate"},
        tags = {"@megaImagePDP"},
        glue = {"com.jcrew.util",
        		"com.jcrew.steps"
        		
        },
        format = {
                "json:target/cucumber-megatemplateImageValidation.json",
                "pretty",
                "html:target/cucumber/megatemplateImageValidation"
        }
)
public class PDPImageValidationRunnerTest {
}