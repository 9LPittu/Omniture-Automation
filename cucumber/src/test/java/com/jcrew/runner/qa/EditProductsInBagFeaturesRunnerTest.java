package com.jcrew.runner.qa;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/qa",
        tags = {"@EditProductsInBag"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-editproductsinbagqafeatures.json",
                "pretty",
                "html:target/cucumber/editproductsinbagqafeatures"
        }
)

public class EditProductsInBagFeaturesRunnerTest {
}
