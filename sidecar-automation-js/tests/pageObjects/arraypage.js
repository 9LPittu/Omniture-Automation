import { driver, defaultTimeout } from '../helpers';
import { load, closeIconInPAP } from '../pageObjects/jcrewdesktoppageobj';
import { waitSeconds } from '../util/commonutils';

const { Builder, By, Key, until } = require('selenium-webdriver');
//array Elements
const privacyPolicyCloseBtn = By.xpath("//div[@class='mt-close-lb-slide privacyPolicyClose']");
const searchLink = By.xpath("//span[text()='search']");
const searchClear = By.xpath("//SPAN[@class='icon-close js-primary-nav__search__button--clear']");

const searchTxt_factory = By.xpath("//span[@class='icon-header icon-header-search icon-search']");
const searchTxt_factory1 = By.xpath("//input[contains(@class,'js-primary-nav__input--search')]");
const searchTxt_jcrew = By.xpath("//input[@class='nc-nav__search__input']");
const product_image = By.xpath("//div[@class='c-product__photos'][1]");
const product_list = By.className('c-product__list');
const product_title = By.xpath("(//span[contains(@class,'tile__detail--name')])[1]");
const product_price = By.xpath("(//span[contains(@class,'tile__detail--price--list')])[1]");
const product_image3 = By.xpath("(//div[@class='c-product__photos'])[3]");
const product_image2 = By.xpath("(//div[@class='c-product__photos'])[2]");
const product_image_shop = By.xpath("(//div[@class='c-product__photos'])[1]")
const product_size = By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]");
const addToBag = By.id("btn__add-to-bag-wide");
const cartSize = By.xpath("//span[@class='js-cart-size']");
const productCart = By.id("js-header__cart");
const cart_count = By.xpath("//div[@class='nc-nav__bag-tab__count']");
const cartIcon = By.xpath("//div[@class='nc-nav__bag-tab__icon']");
const wishList_btn = By.xpath("//button[contains(.,'Add To Wishlist')]");
const dept_suits_tuxedos_jcrew = By.xpath("//a[text()='suits & tuxedos']");

//Baynote elements
const likeTheAbove_link = By.xpath("//h1[contains(text(),'Like the above?')]");
const shoppingBag = By.xpath("(//a[@data-qs-location='Shopping Bag - Recommendations']/img)[1]");
const quickShop = By.xpath("//button[@class='c-product-tile__quickshop'][contains(.,'QUICK SHOP')]");

//Shop the look elements
const shopTheLook_Link = By.xpath("//h3[text()='Shop The Look']");
const shopImage1 = By.xpath("(//li/figure/img)[1]");
const shopProduct = By.xpath("//*[@class='c-product c-quickshop__page']");
const addtoBag2 = By.xpath("(//button[text()='Add to Bag'])[2]");
const wishListBtnText = By.xpath("//button[text()='Wishlist']");
const productColorsList = By.xpath("(//ul[@class='product__colors colors-list'])[2]");
const productSizesList = By.xpath("(//div[@class='c-sizes-list'])[2]");
const shop_Women = By.xpath("//a[text()='Women']")
const shop_sweaters = By.xpath("//a[text()='sweaters']")

//Department , product sizes elements
const dept_mens_factory = By.xpath("//li[@data-department='men']");
const dept_tshirts_factory = By.xpath("//span[text()='T-shirts & Henleys']");
const dept_mens_jcrew = By.xpath("//a[text()='Men']");
const dept_tshirts_jcrew = By.xpath("//a[text()='t-shirts & polos']");
const dept_product_variation = By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn')])")
const dept_product_sizelist = By.xpath("(.//li[contains(@class,'js-product__size sizes-list__item btn')])[1]");
const product_sizesList_Item_btn = By.xpath("(.//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]");
const wishlist = By.id("btn__wishlist");
const Thompson_Suits_factory = By.xpath("//span[text()='Thompson Suits & Blazers']");

