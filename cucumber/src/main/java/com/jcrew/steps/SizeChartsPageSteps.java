package com.jcrew.steps;

import com.jcrew.page.SizeChartsPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;

import static org.junit.Assert.assertTrue;

/**
 * Created by 9hvenaga on 9/1/2015.
 */
public class SizeChartsPageSteps extends DriverFactory {
    SizeChartsPage sizeChart = new SizeChartsPage(getDriver());


    @And("^Verify user is on size charts page$")
    public void verify_user_is_on_size_charts_page() throws Throwable {
        assertTrue("User should have been in size chart page", sizeChart.isSizeChartPage());
    }


}