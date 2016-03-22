package com.jcrew.steps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nadiapaolagarcia on 3/22/16.
 */
public class SiteMapsPage {

    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(SiteMapsPage.class);

    public SiteMapsPage(WebDriver driver) {
        this.driver = driver;
    }

    public List<String> getUrlsToCheck() {
        List<String> urls_text = new ArrayList<>();
        List<WebElement> urls = driver.findElements(By.xpath("//div[@class='line']/span[@class='text']"));

        for(WebElement url:urls){
            urls_text.add(url.getText());
            logger.debug("Getting url: {}",url.getText());
        }

        return urls_text;
    }
}
