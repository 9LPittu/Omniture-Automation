package com.jcrew.steps;

import com.jcrew.page.HomePage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;

public class HomePageSteps extends DriverFactory {

    private HomePage homePage = new HomePage(driver);

    @When("^Selects a category$")
    public void selects_a_category() {
        homePage.selects_a_category();
    }


    @Then("^JCrew Logo is present$")
    public void jcrew_logo_is_present() throws Throwable {
        assertTrue("JCrew logo must be present", homePage.isJCrewLogoPresent());
    }


    @Then("^Department Links are present$")
    public void department_links_are_present() throws Throwable {
        assertTrue("Women should be present", homePage.isWomenDepartmentPresent());
        assertTrue("Men should be present", homePage.isMenDepartmentPresent());
        assertTrue("Boys should be present", homePage.isBoysDepartmentPresent());
        assertTrue("Girls should be present", homePage.isGirlsDepartmentPresent());
    }
}
