package cucumber.com.jcrew.stepdefs;

import com.cucumber.listener.Reporter;
import com.jcrew.cucumber.container.EmailMarketingPOJO;
import com.jcrew.cucumber.util.ReadExcel;
import com.jcrew.cucumber.view.EmailView;
import com.jcrew.cucumber.view.JiraView;
import com.jcrew.helper.*;
import com.jcrew.helper.mailclient.EmailClient;
import com.jcrew.helper.mailclient.GmailClient;
import com.jcrew.helper.mailclient.YahooMailClient;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.io.File;
import java.util.*;
import java.util.logging.Logger;

import static com.jcrew.cucumber.view.EmailView.getImgText;

public class EmailStepDef {

    private String subjectInEmai;
    private static final Logger LOGGER = Logger.getLogger(EmailStepDef.class.getName());
    public String emailLink;
    @When("^I open email in browser$")
    public void I_open_email_in_browser() throws Throwable {	
        String srcCode = EmailMarketingPOJO.getSourceCode();
        String preHeader = EmailMarketingPOJO.getPreHeader().trim();
        EmailClient emailClient = null;
        String mailClient = PropertyLoader.getEmailClient().trim();
        String mailUserId = PropertyLoader.getEmailUserId().trim();
        String mailPwd = PropertyLoader.getEmailUserPassword().trim();
        System.out.println("preheader is.. "+preHeader);
        if (preHeader == "" || preHeader == null) {
            Assert.fail("Pre-header text is not available in Jira ticket");
        }
        
        if(!mailClient.equalsIgnoreCase("outlook"))
        {
        if (mailClient.equalsIgnoreCase("gmail")) {
        	Thread.sleep(6000);
            emailClient = new GmailClient(mailUserId, mailPwd, preHeader);            
        } else if (mailClient.equalsIgnoreCase("yahoo")) {
            emailClient = new YahooMailClient(mailUserId, mailPwd, preHeader);
        }
        subjectInEmai = emailClient.getEmailSubject();
        emailLink = System.getProperty("user.dir") + File.separator + "testdata" + File.separator + EmailMarketingPOJO.getJiraTicket() + File.separator + EmailMarketingPOJO.getJiraTicket() + "_email.html";

        }
        else if(mailClient.equalsIgnoreCase("outlook")){        
        OutlookEmailClient outlookemailClient = new OutlookEmailClient(preHeader, srcCode);
        subjectInEmai = outlookemailClient.getEmailSubject();
        emailLink = outlookemailClient.emailBodyToFile(EmailMarketingPOJO.getJiraTicket() + "_email".replace("-", "") + ".html", outlookemailClient.getEmailHtmlBody());
        }
        BrowserDriver.loadPage(emailLink);
        EmailMarketingPOJO.setEmailLink(emailLink);
        Reporter.addStepLog("Converted email to html file with the help of OutlookEmailClient");
        Reporter.addStepLog("Opened email in web browser");
    }

    @When("^email \"(.*?)\" in browser$")
    public void openmail(String tckt) throws Throwable {
        BrowserDriver.loadPage("file:///D:/JCrew/Automation/workspace/cucumber-automation/cucumber-automation/testdata/JCBEMAIL-3491/JCBEMAIL-3491_email.html");
        String sub = EmailMarketingPOJO.getSubjectLine();
        OutlookEmailClient emailClient = new OutlookEmailClient(sub, "");
        //subjectInEmai = emailClient.getEmailSubject();
        emailClient.emailBodyToFile(EmailMarketingPOJO.getJiraTicket() + "_email".replace("-", "") + ".html", emailClient.getEmailHtmlBody());
        BrowserDriver.loadPage(System.getProperty("user.dir") + File.separator + "testdata" + File.separator + EmailMarketingPOJO.getJiraTicket() + File.separator + EmailMarketingPOJO.getJiraTicket() + "_email.html");
        Reporter.addStepLog("Converted email to html file with the help of OutlookEmailClient");
        Reporter.addStepLog("Opened email in web browser");
    }

