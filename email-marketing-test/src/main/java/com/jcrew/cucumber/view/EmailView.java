package com.jcrew.cucumber.view;

import com.cucumber.listener.Reporter;
import com.jcrew.cucumber.container.EmailContainer;
import com.jcrew.cucumber.container.EmailMarketingPOJO;
import com.jcrew.helper.BrowserDriver;
import com.jcrew.helper.HttpJcrewClient;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.ImageIOHelper;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class EmailView {
    private static EmailContainer emailContainer;

    public static String getPreHeaderText() throws InterruptedException {
    	Thread.sleep(5000);
        return emailContainer.preHeader.getText();
    }

    public static String emailInBrowserLink() {
        if (emailContainer.getEmailBrowserLink() != null) {
            return emailContainer.getEmailBrowserLink().getAttribute("href");
        } else return null;
    }

    public static void clickPreHeaderLink() {
        emailContainer.preHeader.click();
    }

    public static String getPreHeaderLink() throws InterruptedException {
    	Thread.sleep(5000);
        return emailContainer.preHeader.getAttribute("href");
    }

    private static boolean isClickOnUsSitePopupLink() {
        boolean isElementClicked = false;
        try {
            WebDriverWait wait = new WebDriverWait(BrowserDriver.getCurrentDriver(), 10);
            By changeToUsPopupLoc = By.xpath("//*[@class='c-header__welcomemat--ussite js-to-us-site']");
            WebElement country = BrowserDriver.getCurrentDriver().findElement(changeToUsPopupLoc);
            wait.until(ExpectedConditions.elementToBeClickable(country));
            country.click();
            isElementClicked = true;
        } catch (Exception e) {

        }
        return isElementClicked;
    }

    private static boolean isClickOnUsSiteLink() {
        boolean isElementClicked = false;
        try {
            BrowserDriver.waitForElementToBeClickable(emailContainer.countrySelection, 5);
            emailContainer.countrySelection.click();
            isElementClicked = true;
        } catch (Exception e) {

        }
        return isElementClicked;
    }

    public static void changeCountryToUs() {
        WebDriverWait wait = new WebDriverWait(BrowserDriver.getCurrentDriver(), 5);
        BrowserDriver.getCurrentDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        try {
            By changeToUsPopupLoc = By.xpath("//*[@class='c-header__welcomemat--ussite js-to-us-site']");
            By changeCountryLinkLoc = By.xpath("//a[@class='footer__country-context__link']");
            By closePopup= By.xpath("//div[@class='email-capture--close modal-capture--close js-email-capture--close']/span");

            boolean isPopupDisplayed = BrowserDriver.isElementDisplayed(closePopup);
            if(isPopupDisplayed){
                BrowserDriver.getCurrentDriver().findElement(closePopup).click();
            }
            if (BrowserDriver.isElementDisplayed(changeToUsPopupLoc)) {
                for (int i = 0; i <= 5; i++) {
                    boolean isClicked = isClickOnUsSitePopupLink();
                    if (isClicked) {
                        break;
                    }
                    BrowserDriver.getCurrentDriver().navigate().refresh();
                }
            } else if (BrowserDriver.isElementDisplayed(By.xpath("//a[@class='footer__country-context__link']/../div[@title='India']"))) {
                for (int i = 0; i <= 5; i++) {
                    boolean isClicked = isClickOnUsSiteLink();
                    if (isClicked) {
                        break;
                    }
                    BrowserDriver.getCurrentDriver().navigate().refresh();
                }
                BrowserDriver.waitForElementToBeClickable(emailContainer.us, 30);
                Thread.sleep(2000);
                emailContainer.us.click();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Reporter.addStepLog("Unable to change shipping country to \"United States\"");
        }
        BrowserDriver.setImplicitWaitToDefault();
    }

    public static String getDisclaimerCopy() {
        return emailContainer.disclaimerCopy().getText();
    }

    public static Map<String, String> getAllLinksFromEmail() {
        Map<String, String> links = new HashMap<>();
        for (WebElement link : emailContainer.links) {
            links.put(link.getAttribute("alt").trim(), link.getAttribute("href").trim());
        }
        return links;
    }

    public static List<String> getAllSrcValidationLinksFromEmail() {
        List<String> links = new ArrayList<>();
        for (WebElement link : emailContainer.links) {
            links.add(link.getAttribute("href").trim());
        }
        return links;
    }

    public static void downloadAllImagesFromEmail() {
        List<String> links = getAllImageLinksFromEmail();
        HttpJcrewClient http = new HttpJcrewClient();
        for (String link : links) {
            String[] temp = link.split("/");
            String fileName = temp[temp.length - 1].trim();
            http.downloadFile(link, System.getProperty("user.dir") + "/testdata/" + EmailMarketingPOJO.getJiraTicket() + "/images/" + fileName);
        }
    }

    public static List<String> getAllImageLinksFromEmail() {
        List<String> links = new ArrayList<>();
        for (WebElement link : emailContainer.emailImages) {
            links.add(link.getAttribute("src").trim());
        }
        return links;
    }

    public static void validateBrokenImageInLink() {
        List<String> links = getAllImageLinksFromAdditionalLink();
        HttpJcrewClient http = new HttpJcrewClient();
        if (links != null && links.size() != 0) {
            int i = 1;
            for (String link : links) {
                String fileName = "temp_" + getRandomeText() + "_" + i++;
                if (link.endsWith("png")) {
                    fileName = fileName + ".png";
                } else {
                    fileName = fileName + ".jpg";
                }
                String destFileName = System.getProperty("user.dir") + "/testdata/" + EmailMarketingPOJO.getJiraTicket() + "/images/linkimages/" + fileName;
                http.downloadFile(link, destFileName);
                String imgText = getImgText(destFileName);
                if (isBrokenImage(imgText)) {
                    Reporter.addStepLog("Image \"" + link + "\" is broken ");
                    Assert.fail();
                }
            }
        }
    }

    public static List<String> getAllImageLinksFromAdditionalLink() {
        List<String> links = new ArrayList<>();
        try {
            for (WebElement link : emailContainer.getEmailImagesFromAddLink()) {
                links.add(link.getAttribute("src").trim());
            }
        } catch (Exception e) {

        }
        return links;
    }

    public static boolean isBrokenImage(String imgText) {
        boolean isBrokenImage = false;
        imgText = imgText.replace("\n", "");
        String text = "A GREAT IMAGEIS ON ITS WAY.PLEASE POPBACK LATER.";
        if (imgText.trim().equalsIgnoreCase(text.trim())) {
            isBrokenImage = true;
        }
        return isBrokenImage;
    }

    public static String getImgText(String imageLocation, String language) {
        String imgText = "";
        File image = new File(imageLocation);
        Tesseract1 tessInst = new Tesseract1();
        try {
            imgText = tessInst.doOCR(image);
            return imgText;
        } catch (Exception e) {
            return "Error while reading image";
        }
    }

    public static String getImgText(String imageLocation) {
        String imgText = "";
        File image = new File(imageLocation);
        Tesseract tessInst = new Tesseract();
        try {
            imgText = EmailView.doOCR(image, tessInst, (Rectangle) null);
            return imgText;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "Error while reading image";
        }
    }

    public static String getRandomeText() {
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();
        return output;
    }

    public static void init() {
        emailContainer = PageFactory.initElements(BrowserDriver.getCurrentDriver(), EmailContainer.class);
    }

    private static String doOCR(File var1, Tesseract tesseract, Rectangle var2) throws TesseractException {
        try {
            return tesseract.doOCR(ImageIOHelper.getIIOImageList(var1), var1.getPath(), var2);
        } catch (Exception var4) {
            throw new TesseractException(var4);
        }
    }
}
