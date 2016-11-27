package com.jcrew.steps;

import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.TestDataReader;
import com.jcrew.utils.Util;

import cucumber.api.java.en.Then;
import org.openqa.selenium.WebDriverException;

import java.util.Arrays;
import java.util.List;


public class OmnitureSteps extends DriverFactory{
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
}