    @Then("^I should see email in browser$")
    public void I_should_see_email() throws Throwable {
        String emailBrowser = EmailView.emailInBrowserLink();
        if ((emailBrowser != null) || (emailBrowser != null)) {
            EmailMarketingPOJO.setEmailLink(emailBrowser);
        } else {
            EmailMarketingPOJO.setEmailLink(System.getProperty("user.dir") + File.separator + "testdata" + File.separator + EmailMarketingPOJO.getJiraTicket() + File.separator + EmailMarketingPOJO.getJiraTicket() + "_email.html");
        }
        BrowserDriver.getCurrentDriver().get(EmailMarketingPOJO.getEmailLink());
    }

    @Then("^I validate \"(.*?)\" in email$")
    public void I_validate_data_in_email(String jiraField) throws Throwable {
        switch (jiraField.toUpperCase().trim()) {
            case "SUBJECT":
                String subjectInEmail = subjectInEmai.replace("Proof Launch: (HTML-only)", "").trim();
                if (subjectInEmail.equalsIgnoreCase(EmailMarketingPOJO.getSubjectLine().trim())) {
                    Reporter.addStepLog("Subject line text is validated and it is as per the Jira ticket");
                } else {
                    Reporter.addStepLog("Subject line text is not as per the Jira ticket");
                    Reporter.addStepLog("Subject line in email is \"" + subjectInEmail + "\" but in Jira it is \"" + EmailMarketingPOJO.getSubjectLine() + "\"");
                    //Assert.fail();
                    GenericMethods.getAssertFails().add("Subject line in email is \"" + subjectInEmail + "\" but in Jira it is \"" + EmailMarketingPOJO.getSubjectLine() + "\"");
                }
                break;
            case "PRE-HEADER":
                String jiraPreHeader = EmailMarketingPOJO.getPreHeader();
                String emailPreheader = EmailView.getPreHeaderText().trim();
                if (!(jiraPreHeader == null || jiraPreHeader == "")) {
                    if (jiraPreHeader.trim().equalsIgnoreCase(emailPreheader)) {
                        Reporter.addStepLog("Pre-Header text is validated and it is as per the Jira ticket");
                    } else {
                        Reporter.addStepLog("Pre-Header text is not as per the Jira ticket");
                        Reporter.addStepLog("Pre-Header text in email is \"" + emailPreheader + "\" but in Jira it is \"" + jiraPreHeader.trim() + "\"");
                        //Assert.fail();
                        GenericMethods.getAssertFails().add("Pre-Header text in email is \"" + emailPreheader + "\" but in Jira it is \"" + jiraPreHeader.trim() + "\"");
                    }
                } else {
                    Reporter.addStepLog("Unable to validate Pre-Header text since it is not available in the Jira ticket");
                    //Assert.fail();
                    GenericMethods.getAssertFails().add("Unable to validate Pre-Header text since it is not available in the Jira ticket");
                }
                break;
            case "PRE-HEADER LINK":
                String jiraPreHeaderLink = EmailMarketingPOJO.getPreHeaderLink();
                String emailPreHeaderLinkOrg = EmailView.getPreHeaderLink();
                BrowserDriver.getCurrentDriver().get(emailPreHeaderLinkOrg);
                if (BrowserDriver.getCurrentDriver().getPageSource().contains("Hmmm... It seems we are unable")) {
                    Reporter.addStepLog("Hmmm... page displayed for pre-header link \"" + emailPreHeaderLinkOrg + "\"" + System.lineSeparator() + "Please check above link manually");
                }
                String emailPreHeaderLink = BrowserDriver.getCurrentDriver().getCurrentUrl();
                if (emailPreHeaderLink.contains("com/in/")) {
                    try {
                        EmailView.changeCountryToUs();
                        Reporter.addStepLog("Changed country to \"United States\"");
                        BrowserDriver.getCurrentDriver().get(emailPreHeaderLinkOrg);
                        emailPreHeaderLink = BrowserDriver.getCurrentDriver().getCurrentUrl();
                    } catch (Exception e) {

                    }
                }
                Reporter.addStepLog("Browser navigated to pre-Header link \"" + emailPreHeaderLink + "\" as expected");
                boolean isPreheaderLinkPresentInJira = !(jiraPreHeaderLink == null || jiraPreHeaderLink == "");
                if (isPreheaderLinkPresentInJira) {
                    if (emailPreHeaderLink.trim().contains(jiraPreHeaderLink.trim())) {
                        if (HttpJcrewClient.isLinkStatusIsOk(emailPreHeaderLink.trim())) {
                            Reporter.addStepLog("Pre-Header link is returning HTTP status code 200 as expected");
                            if (PropertyLoader.getBrokeImgConfig().equalsIgnoreCase("yes")) {
                                LOGGER.info("Downloading all images from current like to validate whether there is any broken image ....");
                                EmailView.validateBrokenImageInLink();
                                Reporter.addStepLog("Broken image validation is done in above link, there is no broken image");
                            }
                        } else {
                            Reporter.addStepLog("Pre-Header link is not returning HTTP status code 200 as expected");
                            //Assert.fail();
                            GenericMethods.getAssertFails().add("Pre-Header link is not returning HTTP status code 200 as expected");
                        }
                        Reporter.addStepLog("Pre-Header link is validated and it is as per the Jira ticket");
                    } else {
                        Reporter.addStepLog("Pre-Header link is not as per the Jira ticket");
                        Reporter.addStepLog("Pre-Header link in email is \"" + emailPreHeaderLink + "\" but in Jira it is \"" + EmailMarketingPOJO.getPreHeaderLink() + "\"");
                        //Assert.fail();
                        GenericMethods.getAssertFails().add("Pre-Header link in email is \"" + emailPreHeaderLink + "\" but in Jira it is \"" + EmailMarketingPOJO.getPreHeaderLink() + "\"");
                    }
                } else {
                    Reporter.addStepLog("Pre-Header link is not available in the Jira ticket fields, so trying retrieving it from excel file ");
                    File xlFile = JiraView.getExcelFile(System.getProperty("user.dir") + "/testdata/" + EmailMarketingPOJO.getJiraTicket());
                    if (xlFile != null) {
                        ReadExcel excelData = new ReadExcel(xlFile.getAbsolutePath());
                        Map<String, String> excelPreheaderLinks = excelData.getPreheaderLinkFromExcelSheet();
                        if (excelPreheaderLinks != null && excelPreheaderLinks.size() != 0) {
                            for (String preHeaderName : excelPreheaderLinks.keySet()) {
                                String countryPreH = excelPreheaderLinks.get(preHeaderName);
                                String jiraCountry = EmailMarketingPOJO.getCountry().trim().toUpperCase();
                                if (preHeaderName.contains("CA") || preHeaderName.contains("US")) {
                                    if (jiraCountry.contains("CA") || jiraCountry.contains("US")) {
                                        EmailMarketingPOJO.setPreHeaderLink(countryPreH);
                                    }
                                }
                                //if (preHeaderName.contains("IXC")) {
                                else {
                                    if (jiraCountry.contains("IXC") || jiraCountry.contains("IXC")) {
                                        EmailMarketingPOJO.setPreHeaderLink(countryPreH);
                                    }
                                }
                            }
                        }
                        boolean isPreheaderLinkPresentInexcel = !(EmailMarketingPOJO.getPreHeaderLink() == null || EmailMarketingPOJO.getPreHeaderLink() == "");
                        if (isPreheaderLinkPresentInexcel) {
                            if (EmailMarketingPOJO.getPreHeaderLink().trim().contains(jiraPreHeaderLink.trim())) {
                                if (HttpJcrewClient.isLinkStatusIsOk(EmailMarketingPOJO.getPreHeaderLink().trim())) {
                                    Reporter.addStepLog("Pre-Header link is returning HTTP status code 200 as expected");
                                    if (PropertyLoader.getBrokeImgConfig().equalsIgnoreCase("yes")) {
                                        LOGGER.info("Downloading all images from current like to validate whether there is any broken image ....");
                                        EmailView.validateBrokenImageInLink();
                                        Reporter.addStepLog("Broken image validation is done in above link, there is no broken image");
                                    }
                                } else {
                                    Reporter.addStepLog("Pre-Header link is not returning HTTP status code 200 as expected");
                                    //Assert.fail();
                                    GenericMethods.getAssertFails().add("Pre-Header link is not returning HTTP status code 200 as expected");
                                }
                                Reporter.addStepLog("Pre-Header link is validated and it is as per the Jira ticket");
                            } else {
                                Reporter.addStepLog("Pre-Header link is not as per the Jira ticket");
                                Reporter.addStepLog("Pre-Header link in email is \"" + emailPreHeaderLink + "\" but in Jira it is \"" + EmailMarketingPOJO.getPreHeaderLink() + "\"");
                                //Assert.fail();
                                GenericMethods.getAssertFails().add("Pre-Header link in email is \"" + emailPreHeaderLink + "\" but in Jira it is \"" + EmailMarketingPOJO.getPreHeaderLink() + "\"");
                            }
                        } else {
                            Reporter.addStepLog("Pre-Header link is not available in either in Jira fields or excel file");
                            //Assert.fail();
                            GenericMethods.getAssertFails().add("Pre-Header link is not available in either in Jira fields or excel file");
                        }
                    } else {
                        Reporter.addStepLog("Excel file is not available to validate Pre-Header link, so failing this test");
                        //Assert.fail();
                        GenericMethods.getAssertFails().add("Excel file is not available to validate Pre-Header link, so failing this test");
                    }
                }
                BrowserDriver.getCurrentDriver().get(EmailMarketingPOJO.getEmailLink());
                break;
            case "ADDITIONAL LINKS":
                Set<String> linksForSrc = new HashSet<>();
                String addLinks = EmailMarketingPOJO.getAdditionalLinks();
                addLinks = addLinks.replace("\t\n", " ").replace("\n", " ");
                System.out.println("addLinks.. "+addLinks);
                List<String> additionalLinks = new ArrayList<>();
                Map<String, String> emailLinks = EmailView.getAllLinksFromEmail();
                System.out.println("emailLinks.. "+emailLinks);
                boolean isAddLinkPresent = (!(addLinks == null || addLinks == "")) && addLinks.contains("https://");
                if (isAddLinkPresent) {
                    String temp[] = addLinks.split(" ");
                    for (String link : temp) {
                        if (link.startsWith("https://")) {
                            additionalLinks.add(link);
                        }
                    }
                    for (String link : additionalLinks) {
                        if (link != "" || link != null) {
                            boolean isLinkPresent = false;
                            for (String emailLink : emailLinks.keySet()) {
                                if (emailLink.contains(link)) {
                                    // BrowserDriver.loadPage(emailLink);
                                    //linksForSrc.add(emailLinks.get(emailLink));
                                    BrowserDriver.getCurrentDriver().get(emailLinks.get(emailLink));
                                    if (BrowserDriver.getCurrentDriver().getPageSource().contains("Hmmm... It seems we are unable")) {
                                        Reporter.addStepLog("Hmmm... page displayed for additional link \"" + emailLinks.get(emailLink) + "\"" + System.lineSeparator() + "Please check above link manually");
                                    }
                                    String addLinkUrl = BrowserDriver.getCurrentDriver().getCurrentUrl();
                                    if(addLinkUrl.contains(link)) {
                                    	System.out.println("Redirecting to Correct link - As Expected"); }		
                                    else
                                    	{ 	Reporter.addStepLog("Redirecting to wrong link - Failed"); 	}
                                    
                                    String src = EmailMarketingPOJO.getSourceCode();
                                    if (src != "" || src != null) {
                                        if (!addLinkUrl.contains(src)) {
                                            GenericMethods.getAssertFails().add("Additional link \"" + addLinkUrl + "\" doesn't contain the expected src code \"" + src + "\"");
                                        }
                                    }
                                    if (addLinkUrl.replace(" ", "") != "") {
                                        if (HttpJcrewClient.isLinkStatusIsOk(link.trim())) {
                                            Reporter.addStepLog("Link \"" + link + "\" is returning HTTP status code 200 as expected");
                                            if (PropertyLoader.getBrokeImgConfig().equalsIgnoreCase("yes")) {
                                                LOGGER.info("Downloading all images from current like to validate whether there is any broken image ....");
                                                EmailView.validateBrokenImageInLink();
                                                Reporter.addStepLog("Broken image validation is done in above link, there is no broken image");
                                            }
                                        } else {
                                            Reporter.addStepLog("Link \"" + link + "\" is not returning HTTP status code 200 as expected");
                                            //Assert.fail();
                                            GenericMethods.getAssertFails().add("Link \"" + link + "\" is not returning HTTP status code 200 as expected");
                                        }
                                        Reporter.addStepLog("Additional link \"" + link + "\" is validated and it is as expected");
                                        isLinkPresent = true;
                                        break;
                                    }
                                }
                            }
                            if (!isLinkPresent) {
                                Reporter.addStepLog("Additional link \"" + link + "\" is not present in email");
                                GenericMethods.getAssertFails().add("Additional link \"" + link + "\" is not present in email");
                                //Assert.fail();
                            }
                        }
                    }
                } else {
                    Reporter.addStepLog("Unable to validate additional links since they are not available in the Jira ticket");
                }
                EmailMarketingPOJO.setLinksForSrc(linksForSrc);
                BrowserDriver.getCurrentDriver().get(EmailMarketingPOJO.getEmailLink());
                break;
            case "DISCLAIMER COPY":
                String emailDisclaimerCopy = EmailView.getDisclaimerCopy().replace("â€™", "'").replace("â€œ", "\"").replace("â€�", "\"").replace("\n", "");
                String jiraDisclaimerCopy = EmailMarketingPOJO.getDisclaimerCopy();
                if (!(jiraDisclaimerCopy == null || jiraDisclaimerCopy == "")) {
                    if (emailDisclaimerCopy.replace(" ", "").trim().contains(EmailMarketingPOJO.getDisclaimerCopy().replace(" ", "").trim())) {
                        Reporter.addStepLog("DISCLAIMER COPY text is validated and it is as per the Jira ticket");
                    } else {
                        Reporter.addStepLog("DISCLAIMER COPY text is not as per the Jira ticket");
                        Reporter.addStepLog("DISCLAIMER COPY text in email is \"" + emailDisclaimerCopy + "\" but in Jira it is \"" + jiraDisclaimerCopy + "\"");
                        //Assert.fail();
                        GenericMethods.getAssertFails().add("DISCLAIMER COPY text is not as per the Jira ticket");
                    }
                } else {
                    Reporter.addStepLog("Unable to validate DISCLAIMER COPY text since it is not available in the Jira ticket");
                }
                break;
        }
    }

