package com.jcrew.page;

import com.jcrew.utils.Util;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.support.FindBy;
        import org.openqa.selenium.support.PageFactory;
        import org.openqa.selenium.support.ui.ExpectedConditions;
        import org.openqa.selenium.support.ui.WebDriverWait;

public class ForgotPassword {

    private final WebDriverWait wait;

    @FindBy(id = "forgotPassword")
    private WebElement forgotPasswordForm;

    public ForgotPassword(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.wait = Util.createWebDriverWait(driver);
        wait.until(ExpectedConditions.visibilityOf(forgotPasswordForm));
    }


    public boolean isForgotPasswordPage() {
        return forgotPasswordForm.isDisplayed();
    }
}
