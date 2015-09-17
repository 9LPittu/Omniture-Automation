package com.jcrew.runner.qa;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/qa",
        tags = {"@EditProductsInBagSignedIn"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-editproductsinbagsignedinqafeatures.json",
                "pretty",
                "html:target/cucumber/editproductsinbagsignedinqafeatures"
        }
)

public class EditProductsInBagSignedInFeaturesRunnerTest {
}
