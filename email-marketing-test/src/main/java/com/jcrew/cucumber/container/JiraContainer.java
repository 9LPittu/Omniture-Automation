package com.jcrew.cucumber.container;

import com.jcrew.helper.BrowserDriver;
import org.apache.commons.collections.map.HashedMap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Venkat on 06-09-2017.
 */
public class JiraContainer {
    @FindBy(how = How.ID, using = "login-form-username")
    public WebElement username;

    @FindBy(how = How.ID, using = "login-form-password")
    public WebElement password;

    @FindBy(how = How.ID, using = "login") 
    public WebElement login;

    @FindBy(how = How.XPATH, using = "//*[@title='View and manage your dashboards']")
    public WebElement menu_dashboard;

    @FindBy(how = How.ID, using = "quickSearchInput")
    public WebElement quickSearch;

    @FindBy(how = How.XPATH, using = "//span[@id='status-val']/span")
    public WebElement ticketStatus;

    @FindBy(how = How.XPATH, using = "//ul[@id='issuedetails']//li")
    public List<WebElement> jiraDefaultsFileds;

    @FindBy(how = How.XPATH, using = "//div[@id='customfieldmodule']/div/div/ul//li")
    public List<WebElement> jiraCustomFields;

    @FindBy(how = How.XPATH, using = "//ol[@id='attachment_thumbnails']//li")
    public List<WebElement> downloadAttachments;

    public WebElement getJiraFieldElement(String field) {
        return BrowserDriver.getCurrentDriver().findElement(By.xpath("//strong[text()='" + field + "']/../div[1]"));
    }

    public WebElement getDashboardTableElement(String dashboard) {
        if (dashboard.toUpperCase().contains("J.CREW")) {
            BrowserDriver.getCurrentDriver().switchTo().frame(BrowserDriver.getCurrentDriver().findElement(By.cssSelector("iframe[id='gadget-17913']")));
        } else if (dashboard.toUpperCase().contains("MADEWELL")) {
            BrowserDriver.getCurrentDriver().switchTo().frame(BrowserDriver.getCurrentDriver().findElement(By.cssSelector("iframe[id='gadget-17911']")));
        } else if (dashboard.toUpperCase().contains("FACTORY")) {
            BrowserDriver.getCurrentDriver().switchTo().frame(BrowserDriver.getCurrentDriver().findElement(By.cssSelector("iframe[id='gadget-17912']")));
        }
        WebElement dashElement = BrowserDriver.getCurrentDriver().findElement(By.xpath("//a[contains(text(),'" + dashboard + "')]/ancestor::div[last()]"));
        return dashElement;
    }

    public Map<String, Set<String>> getBusinessEmailTicketsFromAllDays(String dashboard) {
        Map<String, Set<String>> tickets = new HashMap<>();

        List<WebElement> alldays = getDashboardTableElement(dashboard).findElements(By.xpath("//table/tbody//tr/td[@class !='dayOfWeekTitle' and not(contains(@class ,'otherMonth'))]/div[@class='date']"));
        BrowserDriver.getCurrentDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        for (WebElement day : alldays) {
            Set<String> businessTickets = new HashSet<>();
            String data = "";
            try {
                List<WebElement> allTickets = day.findElements(By.xpath("//parent::td/div[text()='" + day.getText() + "']/../div[@class='issues']/a"));
                data = "day: " + day.getText();
                for (WebElement ticket : allTickets) {
                    String link = ticket.getAttribute("href");
                    boolean isBusinessTicket = true;
                    if (isBusinessTicket) {
                        businessTickets.add(link);
                        data = data + "," + link;
                    }
                }
            } catch (Exception e) {
            }
            String dayDate = day.getText();
            System.out.println("Total Data::: " + data);
            tickets.put(dayDate, businessTickets);
        }
        BrowserDriver.getCurrentDriver().switchTo().defaultContent();
        BrowserDriver.setImplicitWaitToDefault();
        return tickets;
    }

