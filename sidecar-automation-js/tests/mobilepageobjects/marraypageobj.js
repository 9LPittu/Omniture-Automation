import { driver, defaultTimeout } from '../helpersMobile';
import { waitSeconds } from '../util/MobileMethods';

const { Builder, By, Key } = require('selenium-webdriver')

//Factory
const contextMenu_Factory = By.xpath("//span[text()='menu']");
const menCat_Factory = By.xpath("(//a[@data-department='men'])[1]");
const newArrivals_Factory = By.xpath("(//a[@data-department='new arrivals'])[2]");

//Jcrew
const contextMenu_Jcrew = By.className("nc-mobile-nav__button hamburger");
const womenCat_Jcrew = By.xpath("//li[@class='hamburger-item']/h2[text()='Women']");
const newArrivals_Jcrew = By.xpath("//li[text()='New Arrivals']");

const arraypage = By.xpath("//div[@class='product__list']");
const productimage = By.xpath("(//div[@class='c-product__photos'])[1]");
const producttitle = By.xpath("(//span[contains(@class,'tile__detail--name')])[1]");
const productprice = By.xpath("(//span[contains(@class,'tile__detail--price')])[1]");
const colorSwatchProduct = By.xpath("//span[text()='available in']/../span[@class='tile__detail tile__detail--name']");
const colorSwatch = By.xpath("(//img[@class='colors-list__image'])[2]");
const customersAlsoLove = By.xpath("//h3[text()='Customers Also Love']");
const productRecommendations = By.xpath("//li[contains(@class,'c-product-recommendations-tile')]");
const alsoIn = By.xpath("(//span[@class='tile__detail--alsoin']/../../a)[1]");
const variationsList = By.className("variations-list-wrap");
const filter = By.xpath("//button[text()='Filter']");
const sortBy = By.xpath("//span[text()='Sort By']");
const lowToHigh = By.xpath("//li[text()='Price: Low to High']");
const sortResult = By.xpath("//span[text()='Price: Low to High']");
const filterBy = By.xpath("//h5[text()='Filter By']");
const category = By.xpath("//span[text()='Category']");
const categoryOption = By.xpath("(//span[@class='refinement--label__checkbox'])[1]");
const selectedOption = By.xpath("//div[text()='1 selected']");
const clearAll = By.id("btn__c-filters--clear");
const done = By.xpath("//button[text()='Done']");
const justIn = By.xpath("//div[@class='c-filters__breadcrumb btn btn--round is-capitalized']/span[1]");
const clearFilteredOption = By.xpath("//span[text()='Clear All']");
const topRated = By.xpath("//span[text()='TOP RATED']/../../../a");
const shopTheLookHeader = By.xpath("//h3[text()='Shop The Look']");
const shopTheLookItems = By.xpath("(//li/figure/img)[1]");
const productColor = By.xpath("//ul[@class='product__colors colors-list']//li[1]");
const productSize = By.xpath("//div[@class='c-sizes-list']");
const addToBag = By.xpath("//button[text()='Add to Bag']");
const addToWhishlist = By.xpath("//span[text()='Add To Wishlist']");
const productImage1 = By.xpath("(//div[@class='c-product-tile__details js-product-tile__details'])[1]");
const colorElement = By.xpath("(//dt[@class='product__label'])[1]");
const product1 = By.xpath("(//a[@class='product-tile__link']/img)[1]");
const shopTheLookItem1 = By.xpath("(//li[contains(@class,'js-styled-with-pdp styled-with')])[1]");
const sizesList = By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]");
const addToBagBtn = By.id("btn__add-to-bag-wide");
const addToWishlistBtn = By.id("btn__wishlist-wide");
const emailTxt = By.xpath("//input[@type='email']");
const passwordTxt = By.xpath("//input[@type='password']");
const signInLink = By.xpath("//button[text()='Sign In Here']");
const userPanel_factory = By.xpath("//*[@id='c-header__userpanelrecognized']");
const myWishList = By.xpath("//a[contains(text(),'My Wishlist')]");
const myAccountLink = By.xpath("//button[@class='hamburger-item']/h3");
const wishListLink = By.xpath("//li[text()='Wishlist']");
const wishListItemsList = By.xpath("(//img[@class='item-image notranslate_alt'])[1]");
const productSizesList = By.xpath("//*[@id='c-product__sizes']/div/button");
const sizesListTxt = By.xpath("//*[@id='page__size-charts']/div/div/div[3]/h3");
const productDesc = By.xpath("//*[@id='c-product__description']/div/div/div[1]/span");
const colorsList = By.xpath("(//img[@class='colors-list__image'])[1]");

