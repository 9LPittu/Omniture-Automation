import { driver, defaultTimeout } from '../helpers';
import { globals } from '../jestJcrewQaConfig';
import { load, closeIconInPAP } from '../pageObjects/jcrewdesktoppageobj';
import { waitSeconds } from '../util/commonutils';

const { Builder, By, Key, until } = require('selenium-webdriver');

//Product description , gift adding related elements
const giftCard = By.xpath("//div[contains(text(),'The J.Crew Gift Card')]");
//By.xpath("//div[text()='The J.Crew Gift Card']")
const classicGiftCard = By.xpath("//a[@id='classicGiftCard']");
const amountList = By.xpath("(//div[@id='amountList']/a)[1]");
const giftFromText = By.xpath("//h3[text()='From']");
const senderName = By.id("senderName");
const recipientName = By.id("RecipientName");
const submitClassicGiftCard = By.xpath("//input[@id='submitClassic']");
const header_cart_factory = By.id("js-header__cart");
const cartSize_factory = By.xpath("//span[@class='js-cart-size']");
//const bagButtonIcon = By.xpath("//div[@class='nc-nav__bag-button__icon']"); //old xpath
const bagButtonIcon = By.xpath("//div[@class='nc-nav__bag-tab__icon']");
//const bagSize = By.xpath("//div[@class='nc-nav__bag-button__count']"); //old xpath
const bagSize = By.xpath("//div[@class='nc-nav__bag-tab__count'] ");
const plusProductGrid3N = By.xpath("(//div[@class='plus_product  plusGrid3N TheSuitShop Ludlow '])[1]");
const productPrice = By.xpath("//div[@class='product__price']");
const productSizeListBtn = By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]");
const addToBag = By.id("btn__add-to-bag-wide");
const plusProductGrid = By.xpath("(//div[@class='c-product__photos'])[1]");
const productColorName = By.xpath("//ul[@class='item-description']/li[3]/span");
const productEditItem = By.xpath("//a[@class='item-edit']");
const productColorList = By.xpath("(//li[contains(@class,'js-product__color colors-list__item') and not(contains(@class,'is-unavailable'))])[2]");
const updateBag = By.xpath("//button[contains(text(),'Update Bag')]");
const productColorsListItems = By.xpath("//div[@class='c-product__colors']");

//Department related Elements
const department_women = By.xpath("//li[@data-department='women']");
const women_jewellery = By.xpath("//span[text()='Jewelry']");
const category_women = By.xpath("//a[text()='Women']");
const jewellery_item1 = By.xpath("(//a[text()='jewelry'])[1]");
const product_pics = By.xpath("(//div[@class='c-product__photos'])[1]");
const product_size_listBtn = By.xpath("//li[contains(@class,'js-product__size sizes-list__item btn') and (contains(@class,'is-selected'))]");
const product_size_unavailable = By.xpath(".//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))]")
const wishListBag = By.id("btn__wishlist-wide");

//Shopping bag elements
const zipcode = By.id("zipcode");
const paypalBtn = By.xpath("//a[@class='button-general button-paypal']");
const summaryPromo = By.id("summary-promo-header");
const promocodeTxt = By.xpath("//input[@data-textboxid='promoCode']");
const summaryGiftCard = By.id("summary-gift-card-header");
const giftCardTxt = By.xpath("//input[@data-textboxid='giftCard']");
const yourReturnPolicy = By.xpath("//a[text()='What is your return policy?']");
const canIExpectOrder = By.xpath("//a[text()='When can I expect my order?']");

export const addGiftCardToBag = async () => {
  await driver.executeScript('window.scrollTo(0, 30000)')
  await waitSeconds(2)
  await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(giftCard));
  await waitSeconds(2)
  await driver.findElement(giftCard).click()
  await waitSeconds(2)
  await closeIconInPAP()
  await driver.executeScript('window.scrollTo(0, 200)')
  await waitSeconds(2)
  await driver.findElement(classicGiftCard).click()
  await waitSeconds(2)
  await driver.findElement(amountList).click()
  await waitSeconds(2)
  await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(giftFromText));
  await waitSeconds(2)
  await driver.findElement(senderName).sendKeys("tester1")
  // await waitSeconds(2)
  await driver.findElement(recipientName).sendKeys("tester2")
  //await waitSeconds(2)
  driver.findElement(submitClassicGiftCard).click()
  await waitSeconds(2)
  let url = await driver.getCurrentUrl();
  if (url.indexOf("factory.jcrew.com") > -1) {
    await driver.findElement(header_cart_factory).click()
    await waitSeconds(1)
    let bagsize = await driver.findElement(cartSize_factory).getText()
    console.log("=======Bag size " + bagsize)
    expect(bagsize).toBeTruthy()
  } else {
    await driver.findElement(bagButtonIcon).click()
    await waitSeconds(1)
    await driver.navigate().to(globals.__baseUrl__ + "/checkout2/shoppingbag.jsp?sidecar=true")
    await waitSeconds(1)
    let bagsize = await driver.findElement(bagSize).getText()
    expect(bagsize).toBeTruthy()
    console.log("=======Bag size " + bagsize)
    expect(bagsize).toBeTruthy()
  }
};

