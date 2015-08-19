package com.jcrew.page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomePage {

    private final WebDriver driver;
    private Logger logger = LoggerFactory.getLogger(HomePage.class);

    @FindBy(xpath = ".//*[@id='globalHeaderNav']/li[1]/a")
    private WebElement firstCategoryDisplayed;

    @FindBy(id = "lightbox")
    private WebElement modalWindow;

    @FindBy(className = "closePopup")
    private WebElement closePopupLink;

    @FindBy(id = "header__logo")
    private WebElement headerLogo;

    @FindBy(xpath = "//*/span[@class='department-nav__text' and contains(text(), 'Women')]")
    private WebElement womenDepartmentLink;

    @FindBy(xpath = "//*/span[@class='department-nav__text' and contains(text(), 'Men')]")
    private WebElement menDepartmentLink;

    @FindBy(xpath = "//*/span[@class='department-nav__text' and contains(text(), 'Boys')]")
    private WebElement boysDepartmentLink;

    @FindBy(xpath = "//*/span[@class='department-nav__text' and contains(text(), 'Girls')]")
    private WebElement girlsDepartmentLink;

    @FindBy(className = "primary-nav__text--stores")
    private WebElement storesLink;

    @FindBy(className = "js-primary-nav__link--menu")
    private WebElement hamburgerMenuLink;

    @FindBy(xpath = "//a[contains(@class, 'menu__link--forward') and contains(text(), 'Women')]")
    private WebElement hamburgerMenuWomenLink;

    @FindBy(xpath = "//a[contains(@class, 'menu__link--forward') and contains(text(), 'Men')]")
    private WebElement hamburgerMenuMenLink;

    @FindBy(xpath = "//a[contains(@class, 'menu__link--forward') and contains(text(), 'Boys')]")
    private WebElement hamburgerMenuBoysLink;

    @FindBy(xpath = "//a[contains(@class, 'menu__link--forward') and contains(text(), 'Girls')]")
    private WebElement hamburgerMenuGirlsLink;

    @FindBy(xpath = "//a[contains(@class, 'menu__link--forward') and contains(text(), 'Wedding')]")
    private WebElement hamburgerMenuWeddingLink;

    @FindBy(xpath = "//a[contains(@class, 'menu__link--forward') and contains(text(), 'sale')]")
    private WebElement hamburgerMenuSaleLink;

    @FindBy(xpath = "//a[contains(@class, 'menu__link--has-href') and contains(text(), 'Blog')]")
    private WebElement hamburgerMenuBlogLink;

    @FindBy(xpath = "//a[contains(@class, 'menu__link--has-href') and @href='/c/womens_category/shirtsandtops']")
    private WebElement womenShirtAndTopsCategoryLink;

    @FindBy(xpath = "//a[contains(@class, 'menu__link--has-href') and @href='/c/womens_category/sweaters']")
    private WebElement womenSweatersCategoryLink;

    @FindBy(className = "header__search__input")
    private WebElement searchInput;

    @FindBy(className = "header__search__button--find")
    private WebElement headerSearchButtonFind;

    @FindBy(css = ".primary-nav__item--account > a")
    private WebElement signInLink;

    @FindBy(css = "#c-nav__userpanel > a")
    private WebElement signInLinkFromHamburger;

    @FindBy(className = "menu__nested")
    private WebElement nestedMenu;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void hit_enter_in_search_field() {
        searchInput.sendKeys(Keys.ENTER);
    }

    public void input_search_term(String searchTerm) {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(searchInput));
        searchInput.sendKeys(searchTerm);
    }


    public void click_on_hamburger_menu() {
        hamburgerMenuLink.click();
    }

    public void selects_a_category() {
        closeModalWindowIfPresent();

        new WebDriverWait(driver, 10).until(
                ExpectedConditions.elementToBeClickable(firstCategoryDisplayed));

        firstCategoryDisplayed.click();

    }

    public void click_on_shirts_and_tops_from_women_category_in_hamburger_menu() {
        waitForVisibility(womenShirtAndTopsCategoryLink);
        womenShirtAndTopsCategoryLink.click();
    }

    public void click_on_women_category_from_hamburger_menu() {
        waitForVisibility(hamburgerMenuWomenLink);
        hamburgerMenuWomenLink.click();
    }


    private void closeModalWindowIfPresent() {
        try {

            waitForVisibility(modalWindow);

            closePopupLink.click();

            new WebDriverWait(driver, 10).until(ExpectedConditions.not(
                    ExpectedConditions.visibilityOf(modalWindow)));

        } catch (WebDriverException wde) {
            logger.info("Initial window did not show up, continuing with tests");
        }
    }

    public boolean isJCrewLogoPresent() {
        return headerLogo.isDisplayed();
    }

    public boolean isWomenDepartmentPresent() {
        return womenDepartmentLink.isDisplayed();
    }

    public boolean isMenDepartmentPresent() {
        return menDepartmentLink.isDisplayed();
    }

    public boolean isBoysDepartmentPresent() {
        return boysDepartmentLink.isDisplayed();
    }

    public boolean isGirlsDepartmentPresent() {
        return girlsDepartmentLink.isDisplayed();
    }

    public boolean isStoresLinkPresent() {
        return storesLink.isDisplayed();
    }

    public boolean isHamburgerMenuPresent() {
        boolean isIconDisplayed = hamburgerMenuLink.findElement(By.className("icon-header-menu")).isDisplayed();
        boolean isMenuTextDisplayed = hamburgerMenuLink.findElement(By.className("primary-nav__text")).isDisplayed();
        return isIconDisplayed && isMenuTextDisplayed;
    }

    public boolean isHamburgerMenuWomenLinkPresent() {
        waitForVisibility(hamburgerMenuWomenLink);
        return hamburgerMenuWomenLink.isDisplayed();
    }

    public boolean isHamburgerMenuMenLinkPresent() {
        waitForVisibility(hamburgerMenuMenLink);
        return hamburgerMenuMenLink.isDisplayed();
    }

    public boolean isHamburgerMenuBoysLinkPresent() {
        waitForVisibility(hamburgerMenuBoysLink);
        return hamburgerMenuBoysLink.isDisplayed();
    }

    public boolean isHamburgerMenuGirlsLinkPresent() {
        waitForVisibility(hamburgerMenuGirlsLink);
        return hamburgerMenuGirlsLink.isDisplayed();
    }

    public boolean isHamburgerMenuWeddingLinkPresent() {
        waitForVisibility(hamburgerMenuWeddingLink);
        return hamburgerMenuWeddingLink.isDisplayed();
    }

    public boolean isHamburgerMenuSaleLinkPresent() {
        waitForVisibility(hamburgerMenuSaleLink);
        return hamburgerMenuSaleLink.isDisplayed();
    }

    public boolean isHamburgerMenuBlogLinkPresent() {
        waitForVisibility(hamburgerMenuBlogLink);
        return hamburgerMenuBlogLink.isDisplayed();
    }

    private void waitForVisibility(WebElement element) {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(element));
    }

    public void click_on_sign_in_link() {
        if (signInLink.isDisplayed()) {
            signInLink.click();
        } else {
            click_on_hamburger_menu();
            click_on_sign_in_link_from_hamburger_menu();
        }
    }

    public void click_on_sign_in_link_from_hamburger_menu() {
        waitForVisibility(signInLinkFromHamburger);
        signInLinkFromHamburger.click();
    }

    public void click_on_search_button_for_input_field() {
        headerSearchButtonFind.click();
    }

    public void click_on_subcategory(String subcategory) {
        nestedMenu.findElement(By.linkText(subcategory)).click();
    }
}
