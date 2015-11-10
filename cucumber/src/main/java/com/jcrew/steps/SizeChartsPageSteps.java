package com.jcrew.steps;

import com.jcrew.page.SizeChartsPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;

import static org.junit.Assert.assertTrue;

public class SizeChartsPageSteps extends DriverFactory {
    private final SizeChartsPage sizeChart = new SizeChartsPage(getDriver());


    @And("^Verify user is on size charts page$")
    public void verify_user_is_on_size_charts_page() throws Throwable {
        assertTrue("User should have been in size chart page", sizeChart.isSizeChartPage());
    }


}