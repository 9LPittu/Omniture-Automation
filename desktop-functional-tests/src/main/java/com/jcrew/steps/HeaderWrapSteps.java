package com.jcrew.steps;

import com.jcrew.page.HeaderWrap;
import com.jcrew.pojo.User;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.Util;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by nadiapaolagarcia on 3/28/16.
 */
public class HeaderWrapSteps extends DriverFactory {
    HeaderWrap header = new HeaderWrap(getDriver());

    @When("User clicks on sign in using header")
    public void click_sign_in() {
        header.clickSignIn();
    }

    @When("User opens menu")
    public void user_opens_menu() {
        header.openMenu();
    }

    @When("User hovers over ([^\"]*)")
    public void user_hovers_over(String icon) {
        header.hoverOverIcon(icon);
    }

    @When("User signs out using header")
    public void sign_out_using_header() {
        header.goToMyDetailsDropDownMenu("Sign Out");
    }

    @When("User goes to ([^\"]*) using header")
    public void user_goes_to_using_header(String item) {
        header.goToMyDetailsDropDownMenu(item);
    }

    @When("User clicks JCrew logo")
    public void user_clicks_jcrew_logo() {
        header.clickLogo();
    }

    @Then("Dropdown should welcome user by first name")
    public void dropdown_should_welcome_user_using_first_name() {
        String expectedWelcomeMessage = "Welcome, " + User.getUser().getFirstName();
        String actualWelcomeMessage = header.getWelcomeMessage();

        assertEquals("First name should match message", expectedWelcomeMessage, actualWelcomeMessage);
    }

    @Then("Verify header contains Sign In visible")
    public void verify_header_contains_sign_in_visible() {
        assertTrue("Header contains a visible sign in", header.isSignInVisible());
    }

    @Then("User searches for specific term ([^\"]*)")
    public void user_searches_for(String term) {
        header.searchFor(term);
    }

    @Then("User searches for a random term from list")
    public void user_searches_for_random_term(List<String> terms) {
        int index = Util.randomIndex(terms.size());
        String term = terms.get(index);

        header.searchFor(term);
    }

    @When("User enters ([^\"]*) to the search field ")
    public void user_enters_search_term(String term) {
        header.searchFor(term);
    }

    @When("User clicks in bag")
    public void user_clicks_in_bag() {
        header.clickBag();
    }

    @When("^User clicks on ([^\"]*) link from top nav$")
    public void click_on_given_link_from_top_nav(String Dept) {
        header.clickDeptLinkFromTopNav(Dept);
    }


}
