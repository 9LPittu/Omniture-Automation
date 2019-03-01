import { driver, defaultTimeout } from '../helpersMobile';
import { creditcard, Billing } from '../testdata/jcrewTestData';
import { waitSeconds } from '../util/MobileMethods';

const { Builder, By, Key, until } = require('selenium-webdriver')

const paypal_loginBtn = By.xpath("//a[@class='btn full ng-binding'][contains(.,'Log In')]")
const paypal_loginEmail = By.xpath("//input[@id='email']")
const paypal_loginNxtBtn = By.xpath("//button[@id='btnNext']")
const paypal_passwordTxt = By.xpath("//input[@id='password']")
const placeMyOrderBtn = By.xpath("//button[@class='button__2PXcN'][contains(text(),'Place My Order')]")
const continue_paypallogin = By.xpath("//button[@id='btnLogin']")
const doPaymentBtn = By.xpath("//input[@id='confirmButtonTop']")
const orderConfirmationMsg = By.xpath("//p[@class='_order_heading__1ECq_']")
const orderNumber = By.xpath("//p[@class='order_row__4HaoA']")

const billingPayment = By.xpath("//a[text()='Billing']");
const scrollRefLabel = By.xpath("//span[@class='form-label']");
const ccNumber = By.xpath("//input[@id='creditCardNumber']");
const ccSecurityCode = By.xpath("//input[@id='securityCode']");
const ccExpirationMonth = By.id('expirationMonth');
const ccExpirationYear = By.id('expirationYear');
const ccNameOnCard = By.xpath("//input[@id='nameOnCard']");
const ccDetailsSubmit = By.xpath("//a[@class='button-submit']");
const paymentMethod1 = By.xpath("//div[@class='payment-method first-card same-billing last']");
const ccDetailsUpdateIcon = By.xpath("//a[@data-bma='update_payment_method']");
const ccDetailsEditIcon = By.xpath("//a[@class='item-edit']");
const choosePaymentCard = By.xpath("//h3[text()='Choose a Credit or Debit Card:']");
const cardToRemoveChkBox = By.xpath("(//li/label[contains(@class,'form-label') and not(contains(@class,'checked'))])[1]");
const cardRemoveConfirm = By.xpath("//span[@class='address-actions radio-checked']/a[@class='item-remove']");

const shippingAddressEdit = By.css("#nav-shipping");
const shippingAddressNew = By.css("#address-new");
const addressFirstName = By.css("#firstNameAM");
const addressLastName = By.css("#lastNameAM");
const addressLine3 = By.css("#address3");
const addressLine1 = By.css("#address1");
const addressLine2 = By.css("#address2");
const addressZipcode = By.css("#zipcode");
const phoneNumber = By.css("#phoneNumAM");
const addressSubmit = By.css("#submit-new-shipping-address");
const addressIndex = By.xpath("(//input[@name='SelectedAddIndex'])[2]");
const finalAddressSelection = By.xpath("//a[@class='button-submit']");

const shippingOptions = By.xpath("//a[text()='Shipping Options']");
const overNightShippingMethod = By.xpath("(//input[@name='shippingMethod'])[1]");
const saturdayShippingMethod = By.xpath("(//input[@name='shippingMethod'])[2]");
const ExpeditedShippingMethod = By.xpath("(//input[@name='shippingMethod'])[3]");
const standardShippingMethod = By.xpath("(//input[@name='shippingMethod'])[4]");

export const doPaypalPayment = async () => {
    //Switching to paypal window
    await driver.switchTo().defaultContent();
    await driver.getAllWindowHandles().then(function gotWindowHandles(allhandles) {
        driver.switchTo().window(allhandles[allhandles.length - 1]);
    });

    //On paypal window
    await waitSeconds(2)
    await driver.findElement(paypal_loginBtn).click()
    await waitSeconds(2)
    await driver.findElement(paypal_loginEmail).sendKeys(Billing.Paypal_Email)
    await waitSeconds(2)
    try {
        await driver.findElement(paypal_loginNxtBtn).click()
        await waitSeconds(2)
    } catch (err) {
        console.log("no next button , as already login details saved")
    }
    await driver.findElement(paypal_passwordTxt).sendKeys(Billing.Paypal_Password)
    await waitSeconds(2)
    await driver.findElement(continue_paypallogin).click()
    await driver.findElement(doPaymentBtn).click()
    await waitSeconds(2)

    //Back to original site window
    await driver.switchTo().defaultContent();
    await waitSeconds(3)
    let placeorderBtn = await driver.findElement(placeMyOrderBtn)
    expect(placeorderBtn).toBeTruthy()
    if (currentUrl.indexOf("https://uat.") > -1) {
        //Place order 
        await placeorderBtn.click()
        //Placed order validation
        let orderConfirmationMsgTxt = await driver.wait(until.elementLocated(orderConfirmationMsg), defaultTimeout)
        expect(orderConfirmationMsgTxt).toBeTruthy()
        console.log("order confirmation msg::" + orderConfirmationMsgTxt.getText())
        expect(orderConfirmationMsgTxt.getText(), "Your order is confirmed!").toBeTruthy()
        //Print order number
        console.log("::::::::::" + await driver.findElement(orderNumber).getText())
    }
}

