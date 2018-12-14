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

const giftCardLink = By.xpath("//div[text()='The J.Crew Gift Card']");
const classicGiftCardImg = By.xpath("//*[@id='classicGiftCard']/img");
const dollor_25 = By.xpath("//*[@id='amount25']");
const senderName_giftCard = By.xpath("//*[@id='senderName']");
const recepientname = By.xpath("//*[@id='RecipientName']");
const addressline_txt1 = By.xpath("//*[@id='text1Message']");
const addressline_txt2 = By.xpath("//*[@id='text2Message']");
const submitClassicCard = By.id("submitClassic");
const giftImg1 = By.xpath("//*[@id='eGiftCard']/img");
const amount2 = By.xpath("(//*[@id='amount25'])[2]");
const senderName_egc = By.xpath("//*[@id='senderNameEgc']");
const recepientName_egc = By.xpath("//*[@id='RecipientNameEgc']");
const emailAddress_egc = By.xpath("//*[@id='emailAddressEgc']");
const date_egc = By.xpath("//*[@id='date']");
const txtMsg = By.xpath("//*[@id='textAreaMessage']");
const submit_egiftcard = By.id("submitEgift");
const summary_subtotal = By.xpath("//ul/li[@class='summary-item summary-subtotal clearfix']/span[2]");
const shipping_review = By.xpath("//ul/li[@class='summary-item summary-shipping clearfix']/span[2]");
const taxOnReviewPage = By.xpath("//ul/li[@class='summary-item clearfix']/span[2]");
const totalOnReviewPage = By.xpath("//ul/li[@class='summary-item summary-total clearfix']/span[2]");
const headerPanel = By.id("c-header__userpanelrecognized");
const bagAccountBtn = By.xpath("//a[@class='nc-nav__account_button']");

const orderHistoryTxt = By.xpath("(//a[text()='Order History'])[1]");
const orderNumberTxt = By.xpath("//div[@class='order-history--overview-item overview--order-number']");
const showOrderDetailsTxt = By.xpath("//a[text()='Show Order Details']");
const hideOrderDetailsTxt = By.xpath("(//a[text()='Hide Order Details'])[1]");
const orderHistorySummary = By.xpath("//div[@class='order-history--details-summary']/div[text()='Shipping Summary']");
const paymentSummaryTxt = By.xpath("//div[@class='order-history--details-summary']/div[text()='Payment Summary']");
const orderSummaryTxt = By.xpath("//div[@class='order-history--details-summary']/div[text()='Order Summary']");
const orderHistoryProducts = By.xpath("//div[@class='c-order-history-recs']/div[@class='c-order-history-recs__products']");

const registerNowTxt = By.xpath("//h2[text()='Register Now']");
const passwordTxt = By.id("password");
const passwordConfTxt = By.id("passwordConf");
const registerSubmit = By.id("register-submit");
const registerMsgConf = By.xpath("//h3[@class='register-msg confirmation']");
const placeOrderBtn = By.xpath("//*[@id='order-summary__button-continue']");
const includeGiftchk = By.id("includesGifts");
const giftReceiptRadioBtn = By.xpath("//div[@class='gift-receipt-tooltip clearfix radio-checked']");
const footerCountryLink = By.xpath("//a[@class='footer__country-context__link']");
const globalFooter = By.id('global__footer');
const orderSummary1 = By.xpath("//*[@id='order-summary']/a[1]");

//Order summary page details
let subTotalOnReview;
let shippingOnReview;
let taxOnReview;
let totalOnReview;
let orderNumberLetExpressuser;

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
    } else {  // Production review checkout
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
        await brizateValidationForGold(currentUrl, creditPin)
    }
}