//Add Sales items
const clearance_txt = By.xpath("//span[text()='Clearance']");
const category_women = By.xpath("//span[text()='women']");
const saleTxt = By.xpath("//a[text()='sale']");
const sale_recommendation_item = By.xpath("(//div[@class='c-product__photos'])[1]");

const product_title_info = By.xpath("(//div[@class='product-tile--info'])[1]");

const userPanel_factory = By.id("c-header__userpanelrecognized");
const wishListLink_factory = By.xpath("(//a[@href='/wishlist/?sidecar=true'][contains(.,'Wishlist')])[1]");
const navAccountBtn = By.xpath("//a[@class='nc-nav__account_button']");
const wishListCart = By.xpath("//li/a[text()='Wishlist']");
const wishListItems = By.xpath("//li[contains(@id,'item')]");
//Registration elements
const colorsList = By.xpath("//ul[@class='product__colors colors-list']/li/img");

//Email preferences
const emailPrefs = By.xpath("(//a[@href='/account/email_preferences.jsp'][contains(.,'Email Preferences')])[2]");
const emailText = By.id("emailSub");
const glbLongGreyLine = By.xpath("//section[@class='glb-long-grey-line']/p/a/img");
const glbblackLine = By.xpath("//section[@class='glb-blackline']/p/a/img")

//Filters 
const filterHeader = By.css("#c-filters__header-item--toggle");
const womenLabel = By.xpath("//button[@data-label='Women']");
const filteredCategory = By.xpath("(//div[@class='c-filters__breadcrumb btn btn--round is-capitalized']/span)[1]");
const clearAll = By.xpath("//span[text()='Clear All']");
const sortBy = By.xpath("//span[text()='Sort By']");
const lowToHigh = By.xpath("//li[text()='Price: Low to High']");
const lowToHighTxt = By.xpath("//span[text()='Price: Low to High']");
const myDetailsTxt = By.xpath("(//a[@href='/r/account/details'][contains(.,'My Details')])[2]");
const myDetailsPartialTxt = By.xpath("(//a[@href='/r/account/details'][contains(.,'My Details')])[2]");
const accountMyDetails = By.xpath("//div/div/div[@class='account__my-details--header']");

//Bag cart elements
const cartBag_factory = By.xpath("//span[text()='bag']");
const bagPanel_factory = By.xpath("//div[@class='c-header__minibag is-active']");
const cartBag_jcrew = By.xpath("//div[@class='nc-nav__bag-tab__icon']");
const bagPanel_jcrew = By.xpath("(//li[contains(@class,'nc-nav__bag__item nc-nav__list-item')])[1]");
const clickBag = By.xpath("//span[@class='primary-nav__text'][contains(.,'bag')]");



//Validate Signout
const signout_factory = By.xpath("//a[text()='Sign Out'][1]");
const signIn_factroy = By.xpath(".//span[text()='sign in']");
const signout_jcrew = By.xpath("//li[5]/a[text()='Sign Out']");
const signIn_jcrew = By.xpath(".//a[text()='Sign In']");

//Writing Review Elements
const ratingsSection = By.xpath("//*[@id='BVRRRatingOverall_Rating_Summary_1']/div[2]");
const writeTheFirstReview = By.xpath("(//a[@title='write the first review'])[2]");
const ratingSummary = By.xpath("(//a[@title='Write a review'])[5]");
const fiveStarRating = By.xpath("//a[@id='star_link_rating_5']");
const ratingQuality = By.xpath("//a[@id='star_link_rating_Quality_5']");
const fitToSize = By.xpath("(//label[text()='True to Size'])[1]");
const ratingFieldTitle = By.xpath("//input[@id='BVFieldTitleID']");
const ratingFieldReview = By.xpath("//textarea[@id='BVFieldReviewtextID']");
const ratingFieldPurchaseId = By.xpath("//input[@id='BVFieldAdditionalfieldSizePurchasedID']");
const ratingFieldPreview = By.xpath("//button/span[text()='Preview']");
const ratingFieldPreviewHeader = By.xpath("//span[text()='Preview Your Review']");
const submitReview_gold = By.xpath("(//span[text()='Submit'])[2]");
const submitReview_thanku = By.xpath("//span[text()='Thank you!']");
const reviewsDisplay = By.xpath("(//a[text()='write a review'])[3]");
const userNickName = By.xpath("//input[@name='usernickname']");
const userEmailId = By.xpath("//input[@name='useremail']");

