package com.jcrew.steps;

import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.TestDataReader;
import com.jcrew.utils.Util;

import cucumber.api.java.en.Then;
import org.openqa.selenium.WebDriverException;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class OmnitureSteps extends DriverFactory{
	private TestDataReader testDataReader = TestDataReader.getTestDataReader();
	
	
	@Then("^Verify omniture variables have values$")
    public void verify_omniture_variables() throws InterruptedException {
        TestDataReader testDataReader =  TestDataReader.getTestDataReader();

        String arrOmnitureVariables[] = testDataReader.getDataArray("omniture.variables");
        List<String> omnitureVariables = Arrays.asList(arrOmnitureVariables);

        String emptyVariables = "";
        boolean isblank=false;
        for (String omnitureVariable:omnitureVariables) {
            String omnitureValue = Util.getPageVariableValue(getDriver(), omnitureVariable);
            if (omnitureValue.isEmpty()) {
                emptyVariables = emptyVariables + omnitureVariable + ";";
                isblank = true;
            }
        }

        if (isblank)
            throw new WebDriverException("Omniture variables " + emptyVariables + " does not have a value");
    }
	
	
	@Then("Verify correct value is displayed for the omniture variable ([^\"]*)$")
	public void verify_omniture_variable_value(String variable) throws InterruptedException {
		String expectedValue = testDataReader.getData(variable+".value");
		String actualValue = Util.getPageVariableValue(getDriver(), variable);
		if (expectedValue.equalsIgnoreCase("any")) {
			assertTrue("Omniture variable " + variable  +" should not be empty", !actualValue.isEmpty());
		} else {	
			assertEquals("Omniture variable " + variable  +" value should match", expectedValue, actualValue);
		}
	}
}