    @Then("^I validate all images$")
    public void I_compare_images() throws Throwable {
        EmailView emailView = new EmailView();
        emailView.downloadAllImagesFromEmail();
        File[] jiraImages = JiraView.getAllJpgFiles(System.getProperty("user.dir") + "/testdata/" + EmailMarketingPOJO.getJiraTicket());
        File[] emailImages = JiraView.getAllJpgFiles(System.getProperty("user.dir") + "/testdata/" + EmailMarketingPOJO.getJiraTicket() + "/images");
        if (jiraImages != null && emailImages != null) {
            for (File jiraImage : jiraImages) {
                boolean isImagePresentInEmail = false;
                String jiraImageHash = GenericMethods.calculateFileHashCode(jiraImage.getAbsolutePath());
                for (File emailImage : emailImages) {
                    if (jiraImageHash.equalsIgnoreCase(GenericMethods.calculateFileHashCode(emailImage.getAbsolutePath()))) {
                        Reporter.addStepLog("Attached image \"" + jiraImage.getName() + "\" from Jira is present in email \"" + emailImage + "\"");
                        isImagePresentInEmail = true;
                        Reporter.addScreenCaptureFromPath(jiraImage.getAbsolutePath(), "Image from Jira");
                        Reporter.addScreenCaptureFromPath(emailImage.getAbsolutePath(), "Image from Email");
                        break;
                    }
                }
                if (!isImagePresentInEmail) {
                    Reporter.addStepLog("Attached image \"" + jiraImage.getName() + "\" from Jira is not present in email");
                }
            }
        } else {
            Reporter.addStepLog("Unable to validate images, there no images attached in jira ticket");
        }
    }

