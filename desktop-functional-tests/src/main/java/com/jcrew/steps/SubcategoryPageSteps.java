package com.jcrew.steps;

import com.jcrew.page.*;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.StateHolder;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SubcategoryPageSteps extends DriverFactory {

	private final Logger logger = LoggerFactory.getLogger(SubcategoryPageSteps.class);

    private final SubcategoryPage subcategoryPage = new SubcategoryPage(getDriver());
    private final StateHolder stateHolder = StateHolder.getInstance();
    
    @And("^user selects any item from array page, select any color and size$")
    public void user_selects_any_item_from_array_page_select_any_size_color(){
    	subcategoryPage.selectRandomItemAndSelectSizeColor();
    }
}