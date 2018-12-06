import { until } from 'selenium-webdriver';
import { driver, defaultTimeout_short } from '../helpers';
import { globals } from '../jestJcrewQaConfig';
import { guestuser, creditcard } from '../testdata/jcrewTestData';
import { waitSeconds } from '../util/commonutils';

const { Builder, By, Key } = require('selenium-webdriver')

const checkoutBtn = By.xpath("//*[@id='button-checkout']");
const shippingAddress = By.xpath("//li/a[contains(text(),'Shipping Address')]");
const shipToStoreRadioBtn = By.xpath("//input[@id='shipToStore']");
const zipCodeTxt = By.xpath("//input[@class='textbox-zip']");
const address1 = By.xpath("(//div[@class='address-container'])[1]");
const continueBtn = By.xpath("//a[@id='order-summary__button-continue']");
const secuirtyCode = By.xpath("//*[@id='securityCode']");
const orderSummary = By.xpath("//*[@id='orderSummaryContainer']/div/a");
const submitOrderBtn = By.xpath("//*[@id='button-submitorder']");
const brizatePopupClose = By.xpath("//div[@class='brdialog-close']");
const orderNumberTranslate = By.xpath("//span[@class='order-number notranslate']");
const guestCheckoutOption = By.xpath("//a[text()='Check Out as a Guest']");

//Quickshop elements
const product_image2 = By.xpath("(//div[@class='c-product__photos'])[2]");
const quickBtn = By.xpath("//button[@class='c-product-tile__quickshop']");
const productSizeList = By.xpath("(.//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]");
const addToBag = By.id("btn__add-to-bag-wide");
const shoppingBagLink = By.xpath("//a[text()='shopping bag']");

//Guest user checkout
const firstName = By.xpath("//input[@id='firstNameSA']");
const lastName = By.xpath("//input[@id='lastNameSA']");
const addressline3 = By.xpath("//input[@id='address3']");
const addressline2 = By.xpath("//input[@id='address2']");
const addressline1 = By.xpath("//input[@id='address1']");
const zipcode_guest = By.xpath("//input[@id='zipcode']");
const phoneNumberSA = By.xpath("//input[@id='phoneNumSA']");
const mainBtn_continue = By.xpath("//*[@id='main__button-continue']");
const addressGuest = By.xpath("//*[@id='shoppingAddressValidate']/div[2]/a");

//CreditCard detials
const creditCardNo = By.xpath("//input[@id='creditCardNumber']");
const creditCardSecurityCode = By.xpath("//input[@id='securityCode']");
const creditCardExpiryMonth = By.xpath("//select[@id='expirationMonth']");
const creditCardExpiryYear = By.xpath("//select[@id='expirationYear']");
const creditCardName = By.xpath("//input[@id='nameOnCard']");
const emailTxt = By.xpath("//input[@id='emailReceipt']");

export const clickCheckoutBtn = async () => {
    await driver.findElement(checkoutBtn).click()
    await waitSeconds(2)
}

export const checkoutAsGuest = async () => {
    await driver.findElement(guestCheckoutOption).click()
    await waitSeconds(2)
    // address detaiils
    await driver.findElement(firstName).sendKeys(guestuser.firstNameSA)
    await driver.findElement(lastName).sendKeys(guestuser.lastNameSA)
    await driver.findElement(addressline3).sendKeys(guestuser.address3)
    await driver.findElement(addressline2).sendKeys(guestuser.address2)
    await driver.findElement(addressline1).sendKeys(guestuser.address1)
    await driver.findElement(zipcode_guest).sendKeys(guestuser.zipcode)
    await waitSeconds(3)
    await driver.findElement(phoneNumberSA).sendKeys(guestuser.phoneNumSA)
    await waitSeconds(3)
    await driver.findElement(mainBtn_continue).click()
    try {
        // address confirmation page :
        await waitSeconds(3)
        await driver.findElement(addressGuest).click()
        await waitSeconds(3)
        await driver.findElement(mainBtn_continue).click()
    } catch (err) { }
}

