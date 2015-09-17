package com.jcrew.runner.ci;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/smoke",
        tags = {"@CheckoutSignedInUser"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-checkoutsignedinusercifeatures.json",
                "pretty",
                "html:target/cucumber/checkoutsignedinusercifeatures"
        }
)
public class CheckoutSignedInUserFeaturesRunnerTest {
}
