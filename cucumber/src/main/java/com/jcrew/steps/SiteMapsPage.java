package com.jcrew.steps;

import com.jcrew.omniture.SiteMap;
import com.jcrew.omniture.Sitemapindex;
import com.jcrew.omniture.Url;
import com.jcrew.omniture.UrlSet;
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
        xstream.processAnnotations(Sitemapindex.class);
        Pattern countryContextPattern = Pattern.compile("https://www.jcrew.com/" +
                "\\p{javaLowerCase}{2}/[\\p{javaLowerCase}{2}\\p{Punct}]*\\p{javaLowerCase}{2}-sitemap.xml");
        List<String> siteMapsUrls = new ArrayList<>();

        String source = driver.getPageSource();

        Sitemapindex index = (Sitemapindex) xstream.fromXML(source);
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
            logger.debug("Checking {} map", sitemap);
            driver.get(sitemap);
            String source = driver.getPageSource();

            UrlSet set = (UrlSet) xstream.fromXML(source);

            List<Url> urlsInMap = set.getUrlList();
            logger.debug("Found {} urls", urlsInMap.size(), sitemap);

            for (Url url:urlsInMap) {
                logger.debug(url.getLoc());
            }
        }

        return urlsList;
    }
}
