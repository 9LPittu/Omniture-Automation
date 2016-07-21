package com.jcrew.steps;

import com.jcrew.page.Footer;
import com.jcrew.page.HomePage;
import com.jcrew.pojo.Country;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.Then;

import static org.junit.Assert.*;

/**
 * Created by nadiapaolagarcia on 3/30/16.
 */
public class HomePageSteps extends DriverFactory{
    HomePage homePage = new HomePage(getDriver());

    @Then("Verify user is in homepage")
    public void verify_user_is_in_hompage(){
        assertTrue("user is in homepage",homePage.isHomePage());
    }

    @Then("Verify user is in international homepage")
    public void verify_user_is_in_international_hompage(){
        Country country = homePage.country;
        String countryName = country.getName();

        assertTrue("user is in homepage", homePage.isHomePage());
        assertTrue("Homepage url contains expected country code", homePage.verifyURL());

        Footer footer = new Footer(getDriver());
        assertEquals("Homepage footer matches expected country",
                countryName.toLowerCase(),
                footer.getCountry().toLowerCase());
    }
}