//Review page
const mensDept = By.xpath("//a[@data-department='men']");
const emailPopup = By.xpath("//*[@id='global__email-capture']/section/div[3]/span");
const productTitleLink = By.xpath("(//a[@class='product-tile__link']/img)[2]");
const cartSize = By.css(".js-cart-size");
const promoCodeLink = By.xpath("//h3[text()='Have a promo code?']");
const promoTxt = By.xpath("//input[@data-textboxid='promoCode']");
const promoApplyBtn = By.id("promoApply");
const promoSubmitBtn = By.className("button-general button-submit-bg");
const loginUserTxt = By.xpath("//*[@id='loginUser']");
const loginPwdTxt = By.xpath("//*[@id='loginPassword']");
const signInAndCheckout = By.xpath("//a[text()='Sign In & Check Out']");
const mergeCart = By.xpath("//*[@id='mergedCartActionTop']/a[1]");
const billingCard1 = By.xpath("//div[@class='payment-method first-card same-billing last']");
const billingAddress1 = By.xpath("//div[@class='billing-address notranslate']");
const shippingAddress1 = By.xpath("//div[@class='shipping-address notranslate']");
const taxSummary = By.xpath("//li[@class='summary-item clearfix']");
const giftSummary = By.xpath("//div[@id='gifting-details']/h2/a");
const includeGiftOption = By.xpath("//input[@id='includesGifts']");
const continueBtn = By.xpath("//a[@id='main__button-continue-old']");
const submitBtn = By.xpath("//a[@id='billing-options-submit']");
const giftReceiptTxt = By.xpath("//span[text()='Gift Receipt']");

const writeReviewIntro = By.xpath("//p[@class='intro']");
const ratingSummaryLink = By.xpath("//*[@id='BVRRRatingSummaryLinkWriteFirstID']/span[1]");
const displayContentNoReviews = By.xpath("//*[@id='BVRRDisplayContentNoReviewsID']/a");
const fiveStarRating = By.xpath("//*[@id='star_link_rating_5']");
const ratingStyleLink = By.xpath("//*[@id='star_link_rating_Style_5']");
const ratingQualityLink = By.xpath("//*[@id='star_link_rating_Quality_5']");
const doRecommend = By.xpath("//*[@id='BVFieldRecommendYesID']");
const ratingTitle = By.xpath("//*[@id='BVFieldTitleID']");
const ratingReviewTxt = By.xpath("//*[@id='BVFieldReviewtextID']");
const ratingFieldNickname = By.xpath("(//*[@id='BVFieldUsernicknameID'])[1]");
const ratingUserEmail = By.xpath("//*[@id='BVFieldUseremailID']");
const ratingSubmitBtnPreview = By.xpath("//*[@id='BVButtonPreviewID']/button");
const ratingSubmitBtn = By.xpath("//*[@id='BVButtonSubmitID']/button");
const ratingSumbission = By.xpath("//*[@id='BVSubmissionContainer']/div[1]/div[1]/span/span");
const signInPageLink = By.xpath("(//*[@id='page__signin']//button)[2]");
const myDetailsLink = By.xpath("//span[text()='My Details']");
const myWishListLink = By.xpath("//*[@id='nav__ul']/li[text()='My Wishlist']");

export const validateAyyayPage = async () => {
  expect(driver.findElement(arraypage)).toBeTruthy();
  expect(driver.findElement(productimage)).toBeTruthy();
  expect(driver.findElement(producttitle)).toBeTruthy();
  expect(driver.findElement(productprice)).toBeTruthy();
};

export const verifyColorSwatch = async () => {
  expect(driver.findElement(colorSwatchProduct)).toBeTruthy();
  await driver.findElement(colorSwatchProduct).click();
  await waitSeconds(2)
  await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(colorSwatch));
  await waitSeconds(3)
  expect(driver.findElement(colorSwatch)).toBeTruthy();
};

export const verifyProductRecommendations = async () => {
  await driver.findElement(producttitle).click();
  await driver.executeScript('window.scrollTo(0, 5000)');
  await waitSeconds(3)
  await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(customersAlsoLove));
  await waitSeconds(3)
  expect(customersAlsoLove).toBeTruthy();
  expect(driver.findElement(productRecommendations)).toBeTruthy();
};

