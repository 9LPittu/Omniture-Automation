package com.jcrew.helper.mailclient;

import com.cucumber.listener.Reporter;
import com.jcrew.cucumber.container.EmailMarketingPOJO;
import com.jcrew.cucumber.view.EmailView;
import com.jcrew.helper.BrowserDriver;
import com.jcrew.helper.HttpJcrewClient;
import com.jcrew.helper.PropertyLoader;
import com.jcrew.helper.outlook.msgparser.MsgParser;
import org.junit.Assert;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

public class YahooMailClient implements EmailClient {
    private static Message email;
    private static Folder inbox;
    private static Store store;

    public YahooMailClient(String username, String password, String preHeader) {
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
        String subject = null;
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
                    } else {
                        // some other bodypart...
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
    public Message getEmailFromInboxWithSubject(String username, String password, String subject) {
        Properties props = new Properties();
        Message expectedEmail = null;
        try {
            props.load(new FileInputStream(new File(System.getProperty("user.dir") + "/src/test/resources/mailconfig/smtp.properties")));
            Session session = Session.getDefaultInstance(props, null);

            store = session.getStore("imaps");
            store.connect("imap.mail.yahoo.com", username, password);
            //store.connect("adfs.jcre.com", username, password);

            inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_ONLY);
            int messageCount = inbox.getMessageCount();
            Message[] messages = inbox.getMessages();
            for (int i = messages.length - 1; i >= 0; i--) {
                String emailBodyData = getEmailBody(messages[i]);
                // if (msg.getSubject().toUpperCase().contains(subject.toUpperCase())) {
                System.out.println(messages[i].getSubject());
                expectedEmail = messages[i];
                break;
                // }
            }
            //inbox.close(true);
            //store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return expectedEmail;
    }

    @Override
    public Message getEmailFromInboxWithPreheader(String username, String password, String preHeader) {
        //Properties props = new Properties();
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        Message expectedEmail = null;
        Set<Message> preHeaderMatchingEmails = new HashSet<>();
        try {
            //props.load(new FileInputStream(new File(System.getProperty("user.dir") + "/src/test/resources/mailconfig/smtp.properties")));

            Session session = Session.getDefaultInstance(props, null);

            store = session.getStore("imaps");
            store.connect("imap.mail.yahoo.com", username, password);

            inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_ONLY);
            int messageCount = inbox.getMessageCount();
            Message[] messages = inbox.getMessages();
            boolean isSrcCodeMatched = false;
            String srcCode = EmailMarketingPOJO.getSourceCode();
            int mxMailsSearch = Integer.parseInt(PropertyLoader.getMaxNumberOfEmailsToSearch().trim());
            for (int i = messages.length - 1; i >= messages.length - mxMailsSearch; i--) {
                String emailBodyData = getEmailBody(messages[i]);
                String emailBodyDataEcoded = emailBodyData.replace("&lt;", "<")
                        .replace("&gt;", ">")
                        .replace("&sol;;", "/")
                        .replace("&quot;", "\"")
                        .replace("&apos;", "'")
                        .replace("&amp;", "&");
                if (emailBodyDataEcoded.contains(preHeader.trim())) {
                    String filePath = System.getProperty("user.dir") + File.separator + "testdata" + File.separator + EmailMarketingPOJO.getJiraTicket();
                    String fileName = (EmailMarketingPOJO.getJiraTicket() + "_test_" + i).replace("-", "_") + ".html";
                    String body = emailBodyData;
                    emailBodyToFile(fileName, body);
                    BrowserDriver.getCurrentDriver().get(filePath + "/" + fileName);
                    String emailBrowserLink = EmailView.emailInBrowserLink();
                    BrowserDriver.getCurrentDriver().get(emailBrowserLink);
                    String preLink = EmailView.getPreHeaderLink();
                    BrowserDriver.getCurrentDriver().get(preLink);
                    String url = BrowserDriver.getCurrentDriver().getCurrentUrl();
                    if (!HttpJcrewClient.isLinkStatusIsOk(url.trim())) {
                        BrowserDriver.getCurrentDriver().get(url);
                        url = BrowserDriver.getCurrentDriver().getCurrentUrl();
                    }
                    if (srcCode != null && srcCode != "") {
                        srcCode = srcCode.toUpperCase().trim();
                        if (url.toUpperCase().contains(srcCode.toUpperCase())) {
                            expectedEmail = messages[i];
                            isSrcCodeMatched = true;
                            emailBodyToFile(EmailMarketingPOJO.getJiraTicket() + "_email".replace("-", "") + ".html", body);
                            break;
                        }
                    } else {
                        Reporter.addStepLog("Src code is not available in JIRA ticket so taking the latest email from inbox which is matching with pre-header");
                    }
                }
            }
            if (expectedEmail == null) {
                Reporter.addStepLog("There is no email in inbox with the Pre-header \"" + preHeader + "\" and Src code \"" + srcCode + "\"");
                Assert.fail("There is no email in inbox with the Pre-header \"" + preHeader + "\" and Src code \"" + srcCode + "\"");
            }
            //inbox.close(true);
            //store.close();
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
                    } else {
                        //System.out.println(((Multipart) content).getContentType());
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

    public static void main(String[] args) {
        /*YahooClient eml = new YahooClient("jcrewhclcontenttest@yahoo.com", "Jcrew2017HCL", "Your J.Crew store news, right this way...");
        System.out.println(eml.getEmailBody(email));*/

        String src = "F17_Nov_1109_Store_Promo_Monogram\t\n" +
                "EMSL11192";
        String sourceCode = null;
        if (src != null || src != "") {
            src = src.replace("\t\n", " ").replace("\n", " ");
            String[] temp = src.split(" ");
            for (String srcCode : temp) {
                boolean srcLengthCheck = srcCode.length() == 9 || srcCode.length() == 11;
                boolean srcTxtCheck = srcCode.contains("EMSL") || srcCode.contains("FAEMSL");
                if (srcTxtCheck && srcLengthCheck) {
                    sourceCode = srcCode;
                    break;
                }
            }
            System.out.println("Source code is: " + sourceCode);
        }
    }
}