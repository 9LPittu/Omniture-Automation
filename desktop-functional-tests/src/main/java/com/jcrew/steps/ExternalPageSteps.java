package com.jcrew.steps;

import com.jcrew.page.ExternalPage;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.Then;

import static org.junit.Assert.assertTrue;

/**
 * Created by 9msyed on 11/22/2016.
 */
public class ExternalPageSteps extends DriverFactory {
    private ExternalPage page = new ExternalPage(getDriver());

    @Then("Verify user is in external page url contains ([^\"]*)")
    public void external_page(String page_url) {
        String current_url = getDriver().getCurrentUrl();
        assertTrue("Current page url contains" + page_url + " and current url is " + current_url,
                current_url.contains(page_url));
    }


    @Then("^Verify user is navigated to url ([^\"]*) on external page$")
    public void external_page_different_tab(String page_url) {
    	if (page_url.contains("spotify")) {
    		page_url = "https://open.spotify.com/user/jcrew" ; // current page url is redirected to new url
    	}
        page.changeToNewTab(1);
        external_page(page_url);
        page.closeTab(1);
    }
      
    @Then("^Verify page source contains ([^\"]*)$")
    public void validate_page_source_contains_given_var(String var) {
        assertTrue("page source should contain "+var+"", page.isVariablePresentInSourceCode(var));
    }
}
