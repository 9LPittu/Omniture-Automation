package com.jcrew.runner.qa;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features/qa",
        tags = {"@Wishlist"},
        glue = {"com.jcrew.steps"},
        format = {"json:target/cucumber-wishlistfeatures.json",
                "pretty",
                "html:target/cucumber/wishlistfeatures"}
)


public class WishlistFeaturesRunnerTest {

}