export const addCreditCard = async () => {
    await driver.findElement(billingPayment).click()
    await waitSeconds(1)
    await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(scrollRefLabel));
    await waitSeconds(1)
    await driver.findElement(scrollRefLabel).click();
    await waitSeconds(1)
    await driver.findElement(ccNumber).sendKeys(creditcard.number);
    await driver.findElement(ccSecurityCode).sendKeys(creditcard.pin);
    await driver.findElement(ccExpirationMonth).sendKeys(creditcard.expirationMonth);
    await driver.findElement(ccExpirationYear).sendKeys(creditcard.expirationYear);
    await driver.findElement(ccNameOnCard).sendKeys(creditcard.nameOnCard);
    await waitSeconds(1)
    await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(ccDetailsSubmit));
    await waitSeconds(1)
    await driver.findElement(ccDetailsSubmit).click();
    await waitSeconds(1)
    expect(await driver.findElement(paymentMethod1)).toBeTruthy()
    await driver.findElement(paymentMethod1).click();
}

export const editAddedCreditCard = async () => {
    await driver.findElement(billingPayment).click()
    await waitSeconds(1)
    await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(ccDetailsEditIcon));
    await waitSeconds(1)
    await driver.findElement(ccDetailsEditIcon).isDisplayed();
    await driver.findElement(ccDetailsEditIcon).click();
    await waitSeconds(1)
    await driver.findElement(ccNumber).clear().sendKeys(creditcard.number);
    await driver.findElement(ccSecurityCode).sendKeys("123");
    await driver.findElement(ccExpirationMonth).sendKeys('06');
    await driver.findElement(ccExpirationYear).sendKeys('2028');
    await driver.findElement(ccNameOnCard).sendKeys("tester");
    await waitSeconds(1)
    await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(ccDetailsUpdateIcon));
    await waitSeconds(1)
    await driver.findElement(ccDetailsUpdateIcon).click()
    await waitSeconds(1)
}

export const removeAddedCreditCard = async () => {
    await waitSeconds(1)
    await driver.findElement(billingPayment).click()
    await waitSeconds(1)
    await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(choosePaymentCard));
    await waitSeconds(1)
    if (await driver.findElement(cardToRemoveChkBox).isDisplayed()) {
        await driver.findElement(cardToRemoveChkBox).click()
        await waitSeconds(1)
        await driver.findElement(cardRemoveConfirm).click();
        await waitSeconds(1)
    }
}

export const addEditRemoveAddress = async () => {
    //#address-new
    await driver.findElement(shippingAddressEdit).click();
    await driver.findElement(shippingAddressNew).click();
    await driver.findElement(addressFirstName).sendKeys("Auto Tester1 FN");
    await driver.findElement(addressLastName).sendKeys("Auto Tester1 LN");
    await driver.findElement(addressLine3).sendKeys("Nothing");
    await driver.findElement(addressLine1).sendKeys("770 broadway");
    //address2
    await driver.findElement(addressLine2).sendKeys("address test");
    await driver.findElement(addressZipcode).sendKeys("10003");
    await waitSeconds(5)
    await driver.findElement(phoneNumber).sendKeys("9658742361");
    await driver.findElement(addressSubmit).click();
    await waitSeconds(1)
    await driver.findElement(addressIndex).click()
    await waitSeconds(1)
    await driver.findElement(finalAddressSelection).click();
    await waitSeconds(1)
}

export const verifyAllShippingMethods = async () =>{
  await driver.findElement(shippingOptions).click()
  await waitSeconds(1)
  // OverNight
  let overNightOpt =  await driver.findElement(overNightShippingMethod)
  await overNightOpt.isDisplayed()
  expect(overNightOpt).toBeTruthy()
  await overNightOpt.click();

  // Saturday
  let saturdayOpt =  await driver.findElement(saturdayShippingMethod)
  await saturdayOpt.isDisplayed()
  expect(saturdayOpt).toBeTruthy()
  await saturdayOpt.click();

   // Expedited
  let expeditedOpt =  await driver.findElement(ExpeditedShippingMethod)
  await expeditedOpt.isDisplayed()
  expect(expeditedOpt).toBeTruthy()
  await expeditedOpt.click();

  // Standard
  let standardOpt =  await driver.findElement(standardShippingMethod)
  await standardOpt.isDisplayed()
  expect(standardOpt).toBeTruthy()
  await standardOpt.click();
}