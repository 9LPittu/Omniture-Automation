package com.jcrew.runner.saccount;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;



@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/saccount"},
        tags = {"@ProdSAccount"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-saccountprodotherfeatures.json",
                "pretty",
                "html:target/cucumber/saccountprodotherfeatures"
        }
)public class SAccountProdDesktopRunnerTest {
}