export const verifyExtendedSize = async () => {
  // await driver.actions().mouseMove(await driver.findElement(dept_mens_factory)).perform();
  await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(alsoIn));
  await waitSeconds(1);
  expect(await driver.findElement(alsoIn)).toBeTruthy();
  await driver.findElement(alsoIn).click();
  await waitSeconds(1);
  await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(variationsList));
  expect(await driver.findElement(variationsList)).toBeTruthy();
};

export const verifyFilterAndSortFunction = async () => {
  expect(await driver.findElement(filter)).toBeTruthy();
  expect(await driver.findElement(sortBy)).toBeTruthy();
  console.log("Verifying sort by functionality")
  await driver.findElement(sortBy).click();
  await waitSeconds(2)
  expect(await driver.findElement(lowToHigh)).toBeTruthy();
  await driver.findElement(lowToHigh).click();
  await waitSeconds(2)
  expect(await driver.findElement(sortResult)).toBeTruthy();
  console.log("User is able to sort the items")
  await driver.findElement(filter).click();
  await waitSeconds(2)
  expect(await driver.findElement(filterBy)).toBeTruthy();
  expect(await driver.findElement(category)).toBeTruthy();
  await driver.findElement(category).click();
  await waitSeconds(2);
  await driver.findElement(categoryOption).click();
  await waitSeconds(5);
  expect(await driver.findElement(selectedOption)).toBeTruthy();
  expect(await driver.findElement(clearAll)).toBeTruthy();
  await driver.findElement(clearAll).click();
  await waitSeconds(2)
  await driver.findElement(categoryOption).click();
  await waitSeconds(2)
  await driver.findElement(done).click();
  await waitSeconds(2)
  expect(await driver.findElement(justIn)).toBeTruthy();
  expect(await driver.findElement(clearFilteredOption)).toBeTruthy()
};

export const verifyShopTheLook = async () => {
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
    console.log("for Factory, Shop the look is unavailable")
  }
  else {
    await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(topRated));
    await waitSeconds(2);
    expect(await driver.findElement(topRated)).toBeTruthy();
    await driver.findElement(topRated).click();
    await waitSeconds(2);
    await driver.executeScript('window.scrollTo(0, 1000)');
    await waitSeconds(2);
    await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(shopTheLookHeader));
    await waitSeconds(2);
    await driver.executeScript('window.scrollTo(0, 200)');
    await waitSeconds(3);
    expect(await driver.findElement(shopTheLookItems)).toBeTruthy();
    await driver.findElement(shopTheLookItems).click();
    await waitSeconds(1);
    await driver.executeScript('window.scrollTo(0, 300)');
    await waitSeconds(2);
    expect(await driver.findElement(productColor)).toBeTruthy();
    expect(await driver.findElement(productSize)).toBeTruthy();
    await driver.executeScript('window.scrollTo(0, 300)');
    await waitSeconds(2);
    expect(await driver.findElement(addToBag)).toBeTruthy();
    expect(await driver.findElement(addToWhishlist)).toBeTruthy();
  }
};

// for selecting the Category
export const selectCategory = async () => {
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
    // Menu icon
    await driver.findElement(contextMenu_Factory).click();
    await waitSeconds(2)
    await driver.findElement(menCat_Factory).click();
    await waitSeconds(2)
    await driver.findElement(newArrivals_Factory).click();
    await waitSeconds(2)
  } else {
    // Menu icon
    await driver.findElement(contextMenu_Jcrew).click();
    await waitSeconds(2)
    // Category link
    await driver.findElement(womenCat_Jcrew).click();
    await waitSeconds(2)
    // New arrivals
    await driver.findElement(newArrivals_Jcrew).click();
    await waitSeconds(2)
  }
}

// Select Item and add it to Bag
export const selectItemAddToBag = async () => {
  await waitSeconds(2)
  await driver.findElement(productImage1).click()
  await waitSeconds(1)
  await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(colorElement));
  await waitSeconds(3)
  const size = await driver.findElement(sizesList);
  await size.click()
  await waitSeconds(1)
  await driver.findElement(addToBagBtn).click()
  await waitSeconds(3)
};

