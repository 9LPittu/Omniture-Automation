import { driver, defaultTimeout } from '../helpers';
import { load, closeIconInPAP } from '../pageObjects/jcrewdesktoppageobj';

const { Builder, By, Key, until } = require('selenium-webdriver');
//array Elements
const privacyPolicyCloseBtn = By.xpath("//div[@class='mt-close-lb-slide privacyPolicyClose']");
const searchLink = By.xpath("//span[text()='search']");
const searchTxt_factory = By.xpath("//input[@class='js-primary-nav__input--search']");
const searchTxt_jcrew = By.xpath("//input[@class='nc-nav__search__input']");
const product_image = By.xpath("//div[@class='c-product__photos'][1]");
const product_list = By.className('c-product__list');
const product_title = By.xpath("(//span[contains(@class,'tile__detail--name')])[1]");
const product_price = By.xpath("(//span[contains(@class,'tile__detail--price--list')])[1]");
const product_image3 = By.xpath("(//div[@class='c-product__photos'])[3]");
const product_image2 = By.xpath("(//div[@class='c-product__photos'])[2]");
const product_size = By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]");
const addToBag = By.id("btn__add-to-bag-wide");
const cartSize = By.xpath("//span[@class='js-cart-size']");
const productCart = By.id("js-header__cart");
const cart_count = By.xpath("//div[@class='nc-nav__bag-button__count']");
const cartIcon = By.xpath("//div[@class='nc-nav__bag-button__icon']");
const wishList_btn = By.id("btn__wishlist");

//Baynote elements
const likeTheAbove_link = By.xpath("//h1[contains(text(),'Like the above?')]");
const shoppingBag = By.xpath("(//a[@data-qs-location='Shopping Bag - Recommendations']/img)[1]");
//const quickShop = By.xpath("//div[contains(@class,'btn--quickshop')]");
const quickShop = By.xpath("//button[@class='c-product-tile__quickshop'][contains(.,'QUICK SHOP')]");

//Shop the look elements
const shopTheLook_Link = By.xpath("//h3[text()='Shop The Look']");
const shopImage1 = By.xpath("(//li/figure/img)[1]");
const shopProduct = By.xpath("//*[@class='c-product c-quickshop__page']");
const addtoBag2 = By.xpath("(//button[text()='Add to Bag'])[2]");
const wishListBtnText = By.xpath("//button[text()='Wishlist']");
const productColorsList = By.xpath("(//ul[@class='product__colors colors-list'])[2]");
const productSizesList = By.xpath("(//div[@class='c-sizes-list'])[2]");

//Department , product sizes elements
const dept_mens_factory = By.xpath("//li[@data-department='men']");
const dept_tshirts_factory = By.xpath("//span[text()='T-shirts & Henleys']");
const dept_mens_jcrew = By.xpath("//a[text()='Men']");
const dept_tshirts_jcrew = By.xpath("//a[text()='t-shirts & polos']");
const dept_product_variation = By.xpath("(//li[contains(@class,'js-product__variation') and not(contains(@class,'is-selected'))])[1]")
const dept_product_sizelist = By.xpath("(.//li[contains(@class,'js-product__size sizes-list__item btn')])[1]");

//Add Sales items
const clearance_txt = By.xpath("//span[text()='Clearance']");
const category_women = By.xpath("//span[text()='women']");
const saleTxt = By.xpath("//a[text()='sale']");
const sale_recommendation_item = By.xpath("(//div[@class='c-sale-recommendation-item'])[1]");
const product_title_info = By.xpath("(//div[@class='product-tile--info'])[1]");

//Registration elements
const colorsList = By.xpath("//ul[@class='product__colors colors-list']/li/img");