    @Then("^I validate text from all images$")
    public void I_compare_images_text() throws Throwable {
        EmailView emailView = new EmailView();
        emailView.downloadAllImagesFromEmail();
        File[] jiraImages = JiraView.getAllJpgFiles(System.getProperty("user.dir") + "/testdata/" + EmailMarketingPOJO.getJiraTicket());
        File[] emailImages = JiraView.getAllJpgFiles(System.getProperty("user.dir") + "/testdata/" + EmailMarketingPOJO.getJiraTicket() + "/images");
        if (jiraImages != null && emailImages != null) {
            for (File jiraImage : jiraImages) {
                boolean isImageTextPresentInEmail = false;
                String jiraImagetext = getImgText(jiraImage.getAbsolutePath());
                for (File emailImage : emailImages) {
                    String emailImagetext = getImgText(emailImage.getAbsolutePath());
                    if (jiraImagetext.equalsIgnoreCase(emailImagetext)) {
                        Reporter.addStepLog("Text \"" + jiraImagetext + "\" from attached Jira image \"" + jiraImage.getName() + "\" is present in email \"" + emailImage.getName() + "\"");
                        isImageTextPresentInEmail = true;
                        Reporter.addScreenCaptureFromPath(jiraImage.getAbsolutePath(), "Image from Jira");
                        Reporter.addScreenCaptureFromPath(emailImage.getAbsolutePath(), "Image from Email");
                        break;
                    }
                }
                if (!isImageTextPresentInEmail) {
                    Reporter.addStepLog("Attached Jira image text \"" + jiraImagetext + "\" is not present in email");
                }
            }
        } else {
            Reporter.addStepLog("Unable to validate images, there no images attached in jira ticket");
        }
    }