// Verify Shopping bag
export const verifyBag = async () => {
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
    await waitSeconds(1)
    await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.css(".js-cart-size")));
    await waitSeconds(3)
    const itemsF = await driver.findElement(By.xpath("//*[@id='js-header__cart']/span[2]/span")).getText();
    expect(itemsF).toBeTruthy()
    console.log("numbers of items in the Bag are.. " + itemsF)
    await driver.executeScript("arguments[0].click();", driver.findElement(By.css(".js-cart-size")))
    await waitSeconds(2)
    await driver.findElement(By.css(".js-cart-size")).click()
  } else {
    // clicking on Bag icon
    await waitSeconds(3)
    await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//h1[@id='product-name__p']")));
    await waitSeconds(3)
    let bagIcon = await driver.findElement(By.xpath("//div[@class='nc-nav__bag-tab__icon']"))
    expect(bagIcon).toBeTruthy()
    // item count on shopping bag
    const items = await driver.findElement(By.xpath("//div[@class='nc-nav__bag-tab__count']")).getText();
    expect(items).toBeTruthy()
    //let bagCount = items.get ()
    console.log("numbers of items in the Bag are.. " + items)
    await bagIcon.click()
    await waitSeconds(2)
  }
};

export const goToGiftCardPage = async () => {
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
    await driver.executeScript('window.scrollTo(0, 5000)')
    await waitSeconds(1)
    await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//h6[text()='Contact Us']")));
    await waitSeconds(1)
    await driver.findElement(By.xpath("//h6[text()='Let Us Help You']")).click()
    await waitSeconds(1)
    await driver.findElement(By.xpath("//div[text()='The J.Crew Gift Card']")).click()
    await waitSeconds(1)
  } else {
    // Menu icon
    await driver.findElement(contextMenu_Jcrew).click()
    await waitSeconds(1)
    await driver.executeScript('window.scrollTo(0, 5000)')
    await waitSeconds(1)
    const seeAllTopics = await driver.findElement(By.xpath("//li[text()='See all help topics']"))
    await driver.executeScript("arguments[0].scrollIntoView(true);", seeAllTopics);
    await waitSeconds(1)
    seeAllTopics.click()
    await waitSeconds(1)
    const giftCard = await driver.findElement(By.xpath("//a[text()='J.CREW GIFT CARD']"));
    expect(giftCard).toBeTruthy()
    giftCard.click()
    await waitSeconds(1)
  }
  try {
    await driver.findElement(By.xpath("//div[@class='email-capture--close modal-capture--close js-email-capture--close']")).then(emailCapture => {
      emailCapture.click()
      driver.sleep(1000)
    })
  } catch (err) { }

};

export const selectSaleItems = async () => {
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
    await waitSeconds(2)
    await driver.findElement(By.xpath("//span[text()='menu']")).click()
    await waitSeconds(1)
    await driver.findElement(By.xpath("//a[@data-department='clearance']")).click()
    await waitSeconds(2)
    await driver.findElement(By.xpath("//a[text()='men']")).click()
    await waitSeconds(2)
  } else {
    await waitSeconds(5)
    // Menu icon
    await driver.findElement(contextMenu_Jcrew).click()
    await waitSeconds(2)
    // Category link
    await driver.findElement(By.xpath("(//li[@class='hamburger-item'])[6]")).click()
    await waitSeconds(1)
    await driver.findElement(By.xpath("(//div[@class='c-sale-recommendation-image'])[2]")).click()
    await waitSeconds(1)
    // New arrival  await driver.findElement(By.xpath("//li[text()='New Arrivals']")).click()
  }
  try {
    await driver.findElement(emailPopup).then(emailCapture => {
      console.log("Email capture page")
      emailCapture.click()
      driver.sleep(3000)
    })
  } catch (err) { }
  await waitSeconds(2)
};

export const selectSuits = async () => {
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
    console.log(">>>.. inside factory" + currentUrl.indexOf("factory.jcrew.com"))
    await driver.findElement(By.xpath("//span[text()='menu']")).click()
    await waitSeconds(1)
    await driver.findElement(mensDept).click()
    await waitSeconds(1)
    await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//a[@data-department='thompson suits & blazers']")));
    await waitSeconds(1)
    await driver.findElement(By.xpath("//a[@data-department='thompson suits & blazers']")).click()
    await waitSeconds(1)
  } else {
    await driver.findElement(contextMenu_Jcrew).click()
    await waitSeconds(2)
    // Category link
    await driver.findElement(By.xpath("(//li[@class='hamburger-item'])[2]")).click()
    await waitSeconds(4)
    await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//li[text()='suits & tuxedos']")));
    await waitSeconds(1)
    driver.findElement(By.xpath("//li[text()='suits & tuxedos']")).click()
    await waitSeconds(1)
  }
  try {
    await driver.findElement(emailPopup).then(closeIcon => {
      closeIcon.click()
      driver.sleep(1000)
    })
  } catch (err) { }
};

