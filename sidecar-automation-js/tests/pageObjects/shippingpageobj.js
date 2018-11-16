import { driver } from '../helpers';

const { Builder, By, Key, until } = require('selenium-webdriver');

//Gift and shipping related elements
const checkoutBtn = By.xpath("//*[@id='button-checkout']");
const shippingNavBtn = By.css("#nav-shipping");
const orderSummaryBtn = By.xpath("//*[@id='order-summary__button-continue']");
const giftWrapService = By.css("#giftWrapService0");
const itemBtnBox = By.css(".item-button item-box");
const justAgiftReceipt = By.css("#gift-receipt-msg0");
const giftReceiptMessage = By.css("#giftReceiptMessage0");
const giftsAcceptBtn = By.css("#includesGifts");
const shippingMethod0 = By.css("#method0");
const shippingMethod1 = By.css("#method1");
const shippingMethod2 = By.css("#method2");
const shippingMethod3 = By.css("#method3");

let shippingMethod = "Overnight";
export const chooseShippingMethod = async () => {
  if (shippingMethod.equals("Overnight")) {
    await driver.findElement(shippingMethod0).click();
  } else if (shippingMethod.equals("Expedited")) {
    await driver.findElement(shippingMethod1).click();
  } else if (shippingMethod.equals("Standard")) {
    await driver.findElement(shippingMethod2).click();
  } else if (shippingMethod.equals("Economy")) {
    await driver.findElement(shippingMethod3).click();
  }
};

export const verifyShippingMethodsPage = async () => {
  expect(await driver.findElement(shippingMethod0).isDisplayed()).toBeTruthy();
  expect(await driver.findElement(shippingMethod1).isDisplayed()).toBeTruthy();
  expect(await driver.findElement(giftsAcceptBtn).isDisplayed()).toBeTruthy();
  await driver.findElement(giftsAcceptBtn).click();
  expect(await driver.findElement(giftReceiptMessage).isDisplayed()).toBeTruthy();
  expect(await driver.findElement(giftWrapService).isDisplayed()).toBeTruthy();
}
//#includesGifts
export const giftsYesRadio_Click = async () => {
  await driver.findElement(giftsAcceptBtn).click();
}
//Just a Gift Receipt
export const Just_a_Gift_Receipt_Click = async () => {
  await driver.findElement(giftReceiptMessage).click();
}
export const Just_a_Gift_Receipt_Text = async () => {
  await driver.findElement(justAgiftReceipt).sendKeys("Gift Text");

}
export const newGiftBox_Button = async () => {
  await driver.findElement(itemBtnBox).click();
}
export const Gift_Wrapping_Service_Click = async () => {
  await driver.findElement(giftWrapService).click();
}
export const continueOnShippingMethod = async () => {
  await driver.findElement(orderSummaryBtn).click()
}

export const checkout = async () => {
  await driver.findElement(checkoutBtn).click()
}

export const shippingNav = async () => {
  await driver.findElement(shippingNavBtn).click();
  await driver.sleep(5000);
}

