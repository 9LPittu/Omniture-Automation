import { driver, defaultTimeout } from '../helpersMobile';
import { creditcard, guestuser, Billing } from '../testdata/jcrewTestData';

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

export const doPaypalPayment = async () => {
    //Switching to paypal window
    await driver.switchTo().defaultContent();
    await driver.getAllWindowHandles().then(function gotWindowHandles(allhandles) {
        driver.switchTo().window(allhandles[allhandles.length - 1]);

    });

    //On paypal window
    await driver.sleep(1000)
    await driver.findElement(paypal_loginBtn).click()
    await driver.sleep(2000)
    await driver.findElement(paypal_loginEmail).sendKeys(Billing.Paypal_Email)
    await driver.sleep(2000)
    try {
        await driver.findElement(paypal_loginNxtBtn).click()
        await driver.sleep(2000)
    } catch (err) {
        console.log("no next button , as already login details saved")
    }
    await driver.findElement(paypal_passwordTxt).sendKeys(Billing.Paypal_Password)
    await driver.sleep(2000)
    await driver.findElement(continue_paypallogin).click()
    await driver.findElement(doPaymentBtn).click()
    await driver.sleep(2000)

    //Back to original site window
    await driver.switchTo().defaultContent();
    await driver.sleep(3000)
    let placeorderBtn = await driver.findElement(placeMyOrderBtn)
    expect(placeorderBtn).toBeTruthy()
    if (currentUrl.indexOf("https://or.") > -1) {
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