    @Then("^I compare all links in email with excel sheet and verify there is no broken link$")
    public void I_verify_all_links_are_present_in_email_and_there_is_no_broken_link() throws Throwable {
        Set<String> linksForSrc = new HashSet<>();
        File xlFile = JiraView.getExcelFile(System.getProperty("user.dir") + "/testdata/" + EmailMarketingPOJO.getJiraTicket());
        if (xlFile != null) {
            ReadExcel excelData = new ReadExcel(xlFile.getAbsolutePath());
            List<String> excelLinks = excelData.getAllLinksFromExcelSheet();
            Map<String, String> emailLinks = EmailView.getAllLinksFromEmail();
            for (String link : excelLinks) {
                if (link.trim() != "" || link != null) {
                    boolean isLinkPresent = false;
                    for (String emailLink : emailLinks.keySet()) {
                        if (link.replace(" ", "") != "") {
                            if (emailLink.contains(link)) {
                                //BrowserDriver.loadPage(emailLink);
                                //linksForSrc.add(emailLinks.get(emailLink));
                                BrowserDriver.getCurrentDriver().get(emailLinks.get(emailLink));
                                if (BrowserDriver.getCurrentDriver().getPageSource().contains("Hmmm... It seems we are unable")) {
                                    Reporter.addStepLog("Hmmm... page displayed for additional link \"" + emailLinks.get(emailLink) + "\"" + System.lineSeparator() + "Please check above link manually");

                                }
                                String addLinkUrl = BrowserDriver.getCurrentDriver().getCurrentUrl();
                                String src = EmailMarketingPOJO.getSourceCode();
                                if (src != "" || src != null) {
                                    if (!addLinkUrl.contains(src)) {
                                        GenericMethods.getAssertFails().add("Additional link \"" + addLinkUrl + "\" doesn't contain the expected src code \"" + src + "\"");
                                    }
                                }
                                if (HttpJcrewClient.isLinkStatusIsOk(link.trim())) {
                                    Reporter.addStepLog("Excel link \"" + link + "\" is returning HTTP status code 200 as expected");
                                    if (PropertyLoader.getBrokeImgConfig().equalsIgnoreCase("yes")) {
                                        LOGGER.info("Downloading all images from current like to validate whether there is any broken image ....");
                                        EmailView.validateBrokenImageInLink();
                                        Reporter.addStepLog("Broken image validation is done in above link, there is no broken image");
                                    }
                                } else {
                                    Reporter.addStepLog("Excel link \"" + link + "\" is not returning HTTP status code 200 as expected");
                                    //Assert.fail();
                                    GenericMethods.getAssertFails().add("Excel link \"" + link + "\" is not returning HTTP status code 200 as expected");
                                }
                                Reporter.addStepLog("Excel link \"" + link + "\" is validated and it is as expected");
                                isLinkPresent = true;
                                break;
                            }
                        }
                    }
                    if (link.replace(" ", "") != "") {
                        if (!isLinkPresent) {
                            Reporter.addStepLog("Link \"" + link + "\" is not present in email, so skipping this link validation");
                        }
                    }
                }
            }
            EmailMarketingPOJO.setLinksForSrc(linksForSrc);
            BrowserDriver.getCurrentDriver().get(EmailMarketingPOJO.getEmailLink());
        }
    }

