package com.jcrew.steps;

import com.jcrew.page.ArraySearch;
import com.jcrew.utils.CurrencyChecker;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.StateHolder;

import cucumber.api.java.en.Then;
        import cucumber.api.java.en.When;

import java.util.List;

import static org.junit.Assert.*;

public class ArraySearchRefinementSteps extends DriverFactory {

    ArraySearch searchArray = new ArraySearch(getDriver());
    StateHolder stateHolder = StateHolder.getInstance();


   
}