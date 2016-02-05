package com.jcrew.runner.regression;

/**
 * Created by 9hvenaga on 2/5/2016.
 */

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"features/regression"},
        tags = {"@@BreadCrumbs"},
        glue = {"com.jcrew.steps"},
        format = {
                "json:target/cucumber-breadcrumbregressionfeatures.json",
                "pretty",
                "html:target/cucumber/breadcrumbregressionfeatures"
        }
)

public class BreadCrumbsRunnerTest{
}
