import { driver, defaultTimeout } from '../helpersMobile';
import { globals } from '../jestJcrewQaMobileConfig';
import {creditcard } from '../testdata/jcrewTestData';

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
