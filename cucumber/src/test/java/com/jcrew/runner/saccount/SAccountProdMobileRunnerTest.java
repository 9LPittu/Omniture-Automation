package com.jcrew.runner.saccount;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/saccount"},
        tags = {"@ProdSAccountMobile"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-saccountprodmobileotherfeatures.json",
                "pretty",
                "html:target/cucumber/saccountprodmobileotherfeatures"
        }
)public class SAccountProdMobileRunnerTest {
}
