package com.jcrew.runner.regression.full;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/regression",
        tags = {"@Wishlist"},
        glue = {"com.jcrew.steps"},
        format = {"json:target/cucumber-wishlistfeatures.json",
                "pretty",
                "html:target/cucumber/wishlistfeatures"}
)

public class WishlistFeaturesRunnerTest {

}
