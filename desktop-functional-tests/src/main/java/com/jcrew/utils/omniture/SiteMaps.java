package com.jcrew.utils.omniture;

import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.PropertyReader;
import com.jcrew.utils.Util;
import com.thoughtworks.xstream.XStream;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ngarcia on 12/5/16.
 */
public class SiteMaps {

    private final Logger logger = LoggerFactory.getLogger(SiteMaps.class);
    private XStream xstream = new XStream();
    DriverFactory driverFactory = new DriverFactory();
    WebDriver driver;

    PropertyReader propertyReader = PropertyReader.getPropertyReader();
    String envURL = propertyReader.getProperty("url");

    public SiteMaps() {
    }

    public List<String> getSiteMapsToCheck(InputStream stream) {
        Pattern countryContextPattern = Pattern.compile("https://www.jcrew.com/sidecar/" +
                "\\p{javaLowerCase}{2}/[\\p{javaLowerCase}{2}\\p{Punct}]*\\p{javaLowerCase}{2}-sitemap.xml");
        Pattern categoryPattern = Pattern.compile("https://www.jcrew.com/sidecar/[\\p{javaLowerCase}/]+\\p{javaLowerCase}+_category/" +
                "\\p{javaLowerCase}+_category_sitemap.xml");
        Pattern specialPattern = Pattern.compile("https://www.jcrew.com/sidecar/[\\p{javaLowerCase}/]+" +
                "\\p{javaLowerCase}+_special_\\p{javaLowerCase}+/\\p{javaLowerCase}+_special_\\p{javaLowerCase}+_sitemap.xml");

        List<String> siteMapsUrls = new ArrayList<>();

        xstream.processAnnotations(SitemapIndex.class);
        SitemapIndex index = (SitemapIndex) xstream.fromXML(stream);
        List<SiteMap> siteMapList = index.getSitemap();

        for (SiteMap siteMap : siteMapList) {
            String loc = siteMap.getLoc();

            if(loc != null) {
                Matcher countryMatcher = countryContextPattern.matcher(loc);
                Matcher categoryMatcher = categoryPattern.matcher(loc);
                Matcher specialMatcher = specialPattern.matcher(loc);

                if (!countryMatcher.matches() && !categoryMatcher.matches() && !specialMatcher.matches()) {
                    siteMapsUrls.add(loc);
                }
            }

        }

        logger.debug("Found {} sitemaps", siteMapsUrls.size());

        return siteMapsUrls;
    }

    public List<String> getUrlsToCheck(List<String> sitemapList) {
        xstream.processAnnotations(UrlSet.class);
        xstream.ignoreUnknownElements();
        List<String> urlsList = new ArrayList<>();

        try {
            for (String sitemap : sitemapList) {
                logger.debug("Getting URLs from {} map", sitemap);
                InputStream stream = new URL(sitemap).openStream();

                UrlSet set = (UrlSet) xstream.fromXML(stream);

                List<Url> urlsInMap = set.getUrlList();

                if (urlsInMap != null) {
                    for (Url url : urlsInMap) {
                        String urlToVisit = url.getLoc();
                        if (!isProductURL(urlToVisit) && !urlToVisit.equals("https://www.jcrew.com")) {
                            urlsList.add(urlToVisit);
                        }
                    }
                }
            }

        } catch (IOException e) {
            logger.error("Not able to open stream to sitemap");
        }

        logger.debug("Found {} urls", urlsList.size());
        return urlsList;
    }

    public List<String> checkVariableInUrlList(List<String> urlsList, String variable,
                                               List<String> ignoreList) throws InterruptedException {
        driver = driverFactory.getDriver();
        PropertyReader propertyReader = PropertyReader.getPropertyReader();
        String envURL = propertyReader.getProperty("url");

        List<String> urlsWithNoVariableValue = new ArrayList<>();

        for (String url : urlsList) {
            url = url.replace("https://www.jcrew.com", envURL);

            if (isRedirected(url) && ignoreList.contains(driver.getCurrentUrl())) {

                logger.debug("{} is redirecting to a ignored url, skipping", url);

            } else {
                String value = Util.getPageVariableValue(driver, variable);
                if (value == null || value.isEmpty()) {
                    logger.error("{} contains an empty {}", url, variable);
                    urlsWithNoVariableValue.add(url);
                }
            }
        }

        return urlsWithNoVariableValue;
    }

    public List<String> checkVariableInUrlList(List<String> urlsList, Map<String, String> variablesMap,
                                               List<String> ignoreList) throws InterruptedException {
        driver = driverFactory.getDriver();
        Set<String> variables = variablesMap.keySet();

        List<String> resultMessages = new ArrayList<>();

        for (String url : urlsList) {
            url = url.replace("https://www.jcrew.com", envURL);

            if (isRedirected(url) && ignoreList.contains(driver.getCurrentUrl())) {

                logger.debug("{} is redirecting to a ignored url, skipping", url);

            } else {
                Map<String, String> actualValues = Util.getPageVariablesValue(driver, variables);

                for (String variable : variables) {
                    String expectedValue = variablesMap.get(variable);
                    String actualValue = actualValues.get(variable);

                    if (actualValue == null || actualValue.isEmpty()) {
                        logger.error("{} contains an empty {}", url, variable);
                        resultMessages.add(url + "has an empty " + variable);
                    } else if (!"any".equals(expectedValue) && !actualValue.contains(expectedValue)) {
                        logger.error("{} contains an unexpected value in {} its values is {}", url, variable, actualValue);
                        resultMessages.add(url + " reported value <" + actualValue +
                                "> and does not contain <" + expectedValue + ">");
                    }
                }
            }
        }

        return resultMessages;

    }

    private boolean isRedirected(String url) {
        driver.get(url);
        String destination = driver.getCurrentUrl();
        return !url.equals(destination);
    }

    private boolean isProductURL(String urlToVisit) {
        boolean isProduct = urlToVisit.contains("PRDOVR~")
                || urlToVisit.contains("PRD~")
                || urlToVisit.contains("/p/");

        return isProduct;
    }
}
