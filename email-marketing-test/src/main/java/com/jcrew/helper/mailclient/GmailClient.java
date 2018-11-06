package com.jcrew.helper.mailclient;

import com.cucumber.listener.Reporter;
import com.jcrew.cucumber.container.EmailMarketingPOJO;
import com.jcrew.cucumber.view.EmailView;
import com.jcrew.helper.BrowserDriver;
import com.jcrew.helper.GenericMethods;
import com.jcrew.helper.HttpJcrewClient;
import com.jcrew.helper.PropertyLoader;
import com.jcrew.helper.outlook.msgparser.MsgParser;
import org.junit.Assert;
import org.omg.CORBA.INTERNAL;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class GmailClient implements EmailClient {
    private static final Logger LOGGER = Logger.getLogger(GmailClient.class.getName());
    private static Message email;
    private static Folder inbox;
    private static Store store;

    public GmailClient(String username, String password, String preHeader) {
        email = getEmailFromInboxWithPreheader(username, password, preHeader);
    }

    @Override
    public String getEmailRecipients() {
        String to = null;
        try {
            to = InternetAddress.toString(email.getRecipients(Message.RecipientType.TO));
        } catch (Exception e) {
            //TO DO
        }
        return to;
    }

    @Override
    public String getEmailSubject() {
        String subject = "";
        try {
            subject = email.getSubject();
        } catch (Exception e) {
            //TO DO
        }
        return subject;
    }

    public String getEmailBody() {
        String htmlBody = "";
        try {
            Object content = email.getContent();
            if (content instanceof Multipart) {
                Multipart mp = (Multipart) content;
                for (int i = 0; i < mp.getCount(); i++) {
                    BodyPart bp = mp.getBodyPart(i);
                    if (Pattern
                            .compile(Pattern.quote("text/html"),
                                    Pattern.CASE_INSENSITIVE)
                            .matcher(bp.getContentType()).find()) {
                        htmlBody = (String) bp.getContent();
                    }
                }
            }
        } catch (Exception e) {
            //TO DO
        }
        return htmlBody;
    }

    @Override
    public void closeEmail() {
        try {
            inbox.close(true);
            store.close();
        } catch (Exception e) {
            //TO DO
        }
    }

    @Override
    public Message getEmailFromInboxWithSubject(String username, String password, String preHeader) {
        Properties props = new Properties();
        Message expectedEmail = null;
        try {
            props.load(new FileInputStream(new File(System.getProperty("user.dir") + "/src/test/resources/mailconfig/smtp.properties")));
            Session session = Session.getDefaultInstance(props, null);
            store = session.getStore("imaps");
            store.connect("smtp.gmail.com", username, password);
            inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_ONLY);
            int messageCount = inbox.getMessageCount();
            Message[] messages = inbox.getMessages();
            for (int i = messages.length - 1; i >= 0; i--) {
                if (messages[i].getSubject().toUpperCase().contains(preHeader.toUpperCase())) {
                    System.out.println(messages[i].getSubject());
                    expectedEmail = messages[i];
                    getEmailBody(expectedEmail);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return expectedEmail;
    }

    @Override
    public Message getEmailFromInboxWithPreheader(String username, String password, String preHeader) {
        Properties props = new Properties();
        Message expectedEmail = null;
        try {
            props.load(new FileInputStream(new File(System.getProperty("user.dir") + "/src/test/resources/mailconfig/smtp.properties")));
            Session session = Session.getDefaultInstance(props, null);
            store = session.getStore("imaps");
            store.connect("smtp.gmail.com", username, password);
            Thread.sleep(5000);
            inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_ONLY);
            int messageCount = inbox.getMessageCount();
            Message[] messages = inbox.getMessages();
            boolean isSrcCodeMatched = false;
            String srcCode = EmailMarketingPOJO.getSourceCode().trim().toUpperCase();
            int mxMailsSearch = Integer.parseInt(PropertyLoader.getMaxNumberOfEmailsToSearch().trim());
            for (int i = messages.length - 1; i >= messages.length - mxMailsSearch; i--) {
                String subject = messages[i].getSubject();
                String emailBodyData = getEmailBody(messages[i]);
                String emailBodyDataEcoded = emailBodyData.replace("&lt;", "<")
                        .replace("&gt;", ">")
                        .replace("&sol;;", "/")
                        .replace("&quot;", "\"")
                        .replace("&apos;", "'")
                        .replace("&excl;", "!")
                        .replace("&num;", "#")
                        .replace("&dollar;", "$")
                        .replace("&percnt;", "%")
                        .replace("&amp;", "&")
                        .replace("&lpar;", "(")
                        .replace("&rpar;", ")")
                        // .replace("&ast;", "*")
                        .replace("&plus;", "+")
                        .replace("&colon;", ":")
                        .replace("&semi;", ";")
                        .replace("&equals;", "=")
                        .replace("&quest;", "?")
                        .replace("&commat;", "@")
                        .replace("&lsqb;", "[")
                        .replace("&rsqb;", "]")
                        .replace("&Hat;", "^")
                        .replace("&lowbar;", "_")
                        .replace("&lcub;", "{")
                        .replace("&rcub;", "}")
                        .replace("&verbar;", "|");
                if (emailBodyDataEcoded.contains(preHeader.trim())) {
                    String filePath = System.getProperty("user.dir") + File.separator + "testdata" + File.separator + EmailMarketingPOJO.getJiraTicket();
                    String fileName = (EmailMarketingPOJO.getJiraTicket() + "_test_" + i).replace("-", "_") + ".html";
                    String body = emailBodyData;
                    emailBodyToFile(fileName, body);
                    BrowserDriver.getCurrentDriver().get(filePath + "/" + fileName);
                    LOGGER.info("Email with the subject \"" + subject + "\" is opened and verifying Src code");
                    String emailBrowserLink = EmailView.emailInBrowserLink();
                    BrowserDriver.getCurrentDriver().get(emailBrowserLink);
                    String preLink = EmailView.getPreHeaderLink();
                    BrowserDriver.getCurrentDriver().get(preLink);
                    String url = BrowserDriver.getCurrentDriver().getCurrentUrl();
                    if (!HttpJcrewClient.isLinkStatusIsOk(url.trim())) {
                        BrowserDriver.getCurrentDriver().get(url);
                        url = BrowserDriver.getCurrentDriver().getCurrentUrl();
                    }
                    if (!(srcCode == null || srcCode == "")) {
                        if (url.toUpperCase().contains(srcCode.toUpperCase())) {
                            expectedEmail = messages[i];
                            emailBodyToFile(EmailMarketingPOJO.getJiraTicket() + "_email".replace("-", "") + ".html", body);
                            break;
                        }
                    } else {
                        Reporter.addStepLog("Src code is not available in JIRA ticket so taking the latest email from inbox which is matching with pre-header");
                        expectedEmail = messages[i];
                        emailBodyToFile(EmailMarketingPOJO.getJiraTicket() + "_email".replace("-", "") + ".html", body);
                        break;
                    }
                }
            }
            if (expectedEmail == null) {
                Reporter.addStepLog("There is no email in inbox with the Pre-header \"" + preHeader + "\" and Src code \"" + srcCode + "\"");
                Assert.fail("There is no email in inbox with the Pre-header \"" + preHeader + "\" and Src code \"" + srcCode + "\"");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return expectedEmail;
    }

    private String getEmailBody(Message mail) {
        String htmlBody = "";
        try {
            Object content = mail.getContent();

            if (content instanceof Multipart) {
                Multipart mp = (Multipart) content;
                for (int i = 0; i < mp.getCount(); i++) {
                    BodyPart bp = mp.getBodyPart(i);
                    if (Pattern
                            .compile(Pattern.quote("text/html"),
                                    Pattern.CASE_INSENSITIVE)
                            .matcher(bp.getContentType()).find()) {
                        htmlBody = (String) bp.getContent();
                    }
                }
            } else {
                htmlBody = content.toString();
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return htmlBody;
    }

    private void emailBodyToFile(String filename, String data) {
        String filePath = System.getProperty("user.dir") + File.separator + "testdata" + File.separator + EmailMarketingPOJO.getJiraTicket();
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        writeDataToFile(filePath + File.separator + filename, data);
    }

    private void writeDataToFile(String filePath, String data) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            PrintWriter writer = new PrintWriter(filePath, "UTF-8");
            writer.println(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
