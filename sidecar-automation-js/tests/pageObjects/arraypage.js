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
//const cart_count = By.xpath("//div[@class='nc-nav__bag-button__count']"); //old xpath
const cart_count = By.xpath("//div[@class='nc-nav__bag-tab__count']");
//const cartIcon = By.xpath("//div[@class='nc-nav__bag-button__icon']"); //old xpath
const cartIcon = By.xpath("//div[@class='nc-nav__bag-tab__icon']");
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
const product_sizesList_Item_btn = By.xpath("(.//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]");
const wishlist = By.id("btn__wishlist-wide");

//Add Sales items
const clearance_txt = By.xpath("//span[text()='Clearance']");
const category_women = By.xpath("//span[text()='women']");
const saleTxt = By.xpath("//a[text()='sale']");
const sale_recommendation_item = By.xpath("(//div[@class='c-sale-recommendation-item'])[1]");
const product_title_info = By.xpath("(//div[@class='product-tile--info'])[1]");

const userPanel_factory = By.id("c-header__userpanelrecognized");
const wishListLink_factory = By.xpath("//dd/a[text()='Wishlist']");
const navAccountBtn = By.xpath("//a[@class='nc-nav__account_button']");
const wishListCart = By.xpath("//li/a[text()='Wishlist']");
const wishListItems = By.xpath("//li[contains(@id,'item')]");
//Registration elements
const colorsList = By.xpath("//ul[@class='product__colors colors-list']/li/img");

//Email preferences
const emailPrefs = By.xpath("//a[text()='Email Preferences']");
const emailText = By.id("emailSub");
const glbLongGreyLine = By.xpath("//section[@class='glb-long-grey-line']/p/a/img");

//Filters 
const filterHeader = By.css("#c-filters__header-item--toggle");
const womenLabel = By.xpath("//button[@data-label='Women']");
const filteredCategory = By.xpath("(//div[@class='c-filters__breadcrumb btn btn--round is-capitalized']/span)[1]");
const clearAll = By.xpath("//span[text()='Clear All']");
const sortBy = By.xpath("//span[text()='Sort By']");
const lowToHigh = By.xpath("//li[text()='Price: Low to High']");
const lowToHighTxt = By.xpath("//span[text()='Price: Low to High']");
const myDetailsTxt = By.xpath("//ul/li[2]/a[text()='My Details']");
const myDetailsPartialTxt = By.partialLinkText("My Details");
const accountMyDetails = By.xpath("//div/div/div[@class='account__my-details--header']");

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

export const colorswatchingValidation = async () => {
  await driver.actions().mouseMove(await driver.findElement(product_image3)).perform();
  await driver.sleep(2000);
  expect(await driver.findElement(colorsList).isDisplayed()).toBeTruthy();
}

export const myWishListValidation = async () => {
  await driver.findElement(product_image2).click()
  await driver.sleep(2000)
  await driver.findElement(product_sizesList_Item_btn).click()
  await driver.sleep(1000)
  await driver.findElement(wishlist).click()
  await driver.sleep(3000)
}

export const goToWishList = async (url) => {
  if (url.includes("factory")) {
    const loggedInUser = await driver.findElement(userPanel_factory)
    expect(loggedInUser).toBeTruthy()
    await driver.actions().mouseMove(loggedInUser).perform();
    await driver.sleep(2000)
    const wishlst = await driver.findElement(wishListLink_factory)
    expect(wishlst).toBeTruthy()
    wishlst.click()
  } else {
    const loggedInUser = await driver.findElement(navAccountBtn)
    expect(loggedInUser).toBeTruthy()
    await driver.actions().mouseMove(loggedInUser).perform();
    await driver.sleep(2000)
    const wishlst = await driver.findElement(wishListCart)
    expect(wishlst).toBeTruthy()
    wishlst.click()
  }
}

export const validateWishListBag = async () => {
  expect(await driver.findElement(wishListItems).isDisplayed()).toBeTruthy();
}

export const validateWishListAsGuest = async () => {
  await driver.findElement(product_image).click()
  await driver.sleep(2000)
  await driver.findElement(product_sizesList_Item_btn).click()
  await driver.sleep(1000)
  await driver.findElement(wishlist).click()
  await driver.sleep(1000)
}

export const validateEmailPreferences = async () => {
  await driver.sleep(10000)
  await driver.findElement(emailPrefs).click();
  await driver.sleep(2000);
  await driver.switchTo().frame(await driver.findElement(emailText));
  const subscribebutton = await driver.findElement(glbLongGreyLine);
  expect(subscribebutton.isDisplayed()).toBeTruthy();
  await subscribebutton.click()
  await driver.sleep(2000)
}

export const validateFilterAndSort = async () => {
  const hidefilters = await driver.findElement(filterHeader);
  expect(hidefilters.isDisplayed()).toBeTruthy()
  console.log("filter header is displaying")
  await driver.sleep(3000)
  const gender = await driver.findElement(womenLabel)
  expect(gender).toBeTruthy()
  await gender.click()
  console.log("filter header is displaying")
  await driver.sleep(5000)
  const filteredCategoryItem = await driver.findElement(filteredCategory);
  expect(filteredCategoryItem.isDisplayed()).toBeTruthy()
  const clearAllLink = await driver.findElement(clearAll);
  expect(clearAllLink.isDisplayed()).toBeTruthy()
  await clearAllLink.click()
  await driver.sleep(3000)
  const sortByOpt = await driver.findElement(sortBy);
  expect(sortByOpt.isDisplayed()).toBeTruthy()
  await sortByOpt.click()
  await driver.sleep(1000)
  const lowToHighOpt = await driver.findElement(lowToHigh);
  expect(lowToHighOpt.isDisplayed()).toBeTruthy()
  await lowToHigh.click()
  await driver.sleep(3000)
  const sortApplied = await driver.findElement(lowToHighTxt);
  expect(sortApplied.isDisplayed()).toBeTruthy()
  console.log("Sort functionality is working")
}

