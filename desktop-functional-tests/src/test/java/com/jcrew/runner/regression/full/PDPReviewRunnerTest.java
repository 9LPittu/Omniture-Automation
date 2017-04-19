package com.jcrew.runner.regression.full;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/jcrew/regression"},
        tags = {"@PDPReview"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-pdpreview.json",
                "pretty",
                "html:target/cucumber/pdpreview"
        }
)
public class PDPReviewRunnerTest {

}
