import { driver } from '../helpers';
import { globals } from '../jestJcrewQaConfig';
import {goToShoppingBag} from '../pageObjects/shoppingbagobj';
import {login,loginInAfterCheckoutPage} from '../pageObjects/loginpageobj';
import { Credit_Debit_Card , Billing} from '../testdata/jcrewTestData';
import { waitSeconds } from '../util/commonutils';

const { Builder, By, Key, until } = require('selenium-webdriver');

const paypalRadio = By.css("#paypalPayment");
const paypalButton = By.xpath("//div[@data-funding-source='paypal']");
const loginButton = By.xpath("//a[text()='Log In']");
const email = By.css("#email");
const next = By.css("#btnNext");
const password = By.css("#password");
const btnLogin = By.css("#btnLogin");
const continueButton = By.css("#confirmButtonTop");
const placeMyOrder = By.css("#button-submitorder");
const orderPlaced = By.xpath("//img[@src='https://images.bizrateinsights.com/eval/survey/invite_template/custom/jcrew_714.jpg']");
const closeOrderPlaced = By.xpath("//div[@class='brdialog-close']");
const verifyOrderNumber = By.xpath("//span[@id='confirmation-number']/span[2]");
const submitButton = By.xpath("//input[@type='submit']");
let Name_On_Card = "Visa"

//Payment method elements
const creditPaymentRadioBtn = By.xpath("//label[@class='form-label radio-checked']/input[@id='creditDebitPayment']");
const creditDebitPaymentBtn = By.css("#creditDebitPayment");
const orderSummaryBtn = By.css("#order-summary__button-continue");
const securityCodeCSS = By.css("#securityCode");
const imgBizrateinSights = By.xpath("//img[@src='https://images.bizrateinsights.com/eval/survey/invite_template/custom/jcrew_714.jpg']");
const paypalBtn = By.id("paypalPayment");
const paypalbutton = By.xpath("(//div[contains(@class,'paypal-button')])[3]");
const xcomponent = By.xpath("//iframe[@class='xcomponent-component-frame xcomponent-visible']");
const emailPaypal = By.id("email");
const buttonNext = By.id("btnNext");
const passwordTxt = By.id("password");
const loginBtn = By.id("btnLogin");
const confirmBtnTop = By.id("confirmButtonTop");
const orderNoTranslate = By.xpath("//span[@class='order-number notranslate']");

export const paymentMethod = async (paymentType) => {
  if (paymentType == "Credit/Debit_Card") {
    console.log(await driver.findElement(creditPaymentRadioBtn).isDisplayed())
    if (await driver.findElement(creditPaymentRadioBtn).isDisplayed()) {
      let url = await driver.getCurrentUrl();
      if ((url.indexOf("www.jcrew.com") > -1) || (url.indexOf("factory.jcrew.com") > -1)) {
         await waitSeconds(1)
        await driver.findElement(creditDebitPaymentBtn).isDisplayed();
      } else if ((url.indexOf("or.jcrew.com") > -1) || (url.indexOf("or.factory.jcrew.com") > -1)) {
        let card = "(//span[text()='" + Name_On_Card + "']/parent::label/input[@class='address-radio'])[1]";
        console.log(card);
        await driver.findElement(By.xpath(card)).click();
         await waitSeconds(1)
        await driver.findElement(orderSummaryBtn).click();
         await waitSeconds(1)
        //#securityCode
        await driver.findElement(securityCodeCSS).sendKeys(Credit_Debit_Card.Security_Code);
        //button-submitorder
        let url = await driver.getCurrentUrl();
        console.log(url);
        await driver.findElement(placeMyOrder).click();
         await waitSeconds(1)
        try {
          await driver.findElement(imgBizrateinSights).isDisplayed();
          await driver.findElement(closeOrderPlaced).click();
        } catch (err) {
        }
        let orderNumer = await driver.findElement(verifyOrderNumber).getText();
        console.log("Order Number is : " + orderNumer)
      }
    } else {
      await driver.findElement(creditDebitPaymentBtn).click();
    }
  } else if (paymentType == "Paypal") {
     await waitSeconds(1)
    await driver.findElement(paypalBtn).click()
     await waitSeconds(1)
    //await driver.findElement(paypalbutton).click()
     await waitSeconds(15)
    var parent = await driver.getWindowHandle();
         await waitSeconds(5)
    //await driver.switchTo().frame(await driver.findElement(xcomponent))
    //await driver.switchTo().window(1);
     await waitSeconds(5)
    let url = await driver.getCurrentUrl();
    console.log(url);
    if (url.indexOf("www.jcrew.com") > -1) {
      await driver.wait(until.elementLocated(paypalbutton), 5000).isDisplayed();
    } else {
      await driver.wait(until.elementLocated(paypalButton), 50000).click();
      await driver.wait(until.elementLocated(paypalbutton), 50000).click();
      await driver.switchTo().defaultContent();
      await driver.getAllWindowHandles().then(function gotWindowHandles(allhandles) {
        driver.switchTo().window(allhandles[allhandles.length - 1]);
      });
      console.log("paypal window")
      await driver.sleep(40000)
      await driver.findElement(loginButton).click()
      console.log("clicked login")
       await waitSeconds(10)
      await driver.findElement(emailPaypal).sendKeys(Billing.Paypal_Email)
      console.log("entered uname")
       await waitSeconds(1)
      await driver.findElement(buttonNext).click()
       await waitSeconds(1)
      console.log("clicked next")
       await waitSeconds(10)
      await driver.findElement(passwordTxt).sendKeys(Billing.Paypal_Password)
      console.log("entered password")
       await waitSeconds(1)
      await driver.findElement(loginBtn).click()
      await driver.sleep(25000)
      await driver.findElement(confirmBtnTop).click()
      console.log("clicked continue")
       await waitSeconds(10)
      await driver.switchTo().window(parent)
       await waitSeconds(10)
      await driver.wait(until.elementLocated(placeMyOrder), 5000).click();
       await waitSeconds(10)
      try {
        const bizrate = await driver.findElement(closeOrderPlaced)
        expect(bizrate).toBeTruthy()
        await bizrate.click()
         await waitSeconds(2)
      } catch (err) {
        console.log("bizrate ::"+err)
      }
      let orderNumberLet = await driver.findElement(orderNoTranslate).getText()
      console.log("order Id  > " + orderNumberLet)
       await waitSeconds(1)
    }
  } else {

  }
}

export const addNewCard = async () => {
  await driver.findElement(By.css("#address-entry-new")).click();
}

export const clickOnCreditOrDebitCard = async () => {
  await driver.findElement(creditDebitPaymentBtn).click();
}

export const clikOnPayPal = async () => {
  await driver.findElement(By.css("#paypalPayment")).click();
}

function testMethod(a, b) {
  console.log(a + b);
}

exports.testMethod = testMethod;
