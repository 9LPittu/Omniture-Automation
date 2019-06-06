package com.jcrew.steps.header;

import com.jcrew.page.header.TopNav;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.TestDataReader;
import com.jcrew.utils.Util;
import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.formatter.model.DataTableRow;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by ngarcia on 3/2/17.
 */
public class TopNavSteps extends DriverFactory {
    private TopNav topNav = new TopNav(getDriver());
    private TestDataReader reader = TestDataReader.getTestDataReader();

    @Then("Verify that top nav is visible in page")
    public void verify_top_nav_visible() {
        assertTrue("Top nav is displayed in current page", topNav.isDisplayed());
    }

    @When("^User clicks on ([^\"]*) link from top nav$")
    public void click_on_given_link_from_top_nav(String Dept) {
        if (Dept.equalsIgnoreCase("random") || Dept.equalsIgnoreCase("any"))
            Dept = reader.getCategory().toLowerCase().trim();

        topNav.clickDeptLinkFromTopNav(Dept);
    }

    @Then("Verify that top nav options contains ([^\"]*)")
    public void verify_top_nav_contains(String option) {
        option = option.toLowerCase();
        List<String> options = topNav.getTopNavOptions();

        if (!options.contains(option)) {
            for (String optionList : options)
                System.out.println("Entry: " + optionList);
        }

        assertTrue("Options in topnav contains " + option, options.contains(option));
    }

    @Then("Verify that top nav contains less or equal to (\\d+) options")
    public void verify_top_nav_contains_less(int allowedOptions) {
        List<String> options = topNav.getTopNavOptions();

        assertTrue("Page contains " + allowedOptions + " or less Options in topnav",
                options.size() <= allowedOptions);
    }

    @When("User hovers on a random category from list")
    public void user_hovers_on_random_category_from_list(List<String> categories) {
        topNav.hoverCategory(categories);
    }

    @When("User hovers on a random category and subcategory from list")
    public void user_hovers_on_random_category_from_list(DataTable categories) {
    	List<DataTableRow> row = categories.getGherkinRows();
        DataTableRow selectedRow = row.get(Util.randomIndex(row.size()));
        topNav.hoverCategory(selectedRow);
    	//topNav.hoverCatAndSubCat();
    }

    @When("User hovers on any random category")
    public void user_hovers_on_any_random_category() {
        String Category = reader.getCategory().toLowerCase().trim();
        topNav.hoverCategory(Category);
    }

    @When("User hovers on ([^\"]*) category from header")
    public void user_hovers_category_from_header(String category) {
        topNav.hoverCategory(category);
    }


    @When("User selects a random feature page from list")
    public void select_feature_page(DataTable features) {
        List<DataTableRow> row = features.getGherkinRows();
        DataTableRow selectedRow = row.get(Util.randomIndex(row.size()));

        String category = selectedRow.getCells().get(0);
        String featureName = selectedRow.getCells().get(1);

        topNav.hoverCategory(category, featureName);
    }
}