export const STSSameDayDelivery = async (currentUrl, zipcode) => {
    if (currentUrl.includes("factory")) {
        console.log("Ship to store functionality is not available in Factory")
    } else{  // Production review checkout
        await driver.findElement(shippingAddress).click()
        await waitSeconds(2)
        await driver.findElement(shipToStoreRadioBtn).click()
        await waitSeconds(2)
        await driver.findElement(zipCodeTxt).sendKeys(zipcode)
        await waitSeconds(2)
        const samedayDelivery = await driver.findElement(address1)
        expect(samedayDelivery).toBeTruthy()
        console.log("same day pick up is available")
        let contBtn = await driver.findElement(continueBtn)
        expect(contBtn).toBeTruthy()
        await contBtn.click()
        await waitSeconds(2)
        await driver.findElement(continueBtn).click()
        await waitSeconds(2)
    }
}

export const STSReviewCheckout = async (currentUrl, creditPin) => {
    if (currentUrl.includes("factory")) {
        console.log("Ship to store functionality is not available in Factory")
    } else if (currentUrl.indexOf("https://or.") > -1) {  // Production review checkout
        try {
            await driver.findElement(secuirtyCode).then(securitycodeTxt => {
                console.log("inside securitycode")
                if (securitycodeTxt.isDisplayed()) {
                    securitycodeTxt.sendKeys(creditPin)
                }
            })
        } catch (err) { }
        await waitSeconds(3)
        if (currentUrl.indexOf("https://or.") > -1) {  // Production review checkout
            if (currentUrl.indexOf("factory.jcrew.com") > -1) {
                console.log(">> inside factory")
                await driver.findElement(orderSummary).click()
            } else {
                await driver.findElement(submitOrderBtn).click()
            }
            await waitSeconds(4)
            try {
                const bizrate = await driver.findElement(brizatePopupClose)
                expect(bizrate).toBeTruthy()
                await bizrate.click()
                await waitSeconds(2)
            } catch (err) { }
            let orderNumberLet = await driver.findElement(orderNumberTranslate).getText()
            console.log("order Id  > " + orderNumberLet)
        }
    }
}

export const quickShopCheckoutValidation = async () => {
    await driver.actions().mouseMove(await driver.findElement(product_image2)).perform();
    await waitSeconds(2)
    const quickbutton = driver.findElement(quickBtn)
    await driver.wait(until.elementIsVisible(quickbutton), defaultTimeout_short)
    expect(quickbutton).toBeTruthy()
    await quickbutton.click()
    await waitSeconds(3)
    await driver.findElement(productSizeList).click()
    await waitSeconds(2)
    //await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//a[text()='shopping bag']")));
    await driver.executeScript("arguments[0].click();", driver.findElement(addToBag));
    await waitSeconds(2)
    await driver.executeScript("arguments[0].click();", driver.findElement(shoppingBagLink));
    await waitSeconds(1)
}

export const quickShopReview = async (currentUrl, creditPin) => {
    if (currentUrl.indexOf("https://or.") > -1) {  // Production review checkout
        await waitSeconds(3)
        try {
            await driver.findElement(secuirtyCode).then(securitycodeTxt => {
                console.log("inside securitycode")
                securitycodeTxt.sendKeys(creditPin)
            })
        } catch (err) {

        }
        if (currentUrl.indexOf("factory.jcrew.com") > -1) {
            console.log(">> inside factory")
            await driver.findElement(orderSummary).click()
        } else {
            await driver.findElement(submitOrderBtn).click()
        }
        await waitSeconds(3)
        let orderNumberLet = await driver.findElement(orderNumberTranslate).getText()
        console.log("order Id  > " + orderNumberLet)
    }
}

export const enterCreditCardDetails = async () => { 
    await driver.findElement(creditCardNo).sendKeys(creditcard.number)
    await driver.findElement(creditCardSecurityCode).sendKeys(creditcard.pin)
    await waitSeconds(3)
    await driver.findElement(creditCardExpiryMonth).sendKeys(creditcard.expirationMonth)
    await driver.findElement(creditCardExpiryYear).sendKeys(creditcard.expirationYear)
    await driver.findElement(creditCardName).sendKeys(creditcard.nameOnCard)
    var x = Math.floor((Math.random() * 1000000) + 1);
    let userName = "AutomationTest" + x
    let email = "AutoTest" + x + "@gmail.com"
    await driver.findElement(emailTxt).sendKeys(email)
    // await driver.findElement(By.xpath("//a[@id='billing-options-submit']")).click()
    await driver.findElement(mainBtn_continue).click()
    await waitSeconds(3)
}