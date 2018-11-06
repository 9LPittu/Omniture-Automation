package com.jcrew.helper;

import com.cucumber.listener.Reporter;
import com.jcrew.cucumber.container.EmailMarketingPOJO;
import com.jcrew.cucumber.view.EmailView;
import com.jcrew.helper.outlook.msgparser.Message;
import com.jcrew.helper.outlook.msgparser.MsgParser;
import org.junit.Assert;

import java.io.*;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class OutlookEmailClient {
    private static Message message = null;

    public OutlookEmailClient(String preHeader, String srcCode) {
        message = getEmail(preHeader, srcCode);
    }

    public String getEmailHtmlBody() {
        return message.getConvertedBodyHTML();
    }

    public String getEmailSubject() {
        return message.getSubject();
    }

    public Message getEmail(String preHeader, String srcCode) {
        Message message = null;
        try {
            MsgParser msgp = new MsgParser();

            Handler[] handlers = Logger.getLogger("").getHandlers();
            for (int index = 0; index < handlers.length; index++) {
                handlers[index].setLevel(Level.INFO);
            }
            Logger logger = Logger.getLogger(MsgParser.class.getName());
            logger.setLevel(Level.INFO);

            File testDir = new File("emails");
            File[] testFiles = testDir.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".msg");
                }
            });

            if (preHeader.toUpperCase().trim() != "") {
                boolean isSrcCodeMatched = false;
                for (File testFile : testFiles) {
                    Message msg = msgp.parseMsg(testFile);
                    String emailBody = msg.getConvertedBodyHTML();
                    String body = emailBody.replace(" ", "");
                    String preheader = preHeader.replace(" ", "");
                    if (body.toUpperCase().contains(preheader.toUpperCase())) {
                        message = msg;
                    }
                }
                if (message == null) {
                    Reporter.addStepLog("Email with the preheader \"" + preHeader + "\" is not available in mails folder");
                    Assert.fail("Email with the preheader \"" + preHeader + "\" is not available in mails folder");
                }
            } else {
                Reporter.addStepLog("Pre-header is not available in jira ticket so tool is unable to find email from inbox, please fix that");
                Assert.fail("Pre-header is not available in jira ticket so tool is unable to find email from inbox, please fix that");
            }
        } catch (Exception e) {
            if (message == null) {
                Reporter.addStepLog("Email with the preheader \"" + preHeader + "\" is not available in mails folder");
                Assert.fail("Email with the preheader \"" + preHeader + "\" is not available in mails folder");
            }
        }
        return message;
    }


    public String emailBodyToFile(String filename, String data) {
        String filePath = System.getProperty("user.dir") + File.separator + "testdata" + File.separator + EmailMarketingPOJO.getJiraTicket();
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String str=filePath + File.separator + filename;
        writeDataToFile(str, data);
        return str;
    }

    public void writeDataToFile(String filePath, String data) {
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

    public String getValueFromXmlData(String data, String xPath) {
        String value = null;
        try {
            InputSource source = new InputSource(new StringReader(data));

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(source);
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            value = xpath.evaluate(xPath, document);
        } catch (Exception e) {
            Reporter.addStepLog("Unable find email from inbox with Pre-header text, please make sure email is available to test");
            Assert.fail("Unable find email from inbox with Pre-header text, please make sure email is available to test");
        }
        return value;
    }
}
