package com.jcrew.cucumber.view;

import com.cucumber.listener.Reporter;
import com.jcrew.cucumber.container.EmailMarketingPOJO;
import com.jcrew.cucumber.container.JiraContainer;
import com.jcrew.helper.BrowserDriver;
import com.jcrew.helper.PropertyLoader;
import cucumber.api.java.eo.Se;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Venkat on 06-09-2017.
 */
public class JiraView {
    private static JiraContainer jiraContainer;

    public static void loginUser(String username, String password) throws InterruptedException {
        EmailMarketingPOJO.setJiraUsername(username);
        EmailMarketingPOJO.setJiraPassword(password);
        jiraContainer.username.clear();
        jiraContainer.username.sendKeys(username);
        //BrowserDriver.waitForElementToBeClickable(jiraContainer.login, 10);
        //jiraContainer.login.click();
        //BrowserDriver.waitForElement(jiraContainer.password, 20);
        jiraContainer.password.clear();
        jiraContainer.password.sendKeys(password);
        //BrowserDriver.waitForElementToBeClickable(jiraContainer.login, 10);
        Thread.sleep(5000);
        jiraContainer.login.click();
    }

    public static void openTicket(String ticketNumbet) {
        // jiraContainer.quickSearch.sendKeys(ticketNumbet);
        BrowserDriver.loadPage(PropertyLoader.getServerURL() + "/browse/" + ticketNumbet);
    }

    public static void openJiraDashboard(String dashboard) {
        jiraContainer.menu_dashboard.click();
        BrowserDriver.waitForElementToBeClickable(jiraContainer.getDashboard(dashboard), 5);
        jiraContainer.getDashboard(dashboard).click();
    }

    public static Map<String, Set<String>> getBusinessEmailTicketsFromDashboards(String dashboard) {
        return jiraContainer.getBusinessEmailTicketsFromAllDays(dashboard);
    }

    public static void downloadEachAttachment() {
        Map<String, String> downloads = new HashMap<String, String>();
        downloads = jiraContainer.getDownloadsAttachmentsData();
        Set<String> keySet = downloads.keySet();
        for (String key : keySet) {     
        	//if(key.matches(".zip") || key.matches(".xlsx")) {
            String filePath = System.getProperty("user.dir") + File.separator + "testdata" + File.separator + EmailMarketingPOJO.getJiraTicket() + File.separator + key;
            BrowserDriver.getCurrentDriver().get(downloads.get(key));
            String downloadedFilePath = System.getProperty("user.dir") + File.separator + "temp" + File.separator + key;
            File downloadedFile = new File(downloadedFilePath);
            waitForFileToBePresent(downloadedFile, 60);
            if (!downloadedFile.exists()) {
                BrowserDriver.getCurrentDriver().get(downloads.get(key));
            }
            	
            moveFile(downloadedFilePath, filePath);
        	//}
        }
        BrowserDriver.getCurrentDriver().get(PropertyLoader.getServerURL() + "/browse/" + EmailMarketingPOJO.getJiraTicket());
    }

    public static void moveFile(String src, String dest) {
        try {
            File sorce = new File(src);
            File desti = new File(dest);
            waitForFileToBePresent(sorce, 60);
            if (sorce.exists()) {
                FileUtils.moveFile(sorce, desti);
            }
        } catch (Exception e) {
            //TO Do
        }
    }

    public static void waitForFileToBePresent(File file, int timeInSec) {
        for (int i = 1; i < timeInSec; i++) {
            if (file.exists()) {
                break;
            } else {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {

                }
            }
        }
    }

    public static void waitForDashboard() throws InterruptedException {
        // BrowserDriver.waitForElement(jiraContainer.quickSearch);
    	Thread.sleep(5000);
    }