export const productArrayPage = async () => {
  try {
    await driver.findElement(privacyPolicyCloseBtn).click()
  }
  catch (err) {
    console.log("error in privacyPolicyCloseBtn")
  }
  let url = await driver.getCurrentUrl();
  if (url.indexOf("factory.jcrew.com") > -1) {
    await waitSeconds(2)
    await driver.findElement(searchTxt_factory).click()
    await waitSeconds(2)
    await driver.findElement(searchTxt_factory1).sendKeys("shirts")
    await driver.findElement(searchLink).click()
    await waitSeconds(2)
    await driver.findElement(searchClear).click()
    await driver.findElement(searchLink).click()
  } else {
    var searchText = await driver.findElement(searchTxt_jcrew);
    await searchText.sendKeys("pants")
    await driver.actions().click(searchText).sendKeys(Key.ENTER).perform();
  }
  await waitSeconds(2)
  const productimage = await driver.findElement(product_image)
  expect(productimage).toBeTruthy()
};

export const addProductToBag = async () => {

  await waitSeconds(3)
  await closeIconInPAP()
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
  await driver.findElement(product_image).click()
  await waitSeconds(2)
  const productsize = await driver.findElement(product_size)
  await productsize.click()
  await waitSeconds(1)
  await driver.findElement(addToBag).click()
  await waitSeconds(1)
  } 
  else{
  await driver.findElement(product_image).click()
  await waitSeconds(2)
  const productsize = await driver.findElement(product_size)
  await productsize.click()
  await waitSeconds(1)
  await driver.findElement(addToBag).click()
  await waitSeconds(1)
  }
};

export const verifyAndClickOnBag = async () => {
  let url = await driver.getCurrentUrl();
  if (url.indexOf("factory.jcrew.com") > -1) {
    await waitSeconds(1)
    let bagsize = await driver.findElement(cartSize).getText()
    console.log("=======Bag size " + bagsize)
    await driver.findElement(clickBag).click()
    await waitSeconds(1)
  } else {
    await waitSeconds(1)
    try {
      let bagsize = await driver.findElement(cart_count).getText()
      expect(bagsize).toBeTruthy()
      console.log("=======Bag size " + bagsize)
    } catch (err) { }
    await driver.findElement(cartIcon).click()
    await waitSeconds(1)
  }
};

export const addMultiLineItems = async () => {
  let url = await driver.getCurrentUrl();
  await productArrayPage()
  await closeIconInPAP()
  const productimage = await driver.findElement(product_image)
  expect(productimage).toBeTruthy()
  await productimage.click()
  await waitSeconds(1)
  const productsize = await driver.findElement(product_size)
  await productsize.click()
  await waitSeconds(1)
  await driver.findElement(addToBag).click()
  await waitSeconds(5)
  await driver.findElement(addToBag).click()
  await driver.findElement(clickBag).click()
//await driver.findElement(By.id("js-header__cart")).click()
  await waitSeconds(4)
  await productArrayPage()
  await waitSeconds(2)
  const productimage2 = await driver.findElement(product_image2)
  expect(productimage2).toBeTruthy()
  await driver.findElement(product_image2).click()
  await waitSeconds(1)
  const productsize2 = await driver.findElement(product_size)
  await productsize2.click()
  await waitSeconds(1)
  await driver.findElement(addToBag).click()
  await waitSeconds(4)
  expect(addToBag).toBeTruthy()
  await waitSeconds(4)
  await driver.findElement(addToBag).click()
  await waitSeconds(2)
  await driver.findElement(clickBag).click()
};