    @Then("^I validate \"(.*?)\" in each link$")
    public void I_validate_source_code(String source) throws Throwable {
        String jiraSourceCode = EmailMarketingPOJO.getSourceCode().trim();
        if (!(jiraSourceCode == null || jiraSourceCode == "")) {
            if (EmailMarketingPOJO.getLinksForSrc() != null) {
                for (String link : EmailMarketingPOJO.getLinksForSrc()) {
                    if (link.contains(jiraSourceCode)) {
                        Reporter.addStepLog("Source code \"" + jiraSourceCode + "\" is available in link: " + link);
                    } else {
                        Reporter.addStepLog("Source code \"" + jiraSourceCode + "\" is not available in link: " + link);
                        //Assert.fail();
                        GenericMethods.getAssertFails().add("Source code \"" + jiraSourceCode + "\" is not available in link: " + link);
                    }
                }
            } else {
                Reporter.addStepLog("Source code \"" + jiraSourceCode + "\" validation is skipped because there are no links to verify in jira ticket");
            }
        } else {
            Reporter.addStepLog("Source code validation is skipped because there is no source code in jira ticket");
        }
    }

    @Then("^I should be able to test following metrics of each ticket:$")
    public void I_should_test_each_ticket(List<String> testMetrics) throws Throwable {
        JiraStepDef jiraStepDef = new JiraStepDef();
        EmailStepDef emailStepDef = new EmailStepDef();
        Set<String> s = new HashSet<>();
        s.add("JCD-12922");
        s.add("FAD-5404");
        EmailMarketingPOJO.setDashboradQATickets(s);
        for (String ticket : EmailMarketingPOJO.getDashboradQATickets()) {
            jiraStepDef.I_open_jira_ticket(ticket);
            jiraStepDef.I_get_fields();
            jiraStepDef.I_dowload_attachments();
            emailStepDef.openmail(ticket);
            emailStepDef.I_should_see_email();

            for (String test : testMetrics) {
                switch (test.trim().toUpperCase()) {
                    case "SUBJECT":
                        I_validate_data_in_email("SUBJECT");
                        break;
                    case "PRE-HEADER":
                        I_validate_data_in_email("PRE-HEADER");
                        break;
                    case "PRE-HEADER LINK":
                        I_validate_data_in_email("PRE-HEADER LINK");
                        break;
                    case "DISCLAIMER COPY":
                        I_validate_data_in_email("DISCLAIMER COPY");
                        break;
                    case "ADDITIONAL LINKS":
                        I_validate_data_in_email("ADDITIONAL LINKS");
                        break;
                    case "EXCEL SHEET LINKS":
                        I_verify_all_links_are_present_in_email_and_there_is_no_broken_link();
                        break;
                    case "IMAGES":
                        I_compare_images();
                        break;
                }
            }
        }
    }

    @Then("^I report all failures in \"(.*?)\" if occurred$")
    public void I_failures_in_ticket(String ticket) throws Throwable {
        List<String> errors = GenericMethods.getAssertFails();
        if (errors.size() != 0) {
            Reporter.addStepLog("Failures:: ");
            for (String error : errors) {
                Reporter.addStepLog(error);
            }
            Assert.fail("Ticket failed with above errors:: ");
        } else {
            Reporter.addStepLog("There are no failures in this ticket \"" + ticket + "\"");
        }
    }

}