export const selectExtendSizeCategory = async () => {
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
    await waitSeconds(6)
    await driver.findElement(By.xpath("//span[text()='menu']")).click()
    await waitSeconds(3)
    await driver.findElement(By.xpath("(//a[@data-department='men'])[1]")).click()
    await waitSeconds(3)
    await driver.findElement(newArrivals_Factory).click()
  } else {
    await waitSeconds(5)
    // Menu icon
    await driver.findElement(contextMenu_Jcrew).click()
    await waitSeconds(2)
    // Category link
    await driver.findElement(By.xpath("(//li[@class='hamburger-item'])[2]")).click()
    await waitSeconds(2)
    await driver.findElement(By.xpath("//li[text()='All Clothing']")).click()
    await waitSeconds(2)
    await driver.findElement(By.xpath("(//span[@class='tile__detail--alsoin'])[1]")).click()
  }
};

export const addGiftCardToBag = async (giftcardName) => {
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
    //Footer links are not displaying
  } else {
    await waitSeconds(2)
    // Menu icon
    await driver.findElement(contextMenu_Jcrew).click()
    await waitSeconds(2)
    await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//li[text()='Buy a Gift Card']")));
    await waitSeconds(1)
    await driver.findElement(By.xpath("//li[text()='Buy a Gift Card']")).click();
    await waitSeconds(1)
    await closeIcon()
    await waitSeconds(1)
    if (giftcardName == "classicGiftCard") {
      await driver.findElement(By.xpath("//a[@id='classicGiftCard']/img")).click();
    } else if (giftcardName == "eGiftCard") {
      await driver.findElement(By.xpath("//a[@id='eGiftCard']/img")).click();
    }
    await waitSeconds(2)
    await driver.findElement(By.xpath("//*[@id='amount25']")).click()
    await driver.findElement(By.xpath("//*[@id='senderName']")).sendKeys("test")
    await driver.findElement(By.xpath("//*[@id='RecipientName']")).sendKeys("recipient test")
    await driver.findElement(By.xpath("//*[@id='text1Message']")).sendKeys("line 1")
    await driver.findElement(By.xpath("//*[@id='text2Message']")).sendKeys("line 2")
    //*[@id="submitClassic"]
    await driver.findElement(By.id("submitClassic")).click()
    await waitSeconds(1)
    await driver.findElement(By.className("nc-mobile-nav__button bag")).click()
    await waitSeconds(1)
  }
};

export const addShopTheLookItemToBag = async () => {
  await validateShopTheLookHeader()
  await driver.findElement(sizesList).click()
  await waitSeconds(2)
  await driver.findElement(addToBagBtn).click()
  await waitSeconds(2)
}

export const addShopTheLookItemToWishList = async (username, password) => {
  await validateShopTheLookHeader()
  await driver.findElement(sizesList).click()
  await waitSeconds(2)
  await driver.findElement(addToWishlistBtn).click()
  await waitSeconds(6)
  await driver.findElement(emailTxt).sendKeys(username)
  await driver.findElement(passwordTxt).sendKeys(password)
  await waitSeconds(2)
  await driver.findElement(signInLink).click()
  await waitSeconds(10)
  let currentUrl = await driver.getCurrentUrl()
  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
    const userPannel = await driver.findElement(userPanel_factory)
    expect(userPannel).toBeTruthy()
    await userPannel.click()
    await waitSeconds(3)
    await driver.findElement(myWishList).click()
  } else {
    await driver.findElement(contextMenu_Jcrew).click()
    await waitSeconds(2)
    const myaccount = await driver.findElement(myAccountLink)
    expect(myaccount).toBeTruthy()
    await waitSeconds(1)
    // Rewards
    await myaccount.click()
    await waitSeconds(1)
    await driver.findElement(wishListLink).click()
  }
  await waitSeconds(3)
  const wishlistitems = driver.findElement(wishListItemsList)
  await driver.wait(until.elementIsVisible(wishlistitems), 5000)
  expect(wishlistitems).toBeTruthy()
}

