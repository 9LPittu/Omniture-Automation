package com.jcrew.steps;

import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by nadiapaolagarcia on 3/22/16.
 */
public class SiteMapSteps extends DriverFactory {

    private final SiteMapsPage sitemap = new SiteMapsPage(getDriver());

    List<String> sitemapList = new ArrayList<String>();
    List<String> urlsList = new ArrayList<String>();

    @When("^Select sitemaps to check$")
    public void select_pages_to_check(){
        sitemapList = sitemap.getSiteMapsToCheck();

    }

    @When("^Select urls to check$")
    public void select_urls_to_check(){
        urlsList = sitemap.getUrlsToCheck(sitemapList);

    }

    @Then("^All pages should contain ([^\"]*) variable$")
    public void allPagesShouldContainSPageNameVariable(String variable) {
        assertTrue(false);
    }
}
