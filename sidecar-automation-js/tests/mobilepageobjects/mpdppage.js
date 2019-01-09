import { driver, defaultTimeout } from '../helpersMobile';
import { waitSeconds } from '../util/MobileMethods';
import { closeEmailCapturePopUp } from './mhomepageobj';

const {until, Builder, By, Key } = require('selenium-webdriver')

const contextMenu = By.className("nc-mobile-nav__button hamburger");
const classicGiftCardImg = By.xpath("//a[@id='classicGiftCard']/img");
const eGiftCardImg = By.xpath("//a[@id='eGiftCard']/img");
const cardAmount = By.xpath("//*[@id='amount25']");
const senderName = By.xpath("//*[@id='senderName']");
const recipientName = By.xpath("//*[@id='RecipientName']");
const addressline_txt1 = By.xpath("//*[@id='text1Message']");
const addressline_txt2 = By.xpath("//*[@id='text2Message']");
const submitButton = By.id("submitClassic");
//const submitButton = By.name("gift_card_to_cart");
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
const productColorName = By.xpath("//ul[@class='item-description']/li[3]/span");
const productEditItem = By.xpath("//a[@class='item-edit']");
const productColorList = By.xpath("(//li[contains(@class,'js-product__color colors-list__item') and not(contains(@class,'is-unavailable'))])[1]");
const updateBag = By.xpath("//button[contains(text(),'Update Bag')]");

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
    await driver.findElement(addressline_txt1).sendKeys("line 1")
    await driver.findElement(addressline_txt2).sendKeys("line 2")
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
  await waitSeconds(2);
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
  await driver.navigate().back()
  await waitSeconds(2);
  await closeEmailCapturePopUp()
  await driver.findElement(secondProduct).click();
  await waitSeconds(2);
  await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(colorElement));
  await waitSeconds(2);
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
  await waitSeconds(1);
  await driver.findElement(menCategory).click();
  await waitSeconds(1);
  await driver.findElement(suits).click();
  await waitSeconds(1);
  await driver.findElement(selectSuitProduct).click();
  await waitSeconds(1);
  await driver.findElement(selectSize).click();
  await waitSeconds(1);
  await driver.findElement(addToBagButton).click();
  await waitSeconds(2);
  await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(bagIcon_Jcrew));
  await waitSeconds(2);
  await verifyItemsInBag();
  console.log("Suit Items added to Bag successfully");
};

export const verifyPreSelectedSize = async () =>{
  await driver.findElement(contextMenu).click();
  await waitSeconds(2)
  await driver.findElement(menCategory).click();
  await waitSeconds(2)
  await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(accessories));
  await waitSeconds(2)
  await driver.findElement(accessories).click();
  await waitSeconds(2)
  await driver.findElement(firstProduct).click();
  await waitSeconds(2);
  await driver.findElement(selectSize).click();
  await waitSeconds(2)
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

export const editItemAddedToBag = async () => {
  let result = false;
  try {
    await waitSeconds(1)
    let colrNameBeforeEdit = await driver.findElement(productColorName).getText()
    console.log("previous color :::"+colrNameBeforeEdit)
    await driver.findElement(productEditItem).click()
    await waitSeconds(2)
    await driver.findElement(productColorList).click()
    await waitSeconds(2)
    await driver.findElement(updateBag).click()
    await waitSeconds(2)
    let bagIcon = await driver.findElement(By.xpath("//div[@class='nc-nav__bag-tab__icon']"))
    expect(bagIcon).toBeTruthy()
    await bagIcon.click()
    await waitSeconds(1)
    let colorNameAfterEdit = await driver.findElement(productColorName).getText()
    console.log("color after edit :::"+colrNameBeforeEdit)
    if (!(colrNameBeforeEdit == colorNameAfterEdit)) {
      result = true;
      console.log("Color displayed in chip box after edited the item color >> ")
    }
  } catch (err) {
    console.log("error in editing item added to bag" + err)
  }
  return result;
}

export const validateShoppingBagPage = async () =>{
  expect(await driver.findElement(By.id("zipcode"))).toBeTruthy()
  console.log("Zip code text box is displaying")

  expect(await driver.findElement(By.xpath("//a[@class='button-general button-paypal']"))).toBeTruthy()
  console.log("Paypal payment method is displaying")

  await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.id("summary-promo-header")));
  await driver.sleep(2000)

  expect(await driver.findElement(By.id("summary-promo-header"))).toBeTruthy()
  await driver.findElement(By.id("hasPromo")).click()
  await driver.sleep(1000)

  expect(await driver.findElement(By.xpath("//input[@data-textboxid='promoCode']"))).toBeTruthy()
  console.log("Promo code text box is displaying")
  
  expect(await driver.findElement(By.id("summary-gift-card-header"))).toBeTruthy()
  await driver.findElement(By.id("hasGiftCard")).click()
  await driver.sleep(1000)

  expect(await driver.findElement(By.xpath("//input[@data-textboxid='giftCard']"))).toBeTruthy()
  console.log("Gift card payment method is displaying")

  expect(await driver.findElement(By.xpath("//a[text()='What is your return policy?']"))).toBeTruthy()
  expect(await driver.findElement(By.xpath("//a[text()='When can I expect my order?']"))).toBeTruthy()
  console.log("links are displaying")
}
