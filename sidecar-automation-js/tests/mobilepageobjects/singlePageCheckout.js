import { driver, defaultTimeout } from '../helpersMobile';
import { globals } from '../jestJcrewQaMobileConfig';
import {creditcard,guestuser } from '../testdata/jcrewTestData';

const { Builder, By, Key, until } = require('selenium-webdriver')

const shoppingBagHeader =  driver.findElement(By.xpath("//*[contains(@class,'shopping_bag')]"));
const shoppingBagItems =  driver.findElement(By.xpath("//*[contains(@class,'shopping_bag')]/p/span[2]"))
const checkoutNowButton =  driver.findElement(By.xpath("//span[text()='Check Out Now']"))
const secureCheckOutHeader =  driver.findElement(By.xpath("//*[contains(@class,'secure_checkout_title_container')]"))
const shippingAddressModule =  driver.findElement(By.xpath("//div[contains(@class,'address-module')]"))
const paymentModule =  driver.findElement(By.xpath("//div[contains(@class,'card-module')]"))
const shippingMethodModule =  driver.findElement(By.xpath("//div[contains(@class,'method-module')]"))
const cvv =  driver.findElement(By.name("cvc"))
const placeMyOrder =  driver.findElement(By.xpath("//button[text()='Place My Order']"))
const orderNumber =  driver.findElement(By.xpath("//span[contains(@class,'_order_number')]"))
//Shipping address
const newShippingAddress = driver.findElement(By.xpath("//div[text()='Enter a new shipping address']"))
const firstName = driver.findElement(By.name("firstName"))
const lastName = driver.findElement(By.name("lastName"))
const address1 = driver.findElement(By.name("address1"))
const zipcode = driver.findElement(By.name("zip"))
const phoneNum = driver.findElement(By.name("phone"))
const saveAddress = driver.findElement(By.xpath("//button[text()='Save Address']"))
//Paymentmethod
const newPaymentMethod = driver.findElement(By.xpath("//div[text()='Enter a new payment method']"))
const ccNum = driver.findElement(By.name("cc-number"))
const ccExp = driver.findElement(By.name("cc-exp"))
const cvv = driver.findElement(By.name("cvc"))
const savePaymentMethod = driver.findElement(By.xpath("//*[text()='Save']"))
//Guest user details
const checkoutAsGuest = driver.findElement(By.xpath("//*[text()='Continue as Guest']"))
const emailAddress = driver.findElement(By.xpath("//div[text()='Email Address']"))






export const verifyShoppingBagPage = async()=>{
  expect(shoppingBagHeader).toBeTruthy()
  expect(shoppingBagItems).toBeTruthy()
  console.log("Bag items are: "+shoppingBagItems.getText())
  expect(checkoutNowButton).toBeTruthy()
}

export const clickOnCheckOutNow = async()=>{
  await driver.sleep(1000)
  expect(checkoutNowButton).toBeTruthy()
  checkoutNowButton.click()
  console.log("Clicked on checkout now button")
}

export const verifySecureCheckoutPage = async()=>{
  await driver.sleep(1000)
  expect(secureCheckOutHeader).toBeTruthy()
  expect(shippingAddressModule).toBeTruthy()
  expect(paymentModule).toBeTruthy()
  expect(shippingMethodModule).toBeTruthy()
  expect(placeMyOrder).toBeTruthy()
}

export const addShippingAddress = async()=>{
  await driver.sleep(1000)
  expect(newShippingAddress).toBeTruthy()
  await newShippingAddress.click()
  await driver.sleep(1000)
  firstName.sendKeys(guestuser.firstNameSA)
  lastName.sendKeys(guestuser.lastNameSA)
  address1.sendKeys(guestuser.address1)
  await driver.sleep(2000)
  zipcode.sendKeys(guestuser.zipcode)
  phoneNum.sendKeys(guestuser.phoneNumSA)
  await driver.sleep(1000)
  saveAddress.click()
}

export const addPaymentMethod() = async()=>{
  await driver.sleep(1000)
  expect(newPaymentMethod).toBeTruthy()
  newPaymentMethod.click()
  await driver.sleep(1000)
  ccNum.sendKeys(creditcard.number)
  ccExp.sendKeys(creditcard.monyear)
  cvv.sendKeys(creditcard.pin)
  await driver.sleep(1000)
  savePaymentMethod.click()
}
export const guestUserCheckout() = async()=>{
  expect(checkoutAsGuest).toBeTruthy()
  checkoutAsGuest.click()
  await driver.sleep(1000)
  var x = Math.floor((Math.random() * 1000000) + 1);
  let userName = "AutomationTest"+x
  let email = "AutomationTest"+x+"@gmail.com"
  emailAddress.sendKeys(email)
  firstName.sendKeys(guestuser.firstNameSA)
  lastName.sendKeys(guestuser.lastNameSA)
  address1.sendKeys(guestuser.address1)
  await driver.sleep(2000)
  zipcode.sendKeys(guestuser.zipcode)
  phoneNum.sendKeys(guestuser.phoneNumSA)
  ccNum.sendKeys(creditcard.number)
  ccExp.sendKeys(creditcard.monyear)
  cvv.sendKeys(creditcard.pin)
  await driver.sleep(5000)
}
export const placeOrder = async()=>{
  await driver.sleep(1000)
  let currentUrl = driver.getCurrentUrl()
  if (currentUrl.indexOf("https://or.") > -1) {  // Production review checkout
   try {
      //      console.log("inside securitycode")
              cvv.sendKeys(creditcard.pin)
        } catch (err ){ }
         await driver.sleep(3000)
            if (currentUrl.indexOf("factory.jcrew.com") > -1) {
              console.log(">> inside factory")
            //  await driver.findElement(By.xpath("//*[@id='orderSummaryContainer']/div/a")).click()
            } else {
              placeMyOrder.click()
            }//spinner_container__2sg18
         await driver.sleep(10000)
         let getOrderNumber = orderNumber.getText()
        console.log("order Id  > " + getOrderNumber)
        }

}