export const addsaleitemsToBag = async () => {
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
    await driver.actions().mouseMove(await driver.findElement(clearance_txt)).perform();
    await waitSeconds(2);
    await driver.findElement(category_women).click()
    await waitSeconds(2);
  } else {
    await driver.findElement(saleTxt).click()
    await waitSeconds(2);
    await closeIconInPAP()
    //await driver.findElement(sale_recommendation_item).click()
  }
  await waitSeconds(2)
  await driver.findElement(product_title_info).click()
  await waitSeconds(2)
  const productsize = await driver.findElement(product_size)
  productsize.click()
  await waitSeconds(2)
  const productaddtobag = await driver.findElement(addToBag)
  productaddtobag.click()
  await waitSeconds(2)
  productaddtobag.click()
  await waitSeconds(2)
}

export const selectSuitsFromCategory = async () => {
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
    await waitSeconds(5)
    await driver.actions().mouseMove(await driver.findElement(dept_mens_factory)).perform();
    await driver.findElement(Thompson_Suits_factory).click()
  } else {
    await driver.actions().mouseMove(await driver.findElement(dept_mens_jcrew)).perform();
    await driver.findElement(dept_suits_tuxedos_jcrew).click()
    await waitSeconds(5)
    await closeIconInPAP()
    await waitSeconds(5)
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
    await waitSeconds(1)
    await driver.actions().mouseMove(await driver.findElement(product_image)).perform();
    await waitSeconds(2)
    await driver.findElement(quickShop).click()
    await waitSeconds(6)
    expect(await driver.findElement(product_size)).toBeTruthy()
    expect(await driver.findElement(addToBag)).toBeTruthy()
    expect(await driver.findElement(wishListBtnText)).toBeTruthy()
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
    let currentUrl = await driver.getCurrentUrl();
    if (currentUrl.indexOf("factory.jcrew.com") > -1) {
    await driver.actions().mouseMove(await driver.findElement(shop_Women)).perform();
    await waitSeconds(1);
    await driver.findElement(shop_sweaters).click()
    await waitSeconds(1);
    await closeIconInPAP()
    await waitSeconds(1);
    const productimage = await driver.findElement(product_image3)
    await productimage.click()
    await waitSeconds(3);
    }
    else
    {
    await driver.actions().mouseMove(await driver.findElement(shop_Women)).perform();
    await waitSeconds(1);
    await driver.findElement(shop_sweaters).click()
    await waitSeconds(1);
    await closeIconInPAP()
    await waitSeconds(1);
    const productimage_s = await driver.findElement(product_image_shop)
    await productimage_s.click()

   // const productimage = await driver.findElement(product_image3)
    //await productimage.click()
    //await waitSeconds(3);
    }
    await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(shopTheLook_Link));
    await waitSeconds(1);
    await driver.executeScript('window.scrollTo(0, 200)')
    await waitSeconds(1);
    expect(await driver.findElement(shopImage1)).toBeTruthy()
    await driver.findElement(shopImage1).click()
    await waitSeconds(4);
    expect(await driver.findElement(shopProduct)).toBeTruthy()
    await waitSeconds(1);
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
      await waitSeconds(2);
      await driver.findElement(dept_tshirts_factory).click()
    } else {
      await driver.actions().mouseMove(await driver.findElement(dept_mens_jcrew)).perform();
      await waitSeconds(2);
      await driver.findElement(dept_tshirts_jcrew).click()
      await waitSeconds(3)
      await closeIconInPAP()
    }
    await waitSeconds(1)
    await driver.findElement(product_image2).click()
    await waitSeconds(3)
    const extendedSizes = await driver.findElement(dept_product_variation)
    expect(extendedSizes).toBeTruthy()
    await extendedSizes.click()
    await waitSeconds(3)
    await driver.executeScript('window.scrollTo(0, 100)')
    await waitSeconds(1)
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
  await driver.executeScript('window.scrollTo(0, 2000)')
  await waitSeconds(2)
  await driver.actions().mouseMove(await driver.findElement(product_image2)).perform();
  await waitSeconds(2);
  const color = await driver.findElement(colorsList)
  expect(color.isDisplayed()).toBeTruthy();
}

