import { driver, defaultTimeout } from '../helpersMobile';
import { waitSeconds } from '../util/MobileMethods';

const each = require('jest-each')
const {until, Builder, By, Key } = require('selenium-webdriver')

const contextMenu = By.className("nc-mobile-nav__button hamburger");
const classicGiftCardImg = By.xpath("//a[@id='classicGiftCard']/img");
const eGiftCardImg = By.xpath("//a[@id='eGiftCard']/img");
const cardAmount = By.xpath("//*[@id='amount25']");
const senderName = By.xpath("//*[@id='senderName']");
const recipientName = By.xpath("//*[@id='RecipientName']");
const submitButton = By.name("gift_card_to_cart");
const firstProduct = By.xpath("(//span[contains(@class,'tile__detail--name')])[1]");
const secondProduct = By.xpath("(//span[contains(@class,'tile__detail--name')])[2]");
const selectSize = By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]");
const addToBagButton = By.id("btn__add-to-bag-wide");
const bagSize = By.className("nc-nav__bag-tab__count");
const saleCategory = By.xpath("//h2[contains(text(),'Sale')]");
const saleSubCategory = By.xpath("(//li/ul/a/li[text()='Shop All'])[1]");
const menCategory = By.xpath("//li[@class='hamburger-item']/h2[text()='Men']");
const suits = By.xpath("//li[text()='suits & tuxedos']");
const selectSuitProduct = By.xpath("(//div[@class='plus_detail_wrap'])[1]");
const accessories = By.xpath("//li[text()='accessories']");
const preSelectedSize = By.xpath("//li[contains(@class,'js-product__size sizes-list__item btn') and (contains(@class,'is-selected'))]");
const shoppingTongue = By.className("nc-nav__bag__checkout-button");
const colorElement = By.xpath("(//dt[@class='product__label'])[1]");
//Jcrew
const bagIcon_Jcrew = By.className("nc-nav__bag-tab__icon");
//Factory
const bagIcon_Factory = By.className("icon-header icon-header-bag icon-bag");


export const verifyItemsInBag = async () =>{
  expect(await driver.findElement(bagSize)).toBeTruthy();
  let ItemsInBag = await driver.findElement(bagSize).getText();
  console.log("Items in Bag: "+ItemsInBag);
}
export const clickOnBagIcon = async () => {
   await driver.findElement(bagIcon_Jcrew).click();
};

export const addGiftCardToBag = async (giftcardName) => {
    if(giftcardName=="classicGiftCard"){
      await driver.findElement(classicGiftCardImg).click();
    }else if(giftcardName=="eGiftCard"){
      await driver.findElement(eGiftCardImg).click();
    }
    await waitSeconds(2);
    await driver.findElement(cardAmount).click();
    await driver.findElement(senderName).sendKeys("test");
    await driver.findElement(recipientName).sendKeys("recipient test");
    await driver.findElement(submitButton).click();
    await waitSeconds(2);
    await verifyItemsInBag();
};

export const addSingleLineItemToBag = async () => {
  await driver.findElement(firstProduct).click();
  await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(colorElement));
  await driver.findElement(selectSize).click();
  await waitSeconds(1);
  await driver.findElement(addToBagButton).click();
  await waitSeconds(2);
  await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(bagIcon_Jcrew));
  await verifyItemsInBag();
  console.log("Item added to Bag successfully");
};

export const addMultiLineItemToBag = async () => {
  await driver.findElement(firstProduct).click();
  await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(colorElement));
  await driver.findElement(selectSize).click();
  await waitSeconds(1);
  await driver.findElement(addToBagButton).click();
  await waitSeconds(2);
  await driver.findElement(secondProduct).click();
  await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(colorElement));
  await driver.findElement(selectSize).click();
  await waitSeconds(1);
  await driver.findElement(addToBagButton).click();
  await waitSeconds(2);
  await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(bagIcon_Jcrew));
  await verifyItemsInBag();
  console.log("Item added to Bag successfully");
};

export const addSaleItemsToBag = async () => {
  await driver.findElement(contextMenu).click();
  await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(saleCategory));
  await waitSeconds(1);
  await driver.findElement(saleCategory).click();
  await driver.findElement(saleSubCategory).click();
  await driver.findElement(firstProduct).click();
  await waitSeconds(1);
  await driver.findElement(selectSize).click();
  await driver.findElement(addToBagButton).click();
  await waitSeconds(2);
  await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(bagIcon_Jcrew));
  await verifyItemsInBag();
  console.log("Sale Items added to Bag successfully");
};

export const addSuitItemsToBag = async () => {
  await driver.findElement(contextMenu).click();
  await driver.findElement(menCategory).click();
  await driver.findElement(suits).click();
  await driver.findElement(selectSuitProduct).click();
  await driver.findElement(selectSize).click();
  await driver.findElement(addToBagButton).click();
  await waitSeconds(2);
  await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(bagIcon_Jcrew));
  await verifyItemsInBag();
  console.log("Suit Items added to Bag successfully");
};

export const verifyPreSelectedSize = async () =>{
  await driver.findElement(contextMenu).click();
  await driver.findElement(menCategory).click();
  await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(accessories));
  await driver.findElement(accessories).click();
  await driver.findElement(selectSize).click();
  expect(await driver.findElement(preSelectedSize)).toBeTruthy();
  console.log("pre selected size chip is displayed")
};

export const verifyShoppingTongue = async () => {
  await driver.findElement(firstProduct).click();
  await waitSeconds(2);
  await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(colorElement));
  await waitSeconds(2);
  await driver.findElement(selectSize).click();
  await waitSeconds(1);
  await driver.findElement(addToBagButton).click();
  await waitSeconds(2);
  expect(await driver.findElement(shoppingTongue)).toBeTruthy();
};