export const verifySizeCartInShopTheLook = async () => {
  await validateShopTheLookHeader()
  await driver.findElement(productSizesList).click()
  await waitSeconds(2)
  const size = await driver.findElement(sizesListTxt)
  expect(size).toBeTruthy()
  console.log("size Chart is available for product")
}

export const validateShopTheLookHeader = async () => {
  await driver.findElement(product1).click()
  await waitSeconds(2)
  await driver.executeScript('window.scrollTo(0, 800)')
  await waitSeconds(2)
  await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(shopTheLookHeader));
  await waitSeconds(1)
  const shopthelookElement = await driver.findElement(shopTheLookItem1);
  expect(shopthelookElement).toBeTruthy()
  await shopthelookElement.click()
  await waitSeconds(2)
}

export const verifyFullDetailsInShopTheLook = async () => {
  await validateShopTheLookHeader()
  await driver.executeScript('window.scrollTo(0, 1000)')
  await waitSeconds(2)
  const size = await driver.findElement(productDesc)
  expect(size).toBeTruthy()
  console.log("Full Details are available for product")
}

export const validateSizesAndColors = async () => {
  await validateShopTheLookHeader()
  // Sizes
  const sizes = await driver.findElement(sizesList).click()
  expect(sizes).toBeTruthy()
  console.log("sizes are available for product")
  await waitSeconds(2)
  // Color swatches
  expect(await driver.findElement(colorsList).isDisplayed()).toBeTruthy();
  console.log('color swatch is Displayed')
}

export const reviewPageValidation = async (username, pwd) => {
  await waitSeconds(1)
  await driver.findElement(contextMenu_Factory).click()
  await waitSeconds(1)
  await driver.findElement(mensDept).click()
  await waitSeconds(2)
  await driver.findElement(newArrivals_Factory).click()
  await waitSeconds(1)
  try {
    await driver.findElement(emailPopup).click()  // close the popups
  } catch (err) { }
  await waitSeconds(2)
  await driver.findElement(productTitleLink).click()
  await waitSeconds(1)
  await driver.executeScript('window.scrollTo(0, 700)')
  await waitSeconds(1)
  await driver.findElement(sizesList).click()
  await waitSeconds(1)
  await driver.findElement(addToBagBtn).click()
  await waitSeconds(1)
  await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(cartSize));
  await waitSeconds(1)
  await driver.executeScript("arguments[0].click();", driver.findElement(cartSize))
  await waitSeconds(2)
  await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(promoCodeLink))
  await waitSeconds(3)
  await driver.findElement(promoCodeLink).click()
  await waitSeconds(3)
  await driver.findElement(promoTxt).sendKeys("TEST-10P")
  await waitSeconds(3)
  await driver.findElement(promoApplyBtn).click()
  await waitSeconds(3)
  await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(promoSubmitBtn))
  await waitSeconds(3)
  await driver.findElement(promoSubmitBtn).click()
  await waitSeconds(6)
  await driver.findElement(loginUserTxt).sendKeys(username)
  await driver.findElement(loginPwdTxt).sendKeys(pwd)
  await waitSeconds(2)
  await driver.findElement(signInAndCheckout).click()
  await waitSeconds(3)
  try {
    await driver.findElement(mergeCart).then(mergebutton => {
      mergebutton.click()
      driver.sleep(3000)
    })
  } catch (err) { }
  driver.sleep(3000)
  await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(billingCard1))
  driver.sleep(3000)
  const paymentDetails = await driver.findElement(billingCard1)
  expect(paymentDetails).toBeTruthy()
  await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(billingAddress1))
  driver.sleep(3000)
  const billingAddress = await driver.findElement(billingAddress1)
  expect(billingAddress).toBeTruthy()
  await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(shippingAddress1))
  driver.sleep(3000)
  const shippingAddress = await driver.findElement(shippingAddress1)
  expect(shippingAddress).toBeTruthy()
  await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(taxSummary))
  driver.sleep(3000)
  const taxDetails = await driver.findElement(taxSummary)
  expect(taxDetails).toBeTruthy()
  await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(giftSummary))
  await waitSeconds(2)
  await driver.findElement(giftSummary).click()
  await waitSeconds(2)
  await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(includeGiftOption))
  await waitSeconds(2)
  await driver.findElement(includeGiftOption).click()
  await waitSeconds(2)
  await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(continueBtn))
  await waitSeconds(2)
  await driver.findElement(continueBtn).click()
  await waitSeconds(2)
  await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(submitBtn))
  await waitSeconds(2)
  await driver.findElement(submitBtn).click()
  await waitSeconds(4)
  await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(giftReceiptTxt))
  await waitSeconds(2)
  const giftOption = await driver.findElement(giftReceiptTxt)
  expect(giftOption).toBeTruthy()
}

