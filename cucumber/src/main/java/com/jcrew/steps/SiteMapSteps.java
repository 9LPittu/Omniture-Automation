package com.jcrew.steps;

import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.When;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nadiapaolagarcia on 3/22/16.
 */
public class SiteMapSteps extends DriverFactory {

    private final SiteMapsPage sitemap = new SiteMapsPage(getDriver());

    List<String> pagesToCheck = new ArrayList<String>();

    @When("^Select pages to check$")
    public void select_pages_to_check(){
        pagesToCheck = sitemap.getUrlsToCheck();
    }
}
