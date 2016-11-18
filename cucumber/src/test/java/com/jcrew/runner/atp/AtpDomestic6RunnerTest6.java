package com.jcrew.runner.atp;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/atp",
        tags = {"@ATPDomestic6"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-accountcifeatures.json",
                "pretty",
                "html:target/cucumber/accountcifeatures"
        }
)
public class AtpDomestic6RunnerTest6 {
}