export const addSuitToBag = async () => {
  await waitSeconds(3)
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
    await waitSeconds(5)
    await driver.findElement(plusProductGrid).click()
    await waitSeconds(3)
    await driver.findElement(productSizeListBtn).click()
    await waitSeconds(2)
    await driver.findElement(addToBag).click()
  } 
  else{
  await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(plusProductGrid3N));
  await waitSeconds(3)
  await driver.findElement(plusProductGrid3N).click()
  await waitSeconds(3)
  await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(productPrice));
  await waitSeconds(2)
  await driver.findElement(productSizeListBtn).click()
  await waitSeconds(3)
  await driver.findElement(addToBag).click()
  }
}

export const editItemAddedToBag = async () => {
  let result = false;
  try {
    await driver.navigate().to(globals.__baseUrl__ + "/checkout2/shoppingbag.jsp?sidecar=true")
    await waitSeconds(1)
    let colrNameBeforeEdit = await driver.findElement(productColorName).getText()
    await driver.findElement(productEditItem).click()
    await waitSeconds(2)
    await driver.findElement(productColorList).click()
    await waitSeconds(2)
    await driver.findElement(updateBag).click()
    await waitSeconds(2)
    await driver.navigate().to(globals.__baseUrl__ + "/checkout2/shoppingbag.jsp?sidecar=true")
    await waitSeconds(1)
    let colorNameAfterEdit = await driver.findElement(productColorName).getText()
    //await waitSeconds(2)
    if (!(colrNameBeforeEdit == colorNameAfterEdit)) {
      result = true;
      console.log("Color displayed in chip box after edited the item color >> ")
    }
  } catch (err) {
    console.log("error in editing item added to bag" + err)
  }
  return result;
}

export const oneSizeValidation = async (currentUrl) => {
  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
    await driver.actions().mouseMove(await driver.findElement(department_women)).perform();
    await waitSeconds(2)
    await driver.findElement(women_jewellery).click()
  } else {
    await driver.actions().mouseMove(await driver.findElement(category_women)).perform();
    await waitSeconds(2)
    await driver.findElement(jewellery_item1).click()
  }
  await waitSeconds(2)
  await closeIconInPAP()
  await driver.findElement(product_pics).click()
  await waitSeconds(5)
  // expect(productimage).toBeTruthy()
  const oneSize = await driver.findElement(product_size_listBtn)
  expect(oneSize).toBeTruthy()
  // await waitSeconds(1)
}

export const validatePDPPageObjects = async () => {
  await driver.findElement(product_pics).click()
  await waitSeconds(2)
  const productcolor = await driver.findElement(productColorsListItems)
  expect(productcolor).toBeTruthy()
  const productsize = await driver.findElement(product_size_unavailable)
  expect(productsize).toBeTruthy()
  //await waitSeconds(1)
  const productaddtobag = await driver.findElement(addToBag)
  expect(productaddtobag).toBeTruthy()
  const productaddtowishlist = await driver.findElement(wishListBag)
  expect(productaddtowishlist).toBeTruthy()
}

export const validateShoppingBagPage = async () => {
  await driver.navigate().to(globals.__baseUrl__ + "/checkout2/shoppingbag.jsp?sidecar=true")
  await waitSeconds(1)
  expect(await driver.findElement(zipcode)).toBeTruthy()
  console.log("Zip code text box is displaying")
  expect(await driver.findElement(paypalBtn)).toBeTruthy()
  console.log("Paypal payment method is displaying")
  expect(await driver.findElement(summaryPromo)).toBeTruthy()
  await driver.findElement(summaryPromo).click()
  await waitSeconds(1)
  expect(await driver.findElement(promocodeTxt)).toBeTruthy()
  console.log("Promo code text box is displaying")
  expect(await driver.findElement(summaryGiftCard)).toBeTruthy()
  await driver.findElement(summaryGiftCard).click()
  await waitSeconds(1)
  expect(await driver.findElement(giftCardTxt)).toBeTruthy()
  console.log("Gift card payment method is displaying")
  expect(await driver.findElement(yourReturnPolicy)).toBeTruthy()
  expect(await driver.findElement(canIExpectOrder)).toBeTruthy()
}