export const myDetailsValidation = async () => {
  expect(await driver.findElement(myDetailsTxt).isDisplayed()).toBeTruthy()
  await driver.findElement(myDetailsPartialTxt).click();
  await driver.sleep(2000);
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.includes("r/account/details")) {
    expect(await driver.findElement(accountMyDetails)).toBeTruthy()
    console.log("User navigates to My Details page")
  }
}

export const validateShoppingTongue = async (url) => {
  if (url.includes("factory")) {
    await driver.actions().mouseMove(await driver.findElement(By.xpath("//span[text()='bag']"))).perform();
    await driver.sleep(1000)
    const shoppingTonge = await driver.findElement(By.xpath("//div[@class='c-header__minibag is-active']"))
    expect(shoppingTonge).toBeTruthy()
  } else {
    await driver.sleep(3000)
    await driver.actions().mouseMove(await driver.findElement(By.xpath("//div[@class='nc-nav__bag-button']"))).perform();
    await driver.sleep(1000)
    const shoppingTonge = await driver.findElement(By.xpath("//*[@class='nc-nav__bag__panel is-open']"))
    expect(shoppingTonge).toBeTruthy()
  }
}

export const validateSignOut = async (url) => {
  if (url.includes("factory")) {
    const loggedInUser = await driver.findElement(By.id("c-header__userpanelrecognized"))
    expect(loggedInUser).toBeTruthy()
    await driver.actions().mouseMove(loggedInUser).perform();
    await driver.sleep(2000)
    const signOut = await driver.findElement(By.xpath("//a[@class='js-signout__link']"))
    expect(signOut).toBeTruthy()
    signOut.click()
    await driver.sleep(5000)
    expect(await driver.findElement(By.xpath(".//span[text()='sign in']")).isDisplayed()).toBeTruthy();
  } else {
    await driver.sleep(8000)
    const loggedInUser = await driver.findElement(By.xpath("//a[@class='nc-nav__account_button']"))
    expect(loggedInUser).toBeTruthy()
    await driver.actions().mouseMove(loggedInUser).perform();
    await driver.sleep(2000)
    const signOut = await driver.findElement(By.xpath("//li[5]/a[text()='Sign Out']"))
    expect(signOut).toBeTruthy()
    signOut.click()
    await driver.sleep(5000)
    expect(await driver.findElement(By.xpath(".//a[text()='Sign In']")).isDisplayed()).toBeTruthy();
  }
}

export const validateWritingReviews = async () => {
  await driver.sleep(4000)
  await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[3]")).click()
  await driver.sleep(3000)
  if (await driver.findElement(By.xpath("//*[@id='BVRRRatingOverall_Rating_Summary_1']/div[2]")).isDisplayed()) {
    console.log("Reviews are displaying")
    await driver.sleep(3000)
    await driver.executeScript('window.scrollTo(0, 800)')
    await driver.sleep(5000)
    await driver.findElement(By.xpath("//*[@id='BVRRRatingSummaryLinkWriteID']/a")).click();
    await driver.sleep(2000)
    console.log("writing the review")
    await driver.findElement(By.xpath("//a[@id='star_link_rating_5']")).click()
    await driver.sleep(1000);
    await driver.findElement(By.xpath("//a[@id='star_link_rating_Quality_5']")).click()
    await driver.sleep(1000);
    await driver.findElement(By.xpath("(//label[text()='True to Size'])[1]")).click()
    await driver.sleep(1000);
    await driver.findElement(By.xpath("//input[@id='BVFieldTitleID']")).sendKeys("Automation testing")
    await driver.sleep(1000);
    await driver.findElement(By.xpath("//textarea[@id='BVFieldReviewtextID']")).sendKeys("hi this is automation tester testing write review functionality")
    await driver.sleep(1000);
    await driver.findElement(By.xpath("//input[@id='BVFieldAdditionalfieldSizePurchasedID']")).sendKeys("small")
    await driver.sleep(1000);
    await driver.findElement(By.xpath("//button/span[text()='Preview']")).click()
    await driver.sleep(3000);
    await driver.executeScript('window.scrollTo(0, -100)')
    await driver.sleep(1000);
    const previewHeader = await driver.findElement(By.xpath("//span[text()='Preview Your Review']"));
    expect(previewHeader).toBeTruthy()
    console.log("Preview your review header is displaying")

    let currentUrl = await driver.getCurrentUrl();

    if (currentUrl.indexOf("https://or.") > -1) {
      const submit = await driver.findElement(By.xpath("(//span[text()='Submit'])[2]"));
      expect(submit).toBeTruthy()
      submit.click()
      await driver.sleep(3000);
      const thankyouText = await driver.findElement(By.xpath("//span[text()='Thank you!']"))
      expect(thankyouText).toBeTruthy()
    }
  } else {
    console.log("Reviews are displaying")
    await driver.findElement(By.xpath("(//a[text()='write a review'])[3]")).click();
    await driver.sleep(2000)
  }
}