    public WebElement getDashboard(String dashboard) {
        WebElement dashboardElement = null;
        if (dashboard.equalsIgnoreCase("ECOMD QA Dashboard")) {
            dashboardElement = BrowserDriver.getCurrentDriver().findElement(By.xpath("//ul[@id='dashboard_link_main']//li/a[text()='ECOMD QA Dashboard']"));
        } else if (dashboard.equalsIgnoreCase("Branding Email Delivery Dash")) {
            dashboardElement = BrowserDriver.getCurrentDriver().findElement(By.xpath("//ul[@id='dashboard_link_main']//li/a[text()='Branding Email Delivery Dash']"));
        }
        return dashboardElement;
    }

    public Map<String, String> getTicketFields() {
        Map<String, String> jiraFields = new HashMap<>();
        BrowserDriver.getCurrentDriver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        for (int i = 1; i <= jiraDefaultsFileds.size(); i++) {
            try {
                String fieldName = BrowserDriver.getCurrentDriver().findElement(By.xpath("//ul[@id='issuedetails']/li[" + i + "]/div/strong")).getText();
                String fieldValue = BrowserDriver.getCurrentDriver().findElement(By.xpath("//ul[@id='issuedetails']/li[" + i + "]/div/span")).getText();
                jiraFields.put(fieldName, fieldValue);
            } catch (Exception e) {
            }
        }
        for (int i = 1; i <= jiraCustomFields.size(); i++) {
            String fieldName = "";
            String fieldValue = "";
            try {
            	
                fieldName = BrowserDriver.getCurrentDriver().findElement(By.xpath("//div[@id='customfieldmodule']/div/div/ul/li[" + i + "]/div/strong")).getText();
                //System.out.println(" Field Name is.."+fieldName);
                try {
                	 //Thread.sleep(8000);
                	 /*fieldValue = BrowserDriver.getCurrentDriver().findElement(By.xpath("//strong[@title='"+fieldName+"']/../div")).getText();
                     String fieldValue11 = BrowserDriver.getCurrentDriver().findElement(By.xpath("//strong[@title='"+fieldName+"']/../div")).getAttribute("value");
                     String fieldValue12 = BrowserDriver.getCurrentDriver().findElement(By.xpath("//strong[@title='"+fieldName+"']/../div")).getAttribute("title");*/
                    
                	 fieldValue = BrowserDriver.getCurrentDriver().findElement(By.xpath("//div[@id='customfieldmodule']/div/div/ul//li[" + i + "]/div/div")).getText();
                     //System.out.println(" Field Value is.."+fieldValue);
                    
                    if(fieldName.equals("Pre-Header"))
                    {
                    	System.out.println(i);
                    	fieldValue = BrowserDriver.getCurrentDriver().findElement(By.xpath("//strong[@title='Pre-Header']/../div")).getText();
                    }
                    
                } catch (Exception e) {	
                    //TO DO
                    try {
                        fieldValue = BrowserDriver.getCurrentDriver().findElement(By.xpath("//div[@id='customfieldmodule']/div/div/ul//li[" + i + "]/div/div")).getText();
                    } catch (Exception ee) {
                        try {
                            if (fieldName.contains("Source Code")) {
                                fieldValue = BrowserDriver.getCurrentDriver().findElement(By.xpath("//strong[@title='Source Code']/../div")).getText();
                            }
                        } catch (Exception eee) {            
                            continue;
                        }
                    }
                }
                jiraFields.put(fieldName, fieldValue);
            } catch (Exception e) {

            }
        }
        BrowserDriver.setImplicitWaitToDefault();
        return jiraFields;
    }

    public Map<String, String> getDownloadsAttachmentsData() {
        Map<String, String> downloadsData = new HashMap<>();
        for (WebElement attachment : downloadAttachments) {
            String downloads = attachment.getAttribute("data-downloadurl");
            String temp[] = downloads.split(":", 3);
            String fileName = temp[1];
            String fileUrl = temp[2];
            boolean isFileAttachedByTester = (fileName.toUpperCase().contains("ISSUE")) || (fileName.equalsIgnoreCase(EmailMarketingPOJO.getJiraTicket() + ".zip"));
            if (!isFileAttachedByTester) {
                downloadsData.put(fileName, fileUrl);
            }
        }
        return downloadsData;
    }
}