export const myWishListValidation = async () => {
  await driver.findElement(product_image2).click()
  await waitSeconds(2)
  await driver.findElement(product_sizesList_Item_btn).click()
  await waitSeconds(1)
  await driver.findElement(wishlist).click()
  await waitSeconds(3)
}

export const goToWishList = async (url) => {
  if (url.includes("factory")) {
    await waitSeconds(3);
    const loggedInUser = await driver.findElement(userPanel_factory)
    expect(loggedInUser).toBeTruthy()
    await driver.actions().mouseMove(loggedInUser).perform();
    await waitSeconds(2)
    const wishlst = await driver.findElement(wishListLink_factory)
    expect(wishlst).toBeTruthy()
    wishlst.click()
  } else {
    const loggedInUser = await driver.findElement(navAccountBtn)
    expect(loggedInUser).toBeTruthy()
    await driver.actions().mouseMove(loggedInUser).perform();
    await waitSeconds(2)
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
  await waitSeconds(2)
  await driver.findElement(product_sizesList_Item_btn).click()
  await waitSeconds(1)
  await driver.findElement(wishList_btn).click()
  await waitSeconds(1)
}

export const validateEmailPreferences = async () => {
  await driver.findElement(emailPrefs).click()
  await waitSeconds(2);
  await driver.switchTo().frame(await driver.findElement(emailText));
  let subscribebutton = null;
  try {
    subscribebutton = await driver.findElement(glbLongGreyLine)
  } catch (err) {
    await driver.findElement(glbblackLine).click()
    await waitSeconds(1)
    subscribebutton = await driver.findElement(glbblackLine)
  }
  expect(subscribebutton.isDisplayed()).toBeTruthy();
  await subscribebutton.click()
  await waitSeconds(2)
}

export const validateFilterAndSort = async () => {
  const hidefilters = await driver.findElement(filterHeader);
  expect(hidefilters.isDisplayed()).toBeTruthy()
  console.log("filter header is displaying")
  await waitSeconds(3)
  const gender = await driver.findElement(womenLabel)
  expect(gender).toBeTruthy()
  await gender.click()
  console.log("filter header is displaying")
  await waitSeconds(1)
  const filteredCategoryItem = await driver.findElement(filteredCategory);
  expect(filteredCategoryItem.isDisplayed()).toBeTruthy()
  const clearAllLink = await driver.findElement(clearAll);
  expect(clearAllLink.isDisplayed()).toBeTruthy()
  await clearAllLink.click()
  await waitSeconds(3)
  const sortByOpt = await driver.findElement(sortBy);
  expect(sortByOpt.isDisplayed()).toBeTruthy()
  await sortByOpt.click()
  await waitSeconds(1)
  const lowToHighOpt = await driver.findElement(lowToHigh);
  expect(lowToHighOpt.isDisplayed()).toBeTruthy()
  await lowToHighOpt.click()
  await waitSeconds(3)
  const sortApplied = await driver.findElement(lowToHighTxt);
  expect(sortApplied.isDisplayed()).toBeTruthy()
  console.log("Sort functionality is working")
}

export const myDetailsValidation = async () => {
  expect(await driver.findElement(myDetailsTxt).isDisplayed()).toBeTruthy()
  await driver.findElement(myDetailsPartialTxt).click();
  await waitSeconds(2);
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.includes("r/account/details")) {
    expect(await driver.findElement(accountMyDetails)).toBeTruthy()
    console.log("User navigates to My Details page")
  }
}

export const validateShoppingTongue = async (url) => {
  if (url.includes("factory")) {
    await driver.actions().mouseMove(await driver.findElement(cartBag_factory)).perform();
    await waitSeconds(2)
    const shoppingTonge = await driver.findElement(bagPanel_factory)
    expect(shoppingTonge).toBeTruthy()
  } else {
    const carticonbag = await driver.findElement(cartBag_jcrew)
    console.log("in shopping tounge move")
    await driver.actions().mouseMove(carticonbag).perform();
    await waitSeconds(3)
    const shoppingTonge = await driver.findElement(bagPanel_jcrew)
    expect(shoppingTonge).toBeTruthy()
  }
}