export const productArrayPage = async () => {
  try {
    await driver.findElement(privacyPolicyCloseBtn).click()
  }
  catch (err) {
    console.log("error in privacyPolicyCloseBtn")
  }
  let url = await driver.getCurrentUrl();
  if (url.indexOf("factory.jcrew.com") > -1) {
    await driver.findElement(searchTxt_factory).sendKeys("shirts")
    await driver.findElement(searchLink).click()
    await driver.sleep(2000)
  } else {
    var searchText = await driver.findElement(searchTxt_jcrew);
    await searchText.sendKeys("pants")
    await driver.actions().click(searchText).sendKeys(Key.ENTER).perform();
  }
  await driver.sleep(2000)
  const productimage = await driver.findElement(product_image)
  expect(productimage).toBeTruthy()
};

export const addProductToBag = async () => {
  //driver.sleep(1000)
  await closeIconInPAP()
  //driver.sleep(1000)
  await driver.findElement(product_image3).click()
  driver.sleep(2000)
  const productsize = await driver.findElement(product_size)
  productsize.click()
  await driver.sleep(1000)
  await driver.findElement(addToBag).click()
  await driver.sleep(1000)
};

export const verifyAndClickOnBag = async () => {
  let url = await driver.getCurrentUrl();
  if (url.indexOf("factory.jcrew.com") > -1) {
    await driver.sleep(1000)
    let bagsize = await driver.findElement(cartSize).getText()
    console.log("=======Bag size " + bagsize)
    await driver.findElement(productCart).click()
    await driver.sleep(1000)
  } else {
    await driver.sleep(1000)
    try {
      let bagsize = await driver.findElement(cart_count).getText()
      expect(bagsize).toBeTruthy()
      console.log("=======Bag size " + bagsize)
    } catch (err) { }
    await driver.findElement(cartIcon).click()
    await driver.sleep(1000)
  }
};

export const addMultiLineItems = async () => {
  let url = await driver.getCurrentUrl();
  await productArrayPage()
  await closeIconInPAP()
  const productimage = await driver.findElement(product_image)
  expect(productimage).toBeTruthy()
  await productimage.click()
  await driver.sleep(1000)
  const productsize = await driver.findElement(product_size)
  await productsize.click()
  await driver.sleep(1000)
  await driver.findElement(addToBag).click()
  await driver.sleep(1000)
  await driver.findElement(addToBag).click()
  await driver.sleep(1000)
  await productArrayPage()
  await driver.sleep(2000)
  const productimage2 = await driver.findElement(product_image2)
  expect(productimage2).toBeTruthy()
  await driver.findElement(product_image2).click()
  await driver.sleep(1000)
  const productsize2 = await driver.findElement(product_size)
  await productsize2.click()
  await driver.sleep(1000)
  await driver.findElement(addToBag).click()
  await driver.sleep(1000)
  expect(addToBag).toBeTruthy()
  await driver.findElement(addToBag).click()
  await driver.sleep(2000)
};

export const addsaleitemsToBag = async () => {
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
    await driver.actions().mouseMove(await driver.findElement(clearance_txt)).perform();
    driver.sleep(2000);
    await driver.findElement(category_women).click()
    driver.sleep(2000);
  } else {
    await driver.findElement(saleTxt).click()
    driver.sleep(2000);
    await closeIconInPAP()
    await driver.findElement(sale_recommendation_item).click()
  }
  await driver.sleep(2000)
  await driver.findElement(product_title_info).click()
  driver.sleep(2000)
  const productsize = await driver.findElement(product_size)
  productsize.click()
  driver.sleep(2000)
  const productaddtobag = await driver.findElement(addToBag)
  productaddtobag.click()
  await driver.sleep(2000)
  productaddtobag.click()
  await driver.sleep(2000)
}

export const selectSuitsFromCategory = async () => {
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
    await driver.actions().mouseMove(await driver.findElement(By.xpath("//li[@data-department='men']"))).perform();
    await driver.sleep(1000)
    await driver.findElement(By.xpath("//span[text()='Thompson Suits & Blazers']")).click()
  } else {
    await driver.actions().mouseMove(await driver.findElement(By.xpath("//a[text()='Men']"))).perform();
    await driver.sleep(1000)
    await driver.findElement(By.xpath("//a[text()='suits & tuxedos']")).click()
    await driver.sleep(1000)
    await closeIconInPAP()
    await driver.sleep(1000)
  }
};

