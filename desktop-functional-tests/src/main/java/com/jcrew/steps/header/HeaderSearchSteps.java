package com.jcrew.steps.header;

import com.jcrew.page.header.HeaderSearch;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.TestDataReader;
import com.jcrew.utils.Util;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by ngarcia on 3/2/17.
 */
public class HeaderSearchSteps extends DriverFactory {
    private HeaderSearch search = new HeaderSearch(getDriver());

    @When("User clicks on search using header")
    public void user_clicks_search_from_header(){
        search.clickSearch();
    }

    @When("User searches for a random term from list")
    public void user_searches_for_random_term(List<String> terms) {
        int index = Util.randomIndex(terms.size());
        String term = terms.get(index);

        search.searchFor(term);
    }

    @When("User searches for the item ([^\"]*)")
    public void user_enters_search_item(String item) {
        search.searchFor(item);
    }

    @When("User searches specific term ([^\"]*)")
    public void user_search_for_term(String searchTerm) {
        search.searchForSpecificTerm(searchTerm);
    }

    @When("User searches for a (random|single result|multi result) search term")
    public void user_searches_for_a_search_term(String searchType) {
        TestDataReader testDataReader = TestDataReader.getTestDataReader();
        StateHolder stateHolder = StateHolder.getInstance();

        String term;

        switch (searchType) {
            case "single result":
                term = testDataReader.getData("single.result.search.term");
                break;
            case "multi result":
                term = testDataReader.getData("multi.result.search.term");
                break;
            case "random":
            default:
                term = testDataReader.getSearchWord();
                break;
        }

        user_search_for_term(term);
        stateHolder.put("randomSearchTerm", term);
    }

    @Then("Verify search drawer is (open|closed)")
    public void verify_search_drawer_state(String expectedState) {
        expectedState = expectedState.toLowerCase().trim();
        String actualState = search.getSearchDrawerState().toLowerCase().trim();
        assertEquals("State of search drawer should match",expectedState, actualState );

    }
}
