package com.jcrew.steps;

import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by nadiapaolagarcia on 3/22/16.
 */
public class SiteMapSteps extends DriverFactory {

    private final Logger logger = LoggerFactory.getLogger(StartingSteps.class);
    private final SiteMapsPage sitemap = new SiteMapsPage();
    private InputStream sitemapIndexInputStream = null;

    List<String> sitemapList = new ArrayList<>();
    List<String> urlsList = new ArrayList<>();

    @Given("^User opens stream to ([^\"]*) page$")
    public void userGoesSitemapIndexXmlPage(String page) {
        try {
            sitemapIndexInputStream = new URL(page).openStream();
        } catch (IOException e) {
            logger.error("Not able to open Stream!", e);
        }
    }

    @When("^Select sitemaps to check$")
    public void select_sitemaps_to_check(){
        if(sitemapIndexInputStream != null) {
            sitemapList = sitemap.getSiteMapsToCheck(sitemapIndexInputStream);
        } else {
            logger.error("To use \"Select sitemaps to check\" step you need to initialize the sitemapIndex stream");
        }
    }

    @When("^Select urls to check$")
    public void select_urls_to_check(){
        urlsList = sitemap.getUrlsToCheck(sitemapList);
    }

    @Then("^All pages should contain ([^\"]*) variable$")
    public void allPagesShouldContainSPageNameVariable(String variable) {
        List<String> pagesWithNoVariable = new ArrayList<>();
        try {
            pagesWithNoVariable = sitemap.checkVariableInUrlList(urlsList, variable);
        } catch (InterruptedException ie){
            logger.error("Not able to execute step!!");
        }

        if(pagesWithNoVariable.size() > 0){
            String message = "This pages reported not having variable " + variable + ":\n";
            for(String url : pagesWithNoVariable){
                message += url + "\n";
            }

            assertTrue(message, false);
        } else{
            assertTrue(true);
        }
    }
}
