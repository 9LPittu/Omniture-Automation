package com.jcrew.page;

        import com.jcrew.pojo.Country;
        import com.jcrew.utils.PropertyReader;
        import com.jcrew.utils.StateHolder;
        import com.jcrew.utils.Util;
        import org.openqa.selenium.By;
        import org.openqa.selenium.JavascriptExecutor;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.support.FindBy;
        import org.openqa.selenium.support.PageFactory;
        import org.openqa.selenium.support.ui.ExpectedConditions;
        import org.openqa.selenium.support.ui.WebDriverWait;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;

        import java.util.List;

/**
 * Created by nadiapaolagarcia on 3/30/16.
 */
public class HomePage {
    private final WebDriver driver;
    private HeaderWrap headerWrap;
    private Footer footer;
    private final Logger logger = LoggerFactory.getLogger(HomePage.class);
    private final StateHolder stateHolder = StateHolder.getInstance();
    private WebDriverWait wait;

    @FindBy (id = "page__home")
    private WebElement pageContent;
    @FindBy (className = "c-email-capture")
    private WebElement emailCapture;

    public HomePage(WebDriver driver){
        this.driver = driver;
        headerWrap = new HeaderWrap(driver);
        footer = new Footer(driver);
        wait = Util.createWebDriverWait(driver);
        PageFactory.initElements(driver, this);

        wait.until(ExpectedConditions.visibilityOf(pageContent));
    }

    public boolean isHomePage(){
        headerWrap.reload();
        WebElement body = driver.findElement(By.tagName("body"));
        String bodyClass = body.getAttribute("class");

        return "jcrew home".equals(bodyClass);
    }


    public void handle_email_pop_up() {

        JavascriptExecutor jse = ((JavascriptExecutor) driver);
        boolean emailCapture = jse.executeScript("return jcrew.config.showEmailCapture;").equals(true);
        logger.debug("Email capture? {}", emailCapture);

        if(emailCapture) {
            try{
                List<WebElement> email_capture = Util.createWebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfAllElements(driver.findElements(
                        By.xpath("//div[@id='global__email-capture']/section/div[@class = 'email-capture--close js-email-capture--close']"))));

                if(email_capture.size() > 0) {
                    logger.debug("Email capture on, let's turn it off!!");
                    WebElement close = email_capture.get(0);
                    Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(close));
                    close.click();
                }
            }
            catch(Exception e){
                logger.debug("No email capture displayed...");
            }

        }
    }

    public void closeEmailCapture(){
        PropertyReader propertyReader = PropertyReader.getPropertyReader();
        String browser = propertyReader.getProperty("browser");

        if("chrome".equals(browser) || "firefox".equals(browser)) {
            WebElement closeButton = emailCapture.findElement(By.className("js-email-capture--close"));
            WebElement closeIcon = closeButton.findElement(By.tagName("span"));

            wait.until(ExpectedConditions.elementToBeClickable(closeIcon));
            closeIcon.click();
        }
    }

    public boolean verifyInternational() {
        Country country = (Country) stateHolder.get("context");
        String countryName = country.getName();
        String countryFooter = footer.getCountry();

        boolean urlCompliance = Util.countryContextURLCompliance(driver, country);
        boolean footerCompliance = countryName.equalsIgnoreCase(countryFooter);

        return urlCompliance & footerCompliance;
    }


}