export const validateProductArrayPage = async () => {
  let result = false;
  try {
    await driver.findElement(product_list)
    await driver.findElement(product_image)
    await driver.findElement(product_title)
    await driver.findElement(product_price)
    await driver.executeScript('window.scrollTo(0, 2000)')
    result = true;
  } catch (err) {
    console.log("error in validate product array page" + err)
  }
  return result;
};

export const bayNoteRecommondationValidations = async () => {
  let result = false;
  try {
    await driver.executeScript('window.scrollTo(0, 200)')
    await driver.findElement(likeTheAbove_link)
    await driver.findElement(shoppingBag)
    result = true;
    console.log("baynoterecommendations are displaying in shopping bag page");
  } catch (err) {
    console.log("error in bayNoteRecommondationValidations" + err)
  }
  return result;
};

export const quickShopValidations = async () => {
  console.log("quick shop validations:::::::::::::::::::")
  let result = false;
  try {
    await driver.sleep(1000)
    await driver.actions().mouseMove(await driver.findElement(product_image)).perform();
    await driver.sleep(2000)
    await driver.findElement(quickShop).click()
    await driver.sleep(6000)
    expect(await driver.findElement(product_size)).toBeTruthy()
    expect(await driver.findElement(addToBag)).toBeTruthy()
    expect(await driver.findElement(wishList_btn)).toBeTruthy()
    result = true;
    console.log("User is able to navigate to quick shop from arraypage")
  } catch (err) {
    console.log("error in quickshopvalidations" + err)
  }
  return result;
};

export const shopTheLookValidations = async () => {
  let result = false;
  try {
    const productimage = await driver.findElement(product_image3)
    await productimage.click()
    await driver.sleep(3000)
    await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(shopTheLook_Link));
    await driver.sleep(1000)
    await driver.executeScript('window.scrollTo(0, 200)')
    await driver.sleep(1000)
    expect(await driver.findElement(shopImage1)).toBeTruthy()
    await driver.findElement(shopImage1).click()
    await driver.sleep(4000)
    expect(await driver.findElement(shopProduct)).toBeTruthy()
    await driver.sleep(1000)
    expect(await driver.findElement(addtoBag2)).toBeTruthy()
    expect(await driver.findElement(wishListBtnText)).toBeTruthy()
    expect(await driver.findElement(productColorsList)).toBeTruthy()
    expect(await driver.findElement(productSizesList)).toBeTruthy()
    result = true;
  } catch (err) {
    console.log("error in shop the look validations" + err)
  }
  return result;
};

export const verifyExtendedSizesValidation = async (currentUrl) => {
  let result = false;
  try {
    if (currentUrl.indexOf("factory.jcrew.com") > -1) {
      await driver.actions().mouseMove(await driver.findElement(dept_mens_factory)).perform();
      await driver.sleep(2000);
      await driver.findElement(dept_tshirts_factory).click()
    } else {
      await driver.actions().mouseMove(await driver.findElement(dept_mens_jcrew)).perform();
      driver.sleep(2000);
      await driver.findElement(dept_tshirts_jcrew).click()
      await driver.sleep(3000)
      await closeIconInPAP()
    }
    await driver.sleep(1000)
    await driver.findElement(product_image2).click()
    await driver.sleep(3000)
    const extendedSizes = await driver.findElement(dept_product_variation)
    expect(extendedSizes).toBeTruthy()
    await extendedSizes.click()
    await driver.sleep(3000)
    await driver.executeScript('window.scrollTo(0, 100)')
    await driver.sleep(1000)
    const sizechart = await driver.findElement(dept_product_sizelist)
    expect(sizechart).toBeTruthy()
    result = true;
    console.log("Extended sizes are available for product selected")
  } catch (err) {
    console.log("error in verify extended sizes" + err)
  }
  return result;
};

export const colorswatchingValidation = async () =>{
  await driver.actions().mouseMove(await driver.findElement(product_image3)).perform();
  await driver.sleep(2000);
  expect(await driver.findElement(colorsList).isDisplayed()).toBeTruthy();

}