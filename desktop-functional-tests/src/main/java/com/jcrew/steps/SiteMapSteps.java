package com.jcrew.steps;

import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.omniture.SiteMaps;
import cucumber.api.java.en.And;
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
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * Created by ngarcia on 12/5/16.
 */
public class SiteMapSteps extends DriverFactory {

    private final Logger logger = LoggerFactory.getLogger(SiteMapSteps.class);
    private final SiteMaps sitemap = new SiteMaps();
    private InputStream sitemapIndexInputStream = null;

    List<String> sitemapList = new ArrayList<>();
    List<String> urlsList = new ArrayList<>();
    List<String> ignoreThisUrls = new ArrayList<>();

    @Given("^User opens stream to ([^\"]*) page$")
    public void userGoesSitemapIndexXmlPage(String page) {
        try {
            sitemapIndexInputStream = new URL(page).openStream();
        } catch (IOException e) {
            logger.error("Not able to open Stream!", e);
        }
    }

    @When("^Select sitemaps to check$")
    public void select_sitemaps_to_check() {
        if (sitemapIndexInputStream != null) {
            sitemapList = sitemap.getSiteMapsToCheck(sitemapIndexInputStream);
        } else {
            logger.error("To use \"Select sitemaps to check\" step you need to initialize the sitemapIndex stream");
        }
    }

    @When("^Select urls to check$")
    public void select_urls_to_check() {
        urlsList = sitemap.getUrlsToCheck(sitemapList);
    }

    @Then("^All pages should contain ([^\"]*) variable$")
    public void allPagesShouldContainSPageNameVariable(String variable) {
        List<String> pagesWithNoVariable = new ArrayList<>();
        try {
            pagesWithNoVariable = sitemap.checkVariableInUrlList(urlsList, variable, ignoreThisUrls);
        } catch (InterruptedException ie) {
            logger.error("Not able to execute step!!");
        }

        if (pagesWithNoVariable.size() > 0) {
            String message = "This pages reported not having variable " + variable + ":\n";
            for (String url : pagesWithNoVariable) {
                message += "<a href=\"" + url + "\" target=\"_blank\">" + url + "</a>\n";
            }

            assertTrue(message, false);
        } else {
            assertTrue(true);
        }
    }

    @And("^Exclude url ([^\"]*) from list$")
    public void excludeUrlFromList(String excludedURL) {
        ignoreThisUrls.add(excludedURL);
    }

    @Then("^All pages should contain the following variables$")
    public void allPagesShouldContainTheFollowingVariables(Map<String, String> variableList) {
        List<String> messages = new ArrayList<>();
        try {
            messages = sitemap.checkVariableInUrlList(urlsList, variableList, ignoreThisUrls);
        } catch (InterruptedException ie) {
            logger.error("Not able to execute step!!");
        }

        if (messages.size() > 0) {
            String reportMessage = "These pages reported not having a variable:\n";
            for (String message : messages) {
                reportMessage += message + "\n";
            }

            assertTrue(reportMessage, false);
        } else {
            assertTrue(true);
        }
    }

    @And("^Include url ([^\"]*) to list$")
    public void includeUrlHttpsWwwJcrewComPToList(String url) {
        urlsList.add(url);
    }

}
