import { driver, defaultTimeout } from '../helpersMobile';
import { globals } from '../jestJcrewQaMobileConfig';
import {creditcard,guestuser } from '../testdata/jcrewTestData';

const { Builder, By, Key, until } = require('selenium-webdriver')

const shoppingBagHeader = By.xpath("//*[contains(@class,'shopping_bag')]");
const shoppingBagItems =  By.xpath("//*[contains(@class,'shopping_bag')]/p/span[2]")
const checkoutNowButton =  By.xpath("//span[text()='Check Out Now']")
const secureCheckOutHeader =  By.xpath("//*[contains(@class,'secure_checkout_title_container')]")
const shippingAddressModule =  By.xpath("//div[contains(@class,'address-module')]")
const paymentModule =  By.xpath("//div[contains(@class,'card-module')]")
const shippingMethodModule =  By.xpath("//div[contains(@class,'method-module')]")
//const cvv =  By.name("cvc")
const placeMyOrder =  By.xpath("//button[text()='Place My Order']")
const orderNumber =  By.xpath("//span[contains(@class,'_order_number')]")
//Shipping address
const newShippingAddress = By.xpath("//div[text()='Enter a new shipping address']")
const firstName = By.name("firstName")
const lastName = By.name("lastName")
const address1 = By.name("address1")
const zipcode = By.name("zip")
const phoneNum = By.name("phone")
const saveAddress = By.xpath("//button[text()='Save Address']")
//Paymentmethod
const newPaymentMethod = By.xpath("//div[text()='Enter a new payment method']")
const ccNum = By.name("cc-number")
const ccExp = By.name("cc-exp")
const cvv = By.name("cvc")
const savePaymentMethod = By.xpath("//*[text()='Save']")
//Guest user details
const checkoutAsGuest = By.xpath("//*[text()='Continue as Guest']")
const emailAddress = By.xpath("//div[text()='Email Address']")

//New elements
const paypalBtn = By.xpath("//div[@id='paypal-animation-container']")

export const verifyShoppingBagPage = async()=>{
  expect(driver.findElement(shoppingBagHeader)).toBeTruthy()
  expect(driver.findElement(shoppingBagItems)).toBeTruthy()
  console.log("Bag items are: "+shoppingBagItems.getText())
  expect(driver.findElement(checkoutNowButton)).toBeTruthy()
}

export const clickOnCheckOutNow = async()=>{
  await driver.sleep(1000)
  expect(driver.findElement(checkoutNowButton)).toBeTruthy()
  driver.findElement(checkoutNowButton).click()
  console.log("Clicked on checkout now button")
}

export const clickOnPaypalPayment = async () => {
 /* await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(paypalBtn));
  await driver.sleep(1000)
  expect(driver.findElement(paypalBtn)).toBeTruthy()
  await driver.findElement(paypalBtn).click()
  console.log("Clicked on paypal payment option")*/
  await driver.executeScript('window.scrollTo(0, 5000)')
  await driver.sleep(3000)
 // await driver.executeScript("arguments[0].click();", driver.findElement(paypalBtn));
 await driver.findElement(paypalBtn).click()
}

export const verifySecureCheckoutPage = async()=>{
  await driver.sleep(1000)
  expect(driver.findElement(secureCheckOutHeader)).toBeTruthy()
  expect(driver.findElement(shippingAddressModule)).toBeTruthy()
  expect(driver.findElement(paymentModule)).toBeTruthy()
  expect(driver.findElement(shippingMethodModule)).toBeTruthy()
  expect(driver.findElement(placeMyOrder)).toBeTruthy()
}

export const addShippingAddress = async()=>{
  await driver.sleep(1000)
  expect(driver.findElement(newShippingAddress)).toBeTruthy()
  await driver.findElement(newShippingAddress).click()
  await driver.sleep(1000)
  driver.findElement(firstName).sendKeys(driver.findElement(guestuser.firstNameSA))
  driver.findElement(lastName).sendKeys(driver.findElement(guestuser.lastNameSA))
  driver.findElement(address1).sendKeys(driver.findElement(guestuser.address1))
  await driver.sleep(2000)
  driver.findElement(zipcode).sendKeys(driver.findElement(guestuser.zipcode))
  driver.findElement(phoneNum).sendKeys(driver.findElement(guestuser.phoneNumSA))
  await driver.sleep(1000)
  driver.findElement(saveAddress).click()
}

export const addPaymentMethod = async () => {
  await driver.sleep(1000)
  expect(driver.findElement(newPaymentMethod)).toBeTruthy()
  driver.findElement(newPaymentMethod).click()
  await driver.sleep(1000)
  driver.findElement(ccNum).sendKeys(driver.findElement(creditcard.number))
  driver.findElement(ccExp).sendKeys(driver.findElement(creditcard.monyear))
  driver.findElement(cvv).sendKeys(driver.findElement(creditcard.pin))
  await driver.sleep(1000)
  driver.findElement(savePaymentMethod).click()
}
export const guestUserCheckout = async () => {
  expect(driver.findElement(checkoutAsGuest)).toBeTruthy()
  driver.findElement(checkoutAsGuest).click()
  await driver.sleep(1000)
  var x = Math.floor((Math.random() * 1000000) + 1);
  let userName = "AutomationTest"+x
  let email = "AutomationTest"+x+"@gmail.com"
  driver.findElement(emailAddress).sendKeys(email)
  driver.findElement(firstName).sendKeys(driver.findElement(guestuser.firstNameSA))
  driver.findElement(lastName).sendKeys(driver.findElement(guestuser.lastNameSA))
  driver.findElement(address1).sendKeys(driver.findElement(guestuser.address1))
  await driver.sleep(2000)
  driver.findElement(zipcode).sendKeys(driver.findElement(guestuser.zipcode))
  driver.findElement(phoneNum).sendKeys(driver.findElement(guestuser.phoneNumSA))
  driver.findElement(ccNum).sendKeys(driver.findElement(creditcard.number))
  driver.findElement(ccExp).sendKeys(driver.findElement(creditcard.monyear))
  cvv.sendKeys(creditcard.pin)
  await driver.sleep(5000)
}
export const placeOrder = async()=>{
  await driver.sleep(1000)
  let currentUrl = driver.getCurrentUrl()
  if (currentUrl.indexOf("https://or.") > -1) {  // Production review checkout
   try {
      //      console.log("inside securitycode")
      driver.findElement(cvv).sendKeys(driver.findElement(creditcard.pin))
        } catch (err ){ }
         await driver.sleep(3000)
            if (currentUrl.indexOf("factory.jcrew.com") > -1) {
              console.log(">> inside factory")
            //  await driver.findElement(By.xpath("//*[@id='orderSummaryContainer']/div/a")).click()
            } else {
              driver.findElement(placeMyOrder).click()
            }//spinner_container__2sg18
         await driver.sleep(10000)
         let getOrderNumber = driver.findElement(orderNumber).getText()
        console.log("order Id  > " + getOrderNumber)
        }

}
