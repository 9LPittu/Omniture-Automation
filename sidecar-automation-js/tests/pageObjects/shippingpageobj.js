import { driver } from '../helpers';
import { globals } from '../jestJcrewQaConfig';
import {goToShoppingBag} from '../pageObjects/shoppingbagobj';
import {login,loginInAfterCheckoutPage} from '../pageObjects/loginpageobj';


const { Builder, By, Key, until } = require('selenium-webdriver');




/**

**/
let shippingMethod = "Overnight";
  export const chooseShippingMethod = async ()=>{
    if(shippingMethod.equals("Overnight")){
      await driver.findElement(By.css("#method0")).click();
    }else if(shippingMethod.equals("Expedited")){
      await driver.findElement(By.css("#method1")).click();
    }else if(shippingMethod.equals("Standard")){
      await driver.findElement(By.css("#method2")).click();
    }else if(shippingMethod.equals("Economy")){
      await driver.findElement(By.css("#method3")).click();
    }
  };

export const verifyShippingMethodsPage = async () => {
   expect(await driver.findElement(By.css("#method0")).isDisplayed()).toBeTruthy();
   expect(await driver.findElement(By.css("#method1")).isDisplayed()).toBeTruthy();
   expect(await driver.findElement(By.css("#method2")).isDisplayed()).toBeTruthy();
   expect(await driver.findElement(By.css("#method3")).isDisplayed()).toBeTruthy();
  // expect(await driver.findElement(By.css("#method4")).isDisplayed()).toBeTruthy();
   expect(await driver.findElement(By.css("#includesGifts")).isDisplayed()).toBeTruthy();
   await driver.findElement(By.css("#includesGifts")).click();
   expect(await driver.findElement(By.css("#giftReceiptMessage0")).isDisplayed()).toBeTruthy();
   expect(await driver.findElement(By.css("#giftWrapService0")).isDisplayed()).toBeTruthy();
}
  //#includesGifts
  export const giftsYesRadio_Click = async ()=>{
    await driver.findElement(By.css("#includesGifts")).click();
  }
  //Just a Gift Receipt
  export const Just_a_Gift_Receipt_Click = async ()=>{
    await driver.findElement(By.css("#giftReceiptMessage0")).click();
  }
  export const Just_a_Gift_Receipt_Text = async ()=>{
    await driver.findElement(By.css("#gift-receipt-msg0")).sendKeys("Gift Text");

  }
  export const newGiftBox_Button = async ()=>{
    await driver.findElement(By.css(".item-button item-box")).click();
  }
  export const Gift_Wrapping_Service_Click = async ()=>{
    await driver.findElement(By.css("#giftWrapService0")).click();
  }
  export const continueOnShippingMethod = async ()=>{
    await driver.findElement(By.xpath("//*[@id='order-summary__button-continue']")).click()
  }

  export const checkout = async ()=>{
    await driver.findElement(By.xpath("//*[@id='button-checkout']")).click()
  }
//
//#giftWrapService0
