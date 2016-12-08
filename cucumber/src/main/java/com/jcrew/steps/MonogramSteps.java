package com.jcrew.steps;



import com.jcrew.page.Monogram;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by nadiapaolagarcia on 9/7/16.
 */
public class MonogramSteps extends DriverFactory {
    private Monogram monogram = new Monogram(getDriver());

    @When("User fills monogram options")
    public void fill_options() {
        monogram.selectStampStyle();
        monogram.selectLetters();
        monogram.selectRandomThreadColor();
        monogram.save();
    }

    @When("User select monogram options")
    public void select_options() {
        monogram.selectStampStyle();
        monogram.selectLetters();
    }

    @When("User saves monogram options")
    public void save_options() {
        monogram.save();
    }

    @Then("Verify monogram view is displayed")
    public void monogram_is_displayed() {
        assertTrue("Monogram view is displayed", monogram.isDisplayed());
        assertTrue("Has cancel button", monogram.hasCancelButton());
        assertTrue("Has close button", monogram.hasCloseButton());

    }

    @When("User closes monogram view")
    public void close() {
        monogram.close();
    }

    @When("User cancels monogram view")
    public void cancel() {
        monogram.cancel();
    }

    @Then("Verify help section text is ([^\"]*)")
    public void check_help_text(String expected) {
        String actual = monogram.getMonogramQuestions();

        assertEquals("Help text matches", expected.toLowerCase(), actual.toLowerCase());
    }

    

    

    @Then("Verify monogram style stamps matches list")
    public void verify_available_monogram_styles(List<String> expected) {
        List<String> actual = monogram.getStampStyles();

        assertEquals("Same number of styles", expected.size(), actual.size());

        for (int i = 0; i < expected.size(); i++) {
            String expectedStyle = expected.get(i).toLowerCase();
            String actualStyle = actual.get(i).toLowerCase();

            assertEquals("Expected stamp style", expectedStyle, actualStyle);
        }
    }
}

