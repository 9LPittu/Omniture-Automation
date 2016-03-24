package com.jcrew.steps;

import com.jcrew.omniture.SiteMap;
import com.jcrew.omniture.SitemapIndex;
import com.jcrew.omniture.Url;
import com.jcrew.omniture.UrlSet;
import com.jcrew.util.Util;
import com.thoughtworks.xstream.XStream;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nadiapaolagarcia on 3/22/16.
 */

public class SiteMapsPage {

    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(SiteMapsPage.class);
    private XStream xstream = new XStream();

    public SiteMapsPage(WebDriver driver) {
        this.driver = driver;
    }

    public List<String> getSiteMapsToCheck() {
        xstream.processAnnotations(SitemapIndex.class);
        Pattern countryContextPattern = Pattern.compile("https://www.jcrew.com/" +
                "\\p{javaLowerCase}{2}/[\\p{javaLowerCase}{2}\\p{Punct}]*\\p{javaLowerCase}{2}-sitemap.xml");
        List<String> siteMapsUrls = new ArrayList<>();

        String source = driver.getPageSource();

        SitemapIndex index = (SitemapIndex) xstream.fromXML(source);
        List<SiteMap> siteMapList = index.getSitemap();

        for (SiteMap siteMap: siteMapList) {
            String loc = siteMap.getLoc();
            Matcher countryMatcher = countryContextPattern.matcher(loc);

            if(!countryMatcher.matches()){
                siteMapsUrls.add(loc);
            }

        }

        logger.debug("Found {} sitemaps", siteMapsUrls.size());

        return siteMapsUrls;
    }

    public List<String> getUrlsToCheck(List<String> sitemapList) {
        xstream.processAnnotations(UrlSet.class);
        xstream.ignoreUnknownElements();
        List<String> urlsList = new ArrayList<>();

        for(String sitemap:sitemapList){
            logger.debug("Getting URLs from {} map", sitemap);
            driver.get(sitemap);
            String source = driver.getPageSource();

            UrlSet set = (UrlSet) xstream.fromXML(source);

            List<Url> urlsInMap = set.getUrlList();
            List<String> productURL = new ArrayList<>();

            if(urlsInMap != null) {
                for (Url url : urlsInMap) {
                    String urlToVisit = url.getLoc();
                    if(urlToVisit.contains("PRDOVR~") || urlToVisit.contains("PRD~")){
                        productURL.add(urlToVisit);
                    } else {
                        urlsList.add(urlToVisit);
                    }
                }

                //random product URL
                if(productURL.size() > 0) {
                    urlsList.add(productURL.get(Util.randomIndex(productURL.size())));
                }
            }
        }

        logger.debug("Found {} urls", urlsList.size());
        return urlsList;
    }

    public List<String> checkVariableInUrlList(List<String> urlsList, String variable) {
        List<String> urlsWithNoVariableValue = new ArrayList<>();

        for(String url:urlsList){
            //TODO replace the url with the environment under test
            driver.get(url);
            Util.waitForPageFullyLoaded(driver);
            String value = Util.getPageVariableValue(driver,variable);
            if(value == null || value.isEmpty()){
                logger.error("{} contains an empty {}", url, variable);
                urlsWithNoVariableValue.add(url);
            }
        }

        return urlsWithNoVariableValue;
    }
}
