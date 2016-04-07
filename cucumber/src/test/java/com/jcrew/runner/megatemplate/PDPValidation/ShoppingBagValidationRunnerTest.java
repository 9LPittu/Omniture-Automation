package com.jcrew.runner.megatemplate.PDPValidation;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/megatemplate"},
        tags = {"@megaShoppingBag"},
        glue = {"com.jcrew.util",
        		"com.jcrew.steps"
        		
        },
        format = {
                "json:target/cucumber-megatemplateShoppingBag.json",
                "pretty",
                "html:target/cucumber/megatemplateShoppingBag"
        }
)
public class ShoppingBagValidationRunnerTest {
}