export const writeReviewValidation = async () => {
  await waitSeconds(2)
  await driver.findElement(contextMenu_Factory).click()
  await waitSeconds(1)
  await driver.findElement(mensDept).click()
  await waitSeconds(2);
  await driver.findElement(newArrivals_Factory).click()
  await waitSeconds(2)
  try {
    await driver.findElement(emailPopup).then(closeIcon => {
      closeIcon.click()
      driver.sleep(3000)
    })
  } catch (err) { }

  await driver.findElement(product1).click()
  await waitSeconds(2)
  await driver.executeScript('window.scrollTo(0, 700)')
  await waitSeconds(2)
  await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(writeReviewIntro));
  await waitSeconds(2)
  // clicking on write  review exapnding
  await driver.findElement(ratingSummaryLink).click()
  // clicking on write  review link
  await driver.findElement(displayContentNoReviews).click()
  await waitSeconds(2)
  await driver.executeScript('window.scrollTo(0, 200)')
  // Overall rating selection
  await driver.findElement(fiveStarRating).click()
  await driver.executeScript('window.scrollTo(0, 200)')
  // Style
  await driver.findElement(ratingStyleLink).click()
  // Quality
  await driver.findElement(ratingQualityLink).click()
  // Recommand
  await driver.findElement(doRecommend).click()
  // Review Title
  await driver.findElement(ratingTitle).sendKeys("Good Product")
  // Review
  await driver.findElement(ratingReviewTxt).sendKeys("This is nice Product and Quality is good. This is nice Product and Quality is good. nice Product and Quality is good.")
  // Nickname
  await driver.findElement(ratingFieldNickname).sendKeys("John")
  // Email
  await driver.findElement(ratingUserEmail).sendKeys("John@example.org")
  // Preview button
  await driver.findElement(ratingSubmitBtnPreview).click()
  // Submit button
  await driver.findElement(ratingSubmitBtn).click()
  // Thank you text
  const thankU = await driver.findElement(ratingSumbission)
  expect(thankU).toBeTruthy()
}

export const verifyWishListPage = async (username, pwd) => {
  await waitSeconds(2)
  await driver.findElement(productTitleLink).click()
  await waitSeconds(1)
  await driver.executeScript('window.scrollTo(0, 700)')
  await waitSeconds(1)
  await driver.findElement(sizesList).click()
  await waitSeconds(1)
  await driver.findElement(addToWishlistBtn).click()
  await waitSeconds(6)

  await driver.findElement(emailTxt).sendKeys(username)
  await driver.findElement(passwordTxt).sendKeys(pwd)
  await waitSeconds(2)
  await driver.findElement(signInPageLink).click() // Factory
  await waitSeconds(10)
  const userPannel = await driver.findElement(userPanel_factory)
  expect(userPannel).toBeTruthy()
  await userPannel.click()
  await waitSeconds(3)
  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
    await driver.findElement(myWishList).click()
  } else {
    await driver.findElement(myDetailsLink).click()
    await waitSeconds(3)
    await driver.findElement(myWishListLink).click()
  }
  await waitSeconds(3)
  const wishlistitems = driver.findElement(wishListItemsList)
  await driver.wait(until.elementIsVisible(wishlistitems), 5000)
  expect(wishlistitems).toBeTruthy()
}

export const verifyGuestWishlist = async () => {
  await waitSeconds(2)
  await driver.findElement(productTitleLink).click()
  await waitSeconds(1)
  await driver.executeScript('window.scrollTo(0, 600)')
  await waitSeconds(1)
  await driver.findElement(sizesList).click()
  await waitSeconds(1)
  await driver.findElement(addToWishlistBtn).click()
  await waitSeconds(6)
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.includes("/r/login")) {
    console.log("User navigates to login page")
  }
}