    public static void getJiraData() {
        Map<String, String> jiraFields = new HashMap<String, String>();

        jiraFields = jiraContainer.getTicketFields();
        Set<String> keySet = jiraFields.keySet();
        for (String key : keySet) {
            String formatedKey = key.replace(" ", "").replace(":", "").replace("?", "").replace("-", "").toUpperCase().trim();
            switch (formatedKey) {
                case "TYPE":
                    EmailMarketingPOJO.setType(jiraFields.get(key));
                    break;
                case "STATUS":
                    EmailMarketingPOJO.setStatus(jiraFields.get(key));
                    break;
                case "PRIORITY":
                    EmailMarketingPOJO.setPriority(jiraFields.get(key));
                    break;
                case "RESOLUTION":
                    EmailMarketingPOJO.setResolution(jiraFields.get(key));
                    break;
                case "LABELS":
                    EmailMarketingPOJO.setLabels(jiraFields.get(key));
                    break;
                case "EMAILSEGMENT":
                    EmailMarketingPOJO.setEmailSegment(jiraFields.get(key));
                    break;
                case "COUNTRY":
                    EmailMarketingPOJO.setCountry(jiraFields.get(key));
                    break;
                case "SUBJECTLINE":
                    EmailMarketingPOJO.setSubjectLine(jiraFields.get(key).replace("*FINAL SL*", "").replace("FINAL SL:", "").replace("â€™", "'").replace("â€œ", "\"").replace("â€�", "\"").replace("\n", "").trim());
                    break;
                case "PREHEADER":
                    EmailMarketingPOJO.setPreHeader(jiraFields.get(key).replace("*FINAL PH*", "").replace("â€™", "'").replace("â€œ", "\"").replace("â€�", "\"").replace("\n", "").replace("â€¦", "...").replace("**", ""));
                    break;
                case "PREHEADERLINK":
                    EmailMarketingPOJO.setPreHeaderLink(jiraFields.get(key));
                    break;
                case "MANAGEDEXPOSURE":
                    EmailMarketingPOJO.setManagedExposure(jiraFields.get(key));
                    break;
                case "LINKEDTOMONETATE":
                    EmailMarketingPOJO.setLinkedtoMonetate(jiraFields.get(key));
                    break;
                case "CAMPAIGN":
                    EmailMarketingPOJO.setCampaign(jiraFields.get(key));
                    break;
                case "ADDITIONALLINKS":
                    EmailMarketingPOJO.setAdditionalLinks(jiraFields.get(key));
                    break;
                case "BANNER":
                    EmailMarketingPOJO.setBanner(jiraFields.get(key));
                    break;
                case "DISCLAIMERCOPY":
                    EmailMarketingPOJO.setDisclaimerCopy(jiraFields.get(key).replace("â€™", "'").replace("â€œ", "\"").replace("â€�", "\"").replace("\n", ""));
                    break;
                case "SOURCECODE":
                    String src = jiraFields.get(key);
                    String sourceCode = "";
                    if (src != null || src != "") {
                        src = src.replace("\t\n", " ").replace("\n", " ");
                        String[] temp = src.split(" ");
                        for (String srcCode : temp) {
                            boolean srcLengthCheck = srcCode.length() == 9 || srcCode.length() == 11;
                            boolean srcTxtCheck = srcCode.contains("EMSL") || srcCode.contains("FAEMSL") || srcCode.contains("EMIT");
                            if (srcTxtCheck && srcLengthCheck) {
                                sourceCode = srcCode;
                                break;
                            }
                        }
                        System.out.println("Source code is: " + sourceCode);
                        EmailMarketingPOJO.setSourceCode(sourceCode);
                    }
                    break;
                case "SPECIALNOTES":
                    EmailMarketingPOJO.setSpecialNotes(jiraFields.get(key));
                    break;
                case "TESTINGPROCEDURE":
                    EmailMarketingPOJO.setTestingProcedure(jiraFields.get(key));
                    break;
                case "SUBJECTLINE2":
                    EmailMarketingPOJO.setSubjectLine2(jiraFields.get(key).replace("FINAL SL:", "").trim());
                    break;
                case "ALTERNATEDISCLAIMER":
                    EmailMarketingPOJO.setAlternateDisclaimer(jiraFields.get(key));
                    break;
                case "BANNERNAME":
                    EmailMarketingPOJO.setType(jiraFields.get(key));
                    break;
                case "MONETATEBANNER":
                    EmailMarketingPOJO.setType(jiraFields.get(key));
                    break;
                case "PROMOCODE":
                    EmailMarketingPOJO.setType(jiraFields.get(key));
                    break;
                case "PROOFLAUNCHED":
                    EmailMarketingPOJO.setType(jiraFields.get(key));
                    break;
            }
        }
    }

    public static File getExcelFile(String dirName) {
        File[] files = null;
        try {
            File dir = new File(dirName);
            files = dir.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String filename) {
                    return filename.endsWith(".xlsx");
                }
            });
            return files[0];
        } catch (Exception e) {
            Reporter.addStepLog("Excel file attachment is not available in Jira ticket, could not verify links");
            return null;
        }
    }

    public static File[] getAllJpgFiles(String dirName) {
        File[] files = null;
        try {
            File dir = new File(dirName);
            files = dir.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String filename) {
                    return filename.endsWith(".jpg");
                }
            });
            return files;
        } catch (Exception e) {
            Reporter.addStepLog("Image file attachment is not available in Jira ticket, could not verify links");
            return null;
        }
    }

    public static boolean isTicketAssignedToQa(String tktUrl) {
        boolean isAssignedToQa = false;
        BrowserDriver.getCurrentDriver().get(tktUrl);
        String status = jiraContainer.ticketStatus.getText().trim();
        if (status.equalsIgnoreCase("Pending Functional Testing")) {
            isAssignedToQa = true;
        }
        return isAssignedToQa;
    }

    public static void init() {
        jiraContainer = PageFactory.initElements(BrowserDriver.getCurrentDriver(), JiraContainer.class);
    }
}