export const brizateValidationForGold = async (currentUrl, creditPin) => {
    if (currentUrl.indexOf("https://or.") > -1) {  // Production review checkout
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

export const goToGiftCardsPage = async () => {
    await driver.executeScript('window.scrollTo(0, 20000)')
    await waitSeconds(2)
    await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(giftCardLink));
    await waitSeconds(2)
    try {
        await driver.findElement(giftCardLink).click()
        await waitSeconds(2)
    } catch (err) { }

    await waitSeconds(2)
}

export const addClassicGiftCard = async () => {
    await driver.findElement(classicGiftCardImg).click()
    await waitSeconds(3)
    await driver.findElement(dollor_25).click()
    await driver.findElement(senderName_giftCard).sendKeys("test")
    await driver.findElement(recepientname).sendKeys("recipient test")
    await driver.findElement(addressline_txt1).sendKeys("line 1")
    await driver.findElement(addressline_txt2).sendKeys("line 2")
    await driver.findElement(submitClassicCard).click()
    await waitSeconds(3)
}

export const addEgiftCard = async () => {
    await driver.findElement(giftImg1).click()
    await waitSeconds(3)
    await driver.findElement(amount2).click()
    await waitSeconds(3)
    var date = new Date()
    await driver.findElement(senderName_egc).sendKeys("test")
    await driver.findElement(recepientName_egc).sendKeys("recipient test")
    await driver.findElement(emailAddress_egc).sendKeys("automationca@gmail.com")
    await driver.findElement(date_egc).sendKeys((date.getMonth() + 1) + '/' + date.getDate() + '/' + date.getFullYear())
    await driver.findElement(txtMsg).sendKeys("test message")
    await driver.findElement(submit_egiftcard).click()
    await waitSeconds(3)
}

export const saveOrderDetails = async () => {
    subTotalOnReview = await driver.findElement(summary_subtotal).getText();
    shippingOnReview = await driver.findElement(shipping_review).getText();
    taxOnReview = await driver.findElement(taxOnReviewPage).getText();
    totalOnReview = await driver.findElement(totalOnReviewPage).getText();
    orderNumberLetExpressuser = await driver.findElement(orderNumberTranslate).getText()
    console.log("order Id  > " + orderNumberLetExpressuser)
}

export const expressUserCheckoutValidation = async (currentUrl) => {
    if (currentUrl.indexOf("https://or.") > -1) {
        let subTotalOnOrderComplete = await driver.findElement(summary_subtotal).getText();
        let shippingOnOrderComplete = await driver.findElement(shipping_review).getText();
        let taxOnOrderComplete = await driver.findElement(taxOnReviewPage).getText();
        let totalOnOrderComplete = await driver.findElement(totalOnReviewPage).getText();

        if (subTotalOnReview == subTotalOnOrderComplete && shippingOnReview == shippingOnOrderComplete && taxOnReview == taxOnOrderComplete && totalOnReview == totalOnOrderComplete) {
            console.log("Order summary verified");
            await waitSeconds(1)
            let url = await driver.getCurrentUrl();
            if (url.includes("factory")) {
                const loggedInUser = await driver.findElement(headerPanel)
                expect(loggedInUser).toBeTruthy()
                await driver.actions().mouseMove(loggedInUser).perform();
                await waitSeconds(2)
            } else {
                const loggedInUser = await driver.findElement(bagAccountBtn)
                expect(loggedInUser).toBeTruthy()
                await driver.actions().mouseMove(loggedInUser).perform();
                await waitSeconds(2)
            }

            const orderHistory = await driver.findElement(orderHistoryTxt)
            expect(orderHistory).toBeTruthy()
            orderHistory.click()
            await waitSeconds(2)
            const orderNum = await driver.findElement(orderNumberTxt)
            expect(orderNum).toBeTruthy()
            let getOrderNum = await orderNum.getText()
            console.log("placed order is displaying in order history" + getOrderNum)
            getOrderNum.includes(orderNumberLetExpressuser)
            const showOrderDetails = await driver.findElement(showOrderDetailsTxt)
            expect(showOrderDetails).toBeTruthy()
            await showOrderDetails.click()
            await waitSeconds(1)
            const hideOrderDetails = await driver.findElement(hideOrderDetailsTxt)
            expect(hideOrderDetails).toBeTruthy()
            const shippingSummary = await driver.findElement(orderHistorySummary)
            expect(shippingSummary).toBeTruthy()
            const paymentSummary = await driver.findElement(paymentSummaryTxt)
            expect(paymentSummary).toBeTruthy()
            const orderSummary = await driver.findElement(orderSummaryTxt)
            expect(orderSummary).toBeTruthy()
            await waitSeconds(1)
            await hideOrderDetails.click()
            await waitSeconds(1)
            const bayNoteRecomendations = await driver.findElement(orderHistoryProducts)
            expect(bayNoteRecomendations).toBeTruthy()
            console.log("Order history verified")
            await waitSeconds(1)
        }
    } else {

    }
}

export const expressUserWithoutAddressValidation = async () => {
    await driver.findElement(firstName).sendKeys(guestuser.firstNameSA)
    await driver.findElement(lastName).sendKeys(guestuser.lastNameSA)
    await driver.findElement(addressline3).sendKeys(guestuser.address3)
    await driver.findElement(addressline2).sendKeys(guestuser.address2)
    await driver.findElement(addressline1).sendKeys(guestuser.address1)
    await driver.findElement(zipcode_guest).sendKeys(guestuser.zipcode)
    await waitSeconds(1)
    await driver.findElement(phoneNumberSA).sendKeys(guestuser.phoneNumSA)
    await waitSeconds(1)
    await driver.findElement(placeOrderBtn).click()
    // address confirmation page :
    await waitSeconds(2)
    try {
        await driver.findElement(addressGuest).then(function (webElement) {
            webElement.click();
        }, function (err) {
            if (err.state && err.state === 'no such element') {
                console.log('Element not found');
            } else {
                //    driver.promise.rejected(err);
                console.log('Element not found inside err');
            }
        });
    } catch (err) {
        //  throw err
    }
    //shipping & gift pageObjects
    await waitSeconds(2)
    await driver.findElement(includeGiftchk).click()
    await waitSeconds(1)
    expect(await driver.findElement(giftReceiptRadioBtn)).toBeTruthy()
    await driver.findElement(mainBtn_continue).click()

    //credit card details
    await waitSeconds(2)
    await driver.findElement(creditCardNo).sendKeys(creditcard.number)
    await driver.findElement(creditCardSecurityCode).sendKeys(creditcard.pin)
    await driver.findElement(creditCardExpiryMonth).sendKeys(creditcard.expirationMonth)
    await driver.findElement(creditCardExpiryYear).sendKeys(creditcard.expirationYear)
    await driver.findElement(creditCardName).sendKeys(creditcard.nameOnCard)
    await driver.findElement(mainBtn_continue).click()
    await waitSeconds(1)
}

export const validateExpressUserCheckoutOrder = async (currentUrl) => {
    if (currentUrl.indexOf("https://or.") > -1) {  // Production review checkout
        await waitSeconds(1)
        if (currentUrl.indexOf("factory.jcrew.com") > -1) {
            console.log(">> inside factory")
            await driver.findElement(orderSummary).click()
        } else {
            await driver.findElement(submitOrderBtn).click()
        }
        await waitSeconds(2)
        try {
            const bizrate = await driver.findElement(brizatePopupClose)
            expect(bizrate).toBeTruthy()
            bizrate.click()
            await waitSeconds(2)
        } catch (err) { }
        let orderNumberLet = await driver.findElement(orderNumberTranslate).getText()
        console.log("order Id let > " + orderNumberLet)
        await waitSeconds(2)
    }
}

export const registerNowValidation = async () => {
    expect(await driver.findElement(registerNowTxt)).toBeTruthy()
    await driver.findElement(passwordTxt).sendKeys("123tgb")
    await driver.findElement(passwordConfTxt).sendKeys("123tgb")
    await driver.findElement(registerSubmit).click()
    await waitSeconds(2)
    expect(await driver.findElement(registerMsgConf)).toBeTruthy()
    await waitSeconds(2)
}

export const scrollToFooter = async () =>{
    await waitSeconds(2)
    await driver.executeScript('window.scrollTo(0, 20000)')
    await waitSeconds(3)
    //const footer = await driver.findElement(By.id('global__footer'))
    await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(footerCountryLink));
    await waitSeconds(3)
    const footer = await driver.findElement(globalFooter)
    const location = await footer.findElement(footerCountryLink)
    await expect(location).toBeTruthy()
    await location.click()
    await driver.getCurrentUrl(url => {
      expect(url).toMatch('r/context-chooser')
    })
}

export const doOrder = async() =>{
    try {
        await driver.findElement(orderSummary1).then(continuesummary => {
          continuesummary.click()
          driver.sleep(2000)
          driver.findElement(mainBtn_continue).click()
          driver.findElement(placeOrderBtn).click()
        })
      } catch (err) { }
}

export const clickContextUser = async(contextchooser) =>{
  await driver.findElement(By.xpath("//span[text()='" + contextchooser + "']")).click()
  await waitSeconds(3)
}