export const validateSignOut = async (url) => {
  if (url.includes("factory")) {
    const loggedInUser = await driver.findElement(userPanel_factory)
    expect(loggedInUser).toBeTruthy()
    await driver.actions().mouseMove(loggedInUser).perform();
    await waitSeconds(2)
    const signOut = await driver.findElement(signout_factory)
    expect(signOut).toBeTruthy()
    await signOut.click()
    await waitSeconds(2)
    expect(await driver.findElement(signIn_factroy).isDisplayed()).toBeTruthy();
  } else {
    await waitSeconds(2)
    const loggedInUser = await driver.findElement(navAccountBtn)
    expect(loggedInUser).toBeTruthy()
    await driver.actions().mouseMove(loggedInUser).perform();
    await waitSeconds(2)
    const signOut = await driver.findElement(signout_jcrew)
    expect(signOut).toBeTruthy()
    signOut.click()
    await waitSeconds(2)
    expect(await driver.findElement(signIn_jcrew).isDisplayed()).toBeTruthy();
  }
}

export const validateWritingReviews = async () => {
  let currentUrl = await driver.getCurrentUrl();
  await waitSeconds(1)
  await driver.findElement(product_image3).click()
  await waitSeconds(2)
  try {
    const firstReviewer = await driver.findElement(writeTheFirstReview)
    await firstReviewer.click()
    await waitSeconds(1)
    if ((currentUrl.indexOf("https://www.") > -1) || (currentUrl.indexOf("https://factory.") > -1)) {
      console.log("Production Urls , not supposed to submit reviews as part of automation")
      expect(await driver.findElement(fiveStarRating).isDisplayed()).toBeTruthy();
    } else if (currentUrl.indexOf("https://or.") > -1) {
      console.log("gold environment")
      await writeNewReview(currentUrl)
    }
  } catch (err) {
    console.log("Not a first review, so reviews exists")
    if (await driver.findElement(ratingsSection).isDisplayed()) {
      console.log("Reviews are displaying")
      await driver.executeScript('window.scrollTo(0, 800)')
      await waitSeconds(2)
      await driver.findElement(ratingSummary).click();
      await waitSeconds(2)
      await writeNewReview(currentUrl)
    }
  }
}

export const writeNewReview = async (currentUrl) => {
  console.log("writing the review")
  await driver.findElement(fiveStarRating).click()
  await waitSeconds(1);
  await driver.findElement(ratingQuality).click()
  await waitSeconds(1);
  await driver.findElement(fitToSize).click()
  await waitSeconds(1);
  await driver.findElement(ratingFieldTitle).sendKeys("Automation testing")
  await waitSeconds(1);
  await driver.findElement(ratingFieldReview).sendKeys("hi this is automation tester testing write review functionality")
  await waitSeconds(1);
  await driver.findElement(ratingFieldPurchaseId).sendKeys("small")
  await waitSeconds(1);
  await driver.findElement(userNickName).sendKeys("Automation")
  await driver.findElement(userEmailId).sendKeys("autoredeem@gmail.com")
  await driver.findElement(ratingFieldPreview).click()
  await waitSeconds(3);
  await driver.executeScript('window.scrollTo(0, -100)')
  await waitSeconds(1);
  const previewHeader = await driver.findElement(ratingFieldPreviewHeader);
  expect(previewHeader).toBeTruthy()
  console.log("Preview your review header is displaying")
  if (currentUrl.indexOf("https://or.") > -1) {
    const submit = await driver.findElement(submitReview_gold);
    expect(submit).toBeTruthy()
    submit.click()
    await waitSeconds(3);
    const thankyouText = await driver.findElement(submitReview_thanku)
    expect(thankyouText).toBeTruthy()
  }
  else {
    console.log("Reviews are displaying")
   /* await driver.findElement(reviewsDisplay).click();
    await waitSeconds(2)*/
  }
}