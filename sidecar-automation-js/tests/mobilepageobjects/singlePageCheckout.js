import { driver, defaultTimeout } from '../helpersMobile';
import { globals } from '../jestJcrewQaMobileConfig';

const { Builder, By, Key, until } = require('selenium-webdriver')

const shoppingBagHeader = await driver.findElement(By.xpath("//*[contains(@class,'shopping_bag')]"));
const shoppingBagItems = await driver.findElement(By.xpath("//*[contains(@class,'shopping_bag')]/p/span[2]"))
const checkoutNowButton = await driver.findElement(By.xpath("//span[text()='Check Out Now']"))
const secureCheckOutHeader = await driver.findElement(By.xpath("//*[contains(@class,'secure_checkout_title_container')]"))

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
